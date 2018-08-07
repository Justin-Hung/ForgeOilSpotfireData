import java.util.ArrayList;

import org.apache.commons.codec.binary.StringUtils;

public class DataWriter {
	
	private ArrayList<UnknownData> unknownDataList;
	private String header;
	private ArrayList<MnemonicData> mnemonics;
	private ArrayList<Integer> unknownPositions; 
	private ArrayList<ArrayList<Integer>> positionList;
	private LasDescriptionData descriptionData;
	
	private int[] columnArray;
	private int headerOffset; 
	
	public int getHeaderOffset() { return headerOffset; }
	
	public DataWriter() {}
	
	public DataWriter(ArrayList<MnemonicData> m) { 
		mnemonics = m;
		positionList = new ArrayList<ArrayList<Integer>>(); 
		unknownDataList = new ArrayList<UnknownData>(); 
	}
	
	public void setDescriptionData(LasDescriptionData descriptionData) {
		this.descriptionData = descriptionData;
	}
	
	public ArrayList<UnknownData> getUnknownDataList() { 
		return unknownDataList; 
	}
	
	public ArrayList<ArrayList<String>> getMnemonicUnits(ArrayList<ArrayList<String>> mnemonicsUsed, String uwi) { 
		ArrayList<ArrayList<String>> unitList = new ArrayList<ArrayList<String>>(); 
		for(int i = 0; i < mnemonicsUsed.size() ; i++) {
			ArrayList<String> unit = new ArrayList<String>();
			for (int j = 0 ; j < mnemonicsUsed.get(i).size() ; j++) { 
				for (int k = 0 ; k < descriptionData.size() ; k++) {
					if (mnemonicsUsed.get(i).get(j).equals(descriptionData.getMnemonic(k))) { 
						unit.add(descriptionData.getUnit(k));
					}
				}
			}
			unitList.add(unit);
		}
		return unitList;
	}
	
	public void setColumnArray(String head) {
		String [] headArray = head.split(",");
		for (int i = 0 ; i < headArray.length ; i++) {
			if (headArray[i].startsWith("KB")) {
				columnArray[0] = i;
			}
			if (headArray[i].equals("DEPT")) {
				columnArray[1] = i;
			}
		}
		for (int i = 0 ; i < mnemonics.size() ; i++) {
			if (mnemonics.get(i).getName().startsWith("SP")) {
				columnArray[2] = i + headerOffset;
			}
			if (mnemonics.get(i).getName().startsWith("SRES")) {
				columnArray[3] = i + headerOffset;
			}
			if (mnemonics.get(i).getName().startsWith("MRES")) { 
				columnArray[4] = i + headerOffset;
			}
			if (mnemonics.get(i).getName().startsWith("DRES")) { 
				columnArray[5] = i + headerOffset;
			}
			if (mnemonics.get(i).getName().startsWith("NPHI-SS")) {
				columnArray[6] = i + headerOffset;
			}
			if (mnemonics.get(i).getName().startsWith("DPHI-SS")) {
				columnArray[7] = i + headerOffset;
			}
			if (mnemonics.get(i).getName().equals("Caliper")) {
				columnArray[8] = i + headerOffset;
				columnArray[9] = i + headerOffset + 2; 
			}
			if (mnemonics.get(i).getName().startsWith("DENS")) {
				columnArray[10] = i + headerOffset;
			}
		}
	}
	
