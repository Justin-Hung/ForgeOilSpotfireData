import java.util.ArrayList;

public class LasDescriptionData {
	private ArrayList<String> descriptionList ; 
	private ArrayList<String> unitList; 
	private ArrayList<String> mnemonicList; 
	private String uwi; 
	
	public LasDescriptionData(String u) { 
		descriptionList = new ArrayList<String>(); 
		mnemonicList = new ArrayList<String>();
		unitList = new ArrayList<String>();
		uwi = u;
	}
	
	public int size() { return mnemonicList.size(); }
	
	public String getUwi() { return uwi; }
	
	public String getMnemonic(int i) { 
		return mnemonicList.get(i);
	}
	
	public String getUnit(int i) { 
		return unitList.get(i);
	}
	
	public String getDescription(int i) {
		return descriptionList.get(i);
	}
	
	public void addLine(String line) { 
		line = line.replaceAll(",", "");
		addMnemonic(line.substring(1, line.indexOf('.')).trim());
		addUnit(line.substring(line.indexOf('.'), line.indexOf(':')-13).trim());
		
		int descriptionIndex = line.indexOf(':') + 2;
		while(true) {
			if (line.charAt(descriptionIndex) == ' ') {
				descriptionIndex++; 
				break;
			}
			descriptionIndex++; 
		}
		addDescription(line.substring(descriptionIndex).trim());
	}

	public void addDescription(String description) { 
		descriptionList.add(description);
	}
	
	public void addMnemonic(String mnemonic) {
		mnemonicList.add(mnemonic);
	}
	
	public void addUnit(String unit) {
		unitList.add(unit);
	}
	
	public void display() { 
		System.out.println("---------------" + uwi + "---------------");
		for (int i = 0 ; i < mnemonicList.size() ; i++) {
			System.out.println(mnemonicList.get(i) + " | " + unitList.get(i) + " | " + descriptionList.get(i));	
		}
	}
} 
