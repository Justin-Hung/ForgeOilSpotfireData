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

	private String topFilePath = "C:\\Users\\jhung\\SpotfireDataFiles\\kc_bellatrixVKNGTopsFilteredAgain.xlsx";
	private ArrayList<TopData> topDataList; 
	private String currentUwi = ""; 
	private ArrayList<String> data; 
	private String township;
	private String topForm;
	private String bottomForm;
	private double formBuffer;
	
	//String top, String bottom, String town, double buffer
	public TopFileReader() {
//		topForm = top;
//		bottomForm = bottom; 
//		township = town; 
//		formBuffer = buffer;
		topDataList = new ArrayList<TopData>(); 
		data = new ArrayList<String>();
	}
	
	public ArrayList<TopData> readFile() throws IOException { 
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
						break; 
					case Cell.CELL_TYPE_NUMERIC: 
						if (index == 3) {
							data.add(String.valueOf(cell.getNumericCellValue()));
						}
						break; 
				}
				index++; 
			}
		}
		topDataList.add(new TopData(data));
		workbook.close(); 
		inputStream.close();
		return topDataList;
	}
	
	public static void main(String[] args) { 
		TopFileReader topFileReader = new TopFileReader(); 
		ArrayList<TopData> topDataList;
		try {
			topDataList = topFileReader.readFile();
			for (int i = 0; i < topDataList.size() ; i++) {
				topDataList.get(i).displayTop();
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
