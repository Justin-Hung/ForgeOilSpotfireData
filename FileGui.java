import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Component;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.ImageIcon;

import java.awt.Insets;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.JTextField;

import org.apache.log4j.chainsaw.Main;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;

public class FileGui {

	private JFrame frame;
	private JTextField topTextField;
	private JTextField gwiTextField;
	private JTextField lasTextField;
	private JTextField outputTextField;
	private UserInput user;
	private JTextField outputNameTextField;

	private String rememberOutputField; 
	private String rememberOutputName; 
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
		
			URL url = Main.class.getResource("/resources/previousParameters.txt"); 
			UserInput user = new UserInput();		
				try { 
					BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
					ArrayList<String> forms = new ArrayList<String>(); 
					forms.add(br.readLine());
					forms.add(br.readLine());
					user.setFormations(forms);
					user.setTownshipNw(br.readLine()); 
					user.setTownshipSe(br.readLine()); 
					user.setUpperBuffer(Double.parseDouble(br.readLine()));
					user.setLowerBuffer(Double.parseDouble(br.readLine()));
					user.setTopfilePath(br.readLine());
					user.setLasfilePath(br.readLine());
					user.setWorkingfilePath(br.readLine());
					user.setOutputfilePath(br.readLine());
				}
				catch (IOException e) {
					e.printStackTrace();
				}
					
