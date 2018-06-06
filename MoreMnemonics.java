import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MoreMnemonics {
	private String fileLocation = "UpdatedLasMnemonics.csv";
	private ArrayList<MnemonicData> mnemonicList; 
	
	public MoreMnemonics() {
		mnemonicList = new ArrayList<MnemonicData>();
	}
	
	public ArrayList<MnemonicData> readFile() { 
		try { 
			FileReader fileReader = new FileReader(fileLocation);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				String formatLine = line.replaceAll(",+$", "");
				String[] mnemonicArray = line.split(",");
				String name = mnemonicArray[0]; 
				ArrayList<String> mnemonics = new ArrayList<String>(); 
				for (int i = 1 ; i < mnemonicArray.length ; i++) {
					mnemonics.add(mnemonicArray[i]);
				}
				mnemonicList.add(new MnemonicData(name, mnemonics)); 
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return mnemonicList;
	}
	
	public void display() { 
		for (int i = 0 ; i < mnemonicList.size() ; i++) {
			mnemonicList.get(i).display();
		}
	}
	
	public static void main (String[] args) {
		MoreMnemonics test = new MoreMnemonics();
		test.readFile();
		test.display();
	}
}
