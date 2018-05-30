import java.util.ArrayList;

import org.apache.commons.codec.binary.StringUtils;

public class DataWriter {

	private final String header = "Sort UWI,UWI,Current License,Bottom Hole Latitude,Bottom Hole Longitude,KB Elevation (m),Ground Elevation (m),Max True Vertical Depth (m),Total True Vertical Depth (m),Total Depth (m),Fluid,Mode,Lahee,Type,License Date,Spud Date,Rig Release Date,Producing Zone,Field,DEPT,Subsea,Formation,VKNS Isopach,Interval (step),";
	private int row;
	private Mnemonics mnemonics;
	
	// 0   1    2    3   4   5  6  7   8   9   10   11  12   13    14
	// GR|DPHI|NPHI|SFL|ILM|ILD|SP|DT|RHOB|PE|CAL1|CAL2|BIT|TENS|SERVICE
	private int[] position; 
	
	public DataWriter(Mnemonics m) { 
		mnemonics = m;
		position = new int[15];
	}

	public void formatData(String uwiInfo, LasData lasData, TopData topData) {
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
			String formatRow = lasData.getRow(i).trim().replaceAll(" +", ",");

			Double depth = Double.parseDouble(formatRow.substring(0, formatRow.indexOf(",")));

			Double subsea = Double.parseDouble(getCol(uwiInfo, 5)) - depth;

			String data = formatRow.substring(ordinalIndexOf(formatRow, ",", 1));
	
			String generalWellInfo = uwiInfo.substring(0, uwiInfo.length() - 1) + "," +  depth.toString() + "," + subsea.toString() + "," + topData.getFormation(depth) + ", ,0.1";
			 
			String formattedRow = generalWellInfo + data; 
			
			String finalRow = generalWellInfo + ",";
			
			for (int j = 0 ; j < position.length ; j++) {
				if (position[j] != 0){
					finalRow += getCol(formattedRow, position[j]) + ","; 			
				}
				else {
					finalRow += ",";
				}
			}
			
			finalRow = addCalcValues(finalRow);
			//System.out.println(finalRow);
			formattedData.addRow(finalRow);
		}

		WriteToCSV writer = new WriteToCSV(formattedData);
		writer.write();
	}
	public String addCalcValues(String row) {
		String separation = "";
		if (!getCol(row, 26).equals(",") || !getCol(row, 25).equals("")) {
			separation = String.valueOf(Double.parseDouble(getCol(row, 26)) - Double.parseDouble(getCol(row, 25)));
		}
		//System.out.println(separation);
		
		String mediumSeparation = "";
		if (!getCol(row, 28).equals(",") || !getCol(row, 27).equals(",")) {
			mediumSeparation = String.valueOf(Double.parseDouble(getCol(row, 28)) - Double.parseDouble(getCol(row, 27)));		
		}
		//System.out.println(mediumSeparation);
		
		String deepSeparation = "";
		if (!getCol(row, 29).equals(",") || !getCol(row, 28).equals(",")) {
			deepSeparation = String.valueOf(Double.parseDouble(getCol(row, 29)) - Double.parseDouble(getCol(row, 28)));
		}
		//System.out.println(deepSeparation);
		
		String mudCake = "";
		if (!getCol(row, 34).equals(",") || !getCol(row, 36).equals(",")) {
			mudCake = String.valueOf(Double.parseDouble(getCol(row, 34)) - Double.parseDouble(getCol(row, 36)));
		}
		//System.out.println(mudCake);		
	
		String temp = row.substring(0, ordinalIndexOf(row, "," , 27)) + "," + separation + row.substring(ordinalIndexOf(row, "," , 27), ordinalIndexOf(row, "," , 30))
		 + "," + mediumSeparation + "," + deepSeparation + row.substring(ordinalIndexOf(row, "," , 30), ordinalIndexOf(row, "," , 37)) + "," + mudCake 
		 + row.substring(ordinalIndexOf(row, ",", 37));
		//System.out.println(temp);
		
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
		for(int i = 24 ; i < headerArray.length ; i++) {
			for (int j = 0 ; j < mnemonics.getGammaSize() ; j++) {
				if (headerArray[i].equals(mnemonics.getGamma(j))) {
					position[0] = i;
					break;
				}
			}
			for (int j = 0 ; j < mnemonics.getDensitySize() ; j++) {
				if (headerArray[i].equals(mnemonics.getDensity(j))) {
					position[1] = i;
					break;
				}
			}
			for (int j = 0 ; j < mnemonics.getNeutronSize() ; j++) {
				if (headerArray[i].equals(mnemonics.getNeutron(j))) {
					position[2] = i;
					break;
				}
			}
			for (int j = 0 ; j < mnemonics.getShallowSize() ; j++) {
				if (headerArray[i].equals(mnemonics.getShallow(j))) {
					position[3] = i;
					break;
				}
			}
			for (int j = 0 ; j < mnemonics.getMediumSize() ; j++) {
				if (headerArray[i].equals(mnemonics.getMedium(j))) {
					position[4] = i;
					break;
				}
			}
			for (int j = 0 ; j < mnemonics.getDeepSize() ; j++) {
				if (headerArray[i].equals(mnemonics.getDeep(j))) {
					position[5] = i;
					break;
				}
			}
			for (int j = 0 ; j < mnemonics.getSpSize() ; j++) {
				if (headerArray[i].equals(mnemonics.getSp(j))) {
					position[6] = i;
					break;
				}
			}
			for (int j = 0 ; j < mnemonics.getSonicSize() ; j++) {
				if (headerArray[i].equals(mnemonics.getSonic(j))) {
					position[7] = i;
					break;
				}
			}
			for (int j = 0 ; j < mnemonics.getBulkDensitySize() ; j++) {
				if (headerArray[i].equals(mnemonics.getBulkDensity(j))) {
					position[8] = i;
					break;
				}
			}
			for (int j = 0 ; j < mnemonics.getPeSize() ; j++) {
				if (headerArray[i].equals(mnemonics.getPe(j))) {
					position[9] = i;
					break;
				}
			}
			if (position[10] == 0) {
				for (int j = 0 ; j < mnemonics.getCaliperSize() ; j++) {
					if (headerArray[i].equals(mnemonics.getCaliper(j))) {
						previousCal = mnemonics.getCaliper(j);
						position[10] = i;
						break;
					}
				}
			}
			for (int k = 0 ; k < mnemonics.getCaliperSize() ; k++) {
				if (headerArray[i].equals(mnemonics.getCaliper(k)) && i!=position[10]) {
					position[11] = i;
					break;
				}
			}
			for (int j = 0 ; j < mnemonics.getTensionSize() ; j++) {
				if (headerArray[i].equals(mnemonics.getTension(j))) {
					position[13] = i;
					break;
				}
			}
		}
		position[12] = headerArray.length-2; 
		position[14] = headerArray.length-1;
		
		for (int i = 0; i < position.length ; i++) {
			System.out.print(position[i] + "|");
		}
		System.out.println();
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
