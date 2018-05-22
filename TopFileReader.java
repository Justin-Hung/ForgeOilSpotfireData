import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TopFileReader {

	private String topFilePath = "C:\\Users\\jhung\\SpotfireDataFiles\\GeorgeTopsT37R26_Filtered.xlsx";
	private ArrayList<TopData> topDataList; 
	private String currentUwi = ""; 
	private ArrayList<String> data; 
	
	public TopFileReader() {
		topDataList = new ArrayList<TopData>(); 
		data = new ArrayList<String>();
	}
	
	public void readFile() throws IOException { 
		FileInputStream inputStream = new FileInputStream(new File(topFilePath)); 
		
		Workbook workbook = new XSSFWorkbook(inputStream); 
		Sheet firstSheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = firstSheet.iterator(); 
		iterator.next(); 
		
		while (iterator.hasNext()) {
			Row nextRow = iterator.next();
			Iterator<Cell> cellIterator = nextRow.cellIterator();
			
			int index = 0; 
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				switch(cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						if (index == 0 && !currentUwi.equals(cell.getStringCellValue())) {
							if (!data.isEmpty()) {
								topDataList.add(new TopData(data));
							}
							data = new ArrayList<String>(); 
							currentUwi = cell.getStringCellValue();
							data.add(currentUwi);
						}
						if (index == 2) {
							data.add(cell.getStringCellValue());
						}
						//System.out.print(cell.getStringCellValue());
						break; 
					case Cell.CELL_TYPE_BOOLEAN:
						//System.out.print(cell.getBooleanCellValue());
						break;
					case Cell.CELL_TYPE_NUMERIC: 
						if (index == 3) {
							data.add(String.valueOf(cell.getNumericCellValue()));
						}
						//System.out.print(cell.getNumericCellValue());
						break; 
				}
				//System.out.print(";");
				index++; 
			}
			//System.out.println(); 
		}
		topDataList.add(new TopData(data));
		workbook.close(); 
		inputStream.close();
	}
	
	public void test() {
		topDataList.get(topDataList.size()-1).displayTop();
	}
	
	public static void main(String[] args) { 
		TopFileReader fileReader = new TopFileReader(); 
		try {
			fileReader.readFile();
			fileReader.test();
		}
		catch(Exception e) { 
			e.printStackTrace();
		}
	}
}
