import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.io.File;
import java.net.URL;
import java.awt.FlowLayout;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import org.apache.log4j.chainsaw.Main;

import javax.swing.JTextArea;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class OutputGui {

	private JList<String> successList = new JList<String>(); 
	private DefaultListModel<String> successModel = new DefaultListModel<>(); 
	private JLabel successLabel;
	
	private JList<String> topErrorList = new JList<String>(); 
	private DefaultListModel<String> topErrorModel = new DefaultListModel<>(); 
	private JLabel topErrorLabel;
	
	private JList<String> gwiErrorList = new JList<String>();
	private DefaultListModel<String> gwiErrorModel = new DefaultListModel<>(); 
	private JLabel gwiErrorLabel;
	
	private JList<String> lasErrorList = new JList<String>(); 
	private DefaultListModel<String> lasErrorModel = new DefaultListModel<>(); 
	private	JLabel lasErrorLabel;
	
	private OutputData outputData;
	private JFrame frame;
	private JTextField successTextField;
	private JTextField topErrorTextField;
	private JTextField gwiErrorTextField;
	private JTextField lasErrorTextField;
	private Controller controller;
	
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
	public OutputGui(Controller controller) {
		this.controller = controller;
		outputFilePath =  controller.getUserInput().getOutputfilePath();
		this.outputData = controller.getOutputData();
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
//		URL url = Main.class.getResource("/resources/forgeIcon.png");
//		ImageIcon forgeIcon = new ImageIcon(url);
//		frame.setIconImage(forgeIcon.getImage());
	
		frame = new JFrame();
		frame.setBounds(100, 100, 645, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		if (outputFilePath.endsWith("csv")) { 
			outputFilePath = outputFilePath.substring(0,outputFilePath.lastIndexOf("\\")); 
		}
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
		
		successLabel = new JLabel(String.valueOf(outputData.getSuccessUwis().size()));
		
		JButton successSearchButton = new JButton("Search");
		successSearchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DefaultListModel<String> successSearchModel = new DefaultListModel<>(); 
				for (int i = 0 ; i < successModel.size() ; i++) {
					if (successModel.getElementAt(i).contains(successTextField.getText())) {
						successSearchModel.addElement(successModel.getElementAt(i));
					}
				}
				successLabel.setText(String.valueOf(successSearchModel.size()));
				successList.setModel(successSearchModel); 
				frame.setVisible(true);
			}
		});
		FlowLayout fl_panel = new FlowLayout(FlowLayout.LEFT, 5, 5);
		panel.setLayout(fl_panel);
		
		JButton btnClearSearch = new JButton("Clear Search");
		btnClearSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				successList.setModel(successModel);
				successTextField.setText("");
				successLabel.setText(String.valueOf(successModel.size()));
				frame.setVisible(true);
			}
		});
		
		JButton btnExamine = new JButton("Examine");
		btnExamine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ExamineWellGui(controller, successList.getSelectedValue());
			}
		});
		panel.add(btnExamine);
		
		Component horizontalStrut_7 = Box.createHorizontalStrut(20);
		panel.add(horizontalStrut_7);
		panel.add(btnClearSearch);
		
		Component horizontalStrut_4 = Box.createHorizontalStrut(20);
		panel.add(horizontalStrut_4);
		panel.add(successSearchButton);
		
		successTextField = new JTextField();
		panel.add(successTextField);
		successTextField.setColumns(13);
		
		Component horizontalStrut = Box.createHorizontalStrut(10);
		panel.add(horizontalStrut);
		
		JLabel lblTotalWells = new JLabel("Total Wells: ");
		panel.add(lblTotalWells);
		
		panel.add(successLabel);
		
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
		panel_2.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JLabel topErrorLabel = new JLabel(String.valueOf(outputData.getTopErrorUwis().size()));
		
		JButton topErrorSearchButton = new JButton("Search");
		topErrorSearchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DefaultListModel<String> topErrorSearchModel = new DefaultListModel<>(); 
				for (int i = 0 ; i < topErrorModel.size() ; i++) {
					if (topErrorModel.getElementAt(i).contains(topErrorTextField.getText())) {
						topErrorSearchModel.addElement(topErrorModel.getElementAt(i));
					}
				}
				topErrorLabel.setText(String.valueOf(topErrorSearchModel.size()));
				topErrorList.setModel(topErrorSearchModel);
				frame.setVisible(true);
			}
		});
		
		JButton button = new JButton("Clear Search");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				topErrorList.setModel(topErrorModel);
				topErrorTextField.setText("");
				topErrorLabel.setText(String.valueOf(topErrorModel.size()));
				frame.setVisible(true);
			}
		});
		panel_2.add(button);
		
		Component horizontalStrut_5 = Box.createHorizontalStrut(75);
		panel_2.add(horizontalStrut_5);
		panel_2.add(topErrorSearchButton);
		
		topErrorTextField = new JTextField();
		topErrorTextField.setColumns(13);
		panel_2.add(topErrorTextField);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(70);
		panel_2.add(horizontalStrut_1);
		
		JLabel label_1 = new JLabel("Total Wells: ");
		panel_2.add(label_1);
		

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
		panel_4.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JButton button_1 = new JButton("Clear Search");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gwiErrorList.setModel(gwiErrorModel);
				gwiErrorTextField.setText("");
				gwiErrorLabel.setText(String.valueOf(outputData.getGwiErrorUwis().size()));
				frame.setVisible(true);
			}
		});
		panel_4.add(button_1);
		
		Component horizontalStrut_6 = Box.createHorizontalStrut(75);
		panel_4.add(horizontalStrut_6);
		
		JButton gwiErrorSearchButton = new JButton("Search");
		gwiErrorSearchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultListModel<String> gwiErrorSearchModel = new DefaultListModel<>(); 
				for (int i = 0 ; i < gwiErrorModel.size() ; i++) {
					if (gwiErrorModel.getElementAt(i).contains(gwiErrorTextField.getText())) {
						gwiErrorSearchModel.addElement(gwiErrorModel.getElementAt(i));
					}
				}
				gwiErrorLabel.setText(String.valueOf(gwiErrorSearchModel.size()));
				gwiErrorList.setModel(gwiErrorSearchModel);
				frame.setVisible(true);
			}
		});
		panel_4.add(gwiErrorSearchButton);
		
		gwiErrorTextField = new JTextField();
		gwiErrorTextField.setColumns(13);
		panel_4.add(gwiErrorTextField);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(70);
		panel_4.add(horizontalStrut_2);
		
		JLabel label_4 = new JLabel("Total Wells: ");
		panel_4.add(label_4);
		
		gwiErrorLabel = new JLabel(String.valueOf(outputData.getGwiErrorUwis().size()));
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
		panel_6.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JButton button_2 = new JButton("Clear Search");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lasErrorList.setModel(lasErrorModel);
				lasErrorTextField.setText("");
				lasErrorLabel.setText(String.valueOf(outputData.getLasErrorUwis().size()));
				frame.setVisible(true);
			}
		});
		panel_6.add(button_2);
		
		Component horizontalStrut_8 = Box.createHorizontalStrut(75);
		panel_6.add(horizontalStrut_8);
		
		JButton lasErrorSearchError = new JButton("Search");
		lasErrorSearchError.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultListModel<String> lasErrorSearchModel = new DefaultListModel<>(); 
				for (int i = 0 ; i < lasErrorModel.size() ; i++) {
					if (lasErrorModel.getElementAt(i).contains(lasErrorTextField.getText())) {
						lasErrorSearchModel.addElement(lasErrorModel.getElementAt(i));
					}
				}
				lasErrorLabel.setText(String.valueOf(lasErrorSearchModel.size()));
				lasErrorList.setModel(lasErrorSearchModel);
				frame.setVisible(true);
			}
		});
		panel_6.add(lasErrorSearchError);
		
		lasErrorTextField = new JTextField();
		lasErrorTextField.setColumns(13);
		panel_6.add(lasErrorTextField);
		
		Component horizontalStrut_3 = Box.createHorizontalStrut(70);
		panel_6.add(horizontalStrut_3);
		
		JLabel label_7 = new JLabel("Total Wells: ");
		panel_6.add(label_7);
		
		lasErrorLabel = new JLabel(String.valueOf(outputData.getLasErrorUwis().size()));
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
