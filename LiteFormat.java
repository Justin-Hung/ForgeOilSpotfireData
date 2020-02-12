import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class LiteFormat {
	private ArrayList<FormattedData> formattedData; 
	private ArrayList<FormattedData> liteData;
	private ArrayList<Integer> grabColumns; 
	private ArrayList<String> grabStrings; 
	private int liteHeaderOffset;
	private String header; 
	private String liteActualHeader;
	
	public LiteFormat(ArrayList<FormattedData> formattedData, String header) {
		this.formattedData = formattedData;
		this.header = header; 
	}
	
	public void findColumns() {  
		grabStrings = new ArrayList<String>( Arrays.asList("GR: Gamma Ray", "SRES: Shallow Resistivity", "MRES: Medium Resistivity", 
				"DRES: Deep Resistivity", "DENS: Density", "NPHI: Neutron Porosity", "Separation", "Calculated Density Porosity Sandstone",
				"NPHI-SS: Neutron Porosity Sandstone", "Calculated Density Porosity Limestone", "NPHI-LS: Neutron Porosity Limestone", 
				"Calculated Density Porosity Dolomite", "NPHI-DL: Neutron Porosity Dolomite", "DT: Interval Transit Time",
				"SP: Spontaneous Potential", "PE: Photoelectric Effect", "Caliper", "Bit", "Mudcakes", "Service Co."));
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
		
		liteHeaderOffset = grabColumns.size();
		
		for(int i = 0; i < grabStrings.size() ; i++) {
			for (int j = dataCol ; j < headerArray.length ; j++) {
				if (grabStrings.get(i).equals(headerArray[j])) { 
					grabColumns.add(j);
					break;
				}
			}
		}
	}
	
	public void format() { 
		liteData = new ArrayList<FormattedData>();
		liteActualHeader = formatRow(header.split(","));
		for (int i = 0 ; i < formattedData.size() ; i++) { 
			FormattedData liteFormattedData = new FormattedData(); 
		 	String[] headerArray = formattedData.get(i).getHeader().split(",");
			String liteHeader = formatRow(headerArray); 
			liteFormattedData.addHeader(liteHeader);
			for (int j = 0 ; j < formattedData.get(i).getSize() ; j++) {
				String line = formattedData.get(i).getRow(j);
				line += "placeholder";
				String [] lineArray = line.split(","); 
				lineArray[lineArray.length-1] = lineArray[lineArray.length-1].substring(0, lineArray[lineArray.length-1].indexOf("placeholder"));
				liteFormattedData.addRow(formatRow(lineArray));
			}
			liteData.add(liteFormattedData);
		}
	}
	
	public void write(FileWriter fileWriter, boolean append) {
		try {
			if (!append) {
				fileWriter.write(liteActualHeader);
				fileWriter.write(System.lineSeparator());
				String dummyLine = getDummyLine(liteData.get(0).getRow(0));
				fileWriter.write(dummyLine);
				fileWriter.write(System.lineSeparator());
			}
			else { 
				fileWriter.write(liteData.get(0).getHeader());
				fileWriter.write(System.lineSeparator());
			}
			for (int i = 0 ; i < liteData.get(0).getSize() ; i++) {
				fileWriter.write(liteData.get(0).getRow(i));
				fileWriter.write(System.lineSeparator());
			}
			for (int i = 1 ; i < liteData.size() ; i++) {
				fileWriter.write(liteData.get(i).getHeader());
				fileWriter.write(System.lineSeparator());
				for (int j = 0 ; j < liteData.get(i).getSize() ; j++) {
					fileWriter.write(liteData.get(i).getRow(j));
					fileWriter.write(System.lineSeparator());
				}
			}
			fileWriter.close();
			liteData.clear();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getDummyLine(String firstRow) {
		firstRow += "placeholder";
		String[] firstRowArray = firstRow.split(","); 
		firstRowArray[firstRowArray.length-1] = firstRowArray[firstRowArray.length-1].substring(0, firstRowArray[firstRowArray.length-1].indexOf("placeholder"));
		String[] liteHeaderArray = liteActualHeader.split(",");
		WriteToCSV dummyGetter = new WriteToCSV(); 
		String dummyRow = "";
		for(int i = 1 ; i < liteHeaderOffset-2 ; i++) {
			dummyRow+= ",";
			if (firstRowArray[i].equals("")) {
				dummyRow += dummyGetter.getDummyTypeFromHeader(liteHeaderArray[i]);
			}
		}
		for (int i = liteHeaderOffset-2 ; i < firstRowArray.length-1 ; i++) {
			dummyRow += ",-999.25";
		}
		dummyRow += ",placeholder";
		return dummyRow;
	}
	
	public String formatRow(String[] rowArray) { 
		try {
			String liteRow = rowArray[grabColumns.get(0)]; 
			for (int i = 1 ; i < grabColumns.size() ; i++) {
				liteRow += "," + rowArray[grabColumns.get(i)];
			}
			return liteRow; 
		}
		catch(ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
