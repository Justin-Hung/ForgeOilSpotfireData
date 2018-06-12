import java.util.ArrayList;

public class LasData {
	private boolean mdForDir;
	private String uwi; 
	private String header; 
	private ArrayList<String> rows; 
	private String serviceCo; 
	private String bit; 
	
	public void formatHeader() {
		for (int i = 0 ; i < header.length()-1 ; i++) {
			if(header.charAt(i) == ' ' && header.charAt(i+1) != ' ' && header.charAt(i-1) != ' ' ) {
				StringBuilder build = new StringBuilder(header);
				build.setCharAt(i, '_');
				header = build.toString();
			}
		}
	}
	
	public String getUwi() { return uwi; }
	
	public String getBit() { return bit; }
	
	public String getServiceCo() { return serviceCo; }
	
	public void setServiceCo(String s) {
		serviceCo = s;
	}
	
	public void setBit(String b) {
		bit = b; 
	}
	
	public LasData(String u) {
		uwi = u;
		rows = new ArrayList<String>(); 
		mdForDir = false;
	}
	
	public boolean getMdForDir() { return mdForDir; } 
	
	public void setMdTrue() {
		mdForDir = true; 
	}
	public void addHeader(String head) {
		header = head; 
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
	
	public void display() {
		System.out.println(header);
		for(int i = 0; i < rows.size(); i++) {
			System.out.println(rows.get(i));
		}
		System.out.println("uwi: " + uwi);
	}
	
	public boolean isEmpty() {
		return rows.isEmpty();
	}
	
	public int getSize() {
		return rows.size();
	}
}
