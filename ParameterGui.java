import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.GridBagLayout;
import javax.swing.JTextField;

import org.apache.log4j.chainsaw.Main;

import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import java.awt.Insets;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Component;
import javax.swing.Box;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class ParameterGui {

	private JFrame frame;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ParameterGui window = new ParameterGui();
				} 
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ParameterGui() {
		readPreviousPath(); 
		initialize();
		frame.setVisible(true);
	}
	
	private void readPreviousPath() { 
		URL url = Main.class.getResource("/resources/previousParameterPath.txt"); 
		File parameterFile = new File(url.getFile());
		UserInput user = new UserInput();
		if (parameterFile.exists()) {
			try { 
				BufferedReader br = new BufferedReader(new FileReader(parameterFile));
				textField = new JTextField(br.readLine());
				br.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 645, 201);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut.gridx = 1;
		gbc_verticalStrut.gridy = 0;
		panel.add(verticalStrut, gbc_verticalStrut);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(30);
		GridBagConstraints gbc_horizontalStrut_1 = new GridBagConstraints();
		gbc_horizontalStrut_1.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalStrut_1.gridx = 0;
		gbc_horizontalStrut_1.gridy = 1;
		panel.add(horizontalStrut_1, gbc_horizontalStrut_1);
		
		JPanel panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.anchor = GridBagConstraints.WEST;
		gbc_panel_2.insets = new Insets(0, 0, 5, 0);
		gbc_panel_2.gridx = 1;
		gbc_panel_2.gridy = 1;
		panel.add(panel_2, gbc_panel_2);
		
		JLabel lblParameterTextFile = new JLabel("Parameter Text File Location");
		panel_2.add(lblParameterTextFile);
		
		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.anchor = GridBagConstraints.NORTH;
		gbc_panel_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 2;
		panel.add(panel_1, gbc_panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		textField.setColumns(42);
		panel_1.add(textField);
		
		JButton button = new JButton("Browse");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileBrowser = new JFileChooser(); 
				fileBrowser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				if (fileBrowser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					File file = fileBrowser.getCurrentDirectory();
					File currentDir = fileBrowser.getSelectedFile(); 
					String filePath = file.getAbsolutePath() + "\\" + currentDir.getName();
					textField.setText(filePath);
				}
			}
		});
		panel_1.add(button);
		
		Component horizontalStrut = Box.createHorizontalStrut(30);
		panel_1.add(horizontalStrut);
		
		Component verticalStrut_2 = Box.createVerticalStrut(10);
		GridBagConstraints gbc_verticalStrut_2 = new GridBagConstraints();
		gbc_verticalStrut_2.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut_2.gridx = 1;
		gbc_verticalStrut_2.gridy = 3;
		panel.add(verticalStrut_2, gbc_verticalStrut_2);
		
		JPanel panel_3 = new JPanel();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.insets = new Insets(0, 0, 5, 0);
		gbc_panel_3.fill = GridBagConstraints.VERTICAL;
		gbc_panel_3.gridx = 1;
		gbc_panel_3.gridy = 4;
		panel.add(panel_3, gbc_panel_3);
		
		JButton btnNewButton = new JButton("Use New Parameters");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new FileGui();
				frame.dispose();
			}
		});
		panel_3.add(btnNewButton);
		
		Component horizontalStrut_3 = Box.createHorizontalStrut(20);
		panel_3.add(horizontalStrut_3);
		
		JButton btnNewButton_1 = new JButton("Use Last Parameters");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				URL url = Main.class.getResource("/resources/previousParameters.txt"); 
				File parameterFile = new File(url.getFile());
				UserInput user = new UserInput();
				if (parameterFile.exists()) {
					try { 
						BufferedReader br = new BufferedReader(new FileReader(parameterFile));
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
					catch (IOException ex) {
						ex.printStackTrace();
					}
				}
				frame.dispose();
				new FileGui(user);
			}
		
		});
		panel_3.add(btnNewButton_1);
		
		Component horizontalStrut_4 = Box.createHorizontalStrut(20);
		panel_3.add(horizontalStrut_4);
		
		JButton btnNewButton_2 = new JButton("Use Saved Parameters");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!textField.getText().equals("")) {
					File parameterFile = new File(textField.getText());
					UserInput user = new UserInput();
					if (parameterFile.exists()) {
						try { 
							BufferedReader br = new BufferedReader(new FileReader(parameterFile));
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
							frame.dispose();
							new FileGui(user);
							URL url = Main.class.getResource("/resources/previousParameterPath.txt"); 
							File previousSaveFile = new File(url.getPath()); 
							FileWriter saveParameterPath = new FileWriter(previousSaveFile);
							saveParameterPath.write(textField.getText());
							saveParameterPath.close();
						}
						catch (IOException ex) {
							ex.printStackTrace();
						}
					}
				}
			}
		});
		panel_3.add(btnNewButton_2);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(30);
		panel_3.add(horizontalStrut_2);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_1 = new GridBagConstraints();
		gbc_verticalStrut_1.gridx = 1;
		gbc_verticalStrut_1.gridy = 5;
		panel.add(verticalStrut_1, gbc_verticalStrut_1);
	}

}
