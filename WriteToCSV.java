import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteToCSV {
	private FormattedData data;
	
	public WriteToCSV(FormattedData d) { 
		data = d;
	}
	
	public void write() { 
		String uniqueWell = data.getRow(0).substring(18, 20);
		String uwi = data.getRow(0).substring(21, 34);
		FileWriter fileWriter; 
		
		try {
			fileWriter = new FileWriter(new File(uniqueWell + "_" + uwi + ".csv"));
			fileWriter.write(data.getHeader());
			fileWriter.write(System.lineSeparator());
			
			data.write(fileWriter);
			
			fileWriter.close(); 
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}	
}
