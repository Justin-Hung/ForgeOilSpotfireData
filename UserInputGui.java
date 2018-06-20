import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.ImageIcon;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.Icon;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

import org.apache.log4j.chainsaw.Main;

import javax.swing.JProgressBar;

public class UserInputGui {

	private JFrame frame;
	private UserInput user;
	private JTextField seSecTextField;
	private JTextField seTwpTextField;
	private JTextField seRgeTextField;
	private JTextField seMerTextField;
	private JTextField upperBufferTextField;
	private JTextField lowerBufferTextField;
	private JTextField nwSecTextField;
	private JTextField nwTwpTextField;
	private JTextField nwRgeTextField;
	private JTextField nwMerTextField;
	private JTextField lowerFormationTextField;
	private JTextField upperFormationTextField;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserInputGui window = new UserInputGui();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public UserInputGui() {
		initializeTextFields();
		initialize();
		setTextFieldLimits();
		frame.setVisible(true);
	}
	
	public UserInputGui(UserInput u) {
		user = u;
		initializeTextFields();
		setTextFields(u);
		initialize(); 
		setTextFieldLimits();
		frame.setVisible(true);
	}
	
	private void setTextFieldLimits() { 
		seSecTextField.setDocument(new JTextFieldLimit(2));
		seTwpTextField.setDocument(new JTextFieldLimit(3));
		seRgeTextField.setDocument(new JTextFieldLimit(2));
		seMerTextField.setDocument(new JTextFieldLimit(1));
		
		nwSecTextField.setDocument(new JTextFieldLimit(2));
		nwTwpTextField.setDocument(new JTextFieldLimit(3));
		nwRgeTextField.setDocument(new JTextFieldLimit(2));
		nwMerTextField.setDocument(new JTextFieldLimit(1));
	}
	
	private void initializeTextFields() { 
		seSecTextField = new JTextField(); 
		seTwpTextField = new JTextField(); 
		seRgeTextField = new JTextField(); 
		seMerTextField = new JTextField(); 
		upperBufferTextField = new JTextField(); 
		lowerBufferTextField = new JTextField(); 
		nwSecTextField = new JTextField(); 
		nwTwpTextField = new JTextField(); 
		nwRgeTextField = new JTextField(); 
		nwMerTextField = new JTextField(); 
		lowerFormationTextField = new JTextField(); 
		upperFormationTextField = new JTextField(); 
	}
	
