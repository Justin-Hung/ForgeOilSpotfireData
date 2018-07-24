import java.util.ArrayList;

public class WorkingFileData {
	private int typeCol; 
	private int producingCol;
	private String header;
	private ArrayList<String> rows; 
	
	public WorkingFileData() {
		typeCol = -1; 
		producingCol = -1; 
		rows = new ArrayList<String>(); 
	}
	
	public int getTypeCol() { return typeCol; } 
	
	public int getProducingCol() { return producingCol; }
	
	public void addHeader(String head) {
		header = new DataWriter().removeNewLine(head); 
		setCol(); 
	}

	
	public void setCol() { 
		String[] headerArray = header.split(",");
		
		for (int i = 0 ; i < headerArray.length ; i++) {
			if (headerArray[i].equals("Producing Zone")) {
				producingCol = i; 
			}
			if (headerArray[i].equals("Type")) {
				typeCol = i;
			}
		}
	}
    
	public void addRow(String row) {
		rows.add(row); 
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
}
