import java.util.ArrayList;

import org.apache.commons.codec.binary.StringUtils;

public class DataWriter {

	private final String header = "Sort UWI,UWI,Current License,Bottom Hole Latitude,Bottom Hole Longitude,KB Elevation (m),Ground Elevation (m),Max True Vertical Depth (m),Total True Vertical Depth (m),Total Depth (m),Fluid,Mode,Lahee,Type,License Date,Spud Date,Rig Release Date,Producing Zone,Field,DEPT,Subsea,Formation,VKNS Isopach,Interval (step),";
	private int row;
	private ArrayList<MnemonicData> mnemonics;
	
	private int[] position;
	
	public DataWriter(ArrayList<MnemonicData> m) { 
		mnemonics = m;
		position = new int[60];
	}

	public FormattedData formatData(String uwiInfo, LasData lasData, TopData topData) {
		FormattedData formattedData = new FormattedData();

		String formattedHeader = lasData.getHeader().trim().replaceAll(" +", ",");

		formattedHeader = header + formattedHeader.substring(8);
		
		getPositions(formattedHeader);

		String finalHeader = header; 
		
		for (int j = 0 ; j < position.length-1 ; j++) {
			if (position[j] != 0) {
				finalHeader += getCol(formattedHeader, position[j]) + ",";
			}
			else {
				finalHeader += ",";
			}
		}

		finalHeader = addCalcHeaders(finalHeader);
		
		finalHeader += "Service Co.";
		
		formattedData.addHeader(finalHeader);

		for (int i = 0; i < lasData.getSize(); i++) {
			String formatRow = lasData.getRow(i).trim().replaceAll(" +", ",") + ",";

			Double depth = Double.parseDouble(formatRow.substring(0, formatRow.indexOf(",")));

			Double subsea = Double.parseDouble(getCol(uwiInfo, 5)) - depth;

			String data = formatRow.substring(ordinalIndexOf(formatRow, ",", 1));
	
			String generalWellInfo = uwiInfo.substring(0, uwiInfo.length() - 1) + "," +  depth.toString() + "," + subsea.toString() + "," + topData.getFormation(depth) + ", ,0.1";
			 
			String formattedRow = generalWellInfo + data; 
			
			String finalRow = generalWellInfo + ",";	
			for (int j = 0 ; j < position.length ; j++) {
				boolean checkNum = true; 
				if (j == 12 && Double.parseDouble(getCol(formattedRow, position[j])) == 0) {
					checkNum = false;
				}
				if (position[j] != 0 && !getCol(formattedRow, position[j]).contains("-999") && checkNum){
					if (j == 1 || j == 2) {
						double porosity = Double.parseDouble(getCol(formattedRow, position[j]));
						if ( porosity < 1.0 && porosity > -1.0) {
							finalRow += String.valueOf((porosity*100)) + ",";
						}
						else {
							finalRow += getCol(formattedRow, position[j]) + ","; 
						}
					}
					else {
						finalRow += getCol(formattedRow, position[j]) + ","; 	
					}
				}
				else {
					finalRow += ",";
				}
			}
			finalRow = addCalcValues(finalRow);
			formattedData.addRow(finalRow);
		}
		resetPosition();
		return formattedData;
	}
	
	public boolean isNum(String str) { 
		try  
		  {  
		    double d = Double.parseDouble(str);  
		  }  
		  catch(NumberFormatException nfe)  
		  {  
		    return false;  
		  }  
		  return true;  
	}
	
	public void resetPosition() {
		position = new int[60];
	}
	
	public String addCalcValues(String row) {
		String[] rowArray = row.split(",");
	
		String separation = "";
		if (!rowArray[26].equals("") && !rowArray[25].equals("")) {
			separation = String.valueOf(Double.parseDouble(rowArray[26]) - Double.parseDouble(rowArray[25]));
		}
		
		String mediumSeparation = "";
		if (!rowArray[28].equals("") && !rowArray[27].equals("")) {
			mediumSeparation = String.valueOf(Double.parseDouble(rowArray[28]) - Double.parseDouble(rowArray[27]));
		}
		
		String deepSeparation = "";
		if (!rowArray[29].equals("") && !rowArray[28].equals("")) {
			deepSeparation = String.valueOf(Double.parseDouble(rowArray[29]) - Double.parseDouble(rowArray[28]));
		}
		
		String mudCake = "";
		if (!rowArray[34].equals("") && !rowArray[36].equals("")) {
			mudCake = String.valueOf(Double.parseDouble(rowArray[34]) - Double.parseDouble(rowArray[36]));
		}	
	
		String temp = row.substring(0, ordinalIndexOf(row, "," , 27)) + "," + separation + row.substring(ordinalIndexOf(row, "," , 27), ordinalIndexOf(row, "," , 30))
		 + "," + mediumSeparation + "," + deepSeparation + row.substring(ordinalIndexOf(row, "," , 30), ordinalIndexOf(row, "," , 37)) + "," + mudCake
		 + row.substring(ordinalIndexOf(row, ",", 37));
		
		return temp;
	}
	public String addCalcHeaders(String header) {
		String sep = header.substring(0, ordinalIndexOf(header, "," , 27)) + ",Separation" + header.substring(ordinalIndexOf(header, "," , 27), ordinalIndexOf(header, "," , 30))
		 + ",Medium-Shallow Separation,Deep-Medium Separation" + header.substring(ordinalIndexOf(header, "," , 30), ordinalIndexOf(header, "," , 37)) + ",MudCake"
		 + header.substring(ordinalIndexOf(header, ",", 37));
		return sep;
	}
	
	public void getPositions(String header) {
		String previousCal = null;
		String[] headerArray = header.split(",");
		
		for (int i = 0 ; i < mnemonics.size() ; i++) {
			for (int j = 0 ; j < mnemonics.get(i).getMnemonics().size() ; j++) {
				if (position[i] == 0) {
					for (int k = 0 ; k < headerArray.length ; k++) {
						if (headerArray[k].equals(mnemonics.get(i).getMnemonics().get(j))) { 
							position[i] = k; 
						}
					}
				}
				else {
					break;
				}
			}
		}
	}

	public String getCol(String row, int index) {
		if (index == 0) {
			return row.substring(0,row.indexOf(","));
		}
		return row.substring(ordinalIndexOf(row, ",", index) + 1, ordinalIndexOf(row, ",", index + 1));
	}

	public int ordinalIndexOf(String str, String substr, int n) {
		int pos = str.indexOf(substr);
		while (--n > 0 && pos != -1)
			pos = str.indexOf(substr, pos + 1);
		return pos;
	}
}
