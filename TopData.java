import java.util.ArrayList;

public class TopData {

	private String uwi; 
	private ArrayList<String> form; 
	private ArrayList<Double> tvDepth; 
	private double upperbuffer; 
	private double lowerbuffer;
	private String upperFormation; 
	private boolean bottomCheck;
	
	public String getFormation(Double depth) {
		if (depth >= tvDepth.get(tvDepth.size()-1) && depth < getUpperBound()) {
			return form.get(tvDepth.size()-1);
		}
		for (int i = 0; i < tvDepth.size(); i++) {
			if (depth >= tvDepth.get(i) && depth < tvDepth.get(i+1)) {
				if (form.get(i).equals("VKNS UNCF")) {
					return "VKNS";
				}
				if (form.get(i).equals("VKNS") && form.get(i+1).equals("VKNS UNCF")) {
					return "VKNS UNCF";
				}
				return form.get(i);
			}
		}
		return upperFormation;
	}
	
	public String getUwi() {
		return uwi;
	}
	public double getLowerBound() {
		return tvDepth.get(0) - (upperbuffer + 0.1001); 
	}
	
	public double getUpperBound() { 
		if (bottomCheck) {
			return 9999;
		}
		return tvDepth.get(tvDepth.size()-1) + lowerbuffer + 0.1; 
	}
	
	public TopData(ArrayList<String> data, double upper, double lower, String upperForm, boolean check) { 
		bottomCheck = check;
		upperFormation = upperForm;
		upperbuffer = upper; 
		lowerbuffer = lower;
		form = new ArrayList<String>();
		tvDepth = new ArrayList<Double>();
		uwi = data.get(0);
		for (int i = 1; i < data.size(); i++) {
			if (i % 2 == 1) {
				if (data.get(i).startsWith(" ")) {
					form.add(data.get(i).substring(1));
				}
				else {
					form.add(data.get(i));
				}
			}
			if (i % 2 == 0) {
				tvDepth.add(Double.parseDouble(data.get(i)));
			}
		}
	}
	
	public void displayTop() {
		System.out.println("UWI--------------------FORM-------------------TVDEPTH");
		for(int i = 0; i < form.size(); i++) {
			System.out.println(uwi + " " + form.get(i) + "           " + tvDepth.get(i));
		}
		
	}
}
