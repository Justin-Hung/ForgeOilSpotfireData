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
	private ArrayList<TopData> secondaryTopList;
	private	int topDataIndex; 
	private String currentUwi = ""; 
	private ArrayList<String> data; 
	private int upperbound;
	private int lowerbound; 
	private double upperbuffer;
	private double lowerbuffer; 
	private String upperFormation; 
	private String previousFormation; 
	private boolean checkBottom; 
	private int upperRange; 
	private int lowerRange;
	
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
	
	public TopFileReader(UserInput userInput) {
		topFilePath = userInput.getPrimaryTopFilePath();
		upperbuffer = userInput.getUpperBuffer(); 
		lowerbuffer = userInput.getLowerBuffer();
		formations = userInput.getFormations();
		upperbound = userInput.getNwSortUwi(); 
		lowerbound = userInput.getSeSortUwi();
		upperRange = userInput.getUpperRange();
		lowerRange = userInput.getLowerRange(); 
		
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
		if (cell.getStringCellValue().equals("")) {
			return true;
		}
		return false;
	}
	
	public ArrayList<TopData> readSecondaryFile(String secondaryFilePath) {
		try { 
			FileInputStream inputStream = new FileInputStream(new File(secondaryFilePath)); 
			Workbook workbook = new XSSFWorkbook(inputStream); 
			Sheet firstSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = firstSheet.iterator(); 
			
			Row checkFirstRow = iterator.next(); 
			Iterator<Cell> checkFirstRowIterator = checkFirstRow.cellIterator(); 
			Cell checkFirstRowCell = checkFirstRowIterator.next();
			if (checkFirstRowCell.getStringCellValue().equals("©2018 IHS Markit")) {
				iterator.next();
			}
			
			boolean firstRow = true; 
			String[] previousRow = new String[3]; 
			String[] currentRow = new String[3]; 
			String[] futureRow= new String[3]; 
			secondaryTopList = new ArrayList<TopData>();
			topDataIndex = 0;
			
			while (iterator.hasNext()) {
				Row nextRow = iterator.next();
				Iterator<Cell> cellIterator = nextRow.cellIterator();
				
				int index = 0; 
				while (cellIterator.hasNext()) {
					
					Cell cell = cellIterator.next(); 
					if (index == uwiCol) { 
						futureRow[0] = cell.getStringCellValue(); 
					}
					if (index == formationCol) { 
						futureRow[1] = cell.getStringCellValue();
					}
					if (index == tvdCol) {
						futureRow[2] = String.valueOf(cell.getNumericCellValue()); 
					}
					index++;
 				}
				if (!firstRow) {
					checkRow(previousRow,currentRow,futureRow);
				}
				previousRow = currentRow.clone();
				currentRow = futureRow.clone(); 
				firstRow = false; 
			}
			return secondaryTopList;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void checkRow(String[] previousRow, String[] currentRow, String[] futureRow) {
		if (!currentUwi.equals(currentRow[0])) {
			while (topDataIndex < topDataList.size() && !currentRow[0].equals(topDataList.get(topDataIndex).getUwi())) {
				if (new UserInput().fullSortTownship(topDataList.get(topDataIndex).getUwi()) < new UserInput().fullSortTownship(currentRow[0])) { 
					topDataIndex++;
				}
				else {
					return; 
				}
			}
			currentUwi = currentRow[0];
			data = new ArrayList<String>(); 
			data.add(currentRow[0]);
		}
		if (currentUwi.equals(currentRow[0])) { 
			data.add(currentRow[1]);
			data.add(currentRow[2]);
		}
		if (!futureRow[0].equals(currentUwi) && currentUwi.equals(currentRow[0])) { 
			secondaryTopList.add(new TopData(data, 999, 999, "unknown", false));
			topDataIndex++; 
		}
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
			
			boolean nextRowHasFormationCheck = false;
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
						int range = 0;
						if (cell.getStringCellValue().startsWith("1") ) {
							sortUwi = sort.sortTownship(cell.getStringCellValue().substring(7, 18));
							range = Integer.parseInt(cell.getStringCellValue().substring(14, 16));
						}
						else {
							sortUwi = sort.sortTownship(cell.getStringCellValue().substring(6, 17));
							range = Integer.parseInt(cell.getStringCellValue().substring(13, 15));
						}
						if (sortUwi < lowerbound || sortUwi > upperbound || checkIsEmptyFormation(nextRow.cellIterator(), cell.getStringCellValue().substring(7, 18)) || range > upperRange || range < lowerRange) {
							break;
						}
					}
				
					if (index == uwiCol && !currentUwi.equals(cell.getStringCellValue())) {
						if (!nextRowHasFormationCheck) {
							if (data.size() > 2) {
								if (formations.get(formations.size()-1).equals("TD") || !data.get(data.size()-2).equals(formations.get(formations.size()-1))) {
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
						else { 
							currentUwi = cell.getStringCellValue();
							nextRowHasFormationCheck = false;
							topCheck = true;
						}
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
//							Iterator<Row> checkNextRow = iterator;
//						
//							if (checkNextRow.hasNext()) {
//								Row checkRow = checkNextRow.next();
//								Iterator<Cell> checkCellIterator = checkRow.cellIterator();
//								int checkIndex = 0;
//								String[] rowData = new String[3]; 
//								while (checkCellIterator.hasNext()) {
//									Cell checkCell = checkCellIterator.next();
//									if (checkIndex == uwiCol) {
//										rowData[0] = checkCell.getStringCellValue(); 
//									}
//									if (checkIndex == formationCol) {
//										rowData[1] = checkCell.getStringCellValue();
//									}
//									if (checkIndex == tvdCol) {
//										rowData[2] = String.valueOf(checkCell.getNumericCellValue()); 
//										break;
//									}
//									checkIndex++;
//								}
//								if (!currentUwi.equals(rowData[0])) {
//									checkBottom = true; 
//									if (rowData[1].equals(formations.get(0))) {
//										nextRowHasFormationCheck = true;
//										upperFormation = "UNKNOWN";
//										topDataList.add(new TopData(data, upperbuffer, lowerbuffer, upperFormation, true));
//										checkBottom = false;
//										data = new ArrayList<String>(); 
//										data.add(rowData[0]);
//										data.add(rowData[1]);
//										data.add(rowData[2]);
//										previousFormation = "UNKNOWN";
//									}
//								}
//								else { 
//									data.add(rowData[1]);
//									data.add(rowData[2]);
//								}
//							}
//							else {
//								checkBottom = true; 
//							}
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
			if (formations.get(formations.size()-1).equals("TD") || checkBottom) {
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
