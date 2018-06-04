import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class UserInput {

	private ArrayList<String> formations;
	private String townshipNW;
	private int nwSortUwi; 
	private int seSortUwi; 
	private String townshipSE;
	private double formBuffer; 
	
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
	
	public double getFormBuffer() {
		return formBuffer;
	}
	
	public int sortTownship(String town) {
		String uwi = town.substring(10,11) + town.substring(3,6) + town.substring(7,9) + town.substring(0,2); 
		if (uwi.endsWith("31") || uwi.endsWith("19") || uwi.endsWith("07")) {
			return Integer.parseInt(uwi) + 5;
		}
		return Integer.parseInt(uwi);
	}
	
	public void readInput() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter NW Township in format SS-TTT-RRW? : " );
		townshipNW = scanner.next();
		System.out.println("Enter SE township in format SS-TTT-RRW? : " );
		townshipSE = scanner.next(); 
//		System.out.println("Enter buffer region (number) : " );
//		formBuffer = scanner.nextDouble();	
		System.out.println("Enter formations separated by commas: "); 
		formations = new ArrayList<String>(Arrays.asList(scanner.next().toUpperCase().split(","))); 
		scanner.close();
		nwSortUwi = sortTownship(townshipNW); 
		seSortUwi = sortTownship(townshipSE);
	}
	
	public void display() {
		System.out.println("NW township: " + townshipNW + " SE township: " + townshipSE + " formbuffer: " + formBuffer);
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
