import java.util.ArrayList;

import org.apache.commons.codec.binary.StringUtils;

public class DataWriter {

	private String header;
	private int row;
	private ArrayList<MnemonicData> mnemonics;
	private int[] position;
	private ArrayList<Integer> columnArray; 
	private int headerOffset; 
	
	public DataWriter(ArrayList<MnemonicData> m) { 
		mnemonics = m;
		position = new int[m.size()+1];
	}
	
	public void setColumnArray(String head) {
		String [] headArray = head.split(",");
		for (int i = 0 ; i < headArray.length ; i++) {
			if (headArray[i].startsWith("KB")) {
				columnArray.add(i);
			}
			if (headArray[i].equals("DEPT")) {
				columnArray.add(i);
			}
			if (headArray[i].startsWith("SP")) {
				columnArray.add(i);
			}
			if (headArray[i].startsWith("SRES:")) {
				columnArray.add(i);
			}
			if (headArray[i].startsWith("MRES")) { 
				columnArray.add(i);
			}
			if (headArray[i].startsWith("DRES")) { 
				columnArray.add(i);
			}
			if (headArray[i].startsWith("NPHI-SS")) {
				columnArray.add(i);
			}
			if (headArray[i].startsWith("DPHI-SS")) {
				columnArray.add(i);
			}
			if (headArray[i].equals("Caliper")) {
				columnArray.add(i);
			}
			if (headArray[i].equals("Bit")) {
				columnArray.add(i);
			}
		}
	}

	public FormattedData formatData(String h, String uwiInfo, LasData lasData, TopData topData) {
		header = h; 
		header += "DEPT,Subsea,Formation,VKNS Isopach,Interval (step),";
		headerOffset = header.split(",").length;
		
		FormattedData formattedData = new FormattedData();

		String formattedHeader = lasData.getHeader().trim().replaceAll(" +", ",");

		formattedHeader = header + formattedHeader.substring(8);
		
		getPositions(formattedHeader);
		
//		for (int i = 0; i < position.length ; i++ ) {
//			System.out.print("|" + position[i]);
//		}
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
		
		//setColumnArray(finalHeader);
		
		// finalHeader = addCalcHeaders(finalHeader);
		
		
		formattedData.addHeader(finalHeader);

		for (int i = 0; i < lasData.getSize(); i++) {
			String formatRow = lasData.getRow(i).trim().replaceAll(" +", ",") + ",";

			Double depth = Double.parseDouble(formatRow.substring(0, formatRow.indexOf(",")));

			Double subsea = 0.0;
					//Double.parseDouble(getCol(uwiInfo, 5)) - depth;

			String data = formatRow.substring(ordinalIndexOf(formatRow, ",", 1));
	
			String generalWellInfo = uwiInfo.substring(0, uwiInfo.length() - 1) + "," +  depth.toString() + "," + subsea.toString() + "," + topData.getFormation(depth) + ", ,0.1";
			 
			String formattedRow = generalWellInfo + data; 
			
			String finalRow = generalWellInfo + ",";	
			for (int j = 0 ; j < position.length ; j++) {
				boolean checkNum = true; 
//				if (j == 12 && Double.parseDouble(getCol(formattedRow, position[j])) == 0) {
//					checkNum = false;
//				}
				if (position[j] != 0 && !getCol(formattedRow, position[j]).contains("-999")){
					if (j > 24 || j < 34) {
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
			
			//finalRow = addCalcValues(finalRow);
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
		position = new int[mnemonics.size() + 1];
	}
	
	public String addCalcValues(String row) {
		String[] rowArray = row.split(",");
	
		String separation = "";
		if (!rowArray[columnArray.get(5)].equals("") && !rowArray[columnArray.get(4)].equals("")) {
			separation = String.valueOf(Double.parseDouble(rowArray[columnArray.get(5)]) - Double.parseDouble(rowArray[columnArray.get(4)]));
		}
		
		String mediumSeparation = "";
		if (!rowArray[columnArray.get(3)].equals("") && !rowArray[columnArray.get(2)].equals("")) {
			mediumSeparation = String.valueOf(Double.parseDouble(rowArray[columnArray.get(3)]) - Double.parseDouble(rowArray[columnArray.get(2)]));
		}
		
		String deepSeparation = "";
		if (!rowArray[columnArray.get(5)].equals("") && !rowArray[columnArray.get(5)].equals("")) {
			deepSeparation = String.valueOf(Double.parseDouble(rowArray[columnArray.get(5)]) - Double.parseDouble(rowArray[columnArray.get(5)]));
		}
		
		String mudCake = "";
		if (!rowArray[columnArray.get(5)].equals("") && !rowArray[columnArray.get(5)].equals("")) {
			mudCake = String.valueOf(Double.parseDouble(rowArray[columnArray.get(5)]) - Double.parseDouble(rowArray[columnArray.get(5)]));
		}	
	
		String temp = row.substring(0, ordinalIndexOf(row, "," , 27)) + "," + separation + row.substring(ordinalIndexOf(row, "," , 27), ordinalIndexOf(row, "," , 30))
		 + "," + mediumSeparation + "," + deepSeparation + row.substring(ordinalIndexOf(row, "," , 30), ordinalIndexOf(row, "," , 37)) + "," + mudCake
		 + row.substring(ordinalIndexOf(row, ",", 37));
		
		return temp;
	}
	
	public String addCalcHeaders(String header) {
		String sep = header + ",Separation,Medium-Shallow Separation,Deep-Medium Separation,Mudcakes,Subsea";
		return sep;
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
