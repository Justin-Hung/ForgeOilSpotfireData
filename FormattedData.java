import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FormattedData {
	private String header; 
	private ArrayList<String> rows; 
	
	public FormattedData() {
		rows = new ArrayList<String>();
	}
	
	public void addRow(String row) {
		rows.add(row);
	}
	
	public void addHeader(String head) {
		header = head; 
	}
	
	public String getRow(int i) {
		return rows.get(i);
	}
	
	public String getHeader() {
		return header; 
	}
	
	public int getSize() {
		return rows.size();
	}
	
	public void display() {
		System.out.println(header);
		for(int i = 0; i < rows.size(); i++) {
			System.out.println(rows.get(i));
		}
	}
	
	public void write(FileWriter fw) { 
		try {
			for(int i = 0; i < rows.size(); i++) {
				fw.write(rows.get(i)); 
				fw.write(System.lineSeparator());
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
