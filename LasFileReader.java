import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
 
public class LasFileReader {
	private String lasFilePath = "C:\\Users\\justi\\Desktop\\ForgeOil\\LAS\\";
	
//	public boolean readFile(String lasFile, TopData topData) { 
//		
//	}
	
	public static void main(String[] args) throws IOException { 
		String lasFilePath = "C:\\Users\\justi\\Desktop\\ForgeOil\\LAS\\100010203726W400_1000_MD_COMBINED_MERGED.las";
		String line = null; 
		FileReader fileReader = new FileReader(lasFilePath); 
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		
		 while((line = bufferedReader.readLine()) != null) {
			 if (line.startsWith("~A")) {
				 System.out.println(line);
			 }
//			 if (line.startsWith("      ")) {
//				 if (Double.parseDouble(line.substring(6, 15)) > 1400  && Double.parseDouble(line.substring(6, 15)) < 1800)
//			 	System.out.println(line);
//			 }
         }   
         bufferedReader.close();      
	}
}
