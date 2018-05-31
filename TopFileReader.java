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

	private String topFilePath = "C:\\Users\\jhung\\SpotfireDataFiles\\AllGeorgeTopsMikwan.xlsx";
	private ArrayList<TopData> topDataList; 
	private String currentUwi = ""; 
	private ArrayList<String> data; 
	private String township;
	private String topForm;
	private String bottomForm;
	private double formBuffer;
	
	public TopFileReader(String top, String bottom, String town, double buffer) {
		topForm = top;
		bottomForm = bottom; 
		township = town; 
		formBuffer = buffer;
		topDataList = new ArrayList<TopData>(); 
		data = new ArrayList<String>();
	}
	
	public ArrayList<TopData> readFile() throws IOException { 
		FileInputStream inputStream = new FileInputStream(new File(topFilePath)); 
		
		Workbook workbook = new XSSFWorkbook(inputStream); 
		Sheet firstSheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = firstSheet.iterator(); 
		iterator.next(); 
		boolean topCheck = false;
		
		while (iterator.hasNext()) {
			Row nextRow = iterator.next();
			Iterator<Cell> cellIterator = nextRow.cellIterator();
			
			int index = 0; 
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				if (index == 0 && !cell.getStringCellValue().substring(9, 17).equals(township))	{
					index++;
					break;
				}
				
				if (index == 0 && !currentUwi.equals(cell.getStringCellValue())) {
					if (!data.isEmpty()) {
						topDataList.add(new TopData(data));
						topCheck = false;
					}
					data = new ArrayList<String>(); 
					currentUwi = cell.getStringCellValue();
					data.add(currentUwi);
				}
				
				if (index == 2) {
					if (cell.getStringCellValue().substring(1).equals(bottomForm)) {
						data.add(cell.getStringCellValue());
						cell = cellIterator.next(); 
						data.add(String.valueOf(cell.getNumericCellValue()));
						index++; 
						break;
					}
					if (cell.getStringCellValue().substring(1).equals(topForm)) {
						topCheck = true; 
						index++;
						break;
					}
					if (topCheck) {
						data.add(cell.getStringCellValue());
					}
				}
				
				if (index == 3) {
					if (topCheck) {
						data.add(String.valueOf(cell.getNumericCellValue()));
						break;
					}
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
		UserInput userInput = new UserInput();
		userInput.readInput();
		TopFileReader topFileReader = new TopFileReader(userInput.getTopForm(), userInput.getBottomForm()
				, userInput .getTownship(), userInput.getFormBuffer()); 
		try {
			ArrayList<TopData> topDataList = topFileReader.readFile();
			for (int i = 0; i < topDataList.size(); i++) {
				topDataList.get(i).displayTop();
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
