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
			String township = "T" + data.get(0).getRow(0).substring(28, 30) + "R" + data.get(0).getRow(0).substring(31, 33);
			fileWriter = new FileWriter(new File("Test" + township + "MASTERFILE" + ".csv"));
			
			fileWriter.write("Sort UWI,UWI,Current Licensee,Bottom Hole Latitude,Bottom Hole Longitude,KB Elevation (m),Ground Elevation (m),Max True Vertical Depth (m),Total True Vertical Depth (m),Total Depth (m),Fluid,Mode,Lahee,Type,License Date,Spud Date,Rig Release Date,Producing Zone,Field,DEPT,Subsea,Formation,VKNS Isopach,Interval (step),Gamma,Density,Neutron,Separation,Shallow Resis,Medium Resis,Deep Resis,Medium-Shallow Separation,Deep-Medium Separation,SP,Sonic,Bulk Density,PE ,Caliper 1,Caliper 2,Bit,MudCake,Tension,Logging Company");
			fileWriter.write(System.lineSeparator());
			fileWriter.write(System.lineSeparator());
			for (int i = 0 ; i < data.size() ; i++) {
				String uniqueWell = data.get(i).getRow(0).substring(18, 20);
				String uwi = data.get(i).getRow(0).substring(21, 34);
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
