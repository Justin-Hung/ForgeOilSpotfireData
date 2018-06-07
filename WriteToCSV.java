import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class WriteToCSV {
	private ArrayList<FormattedData> data;
	
	public WriteToCSV(ArrayList<FormattedData> d) { 
		data = d;
	}
	
	public void write(String header, ArrayList<MnemonicData> mnemonicList) { 
		FileWriter fileWriter; 
		
		try {
			String township = "T" + data.get(0).getRow(0).substring(28, 30) + "R" + data.get(0).getRow(0).substring(31, 33);
			fileWriter = new FileWriter(new File("Test" + township + "MASTERFILE" + ".csv"));
			
			header += "DEPT,Subsea,Formation,VKNS Isopach,Interval (step)";
			
			for (int i = 0 ; i < mnemonicList.size() ; i++) {
				header += "," + mnemonicList.get(i).getName();
			}
			
			header += ",Caliper2,Bit,Service Co.";
			
			String uniqueWell = data.get(0).getRow(0).substring(18, 20);
			String uwi = data.get(0).getRow(0).substring(21, 34);
			header += ",Break " + uniqueWell + "_" + uwi;
			
			fileWriter.write(header);
			
			fileWriter.write(System.lineSeparator());

			boolean isFirst = true; 
			
			for (int i = 0 ; i < data.size() ; i++) {
				if (!isFirst) {
					uniqueWell = data.get(i).getRow(0).substring(18, 20);
					uwi = data.get(i).getRow(0).substring(21, 34);
					fileWriter.write(data.get(i).getHeader() + ",Break " + uniqueWell + "_" + uwi);
					fileWriter.write(System.lineSeparator());
				}
				data.get(i).write(fileWriter);
				isFirst = false;
			}
			
			fileWriter.close(); 
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}	
}
