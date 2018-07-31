import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
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

	private boolean meridianCross; 
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
		meridianCross = false;
		int upperMeridian = Integer.parseInt(Integer.toString(upperbound).substring(0, 1));
		int lowerMeridian = Integer.parseInt(Integer.toString(lowerbound).substring(0, 1));
		if (upperMeridian != lowerMeridian) { 
			meridianCross = true; 
		}
		
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
		while (index <= tvdCol) {
			cell = cellIterator.next();
			index++; 
		}
		if (cell.getStringCellValue().equals("")) {
			return true; 
		}
		return false;
	}
	
	public boolean rangeCheck(int range, int town) {
		if (meridianCross) {
			if (range <= upperRange || range >= lowerRange) {
				int upperTown = Integer.parseInt(Integer.toString(upperbound).substring(1, 4));
				int lowerTown = Integer.parseInt(Integer.toString(lowerbound).substring(1, 4));
				if (town > upperTown || town < lowerTown) {
					return true;
				}
				return false;
			}
			return true;
		}
		else { 
			if (range > upperRange ||  range < lowerRange) { 
				return true; 
			}
		}
		return false; 
	}
	
	public ArrayList<TopData> csvReadFile() { 
		try {
			UserInput sort = new UserInput(); 
			
			BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(topFilePath)));
			String line = bufferedReader.readLine();
			if (line.contains("©2018 IHS Markit")) {
				line = bufferedReader.readLine();
			}
			
			boolean nextRowHasFormationCheck = false;
			boolean topCheck = false;
			int row = 0;
			
			line = bufferedReader.readLine();
			while (line != null) {
				String[] lineArray = line.split(",");
				
				int index = 0; 
				while (index < lineArray.length) {
					if (index == uwiCol) {
						int sortUwi = 0;
						int range = 0;
						int town = 0;
						if (lineArray[index].charAt(3) == '/' ) {
							sortUwi = sort.sortTownship(lineArray[index].substring(7, 18));
							range = Integer.parseInt(lineArray[index].substring(14, 16));
							town = Integer.parseInt(lineArray[index].substring(10, 13));
						}
						else {
							sortUwi = sort.sortTownship(lineArray[index].substring(6, 17));
							range = Integer.parseInt(lineArray[index].substring(13, 15));
							town = Integer.parseInt(lineArray[index].substring(9, 12));
						}
						try { 
							System.out.println(sortUwi);
							if (sortUwi < lowerbound || sortUwi > upperbound || lineArray[formationCol].equals("") || rangeCheck(range, town) || lineArray[tvdCol].equals("")) {
								break;
							}
						}
						catch (ArrayIndexOutOfBoundsException e) {
							break; 
						}
					}
				
					if (index == uwiCol && !currentUwi.equals(lineArray[index])) {
						if (!nextRowHasFormationCheck) {
							if (data.size() > 2) {
								if (formations.get(formations.size()-1).equals("TD") || !data.get(data.size()-2).equals(formations.get(formations.size()-1))) {
									topDataList.add(new TopData(data, upperbuffer, lowerbuffer, upperFormation, true, formations.get(0)));
									checkBottom = false;
								}
								else {
									topDataList.add(new TopData(data, upperbuffer, lowerbuffer, upperFormation, false, formations.get(0))); 
								}
								topCheck = false;
							}
							data = new ArrayList<String>(); 
							currentUwi = lineArray[index];
							if (currentUwi.charAt(3) == '/') { 
								data.add(currentUwi);
							}
							else {
								data.add("1" + currentUwi);
							}
							previousFormation = "UNKNOWN";
							if (formations.get(0).equals("")) {
								upperFormation = previousFormation;
								topCheck = true; 
							}
						}
						else { 
							currentUwi = lineArray[index];
							nextRowHasFormationCheck = false;
							topCheck = true;
						}
					}
				
					if (index == formationCol) {
						if (lineArray[index].substring(1).equals(formations.get(formations.size()-1)) || lineArray[index].equals(formations.get(formations.size()-1))) {
							data.add(lineArray[index]);
							data.add(lineArray[tvdCol]);
							if (formations.get(formations.size()-1).equals(formations.get(0))) {
								upperFormation = previousFormation;
							}
							topCheck = false; 
							break;
						}
						if (lineArray[index].substring(1).equals(formations.get(0)) || lineArray[index].equals(formations.get(0))) {
							upperFormation = previousFormation;
							topCheck = true; 
						}
						if (topCheck) {
							data.add(lineArray[index]);
						}
						previousFormation = lineArray[index];
					}
					
					if (index == tvdCol) {
						if (topCheck) {
							data.add(String.valueOf(lineArray[index]));
							break;
						}
					}
					index++; 
				}
				line = bufferedReader.readLine();
				row++;
			}
			if (data.isEmpty()) {
				System.out.println("ERRROR");
			}
			if (formations.get(formations.size()-1).equals("TD") || checkBottom) {
				topDataList.add(new TopData(data, upperbuffer, lowerbuffer, upperFormation, true, formations.get(0)));
				checkBottom = false;
			}
			else {
				topDataList.add(new TopData(data, upperbuffer, lowerbuffer, upperFormation, false, formations.get(0))); 
			}
			bufferedReader.close();
			return topDataList;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public ArrayList<TopData> readSecondaryCsvFile(String secondaryFilePath) {
		try { 
			BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(secondaryFilePath)));
			String line = bufferedReader.readLine();
			if (line.contains("©2018 IHS Markit")) {
				line = bufferedReader.readLine();
			}
			boolean firstRow = true; 
			String[] previousRow = new String[3]; 
			String[] currentRow = new String[3]; 
			String[] futureRow= new String[3]; 
			secondaryTopList = new ArrayList<TopData>();
			topDataIndex = 0;
			
			line = bufferedReader.readLine();
			while (line != null) {
				String[] lineArray = line.split(",");
				String uwi = lineArray[uwiCol]; 
				try { 
					String temp = lineArray[formationCol];
					temp = lineArray[tvdCol];
					if (uwi.charAt(3) == '/') { 
						futureRow[0] = uwi; 
					}
					else { 
						futureRow[0] = "1" + uwi; 
					}
					futureRow[1] = lineArray[formationCol];
					futureRow[2] = lineArray[tvdCol];
					if (!firstRow) {
						checkRow(previousRow,currentRow,futureRow);
					}
					previousRow = currentRow.clone();
					currentRow = futureRow.clone();
					firstRow = false; 
				}
				catch (IndexOutOfBoundsException e) {
					
				}
				line = bufferedReader.readLine();
			}
			bufferedReader.close();
			return secondaryTopList;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
						String uwi = cell.getStringCellValue(); 
						if (uwi.charAt(3) == '/') { 
							futureRow[0] = cell.getStringCellValue(); 
						}
						else { 
							futureRow[0] = "1" + cell.getStringCellValue(); 
						}
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
			if (!data.isEmpty() && checkData(data)) {
				secondaryTopList.add(new TopData(data, 999, 999, "unknown", false, formations.get(0)));
			}
			topDataIndex++; 
		}
	}
	
	public boolean checkData(ArrayList<String> data) {
		for (int i = 0 ; i < data.size() ; i++) {
			if (data.get(i).isEmpty() || data.get(i).equals(" ")) { 
				return false; 
			}
		}
		return true;
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
						int town = 0;
						if (cell.getStringCellValue().charAt(3) == '/' ) {
							sortUwi = sort.sortTownship(cell.getStringCellValue().substring(7, 18));
							range = Integer.parseInt(cell.getStringCellValue().substring(14, 16));
							town = Integer.parseInt(cell.getStringCellValue().substring(10, 13));
						}
						else {
							sortUwi = sort.sortTownship(cell.getStringCellValue().substring(6, 17));
							range = Integer.parseInt(cell.getStringCellValue().substring(13, 15));
							town = Integer.parseInt(cell.getStringCellValue().substring(9, 12));
						}
						if (sortUwi < lowerbound || sortUwi > upperbound || checkIsEmptyFormation(nextRow.cellIterator(), cell.getStringCellValue().substring(7, 18)) || rangeCheck(range, town)) {
							break;
						}
					}
				
					if (index == uwiCol && !currentUwi.equals(cell.getStringCellValue())) {
						if (!nextRowHasFormationCheck) {
							if (data.size() > 2) {
								if (formations.get(formations.size()-1).equals("TD") || !data.get(data.size()-2).equals(formations.get(formations.size()-1))) {
									topDataList.add(new TopData(data, upperbuffer, lowerbuffer, upperFormation, true, formations.get(0)));
									checkBottom = false;
								}
								else {
									topDataList.add(new TopData(data, upperbuffer, lowerbuffer, upperFormation, false, formations.get(0))); 
								}
								topCheck = false;
							}
							data = new ArrayList<String>(); 
							currentUwi = cell.getStringCellValue();
							if (currentUwi.charAt(3) == '/') { 
								data.add(currentUwi);
							}
							else {
								data.add("1" + currentUwi);
							}
							previousFormation = "UNKNOWN";
							if (formations.get(0).equals("")) {
								upperFormation = previousFormation;
								topCheck = true; 
							}
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
				topDataList.add(new TopData(data, upperbuffer, lowerbuffer, upperFormation, true, formations.get(0)));
				checkBottom = false;
			}
			else {
				topDataList.add(new TopData(data, upperbuffer, lowerbuffer, upperFormation, false, formations.get(0))); 
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
