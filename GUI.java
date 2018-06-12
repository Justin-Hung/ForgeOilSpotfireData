import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class GUI extends JFrame {

	private JPanel contentPane;
	private JTextField topFile;
	private JLabel lblGwiFileLocation;
	private JLabel lblLasDataFile;
	private JLabel lblOutputLocation;
	private JTextField gwiFile;
	private JTextField lasFile;
	private JTextField outputFile;
	private JButton btnBrowse;
	private JButton button;
	private JButton button_1;
	private JButton button_2;
	private Component verticalStrut;
	private JLabel lblUpperBuffer;
	private JLabel lblLowerBuffer;
	private JTextField upperBufferField;
	private JTextField lowerBufferField;
	private JTextField upperFormationField;
	private JTextField lowerFormationField;
	private JLabel lblUpperFormation;
	private JLabel lblLowerFormation;
	private JLabel lblTownshipNw;
	private JLabel lblTownshipSe;
	private JLabel lblCreatedByJustin;
	private Component verticalStrut_1;
	private JLabel lblForgeOil;
	private JPanel panel;
	private JTextField nwSectionField;
	private JTextField nwTownField;
	private JTextField nwMeridianField;
	private JTextField nwRangeField;
	private JPanel panel_1;
	private JTextField seSectionField;
	private JTextField seTownField;
	private JTextField seMeridianField;
	private JTextField seRangeField;
	private JLabel lblW;
	private JLabel label;
	private JPanel panel_2;
	private JLabel lblSection;
	private JLabel lblTownship;
	private JLabel lblRange;
	private JLabel lblMeridian;
	private Component horizontalStrut;
	private Component horizontalStrut_1;
	private Component horizontalStrut_2;
	private JButton btnRun;
	private JLabel label_5;
	private JLabel label_6;
	private JLabel label_7;
	private JLabel label_8;
	private Component verticalStrut_2;
	private Component verticalStrut_3;
	private Component verticalStrut_4;
	private JPanel panel_3;
	private JLabel label_1;
	private Component horizontalStrut_3;
	private JLabel label_2;
	private Component horizontalStrut_4;
	private JLabel label_3;
	private Component horizontalStrut_5;
	private JLabel label_4;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		setTitle("Spotfire Data Generator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 959, 482);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{1, 34, 104, 67, 35, 86, 35, 600, 0};
		gbl_contentPane.rowHeights = new int[]{1, 14, 30, 30, 30, 14, 30, 30, 30, 14, 23, 30, 30, 14, 23, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		verticalStrut_1 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_1 = new GridBagConstraints();
		gbc_verticalStrut_1.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut_1.gridx = 0;
		gbc_verticalStrut_1.gridy = 0;
		contentPane.add(verticalStrut_1, gbc_verticalStrut_1);
		
		verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut.gridx = 0;
		gbc_verticalStrut.gridy = 0;
		contentPane.add(verticalStrut, gbc_verticalStrut);
		
		JLabel lblTopFileLocation = new JLabel("Top File Location");
		GridBagConstraints gbc_lblTopFileLocation = new GridBagConstraints();
		gbc_lblTopFileLocation.insets = new Insets(0, 0, 5, 5);
		gbc_lblTopFileLocation.gridx = 2;
		gbc_lblTopFileLocation.gridy = 1;
		contentPane.add(lblTopFileLocation, gbc_lblTopFileLocation);
		
		lblUpperBuffer = new JLabel("Upper Buffer");
		GridBagConstraints gbc_lblUpperBuffer = new GridBagConstraints();
		gbc_lblUpperBuffer.insets = new Insets(0, 0, 5, 5);
		gbc_lblUpperBuffer.gridx = 5;
		gbc_lblUpperBuffer.gridy = 1;
		contentPane.add(lblUpperBuffer, gbc_lblUpperBuffer);
		
		lblTownshipNw = new JLabel("Township NW");
		GridBagConstraints gbc_lblTownshipNw = new GridBagConstraints();
		gbc_lblTownshipNw.insets = new Insets(0, 0, 5, 0);
		gbc_lblTownshipNw.gridx = 7;
		gbc_lblTownshipNw.gridy = 1;
		contentPane.add(lblTownshipNw, gbc_lblTownshipNw);
		
		topFile = new JTextField();
		GridBagConstraints gbc_topFile = new GridBagConstraints();
		gbc_topFile.fill = GridBagConstraints.HORIZONTAL;
		gbc_topFile.insets = new Insets(0, 0, 5, 5);
		gbc_topFile.gridx = 2;
		gbc_topFile.gridy = 2;
		contentPane.add(topFile, gbc_topFile);
		topFile.setColumns(10);
		btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileBrowser = new JFileChooser(); 
				fileBrowser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				if (fileBrowser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					File file = fileBrowser.getCurrentDirectory();
					File currentDir = fileBrowser.getSelectedFile(); 
					String filePath = file.getAbsolutePath() + "\\" + currentDir.getName();
					topFile.setText(filePath);
				}
			}
		});
		GridBagConstraints gbc_btnBrowse = new GridBagConstraints();
		gbc_btnBrowse.insets = new Insets(0, 0, 5, 5);
		gbc_btnBrowse.gridx = 3;
		gbc_btnBrowse.gridy = 2;
		contentPane.add(btnBrowse, gbc_btnBrowse);
		
		upperBufferField = new JTextField();
		GridBagConstraints gbc_upperBufferField = new GridBagConstraints();
		gbc_upperBufferField.fill = GridBagConstraints.HORIZONTAL;
		gbc_upperBufferField.insets = new Insets(0, 0, 5, 5);
		gbc_upperBufferField.gridx = 5;
		gbc_upperBufferField.gridy = 2;
		contentPane.add(upperBufferField, gbc_upperBufferField);
		upperBufferField.setColumns(10);
		
		panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.gridx = 7;
		gbc_panel.gridy = 2;
		contentPane.add(panel, gbc_panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		nwSectionField = new JTextField();
		nwSectionField.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(nwSectionField);
		nwSectionField.setColumns(10);
		
		label_7 = new JLabel("-");
		panel.add(label_7);
		
		nwTownField = new JTextField();
		nwTownField.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(nwTownField);
		nwTownField.setColumns(10);
		
		label_8 = new JLabel("-");
		panel.add(label_8);
		
		nwRangeField = new JTextField();
		nwRangeField.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(nwRangeField);
		nwRangeField.setColumns(10);
		
		lblW = new JLabel("W");
		panel.add(lblW);
		
		nwMeridianField = new JTextField();
		nwMeridianField.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(nwMeridianField);
		nwMeridianField.setColumns(10);
		
		panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_2.insets = new Insets(0, 0, 5, 0);
		gbc_panel_2.gridx = 7;
		gbc_panel_2.gridy = 3;
		contentPane.add(panel_2, gbc_panel_2);
		
		lblSection = new JLabel("Section");
		panel_2.add(lblSection);
		
		horizontalStrut = Box.createHorizontalStrut(70);
		panel_2.add(horizontalStrut);
		
		lblTownship = new JLabel("Town");
		panel_2.add(lblTownship);
		
		horizontalStrut_1 = Box.createHorizontalStrut(77);
		panel_2.add(horizontalStrut_1);
		
		lblRange = new JLabel("Range");
		panel_2.add(lblRange);
		
		horizontalStrut_2 = Box.createHorizontalStrut(70);
		panel_2.add(horizontalStrut_2);
		
		lblMeridian = new JLabel("Meridian");
		panel_2.add(lblMeridian);
		
		verticalStrut_3 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_3 = new GridBagConstraints();
		gbc_verticalStrut_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_verticalStrut_3.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut_3.gridx = 7;
		gbc_verticalStrut_3.gridy = 4;
		contentPane.add(verticalStrut_3, gbc_verticalStrut_3);
		
		lblGwiFileLocation = new JLabel("GWI File Location");
		GridBagConstraints gbc_lblGwiFileLocation = new GridBagConstraints();
		gbc_lblGwiFileLocation.insets = new Insets(0, 0, 5, 5);
		gbc_lblGwiFileLocation.gridx = 2;
		gbc_lblGwiFileLocation.gridy = 5;
		contentPane.add(lblGwiFileLocation, gbc_lblGwiFileLocation);
		
		lblLowerBuffer = new JLabel("Lower Buffer");
		GridBagConstraints gbc_lblLowerBuffer = new GridBagConstraints();
		gbc_lblLowerBuffer.insets = new Insets(0, 0, 5, 5);
		gbc_lblLowerBuffer.gridx = 5;
		gbc_lblLowerBuffer.gridy = 5;
		contentPane.add(lblLowerBuffer, gbc_lblLowerBuffer);
		
		lblTownshipSe = new JLabel("Township SE");
		GridBagConstraints gbc_lblTownshipSe = new GridBagConstraints();
		gbc_lblTownshipSe.insets = new Insets(0, 0, 5, 0);
		gbc_lblTownshipSe.gridx = 7;
		gbc_lblTownshipSe.gridy = 5;
		contentPane.add(lblTownshipSe, gbc_lblTownshipSe);
		
		gwiFile = new JTextField();
		GridBagConstraints gbc_gwiFile = new GridBagConstraints();
		gbc_gwiFile.fill = GridBagConstraints.HORIZONTAL;
		gbc_gwiFile.insets = new Insets(0, 0, 5, 5);
		gbc_gwiFile.gridx = 2;
		gbc_gwiFile.gridy = 6;
		contentPane.add(gwiFile, gbc_gwiFile);
		gwiFile.setColumns(10);
		
		button = new JButton("Browse");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileBrowser = new JFileChooser(); 
				fileBrowser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				if (fileBrowser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					File file = fileBrowser.getCurrentDirectory();
					File currentDir = fileBrowser.getSelectedFile(); 
					String filePath = file.getAbsolutePath() + "\\" + currentDir.getName();
					gwiFile.setText(filePath);
				}
			}
		});
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.insets = new Insets(0, 0, 5, 5);
		gbc_button.gridx = 3;
		gbc_button.gridy = 6;
		contentPane.add(button, gbc_button);
		
		lowerBufferField = new JTextField();
		lowerBufferField.setColumns(10);
		GridBagConstraints gbc_lowerBufferField = new GridBagConstraints();
		gbc_lowerBufferField.fill = GridBagConstraints.HORIZONTAL;
		gbc_lowerBufferField.insets = new Insets(0, 0, 5, 5);
		gbc_lowerBufferField.gridx = 5;
		gbc_lowerBufferField.gridy = 6;
		contentPane.add(lowerBufferField, gbc_lowerBufferField);
		
		panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.gridx = 7;
		gbc_panel_1.gridy = 6;
		contentPane.add(panel_1, gbc_panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		seSectionField = new JTextField();
		seSectionField.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(seSectionField);
		seSectionField.setColumns(10);
		
		label_5 = new JLabel("-");
		panel_1.add(label_5);
		
		seTownField = new JTextField();
		seTownField.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(seTownField);
		seTownField.setColumns(10);
		
		label_6 = new JLabel("-");
		panel_1.add(label_6);
		
		seRangeField = new JTextField();
		seRangeField.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(seRangeField);
		seRangeField.setColumns(10);
		
		label = new JLabel("W");
		panel_1.add(label);
		
		seMeridianField = new JTextField();
		seMeridianField.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(seMeridianField);
		seMeridianField.setColumns(10);
		
		panel_3 = new JPanel();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.insets = new Insets(0, 0, 5, 0);
		gbc_panel_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_3.gridx = 7;
		gbc_panel_3.gridy = 7;
		contentPane.add(panel_3, gbc_panel_3);
		
		label_1 = new JLabel("Section");
		panel_3.add(label_1);
		
		horizontalStrut_3 = Box.createHorizontalStrut(70);
		panel_3.add(horizontalStrut_3);
		
		label_2 = new JLabel("Town");
		panel_3.add(label_2);
		
		horizontalStrut_4 = Box.createHorizontalStrut(77);
		panel_3.add(horizontalStrut_4);
		
		label_3 = new JLabel("Range");
		panel_3.add(label_3);
		
		horizontalStrut_5 = Box.createHorizontalStrut(70);
		panel_3.add(horizontalStrut_5);
		
		label_4 = new JLabel("Meridian");
		panel_3.add(label_4);
		
		verticalStrut_2 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_2 = new GridBagConstraints();
		gbc_verticalStrut_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_verticalStrut_2.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut_2.gridx = 7;
		gbc_verticalStrut_2.gridy = 8;
		contentPane.add(verticalStrut_2, gbc_verticalStrut_2);
		
		lblLasDataFile = new JLabel("Las Data File Location");
		GridBagConstraints gbc_lblLasDataFile = new GridBagConstraints();
		gbc_lblLasDataFile.insets = new Insets(0, 0, 5, 5);
		gbc_lblLasDataFile.gridx = 2;
		gbc_lblLasDataFile.gridy = 9;
		contentPane.add(lblLasDataFile, gbc_lblLasDataFile);
		
		lblUpperFormation = new JLabel("Upper Formation");
		GridBagConstraints gbc_lblUpperFormation = new GridBagConstraints();
		gbc_lblUpperFormation.insets = new Insets(0, 0, 5, 5);
		gbc_lblUpperFormation.gridx = 5;
		gbc_lblUpperFormation.gridy = 9;
		contentPane.add(lblUpperFormation, gbc_lblUpperFormation);
		
		lasFile = new JTextField();
		GridBagConstraints gbc_lasFile = new GridBagConstraints();
		gbc_lasFile.fill = GridBagConstraints.HORIZONTAL;
		gbc_lasFile.insets = new Insets(0, 0, 5, 5);
		gbc_lasFile.gridx = 2;
		gbc_lasFile.gridy = 10;
		contentPane.add(lasFile, gbc_lasFile);
		lasFile.setColumns(10);
		
		button_1 = new JButton("Browse");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileBrowser = new JFileChooser(); 
				fileBrowser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				if (fileBrowser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					File file = fileBrowser.getCurrentDirectory();
					File currentDir = fileBrowser.getSelectedFile(); 
					String filePath = file.getAbsolutePath() + "\\" + currentDir.getName();
					lasFile.setText(filePath);
				}
			}
		});
		GridBagConstraints gbc_button_1 = new GridBagConstraints();
		gbc_button_1.insets = new Insets(0, 0, 5, 5);
		gbc_button_1.gridx = 3;
		gbc_button_1.gridy = 10;
		contentPane.add(button_1, gbc_button_1);
		
		upperFormationField = new JTextField();
		upperFormationField.setColumns(10);
		GridBagConstraints gbc_upperFormationField = new GridBagConstraints();
		gbc_upperFormationField.fill = GridBagConstraints.HORIZONTAL;
		gbc_upperFormationField.insets = new Insets(0, 0, 5, 5);
		gbc_upperFormationField.gridx = 5;
		gbc_upperFormationField.gridy = 10;
		contentPane.add(upperFormationField, gbc_upperFormationField);
		
		btnRun = new JButton("RUN");
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(topFile.getText() + ", " + gwiFile.getText() + ", " + lasFile.getText()
								   + ", " + outputFile.getText() + ", " + upperBufferField.getText() + ", " + lowerBufferField.getText()
								   + ", " + upperFormationField.getText() + ", " + lowerFormationField.getText() 
								   + ", " + nwSectionField.getText() + ", " + nwTownField.getText() + ", " + nwRangeField.getText()
								   + ", " + nwMeridianField.getText() + ", " + seSectionField.getText() + ", " + seTownField.getText()
								   + ", " + seRangeField.getText() + ", " + seMeridianField.getText());
				
				UserInput user = new UserInput(); 
				user.setTopfilePath(topFile.getText());
				user.setLasfilePath(lasFile.getText());
				user.setWorkingfilePath(gwiFile.getText());
				user.setOutputfilePath(outputFile.getText());
				user.setUpperBuffer(Double.parseDouble(upperBufferField.getText())); 
				user.setLowerBuffer(Double.parseDouble(lowerBufferField.getText()));
				ArrayList<String> forms = new ArrayList<String>();
				forms.add(upperFormationField.getText().toUpperCase());
				forms.add(lowerFormationField.getText().toUpperCase());
				user.setFormations(forms);
				user.setTownshipNw(nwSectionField.getText() + "-" + nwTownField.getText() + "-" + nwRangeField.getText()
								   + "W" + nwMeridianField.getText());
				user.setTownshipSe(seSectionField.getText() + "-" + seTownField.getText() + "-" + seRangeField.getText()
								   + "W" + seMeridianField.getText()); 
				Controller controller = new Controller(); 
				controller.run(user);
			}
		});
		GridBagConstraints gbc_btnRun = new GridBagConstraints();
		gbc_btnRun.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRun.insets = new Insets(0, 0, 5, 0);
		gbc_btnRun.gridx = 7;
		gbc_btnRun.gridy = 10;
		contentPane.add(btnRun, gbc_btnRun);
		
		verticalStrut_4 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_4 = new GridBagConstraints();
		gbc_verticalStrut_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_verticalStrut_4.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut_4.gridx = 7;
		gbc_verticalStrut_4.gridy = 12;
		contentPane.add(verticalStrut_4, gbc_verticalStrut_4);
		
		lblOutputLocation = new JLabel("Output Location");
		GridBagConstraints gbc_lblOutputLocation = new GridBagConstraints();
		gbc_lblOutputLocation.insets = new Insets(0, 0, 5, 5);
		gbc_lblOutputLocation.gridx = 2;
		gbc_lblOutputLocation.gridy = 13;
		contentPane.add(lblOutputLocation, gbc_lblOutputLocation);
		
		lblLowerFormation = new JLabel("Lower Formation");
		GridBagConstraints gbc_lblLowerFormation = new GridBagConstraints();
		gbc_lblLowerFormation.insets = new Insets(0, 0, 5, 5);
		gbc_lblLowerFormation.gridx = 5;
		gbc_lblLowerFormation.gridy = 13;
		contentPane.add(lblLowerFormation, gbc_lblLowerFormation);
		
		lblForgeOil = new JLabel("Forge Oil");
		GridBagConstraints gbc_lblForgeOil = new GridBagConstraints();
		gbc_lblForgeOil.insets = new Insets(0, 0, 5, 0);
		gbc_lblForgeOil.gridx = 7;
		gbc_lblForgeOil.gridy = 13;
		contentPane.add(lblForgeOil, gbc_lblForgeOil);
		
		button_2 = new JButton("Browse");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileBrowser = new JFileChooser(); 
				fileBrowser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				if (fileBrowser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					File file = fileBrowser.getCurrentDirectory();
					File currentDir = fileBrowser.getSelectedFile(); 
					String filePath = file.getAbsolutePath() + "\\" + currentDir.getName();
					outputFile.setText(filePath);
				}
			}
		});
		
		outputFile = new JTextField();
		outputFile.setColumns(10);
		GridBagConstraints gbc_outputFile = new GridBagConstraints();
		gbc_outputFile.fill = GridBagConstraints.HORIZONTAL;
		gbc_outputFile.insets = new Insets(0, 0, 0, 5);
		gbc_outputFile.gridx = 2;
		gbc_outputFile.gridy = 14;
		contentPane.add(outputFile, gbc_outputFile);
		GridBagConstraints gbc_button_2 = new GridBagConstraints();
		gbc_button_2.insets = new Insets(0, 0, 0, 5);
		gbc_button_2.gridx = 3;
		gbc_button_2.gridy = 14;
		contentPane.add(button_2, gbc_button_2);
		
		lowerFormationField = new JTextField();
		lowerFormationField.setColumns(10);
		GridBagConstraints gbc_lowerFormationField = new GridBagConstraints();
		gbc_lowerFormationField.fill = GridBagConstraints.HORIZONTAL;
		gbc_lowerFormationField.insets = new Insets(0, 0, 0, 5);
		gbc_lowerFormationField.gridx = 5;
		gbc_lowerFormationField.gridy = 14;
		contentPane.add(lowerFormationField, gbc_lowerFormationField);
		
		lblCreatedByJustin = new JLabel("Created by: Justin Hung");
		GridBagConstraints gbc_lblCreatedByJustin = new GridBagConstraints();
		gbc_lblCreatedByJustin.gridx = 7;
		gbc_lblCreatedByJustin.gridy = 14;
		contentPane.add(lblCreatedByJustin, gbc_lblCreatedByJustin);
	}

}
