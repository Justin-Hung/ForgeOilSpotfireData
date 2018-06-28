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

	private String topFilePath = "C:\\Users\\jhung\\SpotfireDataFiles\\NorthernViking\\newTops.xlsx";

	private ArrayList<TopData> topDataList; 
	private String currentUwi = ""; 
	private ArrayList<String> data; 
	private int upperbound;
	private int lowerbound; 
	private double upperbuffer;
	private double lowerbuffer; 
	private String upperFormation; 
	private String previousFormation; 
	private boolean checkBottom; 
	
	private final int uwiCol = 1;
	private final int formationCol = 3; 
	private final int tvdCol = 5;
	
	private ArrayList<String> formations;
	
	public TopFileReader(ArrayList<String> forms, int nw, int se, double upperbuff, double lowerbuff) {
		upperbuffer = upperbuff; 
		lowerbuffer = lowerbuff;
		formations = forms;
		upperbound = nw; 
		lowerbound = se;
		upperFormation = "UNKNOWN";
		topDataList = new ArrayList<TopData>(); 
		data = new ArrayList<String>();
		checkBottom = false;
	}
	
	public TopFileReader(ArrayList<String> forms, int nw, int se, double upperbuff, double lowerbuff, String filePath) {
		topFilePath = filePath;
		upperbuffer = upperbuff; 
		lowerbuffer = lowerbuff;
		formations = forms;
		upperbound = nw; 
		lowerbound = se;
		upperFormation = "UNKNOWN";
		topDataList = new ArrayList<TopData>(); 
		data = new ArrayList<String>();
		checkBottom = false;
	}
	
	public boolean checkIsEmptyFormation(Iterator<Cell> iterator, String test) { 
		Iterator<Cell> cellIterator = iterator;
		int index = 0;
		Cell cell = null;
		while (index <= formationCol) {
			cell = cellIterator.next();
			index++; 
		}
		System.out.println(cell.getStringCellValue());
		if (cell.getStringCellValue().equals("")) {
			return true;
		}
		return false;
	}
	
	public ArrayList<TopData> readFile() { 
		try {
			UserInput sort = new UserInput(); 
			FileInputStream inputStream = new FileInputStream(new File(topFilePath)); 
			Workbook workbook = new XSSFWorkbook(inputStream); 
			Sheet firstSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = firstSheet.iterator(); 
		
			Row checkFirstRow = iterator.next(); 
			Iterator<Cell> checkFirstRowIterator = checkFirstRow.cellIterator(); 
			Cell checkFirstRowCell = checkFirstRowIterator.next();
			if (checkFirstRowCell.getStringCellValue().equals("©2018 IHS Markit")) {
				iterator.next();
			}
			
			boolean topCheck = false;
			int row = 0;
			while (iterator.hasNext()) {
				Row nextRow = iterator.next();
				Iterator<Cell> cellIterator = nextRow.cellIterator();
			
				int index = 0; 
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					if (index == uwiCol) {
						int sortUwi = 0;
						if (cell.getStringCellValue().startsWith("1") ) {
							sortUwi = sort.sortTownship(cell.getStringCellValue().substring(7, 18));
						}
						else {
							sortUwi = sort.sortTownship(cell.getStringCellValue().substring(6, 17));
						}
						if (sortUwi < lowerbound || sortUwi > upperbound || checkIsEmptyFormation(nextRow.cellIterator(), cell.getStringCellValue().substring(7, 18))) {
							break;
						}
					}
				
					if (index == uwiCol && !currentUwi.equals(cell.getStringCellValue())) {
						if (!data.isEmpty()) {
							if (formations.get(formations.size()-1).equals("BOTTOM") || checkBottom) {
								topDataList.add(new TopData(data, upperbuffer, lowerbuffer, upperFormation, true));
								checkBottom = false;
							}
							else {
								topDataList.add(new TopData(data, upperbuffer, lowerbuffer, upperFormation, false)); 
							}
							topCheck = false;
						}
						data = new ArrayList<String>(); 
						currentUwi = cell.getStringCellValue();
						data.add(currentUwi);
						previousFormation = "UNKNOWN";
					}
				
					if (index == formationCol) {
						if (cell.getStringCellValue().substring(1).equals(formations.get(formations.size()-1)) || cell.getStringCellValue().equals(formations.get(formations.size()-1))) {
							data.add(cell.getStringCellValue());
							while (index < tvdCol) { 
								cell = cellIterator.next(); 
								index++; 
							}
							data.add(String.valueOf(cell.getNumericCellValue()));
							if (formations.get(formations.size()-1).equals(formations.get(0))) {
								upperFormation = previousFormation;
							}
							//check next row for bottom
							Iterator<Row> checkNextRow = iterator;
						
							if (checkNextRow.hasNext()) {
								Row checkRow = checkNextRow.next();
								Iterator<Cell> checkCellIterator = checkRow.cellIterator();
								int checkIndex = 0;
								while (checkCellIterator.hasNext()) {
									Cell checkCell = checkCellIterator.next();
									if (checkIndex == uwiCol && !currentUwi.equals(checkCell.getStringCellValue())) {
										checkBottom = true;
										break;
									}
									if (checkIndex == formationCol) {
										data.add(checkCell.getStringCellValue());
									}
									if (checkIndex == tvdCol) {
										data.add(String.valueOf(checkCell.getNumericCellValue()));
										break;
									}
									checkIndex++;
								}
							}
							else {
								checkBottom = true; 
							}
							topCheck = false; 
							break;
						}
						if (cell.getStringCellValue().substring(1).equals(formations.get(0)) || cell.getStringCellValue().equals(formations.get(0))) {
							upperFormation = previousFormation;
							topCheck = true; 
						}
						if (topCheck) {
							data.add(cell.getStringCellValue());
						}
						previousFormation = cell.getStringCellValue();
					}
					
					if (index == tvdCol) {
						if (topCheck) {
							data.add(String.valueOf(cell.getNumericCellValue()));
							break;
						}
					}
					index++; 
				}
				row++;
			}
			if (formations.get(formations.size()-1).equals("BOTTOM") || checkBottom) {
				topDataList.add(new TopData(data, upperbuffer, lowerbuffer, upperFormation, true));
				checkBottom = false;
			}
			else {
				topDataList.add(new TopData(data, upperbuffer, lowerbuffer, upperFormation, false)); 
			}
			workbook.close(); 
			inputStream.close();
			return topDataList;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