	private void setTextFields(UserInput u) { 
		if (u.getUpperBuffer() != 0.0001) {
			if (String.valueOf(u.getUpperBuffer()).endsWith(".0")) {
				upperBufferTextField.setText(String.valueOf(u.getUpperBuffer()).replace(".0", ""));
			}
			else {
				upperBufferTextField.setText(String.valueOf(u.getUpperBuffer()));
			}
		}
		if (u.getLowerBuffer() != 0.0001) {
			if (String.valueOf(u.getLowerBuffer()).endsWith(".0")) {
				lowerBufferTextField.setText(String.valueOf(u.getLowerBuffer()).replace(".0", ""));
			}
			else {
				lowerBufferTextField.setText(String.valueOf(u.getLowerBuffer()));
			}
		}
		if (!u.getFormations().get(0).equals("")) {
			upperFormationTextField.setText(u.getFormations().get(0));
		}
		if (!u.getFormations().get(1).equals("")) {
			lowerFormationTextField.setText(u.getFormations().get(1));
		}
	
		if (!u.getTownshipNW().split("-")[0].equals("")) { 
			nwSecTextField.setText(u.getTownshipNW().split("-")[0]);
		}
		if (!u.getTownshipNW().split("-")[1].equals("")) { 
			nwTwpTextField.setText(u.getTownshipNW().split("-")[1]);
		}
		if (u.getTownshipNW().split("-")[2].split("W").length == 1) { 
			nwRgeTextField.setText(u.getTownshipNW().split("-")[2].split("W")[0]);
		}
		if (u.getTownshipNW().split("-")[2].split("W").length == 2) { 
			if (!u.getTownshipNW().split("-")[2].split("W")[0].equals("")) {
				nwRgeTextField.setText(u.getTownshipNW().split("-")[2].split("W")[0]);
			}
			if (!u.getTownshipNW().split("-")[2].split("W")[1].equals("")) {
				nwMerTextField.setText(u.getTownshipNW().split("-")[2].split("W")[1]);
			}
		}
		
		if (!u.getTownshipSE().split("-")[0].equals("")) { 
			seSecTextField.setText(u.getTownshipSE().split("-")[0]);
		}
		if (!u.getTownshipSE().split("-")[1].equals("")) { 
			seTwpTextField.setText(u.getTownshipSE().split("-")[1]);
		}
		if (u.getTownshipSE().split("-")[2].split("W").length == 1) { 
			seRgeTextField.setText(u.getTownshipSE().split("-")[2].split("W")[0]);
		}
		if (u.getTownshipSE().split("-")[2].split("W").length == 2) { 
			if (!u.getTownshipSE().split("-")[2].split("W")[0].equals("")) {
				seRgeTextField.setText(u.getTownshipSE().split("-")[2].split("W")[0]);
			}
			if (!u.getTownshipSE().split("-")[2].split("W")[1].equals("")) {
				seMerTextField.setText(u.getTownshipSE().split("-")[2].split("W")[1]);
			}
		}	
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
		gridBagLayout.columnWidths = new int[] {0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut.gridx = 1;
		gbc_verticalStrut.gridy = 0;
		frame.getContentPane().add(verticalStrut, gbc_verticalStrut);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
		gbc_horizontalStrut.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalStrut.gridx = 0;
		gbc_horizontalStrut.gridy = 1;
		frame.getContentPane().add(horizontalStrut, gbc_horizontalStrut);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.anchor = GridBagConstraints.WEST;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.VERTICAL;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 1;
		frame.getContentPane().add(panel, gbc_panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblNewLabel = new JLabel("Upper Buffer");
		panel.add(lblNewLabel);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(68);
		panel.add(horizontalStrut_1);
		
		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 2;
		frame.getContentPane().add(panel_1, gbc_panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		upperBufferTextField.setHorizontalAlignment(SwingConstants.LEFT);
		panel_1.add(upperBufferTextField);
		upperBufferTextField.setColumns(5);
		
		JLabel lblMeters = new JLabel("Meters");
		panel_1.add(lblMeters);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(90);
		panel_1.add(horizontalStrut_2);
		
		URL url2 = Main.class.getResource("/resources/transparentFinal.png");
		ImageIcon forgeLogo = new ImageIcon(url2);
		JLabel label_10 = new JLabel(forgeLogo);
		GridBagConstraints gbc_label_10 = new GridBagConstraints();
		gbc_label_10.gridheight = 5;
		gbc_label_10.insets = new Insets(0, 0, 5, 5);
		gbc_label_10.gridx = 2;
		gbc_label_10.gridy = 1;
		frame.getContentPane().add(label_10, gbc_label_10);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_1 = new GridBagConstraints();
		gbc_verticalStrut_1.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut_1.gridx = 1;
		gbc_verticalStrut_1.gridy = 3;
		frame.getContentPane().add(verticalStrut_1, gbc_verticalStrut_1);
		
		JPanel panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.anchor = GridBagConstraints.WEST;
		gbc_panel_2.insets = new Insets(0, 0, 5, 5);
		gbc_panel_2.fill = GridBagConstraints.VERTICAL;
		gbc_panel_2.gridx = 1;
		gbc_panel_2.gridy = 4;
		frame.getContentPane().add(panel_2, gbc_panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel label_2 = new JLabel("Lower Buffer");
		panel_2.add(label_2);
		
		Component horizontalStrut_3 = Box.createHorizontalStrut(68);
		panel_2.add(horizontalStrut_3);
		
		JPanel panel_3 = new JPanel();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.insets = new Insets(0, 0, 5, 5);
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 1;
		gbc_panel_3.gridy = 5;
		frame.getContentPane().add(panel_3, gbc_panel_3);
		panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		lowerBufferTextField.setHorizontalAlignment(SwingConstants.LEFT);
		lowerBufferTextField.setColumns(5);
		panel_3.add(lowerBufferTextField);
		
		JLabel label_11 = new JLabel("Meters");
		panel_3.add(label_11);
		
		Component horizontalStrut_10 = Box.createHorizontalStrut(90);
		panel_3.add(horizontalStrut_10);
		
		Component verticalStrut_4 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_4 = new GridBagConstraints();
		gbc_verticalStrut_4.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut_4.gridx = 1;
		gbc_verticalStrut_4.gridy = 6;
		frame.getContentPane().add(verticalStrut_4, gbc_verticalStrut_4);
		
		JLabel label_15 = new JLabel("Township NW");
		GridBagConstraints gbc_label_15 = new GridBagConstraints();
		gbc_label_15.anchor = GridBagConstraints.WEST;
		gbc_label_15.insets = new Insets(0, 0, 5, 5);
		gbc_label_15.gridx = 1;
		gbc_label_15.gridy = 7;
		frame.getContentPane().add(label_15, gbc_label_15);
		
		JPanel panel_15 = new JPanel();
		GridBagConstraints gbc_panel_15 = new GridBagConstraints();
		gbc_panel_15.anchor = GridBagConstraints.WEST;
		gbc_panel_15.insets = new Insets(0, 0, 5, 5);
		gbc_panel_15.fill = GridBagConstraints.VERTICAL;
		gbc_panel_15.gridx = 2;
		gbc_panel_15.gridy = 7;
		frame.getContentPane().add(panel_15, gbc_panel_15);
		panel_15.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		Component horizontalStrut_12 = Box.createHorizontalStrut(75);
		panel_15.add(horizontalStrut_12);
		
		JLabel label = new JLabel("Upper Formation");
		panel_15.add(label);
		
		JPanel panel_8 = new JPanel();
		GridBagConstraints gbc_panel_8 = new GridBagConstraints();
		gbc_panel_8.anchor = GridBagConstraints.WEST;
		gbc_panel_8.insets = new Insets(0, 0, 5, 5);
		gbc_panel_8.fill = GridBagConstraints.VERTICAL;
		gbc_panel_8.gridx = 1;
		gbc_panel_8.gridy = 8;
		frame.getContentPane().add(panel_8, gbc_panel_8);
		panel_8.setLayout(new GridLayout(2, 1, 0, 0));
		
		JPanel panel_5 = new JPanel();
		GridBagConstraints gbc_panel_5 = new GridBagConstraints();
		gbc_panel_5.anchor = GridBagConstraints.WEST;
		gbc_panel_5.insets = new Insets(0, 0, 5, 5);
		gbc_panel_5.fill = GridBagConstraints.VERTICAL;
		gbc_panel_5.gridx = 1;
		gbc_panel_5.gridy = 9;
		frame.getContentPane().add(panel_5, gbc_panel_5);
		panel_5.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel panel_6 = new JPanel();
		panel_5.add(panel_6);
		panel_6.setLayout(new GridLayout(2, 1, 0, 0));
		
		JPanel panel_7 = new JPanel();
		panel_6.add(panel_7);
		panel_7.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 1));

		nwSecTextField.setHorizontalAlignment(SwingConstants.CENTER);
		nwSecTextField.setColumns(3);
		panel_7.add(nwSecTextField);
		
		JLabel label_3 = new JLabel("-");
		panel_7.add(label_3);
		
		nwTwpTextField.setHorizontalAlignment(SwingConstants.CENTER);
		nwTwpTextField.setColumns(3);
		panel_7.add(nwTwpTextField);
		
		JLabel label_4 = new JLabel("-");
		panel_7.add(label_4);
		
		nwRgeTextField.setHorizontalAlignment(SwingConstants.CENTER);
		nwRgeTextField.setColumns(3);
		panel_7.add(nwRgeTextField);
		
		JLabel label_5 = new JLabel("W");
		panel_7.add(label_5);
		
		nwMerTextField.setHorizontalAlignment(SwingConstants.CENTER);
		nwMerTextField.setColumns(3);
		panel_7.add(nwMerTextField);
		
		JPanel panel_9 = new JPanel();
		panel_6.add(panel_9);
		panel_9.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 1));
		
		JLabel label_6 = new JLabel("SEC");
		panel_9.add(label_6);
		
		Component horizontalStrut_4 = Box.createHorizontalStrut(16);
		panel_9.add(horizontalStrut_4);
		
		JLabel label_7 = new JLabel("TWP");
		panel_9.add(label_7);
		
		Component horizontalStrut_5 = Box.createHorizontalStrut(16);
		panel_9.add(horizontalStrut_5);
		
		JLabel label_8 = new JLabel("RGE");
		panel_9.add(label_8);
		
		Component horizontalStrut_6 = Box.createHorizontalStrut(25);
		panel_9.add(horizontalStrut_6);
		
		JLabel label_9 = new JLabel("MER");
		panel_9.add(label_9);
		
		JPanel panel_10 = new JPanel();
		GridBagConstraints gbc_panel_10 = new GridBagConstraints();
		gbc_panel_10.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_10.anchor = GridBagConstraints.NORTH;
		gbc_panel_10.insets = new Insets(0, 0, 5, 5);
		gbc_panel_10.gridx = 2;
		gbc_panel_10.gridy = 9;
		frame.getContentPane().add(panel_10, gbc_panel_10);
		panel_10.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		upperFormationTextField.setHorizontalAlignment(SwingConstants.LEFT);
		upperFormationTextField.setColumns(20);
		panel_10.add(upperFormationTextField);
		
		Component verticalStrut_3 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_3 = new GridBagConstraints();
		gbc_verticalStrut_3.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut_3.gridx = 1;
		gbc_verticalStrut_3.gridy = 10;
		frame.getContentPane().add(verticalStrut_3, gbc_verticalStrut_3);
		
		JLabel label_23 = new JLabel("Township SE");
		GridBagConstraints gbc_label_23 = new GridBagConstraints();
		gbc_label_23.anchor = GridBagConstraints.WEST;
		gbc_label_23.insets = new Insets(0, 0, 5, 5);
		gbc_label_23.gridx = 1;
		gbc_label_23.gridy = 11;
		frame.getContentPane().add(label_23, gbc_label_23);
		
		JPanel panel_16 = new JPanel();
		GridBagConstraints gbc_panel_16 = new GridBagConstraints();
		gbc_panel_16.anchor = GridBagConstraints.WEST;
		gbc_panel_16.insets = new Insets(0, 0, 5, 5);
		gbc_panel_16.fill = GridBagConstraints.VERTICAL;
		gbc_panel_16.gridx = 2;
		gbc_panel_16.gridy = 11;
		frame.getContentPane().add(panel_16, gbc_panel_16);
		panel_16.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		Component horizontalStrut_14 = Box.createHorizontalStrut(75);
		panel_16.add(horizontalStrut_14);
		
		JLabel lowerFormationLbl = new JLabel("Lower Formation");
		panel_16.add(lowerFormationLbl);
		
		JPanel panel_11 = new JPanel();
		GridBagConstraints gbc_panel_11 = new GridBagConstraints();
		gbc_panel_11.fill = GridBagConstraints.VERTICAL;
		gbc_panel_11.anchor = GridBagConstraints.WEST;
		gbc_panel_11.insets = new Insets(0, 0, 5, 5);
		gbc_panel_11.gridx = 1;
		gbc_panel_11.gridy = 12;
		frame.getContentPane().add(panel_11, gbc_panel_11);
		panel_11.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel panel_12 = new JPanel();
		panel_11.add(panel_12);
		panel_12.setLayout(new GridLayout(2, 1, 0, 0));
		
		JPanel panel_13 = new JPanel();
		panel_12.add(panel_13);
		panel_13.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 1));
		
		seSecTextField.setHorizontalAlignment(SwingConstants.CENTER);
		seSecTextField.setColumns(3);
		panel_13.add(seSecTextField);
		
		JLabel label_24 = new JLabel("-");
		panel_13.add(label_24);
		
		seTwpTextField.setHorizontalAlignment(SwingConstants.CENTER);
		seTwpTextField.setColumns(3);
		panel_13.add(seTwpTextField);
		
		JLabel label_25 = new JLabel("-");
		panel_13.add(label_25);
		
		seRgeTextField.setHorizontalAlignment(SwingConstants.CENTER);
		seRgeTextField.setColumns(3);
		panel_13.add(seRgeTextField);
		
		JLabel label_26 = new JLabel("W");
		panel_13.add(label_26);
		
		seMerTextField.setHorizontalAlignment(SwingConstants.CENTER);
		seMerTextField.setColumns(3);
		panel_13.add(seMerTextField);
		
		JPanel panel_14 = new JPanel();
		panel_12.add(panel_14);
		panel_14.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 1));
		
		JLabel label_27 = new JLabel("SEC");
		panel_14.add(label_27);
		
		Component horizontalStrut_7 = Box.createHorizontalStrut(16);
		panel_14.add(horizontalStrut_7);
		
		JLabel label_28 = new JLabel("TWP");
		panel_14.add(label_28);
		
		Component horizontalStrut_8 = Box.createHorizontalStrut(16);
		panel_14.add(horizontalStrut_8);
		
		JLabel label_29 = new JLabel("RGE");
		panel_14.add(label_29);
		
		Component horizontalStrut_9 = Box.createHorizontalStrut(25);
		panel_14.add(horizontalStrut_9);
		
		JLabel label_30 = new JLabel("MER");
		panel_14.add(label_30);
		
		JPanel panel_17 = new JPanel();
		GridBagConstraints gbc_panel_17 = new GridBagConstraints();
		gbc_panel_17.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_17.anchor = GridBagConstraints.NORTH;
		gbc_panel_17.insets = new Insets(0, 0, 5, 5);
		gbc_panel_17.gridx = 2;
		gbc_panel_17.gridy = 12;
		frame.getContentPane().add(panel_17, gbc_panel_17);
		panel_17.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		lowerFormationTextField.setHorizontalAlignment(SwingConstants.LEFT);
		lowerFormationTextField.setColumns(20);
		panel_17.add(lowerFormationTextField);
		
		Component verticalStrut_2 = Box.createVerticalStrut(12);
		GridBagConstraints gbc_verticalStrut_2 = new GridBagConstraints();
		gbc_verticalStrut_2.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut_2.gridx = 1;
		gbc_verticalStrut_2.gridy = 13;
		frame.getContentPane().add(verticalStrut_2, gbc_verticalStrut_2);
		
		JPanel panel_18 = new JPanel();
		GridBagConstraints gbc_panel_18 = new GridBagConstraints();
		gbc_panel_18.anchor = GridBagConstraints.EAST;
		gbc_panel_18.insets = new Insets(0, 0, 5, 5);
		gbc_panel_18.fill = GridBagConstraints.VERTICAL;
		gbc_panel_18.gridx = 2;
		gbc_panel_18.gridy = 14;
		frame.getContentPane().add(panel_18, gbc_panel_18);
		panel_18.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton button = new JButton("Previous");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!upperBufferTextField.getText().equals("")) {
					user.setUpperBuffer(Double.parseDouble(upperBufferTextField.getText())); 
				}
				if (!lowerBufferTextField.getText().equals("")) {
					user.setLowerBuffer(Double.parseDouble(lowerBufferTextField.getText()));
				}
				ArrayList<String> forms = new ArrayList<String>();
				forms.add("");
				forms.add("");
				if (!upperFormationTextField.getText().equals("")) {
					forms.set(0, upperFormationTextField.getText().toUpperCase());
				}
				if (!lowerFormationTextField.getText().equals("")) { 
					forms.set(1, lowerFormationTextField.getText().toUpperCase());
				}
				user.setFormations(forms);
				
				user.setTownshipNw(nwSecTextField.getText() + "-" + nwTwpTextField.getText() + "-" + nwRgeTextField.getText()
								   + "W" + nwMerTextField.getText());
				user.setTownshipSe(seSecTextField.getText() + "-" + seTwpTextField.getText() + "-" + seRgeTextField.getText()
								   + "W" + seMerTextField.getText()); 
				frame.dispose();
				
				new FileGui(user);
			}
		});
		panel_18.add(button);
		
		JButton button_1 = new JButton("Run");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				user.setUpperBuffer(Double.parseDouble(upperBufferTextField.getText())); 
				user.setLowerBuffer(Double.parseDouble(lowerBufferTextField.getText()));
				ArrayList<String> forms = new ArrayList<String>();
				forms.add(upperFormationTextField.getText().toUpperCase());
				forms.add(lowerFormationTextField.getText().toUpperCase());
				user.setFormations(forms);
				user.setTownshipNw(nwSecTextField.getText() + "-" + nwTwpTextField.getText() + "-" + nwRgeTextField.getText()
								   + "W" + nwMerTextField.getText());
				user.setTownshipSe(seSecTextField.getText() + "-" + seTwpTextField.getText() + "-" + seRgeTextField.getText()
								   + "W" + seMerTextField.getText());
				frame.dispose();
				RunLoadingScreen swingWorker = new RunLoadingScreen();
				swingWorker.go(user);
			}
		});
		panel_18.add(button_1);
		
		JButton button_2 = new JButton("Cancel");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		panel_18.add(button_2);
		
		Component horizontalStrut_16 = Box.createHorizontalStrut(30);
		panel_18.add(horizontalStrut_16);
		
		Component verticalStrut_5 = Box.createVerticalStrut(10);
		GridBagConstraints gbc_verticalStrut_5 = new GridBagConstraints();
		gbc_verticalStrut_5.insets = new Insets(0, 0, 0, 5);
		gbc_verticalStrut_5.gridx = 2;
		gbc_verticalStrut_5.gridy = 15;
		frame.getContentPane().add(verticalStrut_5, gbc_verticalStrut_5);
	}
}
