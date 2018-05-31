import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Mnemonics {
	private String fileLocation = "lasMnemonics.csv";
	private ArrayList<String> gamma; 
	public int getGammaSize() { return gamma.size(); }
	
	private ArrayList<String> density;
	public int getDensitySize() { return density.size(); }
	
	private ArrayList<String> neutron;
	public int getNeutronSize() { return neutron.size(); } 
	
	private ArrayList<String> shallow;
	public int getShallowSize() { return shallow.size(); }
	
	private ArrayList<String> medium;
	public int getMediumSize() { return medium.size(); } 
	
	private ArrayList<String> deep;
	public int getDeepSize() { return deep.size(); } 
	
	private ArrayList<String> sp;
	public int getSpSize() { return sp.size(); }
	
	private ArrayList<String> sonic;
	public int getSonicSize() { return sonic.size(); }
	
	private ArrayList<String> bulkDensity; 
	public int getBulkDensitySize() { return bulkDensity.size(); }
	
	private ArrayList<String> pe;
	public int getPeSize() { return pe.size(); }
	
	private ArrayList<String> caliper; 
	public int getCaliperSize() { return caliper.size(); }
	
	private ArrayList<String> densityCorrection;
	public int getDensityCorrectionSize() { return densityCorrection.size(); }
	
	private ArrayList<String> tension;
	public int getTensionSize() { return tension.size(); }
	
	public String getGamma(int i) { 
		return gamma.get(i);
	}
	public String getDensity(int i) { 
		return density.get(i);
	}
	public String getNeutron(int i) { 
		return neutron.get(i);
	}
	public String getShallow(int i) { 
		return shallow.get(i);
	}
	public String getMedium(int i) { 
		return medium.get(i);
	}
	public String getDeep(int i) { 
		return deep.get(i);
	}
	public String getSp(int i) { 
		return sp.get(i);
	}
	public String getSonic(int i) { 
		return sonic.get(i);
	}
	public String getBulkDensity(int i) { 
		return bulkDensity.get(i);
	}
	public String getPe(int i) { 
		return pe.get(i);
	}
	public String getCaliper(int i) { 
		return caliper.get(i);
	}
	public String getDensityCorrection(int i) { 
		return densityCorrection.get(i);
	}
	public String getTension(int i) { 
		return tension.get(i);
	}
	
	public Mnemonics() {
		gamma = new ArrayList<String>(); 
		density = new ArrayList<String>(); 
		neutron = new ArrayList<String>(); 
		shallow = new ArrayList<String>(); 
		medium = new ArrayList<String>(); 
		deep = new ArrayList<String>(); 
		sp = new ArrayList<String>(); 
		sonic = new ArrayList<String>(); 
		bulkDensity = new ArrayList<String>(); 
		pe = new ArrayList<String>(); 
		caliper = new ArrayList<String>(); 
	}
	
	public void readFile() {
		try { 
			FileReader fileReader = new FileReader(fileLocation);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				String formatLine = line.replaceAll(",+$", "");
				if (line.startsWith("Gamma Ray")) {
					gamma = new ArrayList<String>( Arrays.asList( formatLine.substring(10).split(",")));
				}
				if (line.startsWith("Density Porosity - Sandstone")) {
					density = new ArrayList<String>( Arrays.asList( formatLine.substring(29).split(",")));
				}
				if (line.startsWith("Neutron Porosity - Sandstone")) {
					neutron = new ArrayList<String>( Arrays.asList( formatLine.substring(29).split(",")));
				}
				if (line.startsWith("Sonic")) {
					sonic = new ArrayList<String>( Arrays.asList( formatLine.substring(6).split(",")));
				}
				if (line.startsWith("SpontaneousPotential")) {
					sp = new ArrayList<String>( Arrays.asList( formatLine.substring(21).split(",")));
				}
				if (line.startsWith("Shallow Resistivity")) {
					shallow = new ArrayList<String>( Arrays.asList( formatLine.substring(20).split(",")));
				}
				if (line.startsWith("Medium Resistivity")) {
					medium = new ArrayList<String>( Arrays.asList( formatLine.substring(19).split(",")));
				}
				if (line.startsWith("Deep Resistivity")) {
					deep = new ArrayList<String>( Arrays.asList( formatLine.substring(17).split(",")));
				}
				if (line.startsWith("Photoelectric Effect")) {
					pe = new ArrayList<String>( Arrays.asList( formatLine.substring(21).split(",")));
				}
				if (line.startsWith("Bulk Density")) {
					bulkDensity = new ArrayList<String>( Arrays.asList( formatLine.substring(13).split(",")));
				}
				if (line.startsWith("Caliper")) {
					caliper = new ArrayList<String>( Arrays.asList( formatLine.substring(8).split(",")));
				}
				if (line.startsWith("Density Correction")) { 
					densityCorrection = new ArrayList<String>( Arrays.asList( formatLine.substring(19).split(",")));
				}
				if (line.startsWith("Tension of Cable")) { 
					tension = new ArrayList<String>( Arrays.asList( formatLine.substring(17).split(",")));
				}
				
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void display() {
		System.out.println();
		for (int i = 0; i < gamma.size(); i++) {
			System.out.print(gamma.get(i) + ",");
		}
		System.out.println();
		for (int i = 0; i < density.size(); i++) {
			System.out.print(density.get(i) + ",");
		}
		System.out.println();
		for (int i = 0; i < neutron.size(); i++) {
			System.out.print(neutron.get(i) + ",");
		}
		System.out.println();
		for (int i = 0; i < shallow.size(); i++) {
			System.out.print(shallow.get(i) + ",");
		}
		System.out.println();
		for (int i = 0; i < medium.size(); i++) {
			System.out.print(medium.get(i) + ",");
		}
		System.out.println();
		for (int i = 0; i < deep.size(); i++) {
			System.out.print(deep.get(i) + ",");
		}
		System.out.println();
		for (int i = 0; i < sp.size(); i++) {
			System.out.print(sp.get(i) + ",");
		}
		System.out.println();
		for (int i = 0; i < sonic.size(); i++) {
			System.out.print(sonic.get(i) + ",");
		}
		System.out.println();
		for (int i = 0; i < bulkDensity.size(); i++) {
			System.out.print(bulkDensity.get(i) + ",");
		}
		System.out.println();
		for (int i = 0; i < pe.size(); i++) {
			System.out.print(pe.get(i) + ",");
		}
		System.out.println();
		for (int i = 0; i < caliper.size(); i++) {
			System.out.print(caliper.get(i) + ",");
		}
		System.out.println();
		for (int i = 0; i < densityCorrection.size(); i++) {
			System.out.print(densityCorrection.get(i) + ",");
		}
		System.out.println();
		for (int i = 0; i < tension.size(); i++) {
			System.out.print(tension.get(i) + ",");
		}
		System.out.println();
	}
	
	public static void main(String[] args) { 
		Mnemonics mnemonics = new Mnemonics();
		mnemonics.readFile();
		mnemonics.display();
	}
}
