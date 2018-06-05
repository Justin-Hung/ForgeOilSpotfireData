import java.util.ArrayList;

public class Main {
	
	public static void main(String[] args) { 
		
		UserInput userInput = new UserInput();
		userInput.readInput();
		userInput.display();
		
		WorkingFileReader workingFileReader = new WorkingFileReader(userInput.getNwSortUwi(), userInput.getSeSortUwi()); 
		TopFileReader topFileReader = new TopFileReader(userInput.getFormations(), userInput.getNwSortUwi(), userInput.getSeSortUwi(), userInput.getUpperBuffer(), userInput.getLowerBuffer(), userInput.getFormations().get(0)); 
		LasFileReader lasFileReader = new LasFileReader(); 
		Mnemonics mnemonics = new Mnemonics();
		mnemonics.readFile();
		DataWriter dataWriter = new DataWriter(mnemonics);
		ArrayList<FormattedData> formattedDataList = new ArrayList<FormattedData>();
		
		int wellsCompleted = 0; 
		int workingWellRow = 0; 
		int topRow = 0; 
		
		System.out.println("working.....");
		
		try {
			ArrayList<TopData> topDataList = topFileReader.readFile();
			WorkingFileData workingData = workingFileReader.readFile();
			
			while (workingWellRow < workingData.getSize()) {
				
				String topUwi = topDataList.get(topRow).getUwi(); 
				if (topUwi.startsWith("1")) {
					topUwi = topUwi.substring(1);
				}
				if (topUwi.equals(workingData.getRow(workingWellRow).substring(18, 37))) {
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
						wellsCompleted++;
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
		System.out.println("wells completed: " + wellsCompleted);
		
	}
}
