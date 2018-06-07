import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WorkingFileReader {
	
	private String workingFilePath = "C:\\Users\\jhung\\SpotfireDataFiles\\Garrington\\GarringtonGWIunFiltered.xlsx";
	private int upperbound;
	private int lowerbound; 
	private ArrayList<Integer> dateCol;

	
	public WorkingFileReader(int nw, int se) {
		upperbound = nw; 
		lowerbound = se;
		dateCol = new ArrayList<Integer>();
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
								row += cell.getStringCellValue();
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
				if (row.startsWith("Sort")) {
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
			System.out.println("upper" + upperbound); 
			System.out.println("Lower" + lowerbound);
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
				System.out.println(rowArray[index]);
				dateCol.add(index);
				System.out.println(index);
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
