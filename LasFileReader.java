import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
 
public class LasFileReader {
	private String lasFilePath = "C:\\Users\\jhung\\LasFiles\\T35R23\\T35R23\\log_files\\";
	
	public LasData readFile(TopData topData, boolean dir) { 
		try {
			String lasFile = convertUwi(topData.getUwi(), dir); 
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
					if (Double.parseDouble(line.substring(0, 15)) >= topData.getLowerBound() 
							&& Double.parseDouble(line.substring(0, 15)) < topData.getUpperBound()) {
						lasContainer.addRow(line);
					}
				}
			}   
			if (topData.getUpperBound() == 1450.1) {
				lasContainer.display();
			}
			bufferedReader.close(); 
			return lasContainer;
		}
		catch (Exception e) {
			return null;
		}
	}
	
	public String convertUwi(String uwi, boolean dir) {
		String fileUwi = uwi.replaceAll("/", "");
		fileUwi = fileUwi.replaceAll("-", "");
		if (dir) { 
			fileUwi = "1" + fileUwi + "0_1000_TVD_COMBINED_MERGED.las"; 
		}
		else { 
			fileUwi = "1" + fileUwi + "0_1000_MD_COMBINED_MERGED.las"; 
		}
		return fileUwi;
	}
}
