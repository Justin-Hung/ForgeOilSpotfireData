import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class WriteToCSV {
	private ArrayList<FormattedData> data;
	
	public WriteToCSV(ArrayList<FormattedData> d) { 
		data = d;
	}
	
	public void write() { 
		FileWriter fileWriter; 
		
		try {
			fileWriter = new FileWriter(new File("MASTERFILE" + ".csv"));
			
			for (int i = 0 ; i < data.size() ; i++) {
				String uniqueWell = data.get(i).getRow(0).substring(18, 20);
				String uwi = data.get(i).getRow(0).substring(21, 34);
				fileWriter.write(data.get(i).getHeader() + ",Break " + uniqueWell + "_" + uwi);
				fileWriter.write(System.lineSeparator());
				data.get(i).write(fileWriter);
			}
			
			fileWriter.close(); 
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}	
}
