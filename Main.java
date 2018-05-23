import java.util.ArrayList;

public class Main {
	
	public static void main(String[] args) { 
		TopFileReader topFileReader = new TopFileReader(); 
		LasFileReader lasFileReader = new LasFileReader(); 
		try {
			ArrayList<TopData> topDataList = topFileReader.readFile();
			LasData data = lasFileReader.readFile(topDataList.get(0));
			data.display(); 
		}
		catch(Exception e) { 
			e.printStackTrace();
		}
	}
}
