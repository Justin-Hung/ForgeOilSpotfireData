import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
 
public class LasFileReader {

	private String lasFilePath = "E:\\ForgeOil\\LasFiles\\Bellatrix\\";
	private String lasFile;
	
	public LasFileReader() { 
		
	}
	
	public LasFileReader(String filePath) { 
		lasFilePath = filePath + "\\"; 
	}
	
	public String formatLine(String line) { 
		char[] charArray = line.toCharArray(); 
		StringBuilder builder = new StringBuilder(line);
		char previousChar = ' ';
		int insertOffset = 0; 
		for (int i = 0 ; i < charArray.length ; i++) {
			if (previousChar != ' ' && charArray[i] == '-') {
				builder.insert(i + insertOffset, "  "); 
				insertOffset+=2;
			}
			previousChar = charArray[i];
		}
		return builder.toString();
	}
	
	public LasData readFile(TopData topData, boolean dir, boolean lasFileExists, boolean meridianExists) { 
		System.out.println("UWI: " + topData.getUwi() + " LOWER BOUND: " + topData.getLowerBound() + " UPPER BOUND: " + topData.getUpperBound());
		if (topData.getTvDepth().isEmpty()) {
			return null;
		}
		try {
			boolean usingMdforDir = false;
			String bit = null;
			String serviceCo = null;
			if (lasFileExists) { 
				lasFile = convertUwi(topData.getUwi(), dir); 
			}
			else {
				lasFile = convertUwiWithPath(topData.getUwi(), dir);
			}
			lasFile = checkForLogPath(lasFile); 
			if (meridianExists) {
				lasFile = "W" + topData.getUwi().substring(17,18) + "\\" + lasFile; 
			}
			String fileLocation = lasFilePath + lasFile; 
			String line = null; 
			File fileTest = new File(fileLocation);
			if(!fileTest.exists()) { 
				lasFile = lasFile.substring(0, lasFile.indexOf("COMBINED")) + lasFile.substring(lasFile.indexOf("MERGED"));
				fileLocation = lasFilePath + lasFile; 
				fileTest = new File(fileLocation);
				if (!fileTest.exists()) {
					if (dir) {
						String mdFile; 
						if (lasFileExists) {
							mdFile = convertUwi(topData.getUwi(), false); 
						}
						else {
							mdFile = convertUwiWithPath(topData.getUwi(), false);
						}
						fileLocation= lasFilePath + mdFile; 
						fileTest = new File(fileLocation);
						usingMdforDir = true;
						if (!fileTest.exists()) {
							return null;
						}
					}
					else {
						return null;
					}
				}
			}
			FileReader fileReader = new FileReader(fileLocation); 
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			LasData lasContainer = new LasData(topData.getUwi()); 
			if (usingMdforDir) {
				lasContainer.setMdTrue();
			}

			while((line = bufferedReader.readLine()) != null) {
				if (line.startsWith(" BS  .M")) {
					bit = line.substring(12,18);
				}
				if (line.startsWith(" BS  .MM")) {
					bit = line.substring(12,18);
				}
				if (line.startsWith(" BS  .IN")) {
					bit = String.valueOf(Double.parseDouble(line.substring(12,18)) * 25.4);
				}
				if (line.startsWith(" BS.MM")) {
					bit = line.substring(12,18);
				}
				if (line.startsWith(" SRVC.")) {
					serviceCo = line.substring(12,48);
				}
				if (line.startsWith("~A")) {
					lasContainer.addHeader(line);
				}
//				if (line.startsWith("      ")) {
//					if (Double.parseDouble(line.substring(0, 15)) > topData.getLowerBound() 
//							&& Double.parseDouble(line.substring(0, 15)) < topData.getUpperBound()) {
//						lasContainer.addRow(line + "          buffer              buffer" );
//					}
//				}   
				else if (line.startsWith("   ")) { 
					if (Double.parseDouble(line.substring(0, 15)) > topData.getLowerBound() 
							&& Double.parseDouble(line.substring(0, 15)) < topData.getUpperBound()) {
						String formattedLine = formatLine(line);
						lasContainer.addRow(formattedLine + "      " + bit + "      " + serviceCo);
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
			
			return lasContainer;
		}
		catch (Exception e) {
//			e.printStackTrace();
			return null;
		}
	}
	
	public String checkForLogPath(String lasFile) {
		if (lasFilePath.contains("ForgeOil") && lasFilePath.contains("LasFiles")) { 
			return lasFile.substring(0, lasFile.indexOf("\\") + 1) + "log_files" + lasFile.substring(lasFile.indexOf("\\"));
		}
		return lasFile;
	}
	
	public String convertUwi(String uwi, boolean dir) {
		String fileUwi = uwi.replaceFirst("/", "");
		fileUwi = fileUwi.replaceAll("/", "0");
		fileUwi = fileUwi.replaceAll("-", "");
		if (dir) { 
			if (uwi.startsWith("0")) {
				fileUwi = "1" + fileUwi + "_1000_TVD_COMBINED_MERGED.las"; 
			}
			else {
				fileUwi = fileUwi + "_1000_TVD_COMBINED_MERGED.las";
			}
		}
		else { 
			if (uwi.startsWith("0")) {
				fileUwi = "1" + fileUwi + "_1000_MD_COMBINED_MERGED.las"; 
			}
			else {
				fileUwi = fileUwi + "_1000_MD_COMBINED_MERGED.las"; 
			}
		}
		return fileUwi;
	}
	
	public String convertUwiWithPath(String uwi, boolean dir) {
		String township = uwi.substring(11,13);
		String range = uwi.substring(14,16); 
		String meridian = uwi.substring(17,18); 
		String fileUwi = uwi.replaceFirst("/", "");
		fileUwi = fileUwi.replaceAll("/", "0");
		fileUwi = fileUwi.replaceAll("-", "");
		if (dir) { 
			if (uwi.startsWith("0")) {
				fileUwi = "Twp " + township + "W" + meridian + "\\" + township + "-" + range + "W" + meridian + "\\1" + fileUwi + "_1000_TVD_COMBINED_MERGED.las"; 
			}
			else {
				fileUwi =  "Twp " + township + "W" + meridian + "\\" + township + "-" + range + "W" + meridian + "\\" + fileUwi + "_1000_TVD_COMBINED_MERGED.las";
			}
		}
		else { 
			if (uwi.startsWith("0")) {
				fileUwi =  "Twp " + township + "W" + meridian + "\\" + township + "-" + range + "W" + meridian + "\\1" + fileUwi + "_1000_MD_COMBINED_MERGED.las"; 
			}
			else {
				fileUwi =  "Twp " + township + "W" + meridian + "\\" + township + "-" + range + "W" + meridian + "\\" + fileUwi + "_1000_MD_COMBINED_MERGED.las"; 
			}
		}
		//System.out.println(fileUwi);
		return fileUwi;
	}
}
