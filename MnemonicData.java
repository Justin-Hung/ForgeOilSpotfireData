import java.util.ArrayList;

public class MnemonicData {
	private String name; 
	private ArrayList<String> mnemonics; 
	
	public MnemonicData(String n, ArrayList<String> m) { 
		name = n; 
		mnemonics = m;
	}
	
	public ArrayList<String> getMnemonics() {
		return mnemonics; 
	}
	
	public String getName() { 
		return name; 
	}
	
	public void display() { 
		System.out.println("\n--------------------------------------------");
		System.out.println(name + ": ");
		for (int i = 0 ; i < mnemonics.size() ; i++) {
			System.out.print(mnemonics.get(i) + ",");
		}
	}
}
