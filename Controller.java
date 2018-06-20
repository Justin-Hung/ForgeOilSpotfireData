import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Controller {
	
	private LasFileReader lasFileReader;
	private ArrayList<MnemonicData> mnemonicList;
	private DataWriter dataWriter;
	private ArrayList<FormattedData> formattedDataList;
	private ArrayList<TopData> topDataList;
	private WorkingFileData workingData;
	private UserInput userInput; 
	private OutputData outputData;
	
	private int wellsCompleted = 0; 
	private int workingWellRow = 0; 
	private int topRow = 0; 
	
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
		WorkingFileReader workingFileReader = new WorkingFileReader(userInput.getNwSortUwi(), userInput.getSeSortUwi(), userInput.getWorkingfilePath()); 
		TopFileReader topFileReader = new TopFileReader(userInput.getFormations(), userInput.getNwSortUwi(), userInput.getSeSortUwi(), userInput.getUpperBuffer(), userInput.getLowerBuffer()
														, userInput.getTopfilePath()); 
		lasFileReader = new LasFileReader(userInput.getLasfilePath()); 
		MoreMnemonics mnemonics = new MoreMnemonics();
		mnemonicList = mnemonics.readFile();
		dataWriter = new DataWriter(mnemonicList);
		formattedDataList = new ArrayList<FormattedData>();
		
		wellsCompleted = 0; 
		workingWellRow = 0; 
		topRow = 0; 
		
		topDataList = topFileReader.readFile();
		workingData = workingFileReader.readFile();
		outputData = new OutputData();
	}
	
	public void formatWellData() { 
		String topUwi = topDataList.get(topRow).getUwi(); 
		String workingUwi = workingData.getRow(workingWellRow).substring(17, 37);
		if (topUwi.startsWith("0")) {
			topUwi = "1" + topUwi;
		}
		if (topUwi.equals(workingUwi)) {
			LasData lasData = null;
			if (!workingData.getRow(workingWellRow).split(",")[workingData.getTypeRow()].equals("Vertical"))
			{
				lasData = lasFileReader.readFile(topDataList.get(topRow), true);
			}
			else {
				lasData = lasFileReader.readFile(topDataList.get(topRow), false);
			}
			
			if (lasData != null) { 
				FormattedData formattedData = dataWriter.formatData(workingData.getHeader(), workingData.getRow(workingWellRow), lasData, topDataList.get(topRow));
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
	
	public void writeToFile() {
		WriteToCSV writer = new WriteToCSV(formattedDataList, userInput.getOutputfilePath());
		writer.write(workingData.getHeader(), mnemonicList);
		System.out.println("DONE");
		System.out.println("wells completed: " + wellsCompleted);
	}
}
