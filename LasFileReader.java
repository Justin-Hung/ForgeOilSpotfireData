import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
 
public class LasFileReader {
	private String lasFilePath = "C:\\Users\\jhung\\SpotfireDataFiles\\LasFiles\\";
	
	public LasData readFile(TopData topData) { 
		try {
			String lasFile = convertUwi(topData.getUwi()); 
			String fileLocation = lasFilePath + lasFile; 
			String line = null; 
			FileReader fileReader = new FileReader(fileLocation); 
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			LasData lasContainer = new LasData(topData.getUwi()); 
			
			while((line = bufferedReader.readLine()) != null) {
				if (line.startsWith("~A")) {
					lasContainer.addHeader(line);
				}
				if (line.startsWith("      ")) {
					if (Double.parseDouble(line.substring(0, 15)) > topData.getLowerBound() 
							&& Double.parseDouble(line.substring(0, 15)) <= topData.getUpperBound()) {
						lasContainer.addRow(line);
					}
				}
			}   
			bufferedReader.close(); 
			return lasContainer;
		}
		catch (Exception e) {
			return null;
		}
	}
	
	public String convertUwi(String uwi) {
		String fileUwi = uwi.replaceAll("/", "");
		fileUwi = fileUwi.replaceAll("-", "");
		fileUwi = "1" + fileUwi + "0_1000_MD_COMBINED_MERGED.las"; 
		return fileUwi;
	}
}
