import java.util.Scanner;

public class UserInput {

	private String topForm;
	private String bottomForm; 
	private String township;
	private double formBuffer; 
	
	public String getTopForm() {
		return topForm;
	}
	
	public String getBottomForm() {
		return bottomForm; 
	}
	
	public String getTownship() { 
		return township;
	}
	
	public double getFormBuffer() {
		return formBuffer;
	}
	public void readInput() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter Township in format ttt-rrWw : " );
		township = scanner.next();
		System.out.println("Enter bottom formation: " );
		bottomForm = scanner.next().toUpperCase();
		System.out.println("Enter top formation: " );
		topForm = scanner.next().toUpperCase(); 
		System.out.println("Enter buffer region (number) : " );
		formBuffer = scanner.nextDouble();	
	}
	
	public void display() {
		System.out.println("top:" + topForm + " bottom:" + bottomForm + " township:" + township + " formbuffer:" + formBuffer);
	}
	
	public static void main (String [] args) {
		UserInput user = new UserInput(); 
		user.readInput();
		user.display();
	}
}
