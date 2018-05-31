import java.util.ArrayList;

public class Main {
	
	public static void main(String[] args) { 
		
		UserInput userInput = new UserInput();
		userInput.readInput();
		
		WorkingFileReader workingFileReader = new WorkingFileReader(); 
		TopFileReader topFileReader = new TopFileReader(userInput.getTopForm(), userInput.getBottomForm()
														, userInput .getTownship(), userInput.getFormBuffer()); 
		LasFileReader lasFileReader = new LasFileReader(); 
		Mnemonics mnemonics = new Mnemonics();
		mnemonics.readFile();
		DataWriter dataWriter = new DataWriter(mnemonics);
		ArrayList<FormattedData> formattedDataList = new ArrayList<FormattedData>();

		int workingWellRow = 0; 
		int topRow = 0; 

		try {
			ArrayList<TopData> topDataList = topFileReader.readFile();
			WorkingFileData workingData = workingFileReader.readFile();
			
			while (workingWellRow < workingData.getSize()) {
				
				if (topDataList.get(topRow).getUwi().equals(workingData.getRow(workingWellRow).substring(18, 37))) {
					LasData lasData = null;
					if (!dataWriter.getCol(workingData.getRow(workingWellRow), 13).equals("Vertical"))
					{
						lasData = lasFileReader.readFile(topDataList.get(topRow), true);
					}
					else {
						lasData = lasFileReader.readFile(topDataList.get(topRow), false);
					}
					
					if (lasData != null) { 
						FormattedData formattedData = dataWriter.formatData(workingData.getRow(workingWellRow), lasData, topDataList.get(topRow));
						System.out.println(topDataList.get(topRow).getUwi());
						formattedDataList.add(formattedData);
					}
					else { 
						System.err.println(topDataList.get(topRow).getUwi() + " Error in lasFile");
					}
					workingWellRow++; 
					topRow++; 
				}
				else {
					System.err.println(workingData.getRow(workingWellRow).substring(18, 37) + " Does not have a Top");
					workingWellRow++; 
				}
			}
		}
		catch(Exception e) { 
			e.printStackTrace();
		}
		WriteToCSV writer = new WriteToCSV(formattedDataList);
		writer.write();
		System.out.println("DONE");
		
	}
}
