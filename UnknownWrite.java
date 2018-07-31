import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class UnknownWrite {
	private String outputFilePath; 
	private String outputFileName; 
	private ArrayList<UnknownData> unknownDataList; 
	private	ArrayList<LasDescriptionData> descriptionDataList;
	private ArrayList<String> formattedRow; 
	
	public UnknownWrite(String filePath, String fileName, ArrayList<UnknownData> unknownData, ArrayList<LasDescriptionData> descriptionData) {
		outputFilePath = filePath;
		outputFileName = fileName; 
		unknownDataList = unknownData; 
		descriptionDataList = descriptionData; 
		formattedRow = new ArrayList<String>(); 
	}
	
	public void formatLoop() {
		int unknownIndex = 0; 
		for (int i = 0 ; i < descriptionDataList.size() ; i++) {
			if (descriptionDataList.get(i).getUwi().equals(unknownDataList.get(unknownIndex).getUwi())) { 
				format(descriptionDataList.get(i), unknownDataList.get(unknownIndex));
				unknownIndex++; 
			} 
			if (unknownIndex == unknownDataList.size()) { 
				break;
			}
		}
	}
	
	public void format(LasDescriptionData descriptionData, UnknownData unknownData) { 
		for (int i = 0 ; i < unknownData.size() ; i++) {
			for (int j = 0 ; j < descriptionData.size() ; j++) {
				if (unknownData.getUnknownMnemonic(i).equals(descriptionData.getMnemonic(j))) {
					String row = unknownData.getUwi() + "," + unknownData.getUnknownMnemonic(i) + "," 
							+ descriptionData.getDescription(j) + "," + descriptionData.getUnit(j);
					formattedRow.add(row);
				}
			}
		}
	}
	
	public void display() { 
		for (int i = 0 ; i < formattedRow.size() ; i++) {
			System.out.println(formattedRow.get(i));
		}
	}
	
	public void write() throws Exception { 
		String filePath = outputFilePath.substring(0, outputFilePath.lastIndexOf(outputFileName)); 
		filePath += outputFileName + "UnknownMnemonics.csv";
		System.out.println(filePath);
		FileWriter fileWriter = new FileWriter(new File(filePath)); 
		fileWriter.write("Uwi,Unknown Mnemonic,Description,Unit");
		fileWriter.write(System.lineSeparator()); 
		for (int i = 0 ; i < formattedRow.size() ; i++) {
			fileWriter.write(formattedRow.get(i));
			fileWriter.write(System.lineSeparator()); 
		}
		fileWriter.close();
	}
}  