	public FormattedData secondaryFormatData(String h, String uwiInfo, LasData lasData, TopData topData, ArrayList<TopData> secondaryTopDataList, boolean unknownOutput) {
		UnknownData unknownData = null;
		if (unknownOutput) { 
			unknownData = new UnknownData(topData.getUwi());
		}
		TopData secondaryTopData = null;
		for (int i = 0 ; i < secondaryTopDataList.size() ; i++) {
			if (topData.getUwi().equals(secondaryTopDataList.get(i).getUwi())) {
				secondaryTopData = secondaryTopDataList.get(i);
			}
		}
		unknownPositions = new ArrayList<Integer>(); 
		columnArray = new int[11];
		header = h; 

		header += "DEPT,Primary Formation,Secondary Formation,VKNS Isopach,Interval (step),";

		headerOffset = header.split(",").length;
		
		FormattedData formattedData = new FormattedData();

		String formattedHeader = lasData.getHeader().trim().replaceAll(" +", ",");
		
		formattedHeader = header + formattedHeader.substring(8);
		
		getPositions(formattedHeader);
		
		if (unknownOutput) { 
			getUnknownPositions(formattedHeader);
		}
		
		ArrayList<ArrayList<String>> mnemonicsUsed = new ArrayList<ArrayList<String>>();
		for (int i = 0 ; i < positionList.size() ; i++) {
			ArrayList<String> mnemonics = new ArrayList<String>(); 
			for (int j = 0 ; j < positionList.get(i).size() ; j++) {
				mnemonics.add(formattedHeader.split(",")[positionList.get(i).get(j)]); 
			}
			mnemonicsUsed.add(mnemonics);
		}
		
		String finalHeader = header; 

		for (int j = 0 ; j < positionList.size() ; j++) {
			if (!positionList.get(j).isEmpty()) {
				finalHeader += formattedHeader.split(",")[positionList.get(j).get(0)] + ",";
			}
			else {
				finalHeader += ",";
			}
		}
		finalHeader += "Bit,Service Co.";
		
		setColumnArray(finalHeader);
		
		finalHeader = addCalcHeaders(finalHeader);
		
		if (unknownOutput) { 
			for (int i = 0 ; i < unknownPositions.size() ; i++) {
				unknownData.addUnknownMnemonic(formattedHeader.split(",")[unknownPositions.get(i)]);
			}
		}
		
		finalHeader = removeNewLine(finalHeader);
		
		formattedData.addHeader(finalHeader);
		
		ArrayList<ArrayList<String>> unitList = null;
		if (descriptionData != null) {
			unitList = getMnemonicUnits(mnemonicsUsed, topData.getUwi());
		}
		
		for (int i = 0; i < lasData.getSize(); i++) {
			String formatRow = lasData.getRow(i).trim().replaceAll(" +", ",") + ",";

			Double depth = Double.parseDouble(formatRow.substring(0, formatRow.indexOf(",")));

			String data = formatRow.substring(ordinalIndexOf(formatRow, ",", 1));
			
			String generalWellInfo = null;
			if (secondaryTopData != null) {
				generalWellInfo = uwiInfo.substring(0, uwiInfo.length() - 1) + "," +  depth.toString() + "," 
						+ topData.getFormation(depth) + "," + secondaryTopData.getFormation(depth) + ", ,0.1";
			}
			else {
				generalWellInfo = uwiInfo.substring(0, uwiInfo.length() - 1) + "," +  depth.toString() + "," 
						+ topData.getFormation(depth) + ", , ,0.1"; 
			}
			
			String formattedRow = generalWellInfo + data; 
			
			String finalRow = generalWellInfo + ",";	
			for (int j = 0 ; j < positionList.size() ; j++) {
				String value = "-999"; 
				int kValue = 0;
				for (int k = 0 ; k < positionList.get(j).size() ; k++) {
					if (!getCol(formattedRow, positionList.get(j).get(k)).contains("-999")) {
						kValue = k;
						value = getCol(formattedRow, positionList.get(j).get(k));
						break;
					}
				}
				if (!value.contains("-999")){
					if (j == 18) {
						double density = Double.parseDouble(value);
						if ( density <= 4.0 && density >= 0.0) {
							finalRow += String.valueOf((density*1000)) + ",";
						}
						else {
							finalRow += value + ","; 
						}
					}
					else if (j > 20 && j < 33) {
						if (unitList == null) { 
							double porosity = -999;
							try {
								porosity = Double.parseDouble(value);
							}
							catch (NumberFormatException e) {
								e.printStackTrace();
							}
							if ( porosity < 1.0 && porosity > -1.0) {
								finalRow += String.valueOf((porosity*100)) + ",";
							}
							else {
								finalRow += value + ","; 
							}
						}
						else if (unitList.get(j).get(kValue).contains("%") || unitList.get(j).get(kValue).toUpperCase().contains("PERC")) {
							finalRow += value + ",";
						}
						else { 
							double porosity = -999;
							try {
								porosity = Double.parseDouble(value);
							}
							catch (NumberFormatException e) {
								e.printStackTrace();
							}
							if ( porosity < 1.0 && porosity > -1.0) {
								finalRow += String.valueOf((porosity*100)) + ",";
							}
							else {
								finalRow += value + ","; 
							}
						}
					}
					else {
						finalRow += value + ","; 	
					}
				}
				else {
					finalRow += ",";
				}
			}
			
			finalRow += lasData.getBit() + "," + lasData.getServiceCo();
			finalRow = addCalcValues(finalRow);
			
//			for (int j = 0 ; j < unknownPositions.size() ; j++) {
//				finalRow += "," + getCol(formattedRow, unknownPositions.get(j)); 
//			}
			
			finalRow = removeNewLine(finalRow);
			
			formattedData.addRow(finalRow);
		}
		resetPosition();
		if (lasData.getMdForDir()) {
			formattedData.setMdTrue(); 
		}
		if (unknownOutput) {
			if (unknownData.size() != 0) {
				unknownDataList.add(unknownData);
			}
		}
		return formattedData;
	}

