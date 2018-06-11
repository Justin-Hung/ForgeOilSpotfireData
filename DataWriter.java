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
		if (lasData.getMdForDir()) {
			formattedData.setMdTrue(); 
		}
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
		position = new int[15];
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
		
//		if (!getCol(row, 26).equals(",") && !getCol(row, 25).equals("")) {
//			separation = String.valueOf(Double.parseDouble(getCol(row, 26)) - Double.parseDouble(getCol(row, 25)));
//		}
//		System.out.println(separation);
//		
//		String mediumSeparation = "";
//		if (!getCol(row, 28).equals(",") && !getCol(row, 27).equals(",")) {
//			mediumSeparation = String.valueOf(Double.parseDouble(getCol(row, 28)) - Double.parseDouble(getCol(row, 27)));		
//		}
//		System.out.println(mediumSeparation);
//		
//		String deepSeparation = "";
//		if (!getCol(row, 29).equals(",") && !getCol(row, 28).equals(",")) {
//			deepSeparation = String.valueOf(Double.parseDouble(getCol(row, 29)) - Double.parseDouble(getCol(row, 28)));
//		}
//		System.out.println(deepSeparation);
//		
//		String mudCake = "";
//		if (!getCol(row, 34).equals(",") && !getCol(row, 36).equals(",")) {
//			mudCake = String.valueOf(Double.parseDouble(getCol(row, 34)) - Double.parseDouble(getCol(row, 36)));
//		}
//		System.out.println(mudCake);		
	
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
		
		for (int i = 0 ; i < mnemonics.getGammaSize() ; i++) {
			if (position[0] == 0) {
				for (int j = 0 ; j < headerArray.length ; j++) {
					if (headerArray[j].equals(mnemonics.getGamma(i))) {
						position[0] = j; 
					}
				}
			}
			else {
				break; 
			}
		}
		
		for (int i = 0 ; i < mnemonics.getDensitySize() ; i++) {
			if (position[1] == 0) {
				for (int j = 0 ; j < headerArray.length ; j++) {
					if (headerArray[j].equals(mnemonics.getDensity(i))) {
						position[1] = j; 
					}
				}
			}
			else {
				break; 
			}
		}
		
		for (int i = 0 ; i < mnemonics.getNeutronSize() ; i++) {
			if (position[2] == 0) {
				for (int j = 0 ; j < headerArray.length ; j++) {
					if (headerArray[j].equals(mnemonics.getNeutron(i))) {
						position[2] = j; 
					}
				}
			}
			else {
				break; 
			}
		}
		
		for (int i = 0 ; i < mnemonics.getShallowSize() ; i++) {
			if (position[3] == 0) {
				for (int j = 0 ; j < headerArray.length ; j++) {
					if (headerArray[j].equals(mnemonics.getShallow(i))) {
						position[3] = j; 
					}
				}
			}
			else {
				break; 
			}
		}
		
		for (int i = 0 ; i < mnemonics.getMediumSize() ; i++) {
			if (position[4] == 0) {
				for (int j = 0 ; j < headerArray.length ; j++) {
					if (headerArray[j].equals(mnemonics.getMedium(i))) {
						position[4] = j; 
					}
				}
			}
			else {
				break; 
			}
		}
		
		for (int i = 0 ; i < mnemonics.getDeepSize() ; i++) {
			if (position[5] == 0) {
				for (int j = 0 ; j < headerArray.length ; j++) {
					if (headerArray[j].equals(mnemonics.getDeep(i))) {
						position[5] = j; 
					}
				}
			}
			else {
				break; 
			}
		}
		
		for (int i = 0 ; i < mnemonics.getSpSize() ; i++) {
			if (position[6] == 0) {
				for (int j = 0 ; j < headerArray.length ; j++) {
					if (headerArray[j].equals(mnemonics.getSp(i))) {
						position[6] = j; 
					}
				}
			}
			else {
				break; 
			}
		}
		
		for (int i = 0 ; i < mnemonics.getSonicSize() ; i++) {
			if (position[7] == 0) {
				for (int j = 0 ; j < headerArray.length ; j++) {
					if (headerArray[j].equals(mnemonics.getSonic(i))) {
						position[7] = j; 
					}
				}
			}
			else {
				break; 
			}
		}
		
		for (int i = 0 ; i < mnemonics.getBulkDensitySize() ; i++) {
			if (position[8] == 0) {
				for (int j = 0 ; j < headerArray.length ; j++) {
					if (headerArray[j].equals(mnemonics.getBulkDensity(i))) {
						position[8] = j; 
					}
				}
			}
			else {
				break; 
			}
		}
		
		for (int i = 0 ; i < mnemonics.getPeSize() ; i++) {
			if (position[9] == 0) {
				for (int j = 0 ; j < headerArray.length ; j++) {
					if (headerArray[j].equals(mnemonics.getPe(i))) {
						position[9] = j; 
					}
				}
			}
			else {
				break; 
			}
		}
		
		int caliperIndex = 0;
		while (caliperIndex < mnemonics.getCaliperSize()) {
			if (position[10] == 0) {
				for (int j = 0 ; j < headerArray.length ; j++) {
					if (headerArray[j].equals(mnemonics.getCaliper(caliperIndex))) {
						position[10] = j; 
					}
				}
			}
			else {
				break; 
			}
			caliperIndex++;
		}
		while (caliperIndex < mnemonics.getCaliperSize()) {
			if (position[11] == 0) {
				for (int j = 0 ; j < headerArray.length ; j++) {
					if (headerArray[j].equals(mnemonics.getCaliper(caliperIndex))) {
						position[11] = j; 
					}
				}
			}
			else {
				break; 
			}
			caliperIndex++;
		}
		
		for (int i = 0 ; i < mnemonics.getTensionSize() ; i++) {
			if (position[13] == 0) {
				for (int j = 0 ; j < headerArray.length ; j++) {
					if (headerArray[j].equals(mnemonics.getTension(i))) {
						position[13] = j; 
					}
				}
			}
			else {
				break; 
			}
		}
		
		position[12] = headerArray.length-2; 
		position[14] = headerArray.length-1;
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
