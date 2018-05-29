import java.util.ArrayList;

public class Main {
	
	public static void main(String[] args) { 
		WorkingFileReader workingFileReader = new WorkingFileReader(); 
		TopFileReader topFileReader = new TopFileReader(); 
		LasFileReader lasFileReader = new LasFileReader(); 
		DataWriter dataWriter = new DataWriter();

		int workingWellRow = 0; 
		int topRow = 0; 

		try {
			ArrayList<TopData> topDataList = topFileReader.readFile();
			WorkingFileData workingData = workingFileReader.readFile();

			
			while (workingWellRow < topDataList.size()) {
				
				if (topDataList.get(topRow).getUwi().equals(workingData.getRow(workingWellRow).substring(18, 37))) {
					LasData lasData = lasFileReader.readFile(topDataList.get(topRow), false);
//					if (!dataWriter.getCol(workingData.getRow(workingWellRow), 13).equals("Vertical"))
//					{
//						lasData = lasFileReader.readFile(topDataList.get(topRow), true);
//					}
//					else {
//						lasData = lasFileReader.readFile(topDataList.get(topRow), false);
//					}
					dataWriter.formatData(workingData.getRow(workingWellRow), lasData, topDataList.get(topRow));
					System.out.println(topDataList.get(topRow).getUwi());
					workingWellRow++; 
					topRow++; 
				}
				else {
					workingWellRow++; 
				}
			}
		}
		catch(Exception e) { 
			e.printStackTrace();
		}
		
	}
}
