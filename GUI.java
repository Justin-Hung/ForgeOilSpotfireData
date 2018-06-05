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
import java.awt.Component;
import javax.swing.Box;

public class GUI extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JLabel lblGwiFileLocation;
	private JLabel lblLasDataFile;
	private JLabel lblOutputLocation;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JButton btnBrowse;
	private JButton button;
	private JButton button_1;
	private JButton button_2;
	private Component verticalStrut;
	private JLabel lblUpperBuffer;
	private JLabel lblLowerBuffer;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JLabel lblUpperFormation;
	private JLabel lblLowerFormation;
	private JLabel lblTownshipNw;
	private JLabel lblTownshipSe;
	private JButton btnRun;
	private JLabel lblCreatedByJustin;
	private JTextField textField_8;
	private JTextField textField_9;
	private Component verticalStrut_1;
	private JLabel lblForgeOil;

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
		setBounds(100, 100, 700, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{30, 30, 30, 30, 30, 30, 30, 30, 30};
		gbl_contentPane.rowHeights = new int[]{30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut.gridx = 2;
		gbc_verticalStrut.gridy = 0;
		contentPane.add(verticalStrut, gbc_verticalStrut);
		
		JLabel lblTopFileLocation = new JLabel("Top File Location");
		GridBagConstraints gbc_lblTopFileLocation = new GridBagConstraints();
		gbc_lblTopFileLocation.gridwidth = 2;
		gbc_lblTopFileLocation.insets = new Insets(0, 0, 5, 5);
		gbc_lblTopFileLocation.gridx = 1;
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
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 2;
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 2;
		contentPane.add(textField, gbc_textField);
		textField.setColumns(10);
		
		btnBrowse = new JButton("Browse");
		GridBagConstraints gbc_btnBrowse = new GridBagConstraints();
		gbc_btnBrowse.insets = new Insets(0, 0, 5, 5);
		gbc_btnBrowse.gridx = 3;
		gbc_btnBrowse.gridy = 2;
		contentPane.add(btnBrowse, gbc_btnBrowse);
		
		textField_4 = new JTextField();
		GridBagConstraints gbc_textField_4 = new GridBagConstraints();
		gbc_textField_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_4.insets = new Insets(0, 0, 5, 5);
		gbc_textField_4.gridx = 5;
		gbc_textField_4.gridy = 2;
		contentPane.add(textField_4, gbc_textField_4);
		textField_4.setColumns(10);
		
		textField_8 = new JTextField();
		GridBagConstraints gbc_textField_8 = new GridBagConstraints();
		gbc_textField_8.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_8.insets = new Insets(0, 0, 5, 0);
		gbc_textField_8.gridx = 7;
		gbc_textField_8.gridy = 2;
		contentPane.add(textField_8, gbc_textField_8);
		textField_8.setColumns(10);
		
		lblGwiFileLocation = new JLabel("GWI File Location");
		GridBagConstraints gbc_lblGwiFileLocation = new GridBagConstraints();
		gbc_lblGwiFileLocation.gridwidth = 2;
		gbc_lblGwiFileLocation.insets = new Insets(0, 0, 5, 5);
		gbc_lblGwiFileLocation.gridx = 1;
		gbc_lblGwiFileLocation.gridy = 4;
		contentPane.add(lblGwiFileLocation, gbc_lblGwiFileLocation);
		
		lblLowerBuffer = new JLabel("Lower Buffer");
		GridBagConstraints gbc_lblLowerBuffer = new GridBagConstraints();
		gbc_lblLowerBuffer.insets = new Insets(0, 0, 5, 5);
		gbc_lblLowerBuffer.gridx = 5;
		gbc_lblLowerBuffer.gridy = 4;
		contentPane.add(lblLowerBuffer, gbc_lblLowerBuffer);
		
		lblTownshipSe = new JLabel("Township SE");
		GridBagConstraints gbc_lblTownshipSe = new GridBagConstraints();
		gbc_lblTownshipSe.insets = new Insets(0, 0, 5, 0);
		gbc_lblTownshipSe.gridx = 7;
		gbc_lblTownshipSe.gridy = 4;
		contentPane.add(lblTownshipSe, gbc_lblTownshipSe);
		
		textField_1 = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.gridwidth = 2;
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.gridx = 1;
		gbc_textField_1.gridy = 5;
		contentPane.add(textField_1, gbc_textField_1);
		textField_1.setColumns(10);
		
		button = new JButton("Browse");
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.insets = new Insets(0, 0, 5, 5);
		gbc_button.gridx = 3;
		gbc_button.gridy = 5;
		contentPane.add(button, gbc_button);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		GridBagConstraints gbc_textField_5 = new GridBagConstraints();
		gbc_textField_5.insets = new Insets(0, 0, 5, 5);
		gbc_textField_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_5.gridx = 5;
		gbc_textField_5.gridy = 5;
		contentPane.add(textField_5, gbc_textField_5);
		
		textField_9 = new JTextField();
		textField_9.setColumns(10);
		GridBagConstraints gbc_textField_9 = new GridBagConstraints();
		gbc_textField_9.insets = new Insets(0, 0, 5, 0);
		gbc_textField_9.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_9.gridx = 7;
		gbc_textField_9.gridy = 5;
		contentPane.add(textField_9, gbc_textField_9);
		
		lblLasDataFile = new JLabel("Las Data File Location");
		GridBagConstraints gbc_lblLasDataFile = new GridBagConstraints();
		gbc_lblLasDataFile.gridwidth = 2;
		gbc_lblLasDataFile.insets = new Insets(0, 0, 5, 5);
		gbc_lblLasDataFile.gridx = 1;
		gbc_lblLasDataFile.gridy = 7;
		contentPane.add(lblLasDataFile, gbc_lblLasDataFile);
		
		lblUpperFormation = new JLabel("Upper Formation");
		GridBagConstraints gbc_lblUpperFormation = new GridBagConstraints();
		gbc_lblUpperFormation.insets = new Insets(0, 0, 5, 5);
		gbc_lblUpperFormation.gridx = 5;
		gbc_lblUpperFormation.gridy = 7;
		contentPane.add(lblUpperFormation, gbc_lblUpperFormation);
		
		textField_2 = new JTextField();
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.gridwidth = 2;
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.insets = new Insets(0, 0, 5, 5);
		gbc_textField_2.gridx = 1;
		gbc_textField_2.gridy = 8;
		contentPane.add(textField_2, gbc_textField_2);
		textField_2.setColumns(10);
		
		button_1 = new JButton("Browse");
		GridBagConstraints gbc_button_1 = new GridBagConstraints();
		gbc_button_1.insets = new Insets(0, 0, 5, 5);
		gbc_button_1.gridx = 3;
		gbc_button_1.gridy = 8;
		contentPane.add(button_1, gbc_button_1);
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		GridBagConstraints gbc_textField_6 = new GridBagConstraints();
		gbc_textField_6.insets = new Insets(0, 0, 5, 5);
		gbc_textField_6.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_6.gridx = 5;
		gbc_textField_6.gridy = 8;
		contentPane.add(textField_6, gbc_textField_6);
		
		btnRun = new JButton("RUN");
		GridBagConstraints gbc_btnRun = new GridBagConstraints();
		gbc_btnRun.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRun.insets = new Insets(0, 0, 5, 0);
		gbc_btnRun.gridx = 7;
		gbc_btnRun.gridy = 8;
		contentPane.add(btnRun, gbc_btnRun);
		
		lblOutputLocation = new JLabel("Output Location");
		GridBagConstraints gbc_lblOutputLocation = new GridBagConstraints();
		gbc_lblOutputLocation.gridwidth = 2;
		gbc_lblOutputLocation.insets = new Insets(0, 0, 5, 5);
		gbc_lblOutputLocation.gridx = 1;
		gbc_lblOutputLocation.gridy = 10;
		contentPane.add(lblOutputLocation, gbc_lblOutputLocation);
		
		lblLowerFormation = new JLabel("Lower Formation");
		GridBagConstraints gbc_lblLowerFormation = new GridBagConstraints();
		gbc_lblLowerFormation.insets = new Insets(0, 0, 5, 5);
		gbc_lblLowerFormation.gridx = 5;
		gbc_lblLowerFormation.gridy = 10;
		contentPane.add(lblLowerFormation, gbc_lblLowerFormation);
		
		lblForgeOil = new JLabel("Forge Oil");
		GridBagConstraints gbc_lblForgeOil = new GridBagConstraints();
		gbc_lblForgeOil.insets = new Insets(0, 0, 5, 0);
		gbc_lblForgeOil.gridx = 7;
		gbc_lblForgeOil.gridy = 10;
		contentPane.add(lblForgeOil, gbc_lblForgeOil);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.gridwidth = 2;
		gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_3.insets = new Insets(0, 0, 5, 5);
		gbc_textField_3.gridx = 1;
		gbc_textField_3.gridy = 11;
		contentPane.add(textField_3, gbc_textField_3);
		
		button_2 = new JButton("Browse");
		GridBagConstraints gbc_button_2 = new GridBagConstraints();
		gbc_button_2.insets = new Insets(0, 0, 5, 5);
		gbc_button_2.gridx = 3;
		gbc_button_2.gridy = 11;
		contentPane.add(button_2, gbc_button_2);
		
		textField_7 = new JTextField();
		textField_7.setColumns(10);
		GridBagConstraints gbc_textField_7 = new GridBagConstraints();
		gbc_textField_7.insets = new Insets(0, 0, 5, 5);
		gbc_textField_7.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_7.gridx = 5;
		gbc_textField_7.gridy = 11;
		contentPane.add(textField_7, gbc_textField_7);
		
		lblCreatedByJustin = new JLabel("Created by: Justin Hung");
		GridBagConstraints gbc_lblCreatedByJustin = new GridBagConstraints();
		gbc_lblCreatedByJustin.insets = new Insets(0, 0, 5, 0);
		gbc_lblCreatedByJustin.gridx = 7;
		gbc_lblCreatedByJustin.gridy = 11;
		contentPane.add(lblCreatedByJustin, gbc_lblCreatedByJustin);
		
		verticalStrut_1 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_1 = new GridBagConstraints();
		gbc_verticalStrut_1.insets = new Insets(0, 0, 0, 5);
		gbc_verticalStrut_1.gridx = 0;
		gbc_verticalStrut_1.gridy = 12;
		contentPane.add(verticalStrut_1, gbc_verticalStrut_1);
	}

}
