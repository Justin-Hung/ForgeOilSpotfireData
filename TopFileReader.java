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
	private int upperbound;
	private int lowerbound; 
	private double upperbuffer;
	private double lowerbuffer; 
	
	private ArrayList<String> formations;
	
	public TopFileReader(ArrayList<String> forms, int nw, int se, double upperbuff, double lowerbuff) {
		upperbuffer = upperbuff; 
		lowerbuffer = lowerbuff;
		formations = forms;
		upperbound = nw; 
		lowerbound = se;
		topDataList = new ArrayList<TopData>(); 
		data = new ArrayList<String>();
	}
	
	public ArrayList<TopData> readFile() throws IOException { 
		UserInput sort = new UserInput(); 
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
				if (index == 0)	{
					int sortUwi = sort.sortTownship(cell.getStringCellValue().substring(6, 17));
					if (sortUwi < lowerbound || sortUwi > upperbound) {
						break;
					}
				}
				
				if (index == 0 && !currentUwi.equals(cell.getStringCellValue())) {
					if (!data.isEmpty()) {
						topDataList.add(new TopData(data, upperbuffer, lowerbuffer));
						topCheck = false;
					}
					data = new ArrayList<String>(); 
					currentUwi = cell.getStringCellValue();
					data.add(currentUwi);
				}
				
				if (index == 2) {
					if (cell.getStringCellValue().substring(1).equals(formations.get(formations.size()-1))) {
						data.add(cell.getStringCellValue());
						cell = cellIterator.next(); 
						data.add(String.valueOf(cell.getNumericCellValue()));
						index++; 
						topCheck = false; 
						break;
					}
					if (cell.getStringCellValue().substring(1).equals(formations.get(0))) {
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
		topDataList.add(new TopData(data, upperbuffer, lowerbuffer));
		workbook.close(); 
		inputStream.close();
		return topDataList;
	}
	
}
