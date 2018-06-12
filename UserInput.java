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
	private String topfilePath; 
	private String lasfilePath; 
	private String workingfilePath; 
	private String outputfilePath; 
	
	public String getTopfilePath() { return topfilePath; } 
	
	public String getLasfilePath() { return lasfilePath; } 
	
	public String getWorkingfilePath() { return workingfilePath; } 
	
	public String getOutputfilePath() { return outputfilePath; } 
	
	public void setTopfilePath(String topfile) {
		topfilePath = topfile; 
	}
	
	public void setLasfilePath(String lasfile) {
		lasfilePath = lasfile; 
	}
	
	public void setWorkingfilePath(String workingfile) {
		workingfilePath = workingfile; 
	}
	
	public void setOutputfilePath(String outputfile) {
		outputfilePath = outputfile; 
	}
	
	public void setFormations(ArrayList<String> forms) { 
		formations = forms;
	}
	
	public void setTownshipNw(String townshipNw) {
		townshipNW = townshipNw;
		nwSortUwi = sortTownship(townshipNW); 
	}
	
	public void setNwSortUwi(int nwSort) {
		nwSortUwi = nwSort;
	}
	
	public void setSeSortUwi(int seSort) { 
		seSortUwi = seSort; 
	}
	
	public void setTownshipSe(String townshipSe) {
		townshipSE = townshipSe; 
		seSortUwi = sortTownship(townshipSE);
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
		//System.out.println(town);
		String wackZone = town.substring(1,3);
		if (Character.isLetter(wackZone.charAt(0))) {
			wackZone = String.valueOf(wackZone.charAt(0) - 'A' + 1);
			if (wackZone.length() == 1) {
				wackZone = "0" + wackZone;
			}
		}
		String uwi = town.substring(17,18) + town.substring(10,13) + town.substring(14,16) + town.substring(7,9) 
				   + town.substring(4,6) + wackZone + "0" + town.substring(19);
		//System.out.println(uwi);
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
	
	public static void main (String [] args) {
		UserInput user = new UserInput(); 
		user.readInput();
		user.display();
	}
}
