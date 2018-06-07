import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
 
public class LasFileReader {

	private String lasFilePath = "C:\\Users\\jhung\\LasFiles\\T34R2W5toT35R3W5\\log_files\\";
	private String lasFile;
	
	public LasData readFile(TopData topData, boolean dir) { 
	
		//topData.displayTop();
		if (topData.getTvDepth().isEmpty()) {
			return null;
		}
		try {
			String bit = null;
			String serviceCo = null;
			lasFile = convertUwi(topData.getUwi(), dir); 
			//System.out.println(lasFile);
			String fileLocation = lasFilePath + lasFile; 
			String line = null; 
			File fileTest = new File(fileLocation);
			if(!fileTest.exists()) { 
				lasFile = lasFile.substring(0, lasFile.indexOf("COMBINED")) + lasFile.substring(lasFile.indexOf("MERGED"));
				//System.out.println(lasFile);
				fileLocation = lasFilePath + lasFile; 
				fileTest = new File(fileLocation);
				if (!fileTest.exists()) {
					return null;
				}
			}
			FileReader fileReader = new FileReader(fileLocation); 
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			LasData lasContainer = new LasData(topData.getUwi()); 

			while((line = bufferedReader.readLine()) != null) {
				if (line.startsWith(" BS  .MM")) {
					bit = line.substring(12,18);
				}
				if (line.startsWith(" BS  .IN")) {
					bit = String.valueOf(Double.parseDouble(line.substring(12,18)) * 25.4);
				}
				if (line.startsWith(" SRVC.")) {
					serviceCo = line.substring(12,49);
				}
				if (line.startsWith("~A")) {
					lasContainer.addHeader(line);
				}
				if (line.startsWith("      ")) {
					if (Double.parseDouble(line.substring(0, 15)) > topData.getLowerBound() 
							&& Double.parseDouble(line.substring(0, 15)) < topData.getUpperBound()) {
						lasContainer.addRow(line + "          buffer              buffer" );
					}
				}
			}   
			
			lasContainer.setServiceCo(serviceCo);
			lasContainer.setBit(bit);
			
			bufferedReader.close(); 
			
			if (lasContainer.isEmpty()) {
				return null;
			}
			lasContainer.formatHeader();
			//lasContainer.display();
			return lasContainer;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String convertUwi(String uwi, boolean dir) {
		String fileUwi = uwi.replaceAll("/", "");
		fileUwi = fileUwi.replaceAll("-", "");
		if (dir) { 
			if (uwi.startsWith("0")) {
				fileUwi = "1" + fileUwi + "0_1000_TVD_COMBINED_MERGED.las"; 
			}
			else {
				fileUwi = fileUwi + "0_1000_TVD_COMBINED_MERGED.las";
			}
		}
		else { 
			if (uwi.startsWith("0")) {
				fileUwi = "1" + fileUwi + "0_1000_MD_COMBINED_MERGED.las"; 
			}
			else {
				fileUwi = fileUwi + "0_1000_MD_COMBINED_MERGED.las"; 
			}
		}
		//System.out.println(fileUwi);
		return fileUwi;
	}
}
