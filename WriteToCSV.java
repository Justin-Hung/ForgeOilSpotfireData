import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class WriteToCSV {
	private String outputFilePath;
	private ArrayList<FormattedData> data;
	
	public WriteToCSV(ArrayList<FormattedData> d) { 
		data = d;
	}
	
	public WriteToCSV(ArrayList<FormattedData> d, String filePath) {
		outputFilePath = filePath;
		data = d;
	}
	
	public String formatHeader(String head) { 
		if (head.contains("\n")) { 
			head = head.replace("\n", "").replace("\r", "");
		}
		return head;
	}
	
	public void write(String header, ArrayList<MnemonicData> mnemonicList) { 
		
		header = formatHeader(header);
		FileWriter fileWriter; 
		
		try {
			String township = "T" + data.get(0).getRow(0).substring(28, 30) + "R" + data.get(0).getRow(0).substring(31, 33);
			
			if (outputFilePath.endsWith("csv")) {
				fileWriter = new FileWriter(new File(outputFilePath), true);
			}
			else if (outputFilePath != null) {
				fileWriter = new FileWriter(new File(outputFilePath + "\\" + township + "MASTERFILE" + ".csv"));
			}
			else { 
				fileWriter = new FileWriter(new File( township + "MASTERFILE" + ".csv"));
			}
			
			header += "DEPT,Formation,VKNS Isopach,Interval (step)";

			for (int i = 0 ; i < mnemonicList.size() ; i++) {
				header += "," + mnemonicList.get(i).getName();
			}

			header += ",Caliper2,Bit,Service Co.,Separation,Medium-Shallow Separation,Deep-Medium Separation,Mudcakes,Subsea,Calculated Density Porosity Sandstone,Calculated Density Porosity Limestone,Calculated Density Porosity Dolomite";
			
			String uniqueWell = data.get(0).getRow(0).substring(18, 20);
			String uwi = data.get(0).getRow(0).substring(21, 34);
			
			fileWriter.write(header + ",Break " + uniqueWell + "_" + uwi + "," + "Using MD values for directional well?");
			fileWriter.write(System.lineSeparator());
		
			if (data.get(0).isMdforDir()) {
				data.get(0).write(fileWriter, true);
			}
			else {
				data.get(0).write(fileWriter, false);
			}
			
			for (int i = 1 ; i < data.size() ; i++) {
				uniqueWell = data.get(i).getRow(0).substring(18, 20);
				uwi = data.get(i).getRow(0).substring(21, 34);
				if (data.get(i).isMdforDir()) {
					fileWriter.write(data.get(i).getHeader() + ",Break " + uniqueWell + "_" + uwi + "," + "Using MD values for directional well?");
					fileWriter.write(System.lineSeparator());
					data.get(i).write(fileWriter, true);
				}
				else {
					fileWriter.write(data.get(i).getHeader() + ",Break " + uniqueWell + "_" + uwi + "," + "Using MD values for directional well?");
					fileWriter.write(System.lineSeparator());
					data.get(i).write(fileWriter, false);
				}
			}
			
			fileWriter.close(); 
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}	
}
