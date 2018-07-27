import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Controller {
	
	private LasFileReader lasFileReader;
	private ArrayList<MnemonicData> mnemonicList;
	private DataWriter dataWriter;
	private ArrayList<FormattedData> formattedDataList;
	private ArrayList<TopData> topDataList;
	private ArrayList<TopData> secondaryTopDataList;
	private WorkingFileData workingData;
	private UserInput userInput; 
	private OutputData outputData;
	private boolean lasFileExists; 
	private boolean meridianExists;
	private WriteToCSV writer;
	
	private int wellsCompleted = 0; 
	private int workingWellRow = 0; 
	private int topRow = 0;
	
	public int getWellsCompleted() { return wellsCompleted; } 
	
	public ArrayList<MnemonicData> getMnemonicList() { return mnemonicList; }
	
	public DataWriter getDataWriter() { return dataWriter; }
	
	public ArrayList<FormattedData> getFormattedDataList() { return formattedDataList; }
	
	public UserInput getUserInput() { return userInput; }
	
	public int getWorkingWellRow() { return workingWellRow; }
	
	public int getSize() { return workingData.getSize(); }
	
	public OutputData getOutputData() { return outputData; }
	
	public String getCurrentUwi() {
		if (workingWellRow < workingData.getSize()) {
			return workingData.getRow(workingWellRow).substring(17, 37); 
		}
		return "";
	}
	
	public Controller(UserInput user) {
		userInput = user;
		WorkingFileReader workingFileReader = new WorkingFileReader(userInput.getNwSortUwi(), userInput.getSeSortUwi(), userInput.getWorkingFilePath()); 
		TopFileReader topFileReader = new TopFileReader(userInput); 
		lasFileReader = new LasFileReader(userInput.getLasFilePath()); 
		MoreMnemonics mnemonics = new MoreMnemonics();
		mnemonicList = mnemonics.readFile();
		dataWriter = new DataWriter(mnemonicList);
		formattedDataList = new ArrayList<FormattedData>(); 
		
		wellsCompleted = 0; 
		workingWellRow = 0; 
		topRow = 0; 
		
		secondaryTopDataList = null;
		if (user.getPrimaryTopFilePath().endsWith("csv")) {
			topDataList = topFileReader.csvReadFile(); 
		}
		else {
			topDataList = topFileReader.readFile();
		}
		if (user.secondaryTopFileExist()) { 
			System.out.println("runningSecondary read file");
			if (user.getSystemTopFilePath().endsWith("csv")) {
				secondaryTopDataList = topFileReader.readSecondaryCsvFile(user.getSystemTopFilePath());
			}
			else {
				secondaryTopDataList = topFileReader.readSecondaryFile(user.getSystemTopFilePath());
			}
		}
		
		if (user.getWorkingFilePath().endsWith("csv")) { 
			workingData = workingFileReader.readCsvFile(); 
		}
		else { 
			workingData = workingFileReader.readFile();
		}
		outputData = new OutputData();
		
		checkIfLasFileExists(); 
		checkIfLasMeridianExists();
	}
	
	public void checkIfLasMeridianExists() { 
		File folder = new File(userInput.getLasFilePath());
		File[] listOfFiles = folder.listFiles(); 
		
		for (File file : listOfFiles) { 
			if (file.getName().startsWith("W")) { 
				meridianExists = true;
				return; 
			}
		}
		meridianExists = false; 
	}
	
	public void checkIfLasFileExists() { 
		File folder = new File(userInput.getLasFilePath());
		File[] listOfFiles = folder.listFiles();
		
		for (File file : listOfFiles) {
			if (file.getName().endsWith(".las")) {
				lasFileExists = true; 
				return; 
			}
		}
		lasFileExists = false;
	}
	
	public boolean formatWellData() { 
		try {
			String topUwi = topDataList.get(topRow).getUwi(); 
				
			String workingUwi = workingData.getRow(workingWellRow).substring(17, 37);
			if (topUwi.startsWith("0")) {
				topUwi = "1" + topUwi;
			}
			if (topUwi.equals(workingUwi)) {
				LasData lasData = null;
				if (!workingData.getRow(workingWellRow).split(",")[workingData.getTypeCol()].equals("Vertical"))
				{
					lasData = lasFileReader.readFile(topDataList.get(topRow), true, lasFileExists, meridianExists);
				}
				else {
					lasData = lasFileReader.readFile(topDataList.get(topRow), false, lasFileExists, meridianExists);
				}
				
				if (lasData != null) { 
					FormattedData formattedData = null;
					if (secondaryTopDataList == null) { 
						formattedData = dataWriter.formatData(workingData.getHeader(), workingData.getRow(workingWellRow), lasData, topDataList.get(topRow));
					}
					else { 
						formattedData = dataWriter.secondaryFormatData(workingData.getHeader(), workingData.getRow(workingWellRow), lasData, topDataList.get(topRow),secondaryTopDataList);
					}
					System.out.println(topDataList.get(topRow).getUwi());
					outputData.addSuccess(topDataList.get(topRow).getUwi());
					formattedDataList.add(formattedData);
					wellsCompleted++;
				}
				else { 
					System.err.println(topDataList.get(topRow).getUwi() + " Error in lasfile");
					outputData.addLasError(topDataList.get(topRow).getUwi());
				}
				workingWellRow++; 
				topRow++; 
			}
			else {
				if (userInput.fullSortTownship(topUwi) < userInput.fullSortTownship(workingUwi)) {
					System.err.println(topUwi + " Does not have a matching GWI");
					outputData.addGwiError(topUwi);
					topRow++;
				}
				else {
					System.err.println(workingUwi + " Does not have a Top");
					outputData.addTopError(workingUwi);
					workingWellRow++; 
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return true; 
		}
		return false;
	}
	
	public boolean writeToFile() {
		writer = new WriteToCSV(formattedDataList, userInput);
		try { 
			writer.saveToResourceFile();
			if (userInput.getOutputFilePath().equals("")) {
				return true;
			}
			writer.saveParameters(); 
			if (writer.write(workingData.getHeader(), mnemonicList, userInput.secondaryTopFileExist(), formattedDataList)) {
				return true;
			}
			if (!userInput.getOutputFilePath().endsWith(".csv")) {
				String appendFile = userInput.getOutputFilePath() + "\\" + userInput.getOutputFileName() + "\\" + userInput.getOutputFileName() + ".csv";
				userInput.setOutputFilePath(appendFile);
			}
			formattedDataList.clear();
			System.out.println("wells completed: " + wellsCompleted);
			return false; 
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return true; 
	}
	
	public boolean appendToFile() {
		if (writer.write(workingData.getHeader(), mnemonicList, userInput.secondaryTopFileExist(), formattedDataList)) {
			return true;
		}
		formattedDataList.clear();
		System.out.println("wells completed: " + wellsCompleted);
		return false; 
	}
}
