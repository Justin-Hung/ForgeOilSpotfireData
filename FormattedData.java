import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FormattedData {
	private boolean isMdforDir;
	private String header; 
	private ArrayList<String> rows; 
	
	public String getUwi() { return rows.get(0).substring(17, 37); }
	
	public FormattedData() {
		rows = new ArrayList<String>();
		isMdforDir = false;
	}
	
	public boolean isMdforDir() { return isMdforDir; }
	
	public void setMdTrue() { 
		isMdforDir = true;
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
	
	public void write(FileWriter fw, boolean isMD) { 
		try {
			if (isMD) { 
				for(int i = 0; i < rows.size(); i++) {
					fw.write("," + "Using MD for DIR," + rows.get(i)); 
					fw.write(System.lineSeparator());
				}
			}
			else {
				for(int i = 0; i < rows.size(); i++) {
					fw.write("," + "Using correct las file," + rows.get(i)); 
					fw.write(System.lineSeparator());
				}
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