	public FormattedData formatData(String h, String uwiInfo, LasData lasData, TopData topData, boolean unknownOutput) {
		UnknownData unknownData = null;
		if (unknownOutput) {
			unknownData = new UnknownData(topData.getUwi());
		}
		unknownPositions = new ArrayList<Integer>(); 
		columnArray = new int[11];
		header = h; 
		header += "DEPT,Formation,VKNS Isopach,Interval (step),";
		headerOffset = header.split(",").length;
		
		FormattedData formattedData = new FormattedData();

		String formattedHeader = lasData.getHeader().trim().replaceAll(" +", ",");
		
		formattedHeader = header + formattedHeader.substring(8);
		
		getPositions(formattedHeader);
		if (unknownOutput) {
			getUnknownPositions(formattedHeader);
		}
		
		ArrayList<ArrayList<String>> mnemonicsUsed = new ArrayList<ArrayList<String>>();
		for (int i = 0 ; i < positionList.size() ; i++) {
			ArrayList<String> mnemonics = new ArrayList<String>(); 
			for (int j = 0 ; j < positionList.get(i).size() ; j++) {
				mnemonics.add(formattedHeader.split(",")[positionList.get(i).get(j)]); 
			}
			mnemonicsUsed.add(mnemonics);
		}

		String finalHeader = header; 
		
		for (int j = 0 ; j < positionList.size() ; j++) {
			if (!positionList.get(j).isEmpty()) {
				finalHeader += formattedHeader.split(",")[positionList.get(j).get(0)] + ",";
			}
			else {
				finalHeader += ",";
			}
		}
		finalHeader += "Bit,Service Co.";
		
		setColumnArray(finalHeader);
		
		finalHeader = addCalcHeaders(finalHeader);
		
		if (unknownOutput) {
			for (int i = 0 ; i < unknownPositions.size() ; i++) {
				unknownData.addUnknownMnemonic(formattedHeader.split(",")[unknownPositions.get(i)]);
			}
		}
		
		finalHeader = removeNewLine(finalHeader);
		
		formattedData.addHeader(finalHeader);
		
		ArrayList<ArrayList<String>> unitList = null;
		if (descriptionData != null) {
			unitList = getMnemonicUnits(mnemonicsUsed, topData.getUwi());
		}
		if (topData.getUwi().equals("100/01-01-080-10W6/0")) {
			for (int i = 0 ; i < unitList.size() ; i++) {
				for (int j = 0 ; j < unitList.get(i).size() ; j++) {
					System.out.print(mnemonicsUsed.get(i).get(j) + " " + unitList.get(i).get(j) + " | ");
				}
				System.out.println("");
			}
		}
		for (int i = 0; i < lasData.getSize(); i++) {
			String formatRow = lasData.getRow(i).trim().replaceAll(" +", ",") + ",";

			Double depth = Double.parseDouble(formatRow.substring(0, formatRow.indexOf(",")));

			String data = formatRow.substring(ordinalIndexOf(formatRow, ",", 1));
	
			String generalWellInfo = uwiInfo.substring(0, uwiInfo.length() - 1) + "," +  depth.toString() + "," + topData.getFormation(depth) + ", ,0.1";
			 
			String formattedRow = generalWellInfo + data; 
			
			String finalRow = generalWellInfo + ",";	
			for (int j = 0 ; j < positionList.size() ; j++) {
				String value = "-999"; 
				int kValue = 0;
				for (int k = 0 ; k < positionList.get(j).size() ; k++) {
					if (!getCol(formattedRow, positionList.get(j).get(k)).contains("-999")) {
						kValue = k;
						value = getCol(formattedRow, positionList.get(j).get(k));
						break;
					}
				}
				if (!value.contains("-999")){
					if (j == 18) {
						double density = Double.parseDouble(value);
						if ( density <= 4.0 && density >= 0.0) {
							finalRow += String.valueOf((density*1000)) + ",";
						}
						else {
							finalRow += value + ","; 
						}
					}
					else if (j > 20 && j < 33) {
						if (unitList == null) { 
							double porosity = -999;
							try {
								porosity = Double.parseDouble(value);
							}
							catch (NumberFormatException e) {
								e.printStackTrace();
							}
							if ( porosity < 1.0 && porosity > -1.0) {
								finalRow += String.valueOf((porosity*100)) + ",";
							}
							else {
								finalRow += value + ","; 
							}
						}
						else if (unitList.get(j).get(kValue).contains("%") || unitList.get(j).get(kValue).toUpperCase().contains("PERC")) {
							finalRow += value + ",";
						}
						else { 
							double porosity = -999;
							try {
								porosity = Double.parseDouble(value);
							}
							catch (NumberFormatException e) {
								e.printStackTrace();
							}
							if ( porosity < 1.0 && porosity > -1.0) {
								finalRow += String.valueOf((porosity*100)) + ",";
							}
							else {
								finalRow += value + ","; 
							}
						}
					}
					else {
						finalRow += value + ","; 	
					}
				}
				else {
					finalRow += ",";
				}
			}
			
			finalRow += lasData.getBit() + "," + lasData.getServiceCo();
			
			finalRow = addCalcValues(finalRow);
			
//			for (int j = 0 ; j < unknownPositions.size() ; j++) {
//				finalRow += "," + getCol(formattedRow, unknownPositions.get(j)); 
//			}
			
			finalRow = removeNewLine(finalRow);
			
			formattedData.addRow(finalRow);
		}
		resetPosition();
		if (lasData.getMdForDir()) {
			formattedData.setMdTrue(); 
		}
		if (unknownOutput) {
			if (unknownData.size() != 0) {
				unknownDataList.add(unknownData);
			}
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
	
	public String removeNewLine(String head) { 
		if (head.contains("\n")) { 
			head = head.replace("\n", "").replace("\r", "");
		}
		return head;
	}
	
	public void resetPosition() {
		positionList = new ArrayList<ArrayList<Integer>>(); 
	}
	
	public String addCalcValues(String row) {
		String[] rowArray = row.split(",");
	
		String separation = "";
		if (!rowArray[columnArray[7]].equals("") && !rowArray[columnArray[6]].equals("")) {
			try { 
				separation = String.valueOf(Double.parseDouble(rowArray[columnArray[7]]) - Double.parseDouble(rowArray[columnArray[6]]));
			}
			catch (Exception e) {
				separation = "";
			}
		}
		String mediumSeparation = "";
		if (!rowArray[columnArray[4]].equals("") && !rowArray[columnArray[3]].equals("")) {
			try {
				mediumSeparation = String.valueOf(Double.parseDouble(rowArray[columnArray[4]]) - Double.parseDouble(rowArray[columnArray[3]]));
			}
			catch (Exception e) {
				mediumSeparation = ""; 
			}
		}
		
		String deepSeparation = "";
		if (!rowArray[columnArray[5]].equals("") && !rowArray[columnArray[4]].equals("")) {
			try {	
				deepSeparation = String.valueOf(Double.parseDouble(rowArray[columnArray[5]]) - Double.parseDouble(rowArray[columnArray[4]]));
			}
			catch (Exception e) {
				deepSeparation = ""; 
			}
		}
		
		String mudCake = "";
		if (!rowArray[columnArray[8]].equals("") && !rowArray[columnArray[9]].equals("")) {
			try { 
				mudCake = String.valueOf(Double.parseDouble(rowArray[columnArray[8]]) - Double.parseDouble(rowArray[columnArray[9]]));
			}
			catch (Exception e) {
				mudCake = ""; 
			}
		}	
		
		String subsea = "";
		if (!rowArray[columnArray[0]].equals("") && !rowArray[columnArray[1]].equals("")) {
			try {
				subsea = String.valueOf(Double.parseDouble(rowArray[columnArray[0]]) - Double.parseDouble(rowArray[columnArray[1]]));
			}	
			catch (Exception e) {
				subsea = "";
			}
		}
		
		String sandstonePoro = "";  
		if (!rowArray[columnArray[10]].equals("")) {
			try { 
				sandstonePoro = String.valueOf((Double.parseDouble(rowArray[columnArray[10]]) - 2650) / -16.50);
			}
			catch (Exception e) {
				sandstonePoro = ""; 
			}
		}
		
		String limestonePoro = "";
		if (!rowArray[columnArray[10]].equals("")) {
			try {
				limestonePoro = String.valueOf((Double.parseDouble(rowArray[columnArray[10]]) - 2710) / -17.10);
			}
			catch (Exception e) {
				limestonePoro = "";
			}
		}
		
		String dolomitePoro = ""; 
		if (!rowArray[columnArray[10]].equals("")) {
			try {
				dolomitePoro = String.valueOf((Double.parseDouble(rowArray[columnArray[10]]) - 2870) / -18.70);
			}
			catch (Exception e) { 
				dolomitePoro = "";
			}
		}
		
		String temp = row + "," + separation + "," + mediumSeparation + "," + deepSeparation + "," + mudCake + "," + subsea + "," + sandstonePoro + "," + limestonePoro + "," + dolomitePoro;
		
		return temp;
	}
	
	public String addCalcHeaders(String header) {
		String sep = header + ",Separation,Medium-Shallow Separation,Deep-Medium Separation,Mudcakes,Subsea,Calculated Density Porosity Sandstone,Calculated Density Porosity Limestone,Calculated Density Porosity Dolomite";
		return sep;
	}
	
	public void getUnknownPositions(String header) { 
		 String[] headerArray = header.split(",");
		 boolean[] checkIsUnknown = new boolean[headerArray.length];
		 for (int i = headerOffset ; i < checkIsUnknown.length ; i++) {
			 checkIsUnknown[i] = true;
		 }
		 for (int i = headerOffset ; i < headerArray.length ; i++) {
			 for (int j = 0 ; j < mnemonics.size() ; j++) {
				 for (int k = 0 ; k < mnemonics.get(j).getMnemonics().size() ; k++) {
					 if (headerArray[i].equals(mnemonics.get(j).getMnemonics().get(k))){ 
						 checkIsUnknown[i] = false;
					 }
				 }
			 }
		 }
		 for (int i = headerOffset ; i < checkIsUnknown.length; i++) {
			 if (checkIsUnknown[i]) {
				 unknownPositions.add(i);
			 }
		 }
	}
	
	public void getPositions(String header) {
		String previousCal = null;
		String[] headerArray = header.split(",");
		
		for (int i = 0 ; i < mnemonics.size()-1 ; i++) {
			ArrayList<Integer> columnPosition = new ArrayList<Integer>(); 
			for (int j = 0 ; j < mnemonics.get(i).getMnemonics().size() ; j++) {
				for (int k = 0 ; k < headerArray.length ; k++) {
					if (headerArray[k].equals(mnemonics.get(i).getMnemonics().get(j))) { 
						columnPosition.add(k); 
					}
				}
			}
			positionList.add(columnPosition);
		}
		
		int caliperIndex = 0;
		ArrayList<Integer> firstCalPosition = new ArrayList<Integer>(); 
		while (caliperIndex < mnemonics.get(mnemonics.size()-1).getMnemonics().size()) {
			if (firstCalPosition.size() == 0) {
				for (int j = 0 ; j < headerArray.length ; j++) {
					if (headerArray[j].equals(mnemonics.get(mnemonics.size()-1).getMnemonics().get(caliperIndex))) {
						firstCalPosition.add(j); 
					}
				}
			}
			else {
				break; 
			}
			caliperIndex++;
		}
		positionList.add(firstCalPosition);
		ArrayList<Integer> secondCalPosition = new ArrayList<Integer>(); 
		while (caliperIndex < mnemonics.get(mnemonics.size()-1).getMnemonics().size()) {
			if (secondCalPosition.size() == 0) {
				for (int j = 0 ; j < headerArray.length ; j++) {
					if (headerArray[j].equals(mnemonics.get(mnemonics.size()-1).getMnemonics().get(caliperIndex))) {
						secondCalPosition.add(j); 
					}
				}
			}
			else {
				break; 
			}
			caliperIndex++;
		}
		positionList.add(secondCalPosition);
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
