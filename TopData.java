import java.util.ArrayList;

public class TopData {

	private String uwi; 
	private ArrayList<String> form; 
	private ArrayList<Double> tvDepth; 
	private int index; 
	
	public TopData(ArrayList<String> data) { 
		form = new ArrayList<String>();
		tvDepth = new ArrayList<Double>();
		uwi = data.get(0);
		for (int i = 1; i < data.size(); i++) {
			if (i % 2 == 1) {
				form.add(data.get(i));
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