				if (user.getUpperBuffer() == 0.0001) { 
					FileGui window = new FileGui();
				}
				else {
					FileGui window = new FileGui(user);
				}
			} 
		});
	}

	/**
	 * Create the application.
	 */
	public FileGui() {
		topTextField = new JTextField();
		gwiTextField = new JTextField();
		lasTextField = new JTextField();
		outputTextField = new JTextField();
		outputNameTextField = new JTextField();
		user = new UserInput();
		initialize();
		frame.setVisible(true);
	}
	
	public FileGui(UserInput u) { 
		user = u;
		topTextField = new JTextField(user.getTopfilePath());
		gwiTextField = new JTextField(user.getWorkingfilePath());
		lasTextField = new JTextField(user.getLasfilePath());
		outputTextField = new JTextField(user.getOutputfilePath());
		outputNameTextField = new JTextField(user.getOutputfileName());
		if (outputTextField.getText().endsWith("csv")) {
			outputNameTextField.setText(outputTextField.getText().substring(outputTextField.getText().lastIndexOf("\\") + 1, outputTextField.getText().indexOf(".csv")));
		}
		initialize();
		frame.setVisible(true);
	}
	
	
	public boolean checkTextFields() { 
		if (!new File(topTextField.getText()).exists()) {
			JOptionPane.showMessageDialog(frame, "Top file can not be found");
			return true;
		}
		if (!new File(gwiTextField.getText()).exists()) {
			JOptionPane.showMessageDialog(frame, "GWI file can not be found");
			return true; 
		}
		if (!new File(lasTextField.getText()).isDirectory()) {
			JOptionPane.showMessageDialog(frame, "las directory can not be found");
			return true; 
		} 
		return false; 
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Spotfire Data Generator");
		frame.setBounds(100, 100, 645, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		URL url = Main.class.getResource("/resources/forgeIcon.png");
		ImageIcon forgeIcon = new ImageIcon(url);
		frame.setIconImage(forgeIcon.getImage());
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 11, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		Component verticalStrut_4 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_4 = new GridBagConstraints();
		gbc_verticalStrut_4.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut_4.gridx = 1;
		gbc_verticalStrut_4.gridy = 0;
		frame.getContentPane().add(verticalStrut_4, gbc_verticalStrut_4);
		
		Component horizontalStrut_8 = Box.createHorizontalStrut(30);
		GridBagConstraints gbc_horizontalStrut_8 = new GridBagConstraints();
		gbc_horizontalStrut_8.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalStrut_8.gridx = 0;
		gbc_horizontalStrut_8.gridy = 1;
		frame.getContentPane().add(horizontalStrut_8, gbc_horizontalStrut_8);
		
		JLabel lblTopFileLocation = new JLabel("Top File Location");
		GridBagConstraints gbc_lblTopFileLocation = new GridBagConstraints();
		gbc_lblTopFileLocation.anchor = GridBagConstraints.WEST;
		gbc_lblTopFileLocation.insets = new Insets(0, 0, 5, 0);
		gbc_lblTopFileLocation.gridx = 1;
		gbc_lblTopFileLocation.gridy = 1;
		frame.getContentPane().add(lblTopFileLocation, gbc_lblTopFileLocation);
		
		Component horizontalStrut_7 = Box.createHorizontalStrut(30);
		GridBagConstraints gbc_horizontalStrut_7 = new GridBagConstraints();
		gbc_horizontalStrut_7.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalStrut_7.gridx = 0;
		gbc_horizontalStrut_7.gridy = 2;
		frame.getContentPane().add(horizontalStrut_7, gbc_horizontalStrut_7);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 2;
		frame.getContentPane().add(panel, gbc_panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		panel.add(topTextField);
		topTextField.setColumns(42);
		
		JButton btnNewButton = new JButton("Browse");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileBrowser = new JFileChooser(); 
				if (Files.exists(Paths.get(topTextField.getText()))) {
					fileBrowser = new JFileChooser(new File(topTextField.getText())); 
				}
				fileBrowser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				if (fileBrowser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					File file = fileBrowser.getCurrentDirectory();
					File currentDir = fileBrowser.getSelectedFile(); 
					String filePath = file.getAbsolutePath() + "\\" + currentDir.getName();
					topTextField.setText(filePath);
				}
			}
		});
		panel.add(btnNewButton);
		
		Component horizontalStrut = Box.createHorizontalStrut(30);
		panel.add(horizontalStrut);
		
		Component verticalStrut_3 = Box.createVerticalStrut(21);
		GridBagConstraints gbc_verticalStrut_3 = new GridBagConstraints();
		gbc_verticalStrut_3.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut_3.gridx = 1;
		gbc_verticalStrut_3.gridy = 3;
		frame.getContentPane().add(verticalStrut_3, gbc_verticalStrut_3);
		
		Component horizontalStrut_5 = Box.createHorizontalStrut(30);
		GridBagConstraints gbc_horizontalStrut_5 = new GridBagConstraints();
		gbc_horizontalStrut_5.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalStrut_5.gridx = 0;
		gbc_horizontalStrut_5.gridy = 4;
		frame.getContentPane().add(horizontalStrut_5, gbc_horizontalStrut_5);
		
		JLabel label = new JLabel("General Well File Location");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.WEST;
		gbc_label.insets = new Insets(0, 0, 5, 0);
		gbc_label.gridx = 1;
		gbc_label.gridy = 4;
		frame.getContentPane().add(label, gbc_label);
		
		Component horizontalStrut_4 = Box.createHorizontalStrut(30);
		GridBagConstraints gbc_horizontalStrut_4 = new GridBagConstraints();
		gbc_horizontalStrut_4.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalStrut_4.gridx = 0;
		gbc_horizontalStrut_4.gridy = 5;
		frame.getContentPane().add(horizontalStrut_4, gbc_horizontalStrut_4);
		
		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 5;
		frame.getContentPane().add(panel_1, gbc_panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		gwiTextField.setColumns(42);
		panel_1.add(gwiTextField);
		
		JButton button = new JButton("Browse");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileBrowser = new JFileChooser(); 
				if (Files.exists(Paths.get(gwiTextField.getText()))) {
					fileBrowser = new JFileChooser(new File(gwiTextField.getText())); 
				}
				fileBrowser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				if (fileBrowser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					File file = fileBrowser.getCurrentDirectory();
					File currentDir = fileBrowser.getSelectedFile(); 
					String filePath = file.getAbsolutePath() + "\\" + currentDir.getName();
					gwiTextField.setText(filePath);
				}
			}
		});
		panel_1.add(button);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(30);
		panel_1.add(horizontalStrut_1);
		
		Component verticalStrut_2 = Box.createVerticalStrut(21);
		GridBagConstraints gbc_verticalStrut_2 = new GridBagConstraints();
		gbc_verticalStrut_2.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut_2.gridx = 1;
		gbc_verticalStrut_2.gridy = 6;
		frame.getContentPane().add(verticalStrut_2, gbc_verticalStrut_2);
		
		Component horizontalStrut_12 = Box.createHorizontalStrut(30);
		GridBagConstraints gbc_horizontalStrut_12 = new GridBagConstraints();
		gbc_horizontalStrut_12.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalStrut_12.gridx = 0;
		gbc_horizontalStrut_12.gridy = 7;
		frame.getContentPane().add(horizontalStrut_12, gbc_horizontalStrut_12);
		
		JLabel label_1 = new JLabel("Las Data Directory Location");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.anchor = GridBagConstraints.WEST;
		gbc_label_1.insets = new Insets(0, 0, 5, 0);
		gbc_label_1.gridx = 1;
		gbc_label_1.gridy = 7;
		frame.getContentPane().add(label_1, gbc_label_1);
		
		Component horizontalStrut_11 = Box.createHorizontalStrut(30);
		GridBagConstraints gbc_horizontalStrut_11 = new GridBagConstraints();
		gbc_horizontalStrut_11.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalStrut_11.gridx = 0;
		gbc_horizontalStrut_11.gridy = 8;
		frame.getContentPane().add(horizontalStrut_11, gbc_horizontalStrut_11);
		
		JPanel panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.insets = new Insets(0, 0, 5, 0);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 1;
		gbc_panel_2.gridy = 8;
		frame.getContentPane().add(panel_2, gbc_panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		lasTextField.setColumns(42);
		panel_2.add(lasTextField);
		
		JButton button_1 = new JButton("Browse");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileBrowser = new JFileChooser(); 
				if (Files.isDirectory(Paths.get(lasTextField.getText()))) {
					fileBrowser = new JFileChooser(new File(lasTextField.getText())); 
				}

				fileBrowser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				if (fileBrowser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					File file = fileBrowser.getCurrentDirectory();
					File currentDir = fileBrowser.getSelectedFile(); 
					String filePath = file.getAbsolutePath() + "\\" + currentDir.getName();
					lasTextField.setText(filePath);
				}
			}
		});
		panel_2.add(button_1);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(30);
		panel_2.add(horizontalStrut_2);
		
		Component verticalStrut_1 = Box.createVerticalStrut(21);
		GridBagConstraints gbc_verticalStrut_1 = new GridBagConstraints();
		gbc_verticalStrut_1.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut_1.gridx = 1;
		gbc_verticalStrut_1.gridy = 9;
		frame.getContentPane().add(verticalStrut_1, gbc_verticalStrut_1);
		
		Component horizontalStrut_10 = Box.createHorizontalStrut(30);
		GridBagConstraints gbc_horizontalStrut_10 = new GridBagConstraints();
		gbc_horizontalStrut_10.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalStrut_10.gridx = 0;
		gbc_horizontalStrut_10.gridy = 10;
		frame.getContentPane().add(horizontalStrut_10, gbc_horizontalStrut_10);
		
		JLabel label_2 = new JLabel("Output Location");
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.anchor = GridBagConstraints.WEST;
		gbc_label_2.insets = new Insets(0, 0, 5, 0);
		gbc_label_2.gridx = 1;
		gbc_label_2.gridy = 10;
		frame.getContentPane().add(label_2, gbc_label_2);
		
		Component horizontalStrut_9 = Box.createHorizontalStrut(30);
		GridBagConstraints gbc_horizontalStrut_9 = new GridBagConstraints();
		gbc_horizontalStrut_9.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalStrut_9.gridx = 0;
		gbc_horizontalStrut_9.gridy = 11;
		frame.getContentPane().add(horizontalStrut_9, gbc_horizontalStrut_9);
		
		JPanel panel_3 = new JPanel();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.insets = new Insets(0, 0, 5, 0);
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 1;
		gbc_panel_3.gridy = 11;
		frame.getContentPane().add(panel_3, gbc_panel_3);
		panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		outputTextField.setColumns(42);
		panel_3.add(outputTextField);
		
		JButton button_2 = new JButton("Browse");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileBrowser = new JFileChooser(); 
				if (Files.isDirectory(Paths.get(outputTextField.getText()))) {
					fileBrowser = new JFileChooser(new File(outputTextField.getText())); 
				}
				fileBrowser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				if (fileBrowser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					File file = fileBrowser.getCurrentDirectory();
					File currentDir = fileBrowser.getSelectedFile(); 
					String filePath = file.getAbsolutePath() + "\\" + currentDir.getName();
					outputTextField.setText(filePath);
					if (!outputNameTextField.isEnabled() && !filePath.endsWith("csv")) {
						outputNameTextField.setText("");
					}
					if (filePath.endsWith("csv")) {
						outputNameTextField.setText(filePath.substring(filePath.lastIndexOf("\\") + 1, filePath.indexOf(".csv")));
					}
				}
			}
		});
		panel_3.add(button_2);
		
		Component horizontalStrut_3 = Box.createHorizontalStrut(30);
		panel_3.add(horizontalStrut_3);
		
		Component verticalStrut = Box.createVerticalStrut(21);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut.gridx = 1;
		gbc_verticalStrut.gridy = 12;
		frame.getContentPane().add(verticalStrut, gbc_verticalStrut);
		
		JLabel lblOutputFileName = new JLabel("Output File Name");
		GridBagConstraints gbc_lblOutputFileName = new GridBagConstraints();
		gbc_lblOutputFileName.fill = GridBagConstraints.VERTICAL;
		gbc_lblOutputFileName.anchor = GridBagConstraints.WEST;
		gbc_lblOutputFileName.insets = new Insets(0, 0, 5, 0);
		gbc_lblOutputFileName.gridx = 1;
		gbc_lblOutputFileName.gridy = 13;
		frame.getContentPane().add(lblOutputFileName, gbc_lblOutputFileName);
		
		JPanel panel_4 = new JPanel();
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.fill = GridBagConstraints.VERTICAL;
		gbc_panel_4.insets = new Insets(0, 0, 5, 0);
		gbc_panel_4.anchor = GridBagConstraints.EAST;
		gbc_panel_4.gridx = 1;
		gbc_panel_4.gridy = 14;
		frame.getContentPane().add(panel_4, gbc_panel_4);
		panel_4.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnNext = new JButton("Next");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (checkTextFields()) {
					return; 
				}
				user.setLasfilePath(lasTextField.getText());
				user.setOutputfilePath(outputTextField.getText());
				user.setTopfilePath(topTextField.getText());
				user.setWorkingfilePath(gwiTextField.getText());
				user.setOutputfileName(outputNameTextField.getText());
				UserInputGui inputGui = new UserInputGui(user);
				frame.dispose();
			}
		});
		
		panel_4.add(outputNameTextField);
		outputNameTextField.setColumns(15);
		
		Component horizontalStrut_6 = Box.createHorizontalStrut(1);
		panel_4.add(horizontalStrut_6);
		
		JCheckBox chckbxDontCreateOutput = new JCheckBox("Run with no output");
		chckbxDontCreateOutput.addItemListener(new ItemListener() { 
			public void itemStateChanged(ItemEvent e) { 
				if (chckbxDontCreateOutput.isSelected()) { 
					rememberOutputName = outputNameTextField.getText();
					outputNameTextField.setText("");
					outputNameTextField.setEnabled(false);
		
					rememberOutputField = outputTextField.getText();
					outputTextField.setText("");
					outputTextField.setEnabled(false);
				}
				else { 
					outputNameTextField.setText(rememberOutputName);
					outputNameTextField.setEnabled(true);
					outputTextField.setText(rememberOutputField);
					outputTextField.setEnabled(true);
				}
			}
		});
		panel_4.add(chckbxDontCreateOutput);
		
		JButton btnPrevious = new JButton("Previous");
		btnPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new ParameterGui(); 
			}
		});
		
		Component horizontalStrut_14 = Box.createHorizontalStrut(1);
		panel_4.add(horizontalStrut_14);
		panel_4.add(btnPrevious);
		panel_4.add(btnNext);
		
		JButton btnNewButton_1 = new JButton("Cancel");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose(); 
			}
		});
		panel_4.add(btnNewButton_1);
		
		Component horizontalStrut_13 = Box.createHorizontalStrut(30);
		panel_4.add(horizontalStrut_13);
		
		Component verticalStrut_5 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_5 = new GridBagConstraints();
		gbc_verticalStrut_5.gridx = 1;
		gbc_verticalStrut_5.gridy = 15;
		frame.getContentPane().add(verticalStrut_5, gbc_verticalStrut_5);
	}

}
