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
}
