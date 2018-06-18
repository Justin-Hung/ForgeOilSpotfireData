import java.util.ArrayList;

public class OutputData {
	
	private ArrayList<String> successUwis;
	public ArrayList<String> getSuccessUwis() { return successUwis; }
	
	private ArrayList<String> topErrorUwis; 
	public ArrayList<String> getTopErrorUwis() { return topErrorUwis; }
	
	private ArrayList<String> gwiErrorUwis;
	public ArrayList<String> getGwiErrorUwis() { return gwiErrorUwis; }
	
	private ArrayList<String> lasErrorUwis; 
	public ArrayList<String> getLasErrorUwis() { return lasErrorUwis; }
	
	public OutputData() { 
		successUwis = new ArrayList<String>(); 
		topErrorUwis = new ArrayList<String>(); 
		gwiErrorUwis = new ArrayList<String>(); 
		lasErrorUwis = new ArrayList<String>(); 
	}
	
	public void addSuccess(String uwi) { 
		successUwis.add(uwi);
	}
	
	public void addTopError(String uwi) { 
		topErrorUwis.add(uwi);
	}
	
	public void addGwiError(String uwi) { 
		gwiErrorUwis.add(uwi);
	}
	
	public void addLasError(String uwi) { 
		lasErrorUwis.add(uwi);
	}
	
}
