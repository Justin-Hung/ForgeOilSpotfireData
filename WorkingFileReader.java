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
	private String workingFilePath = "C:\\Users\\jhung\\SpotfireDataFiles\\T36\\WorkingFileT36R23.xlsx";
	
	public WorkingFileData readFile() {
		try { 
			WorkingFileData data = new WorkingFileData(); 
		
			FileInputStream inputStream = new FileInputStream(new File(workingFilePath)); 
			
			Workbook workbook = new XSSFWorkbook(inputStream); 
			Sheet firstSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = firstSheet.iterator(); 
			
			while (iterator.hasNext()) {
				String row = "";
				Row nextRow = iterator.next();
				Iterator<Cell> cellIterator = nextRow.cellIterator(); 
				
				int col = 0; 
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					
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
							if (col > 13 && col < 17)
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
					}
					row += ",";
				}
				if (row.startsWith("Sort")) {
					data.addHeader(row);
				}
				else {
					data.addRow(row);
				}
			}
			workbook.close(); 
			inputStream.close();
			return data;
		}
		catch (Exception e) {
			return null;
		}
	}
	
	public static void main(String[] args) { 
		WorkingFileReader workingFileReader = new WorkingFileReader(); 
		workingFileReader.readFile().display();
	}
}
