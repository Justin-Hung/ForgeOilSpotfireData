import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import java.awt.GridBagLayout;
import javax.swing.Box;
import java.awt.GridBagConstraints;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import java.awt.Panel;
import java.util.ArrayList;

import javax.swing.JScrollPane;

public class ExamineWellGui {

	private Controller controller;
	private String uwi; 
	private JFrame frame;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ExamineWellGui window = new ExamineWellGui();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ExamineWellGui() {
		uwi = "???/??-??-???-??W?/?";
		initialize();
		frame.setVisible(true);
	}
	
	public ExamineWellGui(Controller controller, String uwi) {
		this.uwi = uwi;
		this.controller = controller;
		initialize();
		frame.setVisible(true);
	}
	
	public Object[][] getMnemonics() { 
		String header = "";
		String serviceCo = "";
		for(int i = 0; i < controller.getFormattedDataList().size() ; i++) {
			if (uwi.equals(controller.getFormattedDataList().get(i).getUwi())) { 
				header = controller.getFormattedDataList().get(i).getHeader();
				if (controller.getFormattedDataList().get(i).getRow(0).endsWith(",,")) {
					serviceCo = controller.getFormattedDataList().get(i).getRow(0).split(",")[controller.getFormattedDataList().get(i).getRow(0).split(",").length-6];
				}
				else { 
					serviceCo = controller.getFormattedDataList().get(i).getRow(0).split(",")[controller.getFormattedDataList().get(i).getRow(0).split(",").length-9];
				}
			}
		}
		if (header == "") {
			return null; 
		}
		
		System.out.println(header);
		
		int headerOffset =  controller.getDataWriter().getHeaderOffset();
		String[] headerArray = header.split(",");
		ArrayList<String> lasMnemonicArray = new ArrayList<String>(); 
		ArrayList<String> mnemonicNameArray = new ArrayList<String>(); 
		
		for (int i = headerOffset ; i < headerArray.length - 11 ; i++) {
			if (!headerArray[i].equals("")) {
				mnemonicNameArray.add(controller.getMnemonicList().get(i - headerOffset).getName());
				lasMnemonicArray.add(headerArray[i]);
				System.out.println(controller.getMnemonicList().get(i - headerOffset).getName() + "   " + headerArray[i]);
			}
		}
		if (!headerArray[headerArray.length - 11].equals("")) {
			lasMnemonicArray.add(headerArray[headerArray.length - 11]); 
			mnemonicNameArray.add("Caliper 2");
		}
		lasMnemonicArray.add(serviceCo);
		mnemonicNameArray.add("Service Co.");
		
		
		Object[][] objArray = new Object[lasMnemonicArray.size()][2];
		for (int i = 0; i < objArray.length; i++) { 
			objArray[i][0] = mnemonicNameArray.get(i); 
			objArray[i][1] = lasMnemonicArray.get(i);
		}
		return objArray;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 558, 500);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.SOUTH);
		
		JLabel lblUwi = new JLabel("UWI: ");
		panel.add(lblUwi);
		
		JLabel lblw = new JLabel(uwi);
		panel.add(lblw);
		
		String [] header = {"Mnemonic Name", "Las Data"};
		Object[][] data = getMnemonics();
		table = new JTable(new MyTableModel(header, data));
		table.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(table);
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

	}
	
	class MyTableModel extends AbstractTableModel {
		
	    private String[] columnNames;
	    private Object[][] data;
	    
	    public MyTableModel(String[] columnNames, Object[][] data) {
	    	this.columnNames = columnNames; 
	    	this.data = data;
	    }

	    public int getColumnCount() {
	        return columnNames.length;
	    }

	    public int getRowCount() {
	        return data.length;
	    }

	    public String getColumnName(int col) {
	        return columnNames[col];
	    }

	    public Object getValueAt(int row, int col) {
	        return data[row][col];
	    }

	    public Class getColumnClass(int c) {
	        return getValueAt(0, c).getClass();
	    }
	}
}
