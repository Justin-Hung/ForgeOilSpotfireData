import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class UserInput {

	private ArrayList<String> formations;
	private String townshipNW;
	private int nwSortUwi; 
	private int seSortUwi; 
	private String townshipSE;
	private double upperBuffer; 
	private double lowerBuffer; 
	private String userTopFilePath; 
	private String systemTopFilePath;
	private String lasFilePath; 
	private String workingFilePath; 
	private String outputFilePath; 
	private String outputFileName; 
	
	public UserInput() {
		formations = new ArrayList<String>();
		formations.add("");
		formations.add("");
		townshipNW = "--W";
		townshipSE = "--W"; 
		upperBuffer = 0.0001; 
		lowerBuffer = 0.0001; 
	}
	
	public String getUserTopFilePath() { return userTopFilePath; } 
	
	public String getLasFilePath() { return lasFilePath; } 
	
	public String getWorkingFilePath() { return workingFilePath; } 
	
	public String getOutputFilePath() { return outputFilePath; } 
	
	public String getOutputFileName() { return outputFileName; }
	
	public void setOutputfileName(String name) { 
		outputFileName = name;
	}
	
	public void setUserTopFilePath(String topfile) {
		userTopFilePath = topfile; 
	}
	
	public void setLasFilePath(String lasfile) {
		lasFilePath = lasfile; 
	}
	
	public void setWorkingFilePath(String workingfile) {
		workingFilePath = workingfile; 
	}
	
	public void setOutputFilePath(String outputfile) {
		outputFilePath = outputfile; 
	}
	
	public void setFormations(ArrayList<String> forms) { 
		formations = forms;
	}
	
	public void setTownshipNw(String townshipNw) {
		townshipNW = townshipNw;
		if (townshipNw.length() == 11) {
			nwSortUwi = sortTownship(townshipNW); 
		}
	}
	
	public void setNwSortUwi(int nwSort) {
		nwSortUwi = nwSort;
	}
	
	public void setSeSortUwi(int seSort) { 
		seSortUwi = seSort; 
	}
	
	public void setTownshipSe(String townshipSe) {
		townshipSE = townshipSe; 
		if (townshipSe.length() == 11) {
			seSortUwi = sortTownship(townshipSE);
		}
	}
	
	public void setUpperBuffer(double upperBuff) {
		upperBuffer = upperBuff; 
	}
	
	public void setLowerBuffer(double lowerBuff) { 
		lowerBuffer = lowerBuff;
	}
	
	public int getNwSortUwi() { 
		return nwSortUwi; 
	}
	
	public int getSeSortUwi() { 
		return seSortUwi;
	}
	
	public ArrayList<String> getFormations() {
		return formations;
	}

	public String getTownshipNW() { 
		return townshipNW;
	}
	
	public String getTownshipSE() {
		return townshipSE;
	}
	
	public double getUpperBuffer() {
		return upperBuffer; 
	}
	
	public double getLowerBuffer() { 
		return lowerBuffer;
	}
	
	public int sortTownship(String town) {
		String uwi = town.substring(10,11) + town.substring(3,6) + town.substring(7,9) + town.substring(0,2); 
		if (uwi.endsWith("31") || uwi.endsWith("19") || uwi.endsWith("07")) {
			return Integer.parseInt(uwi) + 5;
		}
		return Integer.parseInt(uwi);
	}
	
	public long fullSortTownship(String town) {
	//	System.out.println(town);
		String wackZone = town.substring(1,3);
		if (Character.isLetter(wackZone.charAt(0))) {
			wackZone = String.valueOf(wackZone.charAt(0) - 'A' + 1);
			if (wackZone.length() == 1) {
				wackZone = "0" + wackZone;
			}
		}
		String uwi = town.substring(17,18) + town.substring(10,13) + town.substring(14,16) + town.substring(7,9) 
				   + town.substring(4,6) + wackZone + "0" + town.substring(19);
	//	System.out.println(uwi);
		return Long.parseLong(uwi);
	}
	
	public void readInput() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter NW Township in format SS-TTT-RRW? : " );
		townshipNW = scanner.next();
		System.out.println("Enter SE township in format SS-TTT-RRW? : " );
		townshipSE = scanner.next(); 
		System.out.println("Enter upper buffer region in meters: " );
		upperBuffer = scanner.nextDouble();	
		System.out.println("Enter lower buffer region in meters: ");
		lowerBuffer = scanner.nextDouble();
		System.out.println("Enter formations separated by commas: "); 
		formations = new ArrayList<String>(Arrays.asList(scanner.next().toUpperCase().split(","))); 
		scanner.close();
		nwSortUwi = sortTownship(townshipNW); 
		seSortUwi = sortTownship(townshipSE);
	}
	
	public void display() {
		System.out.println("NW township: " + townshipNW + " SE township: " + townshipSE + " Upperbuffer: " + upperBuffer + " Lowerbuffer: " + lowerBuffer);
		System.out.println("Formations: ");
		for (int i = 0; i < formations.size() ; i++) {
			System.out.println(formations.get(i));
		}
		
		System.out.println("Sorted nw township: " + nwSortUwi + " Sorted se township: " + seSortUwi); 
	}
	
	public void write(FileWriter writer) { 
		try {
			writer.write(formations.get(0) + System.lineSeparator()); 
			writer.write(formations.get(1) + System.lineSeparator());
			writer.write(townshipNW + System.lineSeparator());
			writer.write(townshipSE + System.lineSeparator());
			writer.write(String.valueOf(upperBuffer) + System.lineSeparator());
			writer.write(String.valueOf(lowerBuffer) + System.lineSeparator());
			writer.write(userTopFilePath + System.lineSeparator());
			writer.write(systemTopFilePath + System.lineSeparator());
			writer.write(lasFilePath + System.lineSeparator());
			writer.write(workingFilePath + System.lineSeparator());
			writer.write(outputFilePath + System.lineSeparator());
			writer.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main (String [] args) {
		UserInput user = new UserInput(); 
		user.readInput();
		user.display();
	}
}
