import java.util.ArrayList;

public class UnknownWrite {
	private String outputFilePath; 
	private String outputFileName; 
	private ArrayList<UnknownData> unknownDataList; 
	private	ArrayList<LasDescriptionData> descriptionDataList;
	private ArrayList<FormattedUnknown> formattedUnknownList; 
	
	public UnknownWrite(String filePath, String fileName, ArrayList<UnknownData> unknownData, ArrayList<LasDescriptionData> descriptionData) {
		outputFilePath = filePath;
		outputFileName = fileName; 
		unknownDataList = unknownData; 
		descriptionDataList = descriptionData; 
	}
	
	
} 
