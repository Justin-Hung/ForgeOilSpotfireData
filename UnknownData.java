import java.util.ArrayList;

public class UnknownData {
	private String uwi; 
	private ArrayList<String> unknownMnemonics; 
	
	public String getUwi() { return uwi; } 
	
	public int size() { return unknownMnemonics.size(); } 
	
	public String getUnknownMnemonic(int i) { 
		return unknownMnemonics.get(i);
	}
	public UnknownData(String uwi) {
		this.uwi = uwi; 
		unknownMnemonics = new ArrayList<String>(); 
	}
	
	public void addUnknownMnemonic(String mnemonic) {
		unknownMnemonics.add(mnemonic);
	}
}
