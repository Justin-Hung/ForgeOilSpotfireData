import org.apache.commons.codec.binary.StringUtils;

public class DataWriter {

	private final String header = "Sort UWI,UWI,Current License,Bottom Hole Latitude,Bottom Hole Longitude,KB Elevation (m),Ground Elevation (m),Max True Vertical Depth (m),Total True Vertical Depth (m),Total Depth (m),Fluid,Mode,Lahee,Type,License Date,Spud Date,Rig Release Date,Producing Zone,Field,DEPT,Subsea,Formation,VKNS Isopach,Interval (step),";
	private int row;

	public void formatData(String uwiInfo, LasData lasData, TopData topData) {
		FormattedData formattedData = new FormattedData();

		String formattedHeader = lasData.getHeader().trim().replaceAll(" +", ",");
		formattedHeader = header + formattedHeader.substring(8);

		formattedData.addHeader(formattedHeader);

		for (int i = 0; i < lasData.getSize(); i++) {
			String formatRow = lasData.getRow(i).trim().replaceAll(" +", ",");

			Double depth = Double.parseDouble(formatRow.substring(0, formatRow.indexOf(",")));

			Double subsea = Double.parseDouble(getCol(uwiInfo, 5)) - depth;

			formatRow = depth.toString() + "," + subsea.toString() + "," + topData.getFormation(depth) + ", ,0.1"
					+ formatRow.substring(ordinalIndexOf(formatRow, ",", 1));

			formatRow = uwiInfo.substring(0, uwiInfo.length() - 1) + "," + formatRow;
			
			formattedData.addRow(formatRow);
		}
		
		if (uwiInfo.startsWith("1W40352317110300")) { 
			System.out.println(uwiInfo);
		}

		// formattedData.display();
		WriteToCSV writer = new WriteToCSV(formattedData);
		writer.write();
	}

	public String getCol(String row, int index) {
		return row.substring(ordinalIndexOf(row, ",", index) + 1, ordinalIndexOf(row, ",", index + 1));
	}

	public int ordinalIndexOf(String str, String substr, int n) {
		int pos = str.indexOf(substr);
		while (--n > 0 && pos != -1)
			pos = str.indexOf(substr, pos + 1);
		return pos;
	}
}
