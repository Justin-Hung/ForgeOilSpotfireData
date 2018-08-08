import java.util.ArrayList;
import java.util.Arrays;

public class LiteFormat {
	private ArrayList<FormattedData> formattedData; 
	private ArrayList<FormattedData> liteData;
	private ArrayList<Integer> grabColumns; 
	private ArrayList<String> grabStrings; 
	private String header; 
	
	public LiteFormat(ArrayList<FormattedData> formattedData, String header) {
		this.formattedData = formattedData;
		this.header = header; 
	}
	
	public void findColumns() {  
		grabStrings = new ArrayList<String>( Arrays.asList("GR: Gamma Ray", "SRES: Shallow Resistivity", "MRES: Medium Resistivity", 
				"DRES: Deep Resistivity", "DENS: Density", ));
		grabColumns = new ArrayList<Integer>(); 
		String[] headerArray = header.split(",");
		int dataCol = 0; 
		for (int i = 0 ; i < headerArray.length ; i++) {
			if (!headerArray[i].contains("Sort UWI") && !headerArray[i].contains("Lahee") && !headerArray[i].contains("Spud Date")
					&& !headerArray[i].contains("Field Name") && !headerArray[i].contains("Mode") && !headerArray[i].contains("Mode")) { 
				grabColumns.add(i);
			}
			if (headerArray[i].contains("Interval (step)")) { 
				dataCol = i + 1; 
				break;
			}
		}
		
		for (int i = dataCol ; i < headerArray.length ; i++) {
			if (headerArray[i].contains("")) { 
				grabColumns.add(i);
			}
		}
		
		
	}
	
	public ArrayList<FormattedData> format() { 
		liteData = new ArrayList<FormattedData>();
		for (int i = 0 ; i < formattedData.size() ; i++) { 
			String[] headerArray = formattedData.get(i).getHeader().split(",");
			
		}

	}
	
	
	
}
