import java.util.ArrayList;

import org.apache.commons.codec.binary.StringUtils;

public class DataWriter {

	private String header;
	private int row;
	private ArrayList<MnemonicData> mnemonics;
	private ArrayList<Integer> unknownPositions; 
	private int[] position;
	private int[] columnArray;
	private int headerOffset; 
	
	public int getHeaderOffset() { return headerOffset; }
	
	public DataWriter() {}
	
	public DataWriter(ArrayList<MnemonicData> m) { 
		mnemonics = m;
		position = new int[m.size()+1];
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
	
	public FormattedData secondaryFormatData(String h, String uwiInfo, LasData lasData, TopData topData, ArrayList<TopData> secondaryTopDataList) {
	
		TopData secondaryTopData = null;
		for (int i = 0 ; i < secondaryTopDataList.size() ; i++) {
			if (topData.getUwi().equals(secondaryTopDataList.get(i).getUwi())) {
				secondaryTopData = secondaryTopDataList.get(i);
			}
		}
		if (secondaryTopData == null) {
//			System.out.println(topData.getUwi());
//			System.out.println("HAS NO SECONDARY");
		}
		unknownPositions = new ArrayList<Integer>(); 
		columnArray = new int[11];
		header = h; 

		header += "DEPT,User Formation,System Formation,VKNS Isopach,Interval (step),";

		headerOffset = header.split(",").length;
		
		FormattedData formattedData = new FormattedData();

		String formattedHeader = lasData.getHeader().trim().replaceAll(" +", ",");
		
		formattedHeader = header + formattedHeader.substring(8);
		
		getPositions(formattedHeader);
		getUnknownPositions(formattedHeader);
		
		String finalHeader = header; 
		
		for (int j = 0 ; j < position.length ; j++) {
			if (position[j] != 0) {
				finalHeader += formattedHeader.split(",")[position[j]] + ",";
			}
			else {
				finalHeader += ",";
			}
		}
		finalHeader += "Bit,Service Co.";
		
		setColumnArray(finalHeader);
		
		finalHeader = addCalcHeaders(finalHeader);
		
		for (int i = 0 ; i < unknownPositions.size() ; i++) {
			finalHeader += "," + formattedHeader.split(",")[unknownPositions.get(i)];
		}
		
		finalHeader = removeNewLine(finalHeader);
		
		formattedData.addHeader(finalHeader);

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
			for (int j = 0 ; j < position.length ; j++) {
				boolean checkNum = true; 
				if (position[j] != 0 && !getCol(formattedRow, position[j]).contains("-999")){
					if (j > 21 || j < 34) {
						double porosity = -999;
						try {
							porosity = Double.parseDouble(getCol(formattedRow, position[j]));
						}
						catch (NumberFormatException e) {
							e.printStackTrace();
						}
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
			
			finalRow += lasData.getBit() + "," + lasData.getServiceCo();
			
			finalRow = addCalcValues(finalRow);
			
			for (int j = 0 ; j < unknownPositions.size() ; j++) {
				finalRow += "," + getCol(formattedRow, unknownPositions.get(j)); 
			}
			
			finalRow = removeNewLine(finalRow);
			
			formattedData.addRow(finalRow);
		}
		resetPosition();
		if (lasData.getMdForDir()) {
			formattedData.setMdTrue(); 
		}
		return formattedData;
	}

	public FormattedData formatData(String h, String uwiInfo, LasData lasData, TopData topData) {
		unknownPositions = new ArrayList<Integer>(); 
		columnArray = new int[11];
		header = h; 
		header += "DEPT,Formation,VKNS Isopach,Interval (step),";
		headerOffset = header.split(",").length;
		
		FormattedData formattedData = new FormattedData();

		String formattedHeader = lasData.getHeader().trim().replaceAll(" +", ",");
		
		formattedHeader = header + formattedHeader.substring(8);
		
		getPositions(formattedHeader);
		getUnknownPositions(formattedHeader);
		
		String finalHeader = header; 
		
		for (int j = 0 ; j < position.length ; j++) {
			if (position[j] != 0) {
				finalHeader += formattedHeader.split(",")[position[j]] + ",";
			}
			else {
				finalHeader += ",";
			}
		}
		finalHeader += "Bit,Service Co.";
		
		setColumnArray(finalHeader);
		
		finalHeader = addCalcHeaders(finalHeader);
		
		for (int i = 0 ; i < unknownPositions.size() ; i++) {
			finalHeader += "," + formattedHeader.split(",")[unknownPositions.get(i)];
		}
		
		finalHeader = removeNewLine(finalHeader);
		
		formattedData.addHeader(finalHeader);

		for (int i = 0; i < lasData.getSize(); i++) {
			String formatRow = lasData.getRow(i).trim().replaceAll(" +", ",") + ",";

			Double depth = Double.parseDouble(formatRow.substring(0, formatRow.indexOf(",")));

			String data = formatRow.substring(ordinalIndexOf(formatRow, ",", 1));
	
			String generalWellInfo = uwiInfo.substring(0, uwiInfo.length() - 1) + "," +  depth.toString() + "," + topData.getFormation(depth) + ", ,0.1";
			 
			String formattedRow = generalWellInfo + data; 
			
			String finalRow = generalWellInfo + ",";	
			for (int j = 0 ; j < position.length ; j++) {
				boolean checkNum = true; 
				if (position[j] != 0 && !getCol(formattedRow, position[j]).contains("-999")){
					if (j > 21 && j < 30) {
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
			
			finalRow += lasData.getBit() + "," + lasData.getServiceCo();
			
			finalRow = addCalcValues(finalRow);
			
			for (int j = 0 ; j < unknownPositions.size() ; j++) {
				finalRow += "," + getCol(formattedRow, unknownPositions.get(j)); 
			}
			
			finalRow = removeNewLine(finalRow);
			
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
	
	public String removeNewLine(String head) { 
		if (head.contains("\n")) { 
			head = head.replace("\n", "").replace("\r", "");
		}
		return head;
	}
	
	public void resetPosition() {
		position = new int[mnemonics.size() + 1];
	}
	
	public String addCalcValues(String row) {
		String[] rowArray = row.split(",");
	
		String separation = "";
		if (!rowArray[columnArray[7]].equals("") && !rowArray[columnArray[6]].equals("")) {
			separation = String.valueOf(Double.parseDouble(rowArray[columnArray[7]]) - Double.parseDouble(rowArray[columnArray[6]]));
		}
		
		String mediumSeparation = "";
		if (!rowArray[columnArray[4]].equals("") && !rowArray[columnArray[3]].equals("")) {
			mediumSeparation = String.valueOf(Double.parseDouble(rowArray[columnArray[4]]) - Double.parseDouble(rowArray[columnArray[3]]));
		}
		
		String deepSeparation = "";
		if (!rowArray[columnArray[5]].equals("") && !rowArray[columnArray[4]].equals("")) {
			deepSeparation = String.valueOf(Double.parseDouble(rowArray[columnArray[5]]) - Double.parseDouble(rowArray[columnArray[4]]));
		}
		
		String mudCake = "";
		if (!rowArray[columnArray[8]].equals("") && !rowArray[columnArray[9]].equals("")) {
			mudCake = String.valueOf(Double.parseDouble(rowArray[columnArray[8]]) - Double.parseDouble(rowArray[columnArray[9]]));
		}	
		
		String subsea = "";
		if (!rowArray[columnArray[0]].equals("") && !rowArray[columnArray[1]].equals("")) {
			subsea = String.valueOf(Double.parseDouble(rowArray[columnArray[0]]) - Double.parseDouble(rowArray[columnArray[1]]));
		}	
		
		String sandstonePoro = "";
		if (!rowArray[columnArray[10]].equals("")) {
			sandstonePoro = String.valueOf((Double.parseDouble(rowArray[columnArray[10]]) - 2650) / -16.50);
		}
		
		String limestonePoro = "";
		if (!rowArray[columnArray[10]].equals("")) {
			limestonePoro = String.valueOf((Double.parseDouble(rowArray[columnArray[10]]) - 2710) / -17.10);
		}
		
		String dolomitePoro = ""; 
		if (!rowArray[columnArray[10]].equals("")) {
			dolomitePoro = String.valueOf((Double.parseDouble(rowArray[columnArray[10]]) - 2870) / -18.70);
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
		
		int caliperIndex = 0;
		while (caliperIndex < mnemonics.get(mnemonics.size()-1).getMnemonics().size()) {
			if (position[mnemonics.size()-1] == 0) {
				for (int j = 0 ; j < headerArray.length ; j++) {
					if (headerArray[j].equals(mnemonics.get(mnemonics.size()-1).getMnemonics().get(caliperIndex))) {
						position[mnemonics.size()-1] = j; 
					}
				}
			}
			else {
				break; 
			}
			caliperIndex++;
		}
		while (caliperIndex < mnemonics.get(mnemonics.size()-1).getMnemonics().size()) {
			if (position[mnemonics.size()] == 0) {
				for (int j = 0 ; j < headerArray.length ; j++) {
					if (headerArray[j].equals(mnemonics.get(mnemonics.size()-1).getMnemonics().get(caliperIndex))) {
						position[mnemonics.size()] = j; 
					}
				}
			}
			else {
				break; 
			}
			caliperIndex++;
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
