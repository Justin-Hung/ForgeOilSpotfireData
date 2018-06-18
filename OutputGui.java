import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.io.File;
import java.awt.FlowLayout;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class OutputGui {

	private JList<String> successList = new JList<String>(); 
	private DefaultListModel<String> successModel = new DefaultListModel<>(); 
	
	private JList<String> topErrorList = new JList<String>(); 
	private DefaultListModel<String> topErrorModel = new DefaultListModel<>(); 
	
	private JList<String> gwiErrorList = new JList<String>();
	private DefaultListModel<String> gwiErrorModel = new DefaultListModel<>(); 
	
	private JList<String> lasErrorList = new JList<String>(); 
	private DefaultListModel<String> lasErrorModel = new DefaultListModel<>(); 
	
	private OutputData outputData;
	private JFrame frame;
	private JTextField successTextField;
	private JTextField topErrorTextField;
	private JTextField gwiErrorTextField;
	private JTextField lasErrorTextField;
	
	private String outputFilePath; 
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OutputGui window = new OutputGui();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public OutputGui(OutputData outputData, String outputFile) {
		outputFilePath = outputFile;
		this.outputData = outputData;
		initializeJList(); 
		initialize();
		frame.setVisible(true);
	}

	public OutputGui() {
		outputFilePath = "C:\\Users\\jhung\\SpotfireDataFiles";
		outputData = new OutputData();
		initializeJList();
		initialize();
		frame.setVisible(true);
	}
	
	public void initializeJList() { 
		successList.setModel(successModel);
		topErrorList.setModel(topErrorModel);
		gwiErrorList.setModel(gwiErrorModel);
		lasErrorList.setModel(lasErrorModel);
		
		for (int i = 0; i < outputData.getSuccessUwis().size() ; i++) {
			successModel.addElement(outputData.getSuccessUwis().get(i));; 
		}
		
		for (int i = 0; i < outputData.getTopErrorUwis().size() ; i++) {
			topErrorModel.addElement(outputData.getTopErrorUwis().get(i));
		}
		
		for (int i = 0; i < outputData.getGwiErrorUwis().size() ; i++) {
			gwiErrorModel.addElement(outputData.getGwiErrorUwis().get(i));
		}
		
		for (int i = 0; i < outputData.getLasErrorUwis().size() ; i++) {
			lasErrorModel.addElement(outputData.getLasErrorUwis().get(i));
		}
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 645, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		File outputFile = new File(outputFilePath);	
		JPanel hyperLinkPanel = new JPanel();
		LinkLabel hyperLinkText = new LinkLabel(outputFile.toURI());
		hyperLinkText.init();
		
		JLabel lblOutputDirectory = new JLabel("Output Directory: ");
		hyperLinkPanel.add(lblOutputDirectory);
		hyperLinkPanel.add(hyperLinkText);
		frame.getContentPane().add(hyperLinkPanel, BorderLayout.SOUTH);
		
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel successPanel = new JPanel();
		tabbedPane.addTab("Success", null, successPanel, null);
		GridBagLayout gbl_successPanel = new GridBagLayout();
		gbl_successPanel.columnWidths = new int[]{0, 0};
		gbl_successPanel.rowHeights = new int[]{0, 0};
		gbl_successPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_successPanel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		successPanel.setLayout(gbl_successPanel);
		
		JPanel panel = new JPanel();
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{184, 33, 86, 50, 59, 6, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JButton successSearchButton = new JButton("Search");
		successSearchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		GridBagConstraints gbc_successSearchButton = new GridBagConstraints();
		gbc_successSearchButton.insets = new Insets(0, 0, 0, 5);
		gbc_successSearchButton.gridx = 1;
		gbc_successSearchButton.gridy = 1;
		panel.add(successSearchButton, gbc_successSearchButton);
		
		successTextField = new JTextField();
		GridBagConstraints gbc_successTextField = new GridBagConstraints();
		gbc_successTextField.anchor = GridBagConstraints.WEST;
		gbc_successTextField.insets = new Insets(0, 0, 0, 5);
		gbc_successTextField.gridx = 2;
		gbc_successTextField.gridy = 1;
		panel.add(successTextField, gbc_successTextField);
		successTextField.setColumns(10);
		
		Component horizontalStrut = Box.createHorizontalStrut(50);
		GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
		gbc_horizontalStrut.anchor = GridBagConstraints.SOUTHWEST;
		gbc_horizontalStrut.insets = new Insets(0, 0, 0, 5);
		gbc_horizontalStrut.gridx = 3;
		gbc_horizontalStrut.gridy = 1;
		panel.add(horizontalStrut, gbc_horizontalStrut);
		
		JLabel lblTotalWells = new JLabel("Total Wells: ");
		GridBagConstraints gbc_lblTotalWells = new GridBagConstraints();
		gbc_lblTotalWells.anchor = GridBagConstraints.WEST;
		gbc_lblTotalWells.insets = new Insets(0, 0, 0, 5);
		gbc_lblTotalWells.gridx = 4;
		gbc_lblTotalWells.gridy = 1;
		panel.add(lblTotalWells, gbc_lblTotalWells);
		
		JLabel successLabel = new JLabel(String.valueOf(outputData.getSuccessUwis().size()));
		GridBagConstraints gbc_successLabel = new GridBagConstraints();
		gbc_successLabel.anchor = GridBagConstraints.WEST;
		gbc_successLabel.gridx = 5;
		gbc_successLabel.gridy = 1;
		panel.add(successLabel, gbc_successLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		successPanel.add(scrollPane, gbc_scrollPane);
		
		scrollPane.setViewportView(successList);
		scrollPane.setColumnHeaderView(panel);
		
		JPanel topErrorPanel = new JPanel();
		tabbedPane.addTab("Top Error", null, topErrorPanel, null);
		GridBagLayout gbl_topErrorPanel = new GridBagLayout();
		gbl_topErrorPanel.columnWidths = new int[]{0, 0};
		gbl_topErrorPanel.rowHeights = new int[]{0, 0};
		gbl_topErrorPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_topErrorPanel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		topErrorPanel.setLayout(gbl_topErrorPanel);
		
		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 0;
		topErrorPanel.add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0};
		gbl_panel_1.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel label = new JLabel("Search");
		panel_2.add(label);
		
		topErrorTextField = new JTextField();
		topErrorTextField.setColumns(10);
		panel_2.add(topErrorTextField);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(50);
		panel_2.add(horizontalStrut_1);
		
		JLabel label_1 = new JLabel("Total Wells: ");
		panel_2.add(label_1);
		
		JLabel topErrorLabel = new JLabel(String.valueOf(outputData.getTopErrorUwis().size()));
		panel_2.add(topErrorLabel);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 0;
		panel_1.add(scrollPane_1, gbc_scrollPane_1);

		scrollPane_1.setViewportView(topErrorList);
		scrollPane_1.setColumnHeaderView(panel_2);
		
		JPanel gwiErrorPanel = new JPanel();
		tabbedPane.addTab("GWI Error", null, gwiErrorPanel, null);
		GridBagLayout gbl_gwiErrorPanel = new GridBagLayout();
		gbl_gwiErrorPanel.columnWidths = new int[]{0, 0};
		gbl_gwiErrorPanel.rowHeights = new int[]{0, 0};
		gbl_gwiErrorPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_gwiErrorPanel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		gwiErrorPanel.setLayout(gbl_gwiErrorPanel);
		
		JPanel panel_3 = new JPanel();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 0;
		gwiErrorPanel.add(panel_3, gbc_panel_3);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[]{0, 0};
		gbl_panel_3.rowHeights = new int[]{0, 0};
		gbl_panel_3.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_3.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel_3.setLayout(gbl_panel_3);
		
		JPanel panel_4 = new JPanel();
		panel_4.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel label_3 = new JLabel("Search");
		panel_4.add(label_3);
		
		gwiErrorTextField = new JTextField();
		gwiErrorTextField.setColumns(10);
		panel_4.add(gwiErrorTextField);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(50);
		panel_4.add(horizontalStrut_2);
		
		JLabel label_4 = new JLabel("Total Wells: ");
		panel_4.add(label_4);
		
		JLabel gwiErrorLabel = new JLabel(String.valueOf(outputData.getGwiErrorUwis().size()));
		panel_4.add(gwiErrorLabel);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_scrollPane_2 = new GridBagConstraints();
		gbc_scrollPane_2.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_2.gridx = 0;
		gbc_scrollPane_2.gridy = 0;
		panel_3.add(scrollPane_2, gbc_scrollPane_2);
		
		scrollPane_2.setViewportView(gwiErrorList);
		scrollPane_2.setColumnHeaderView(panel_4);
		
		JPanel lasErrorPanel = new JPanel();
		tabbedPane.addTab("Las Error", null, lasErrorPanel, null);
		GridBagLayout gbl_lasErrorPanel = new GridBagLayout();
		gbl_lasErrorPanel.columnWidths = new int[]{0, 0};
		gbl_lasErrorPanel.rowHeights = new int[]{0, 0};
		gbl_lasErrorPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_lasErrorPanel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		lasErrorPanel.setLayout(gbl_lasErrorPanel);
		
		JPanel panel_5 = new JPanel();
		GridBagConstraints gbc_panel_5 = new GridBagConstraints();
		gbc_panel_5.fill = GridBagConstraints.BOTH;
		gbc_panel_5.gridx = 0;
		gbc_panel_5.gridy = 0;
		lasErrorPanel.add(panel_5, gbc_panel_5);
		GridBagLayout gbl_panel_5 = new GridBagLayout();
		gbl_panel_5.columnWidths = new int[]{0, 0};
		gbl_panel_5.rowHeights = new int[]{0, 0};
		gbl_panel_5.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_5.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel_5.setLayout(gbl_panel_5);
		
		JPanel panel_6 = new JPanel();
		panel_6.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel label_6 = new JLabel("Search");
		panel_6.add(label_6);
		
		lasErrorTextField = new JTextField();
		lasErrorTextField.setColumns(10);
		panel_6.add(lasErrorTextField);
		
		Component horizontalStrut_3 = Box.createHorizontalStrut(50);
		panel_6.add(horizontalStrut_3);
		
		JLabel label_7 = new JLabel("Total Wells: ");
		panel_6.add(label_7);
		
		JLabel lasErrorLabel = new JLabel(String.valueOf(outputData.getLasErrorUwis().size()));
		panel_6.add(lasErrorLabel);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_scrollPane_3 = new GridBagConstraints();
		gbc_scrollPane_3.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_3.gridx = 0;
		gbc_scrollPane_3.gridy = 0;
		panel_5.add(scrollPane_3, gbc_scrollPane_3);
		
		scrollPane_3.setViewportView(lasErrorList);
		scrollPane_3.setColumnHeaderView(panel_6);
	}
}
