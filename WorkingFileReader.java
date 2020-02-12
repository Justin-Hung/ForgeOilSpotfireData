import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WorkingFileReader {
	
	private String workingFilePath = "C:\\Users\\jhung\\SpotfireDataFiles\\NorthernViking\\GWINorthernViking.xlsx";
	private int upperbound;
	private int lowerbound; 
	private ArrayList<Integer> dateCol;

	
	public WorkingFileReader(int nw, int se) {
		upperbound = nw; 
		lowerbound = se;
		dateCol = new ArrayList<Integer>();
	}
	
	public WorkingFileReader(int nw, int se, String filePath) {
		workingFilePath = filePath;
		upperbound = nw; 
		lowerbound = se;
		dateCol = new ArrayList<Integer>();
	}
	
	public WorkingFileData readCsvFile() {
		try { 
			WorkingFileData data = new WorkingFileData(); 
			
			BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(workingFilePath)));
			String line = bufferedReader.readLine();
			if (line.contains("©2018 IHS Markit")) {
				line = bufferedReader.readLine();
			}
			
			int headerLength = 0; 
			if (line.startsWith("Sort")) {
				data.addHeader(line + ",");
				headerLength = line.split(",").length; 
				line = bufferedReader.readLine(); 
			}
			else {				
				 JOptionPane.showMessageDialog(null, "Incorrect GWI format, check if 'Sort UWI' in first column", "Error", JOptionPane.INFORMATION_MESSAGE);
			}

			while (line != null) {
				String[] lineArray = line.split(",");
				int sortUwi = 0;
				try {
					sortUwi = Integer.parseInt(lineArray[0].substring(2, 10));
					if (sortUwi >= lowerbound && sortUwi <= upperbound) {
						line = filterCommas(line, headerLength);
						line = filterNA(line);
						data.addRow(line + ",");
					}
				}
				catch (StringIndexOutOfBoundsException e) { 
				}
				line = bufferedReader.readLine();
			}
			bufferedReader.close();
			return data;
		}
		catch (Exception e) { 
			e.printStackTrace(); 
			System.err.println("CSV GWI READER NOT WORKING");
			return null;
		}
	}
	
	public String filterCommas(String line, int headerLength) {
		
		String templine = line + "ignore";
		String [] lineArray = templine.split(","); 
		String lastCol = lineArray[lineArray.length-1];
		lineArray[lineArray.length-1] = lastCol.substring(0, lastCol.indexOf("ignore"));
		if (lineArray.length > headerLength) {
			char[] charArray = line.toCharArray();
			boolean dangerZone = false;
			for (int i = 0; i < charArray.length ; i++) {
				if (charArray[i] == '"') { 
					dangerZone = flip(dangerZone);
				}
				if (dangerZone && charArray[i] == ',') { 
					charArray[i] = '&';
				}
			}
			return String.valueOf(charArray); 
		}
		else {
			return line; 
		}
	}
	
	public String filterNA(String line) { 
		String templine = line + "ignore";
		String [] lineArray = templine.split(","); 
		String lastCol = lineArray[lineArray.length-1];
		lineArray[lineArray.length-1] = lastCol.substring(0, lastCol.indexOf("ignore"));
		for (int i = 0 ; i < lineArray.length ; i++) { 
			if (lineArray[i].equals("N/A")) { 
				lineArray[i] = ""; 
			}
		}
		String filteredLine = lineArray[0]; 
		for (int i = 1 ; i < lineArray.length ; i++) { 
			filteredLine += "," + lineArray[i];
		}
		return filteredLine; 
	}
	
	public boolean flip(boolean dangerZone) {
		if (dangerZone) {
			return false;
		}
		return true; 
	}
	 
	public WorkingFileData readFile() {
		try { 
			WorkingFileData data = new WorkingFileData(); 
			
			FileInputStream inputStream = new FileInputStream(new File(workingFilePath)); 
			
			Workbook workbook = new XSSFWorkbook(inputStream); 
			Sheet firstSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = firstSheet.iterator(); 
			
			int rowNum = 0;
			while (iterator.hasNext()) {
				String row = "";
				Row nextRow = iterator.next();
				Iterator<Cell> cellIterator = nextRow.cellIterator(); 
				
				int col = 0; 
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					if (col == 0 && rowNum == 0 && cell.getStringCellValue().equals("©2018 IHS Markit")) {
						rowNum--;
						break; 
					}
		
					if (col == 0 && rowNum > 0) {
						int sortUwi = 0;
						try {
							sortUwi = Integer.parseInt(cell.getStringCellValue().substring(2, 10)); 
						}
						catch (StringIndexOutOfBoundsException e) { 
							break; 
						}
						if ( sortUwi < lowerbound || sortUwi > upperbound) {
							break;
						}
					}
					switch(cell.getCellType()) {
						case Cell.CELL_TYPE_STRING:
							if (cell.getStringCellValue().contains(",")) {
								String s = cell.getStringCellValue().replaceAll(",", "&");
								row += s;
								col++; 
								break; 
							}
							else {
								String s = cell.getStringCellValue();
								if (s.equals("N/A")) { 
									s = ""; 
								}
								row += s;
								col++;
								break;
							}
						case Cell.CELL_TYPE_NUMERIC: 
							if (dateCheck(col))
							{
								row += cell.getDateCellValue().getMonth()+1 + "/" + cell.getDateCellValue().getDate() + "/" + cell.getDateCellValue().toString().substring(24);
								col++;
								break;
							}
							else {
								row += String.valueOf(cell.getNumericCellValue());
								col++;
								break; 
							}
						case Cell.CELL_TYPE_BLANK:
							col++;
							break;
					}
					row += ",";
				}
				if (row.contains("Sort")) {
					getDateCols(row);
					data.addHeader(row);
				}
				else if (!row.equals("")) {
					data.addRow(row);
				}
				rowNum++;
			}
			workbook.close(); 
			inputStream.close();
			return data;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean dateCheck(int col) {
		Integer column = col;
		for (int i = 0 ; i < dateCol.size() ; i++) {
			if (column.equals(dateCol.get(i))) {
				return true;
			}
		}
		return false;
	}
	
	public void getDateCols(String row) {
		String[] rowArray = row.split(",");
		Integer index = 0; 
		while (index < rowArray.length) {
			if (rowArray[index].contains("Date")) {
				dateCol.add(index);
			}
			index++;
		}
	}
	
	
	public static void main(String[] args) { 
		UserInput user = new UserInput(); 
		user.readInput();
		WorkingFileReader workingFileReader = new WorkingFileReader(user.getNwSortUwi(), user.getSeSortUwi()); 
		workingFileReader.readFile().display();
	}
}
