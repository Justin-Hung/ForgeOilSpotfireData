import java.util.ArrayList;

public class UnknownData {
	private String uwi; 
	private ArrayList<String> unknownMnemonics; 
	
	public UnknownData(String uwi) {
		this.uwi = uwi; 
		unknownMnemonics = new ArrayList<String>(); 
	}
	
	public void addUnknownMnemonic(String mnemonic) {
		unknownMnemonics.add(mnemonic);
	}
}
