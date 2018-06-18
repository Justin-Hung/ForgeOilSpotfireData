import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.apache.log4j.chainsaw.Main;

public class MoreMnemonics {
	private ArrayList<MnemonicData> mnemonicList; 
	
	public MoreMnemonics() {
		mnemonicList = new ArrayList<MnemonicData>();
	}
	
	public ArrayList<MnemonicData> readFile() { 
		try { 			
			
			ClassLoader classloader = getClass().getClassLoader();
			URL url = Main.class.getResource("/resources/UpdatedLasMnemonics.csv");
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
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
		System.out.println(test.readFile().size());
		test.display();
	}
}
