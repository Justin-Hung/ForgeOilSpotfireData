import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class UserInput {

	private ArrayList<String> formations;
	private String township;
	private double formBuffer; 
	
	public ArrayList<String> getFormations() {
		return formations;
	}

	public String getTownship() { 
		return township;
	}
	
	public double getFormBuffer() {
		return formBuffer;
	}
	public void readInput() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter Township in format TTT-RRW? : " );
		township = scanner.next();
//		System.out.println("Enter buffer region (number) : " );
//		formBuffer = scanner.nextDouble();	
		System.out.println("Enter formations separated by commas: "); 
		formations = new ArrayList<String>(Arrays.asList(scanner.next().split(","))); 
		scanner.close();

	}
	
	public void display() {
		System.out.println("township: " + township + " formbuffer: " + formBuffer);
		System.out.println("Formations: ");
		for (int i = 0; i < formations.size() ; i++) {
			System.out.println(formations.get(i));
		}
	}
	
	public static void main (String [] args) {
		UserInput user = new UserInput(); 
		user.readInput();
		user.display();
	}
}
