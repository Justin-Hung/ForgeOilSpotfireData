import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import java.awt.Component;
import javax.swing.Box;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridLayout;
import javax.swing.JButton;

public class UserInputGui {

	private JFrame frame;
	private JTextField textField_8;
	private JTextField textField_9;
	private JTextField textField_10;
	private JTextField textField_11;
	private JTextField textField_12;
	private JTextField textField_13;
	private JTextField textField_14;
	private JTextField textField_15;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserInputGui window = new UserInputGui();
					window.frame.setVisible(true);
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
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 645, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.insets = new Insets(0, 0, 5, 0);
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
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.VERTICAL;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 1;
		frame.getContentPane().add(panel, gbc_panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		Component horizontalStrut_11 = Box.createHorizontalStrut(70);
		panel.add(horizontalStrut_11);
		
		JLabel lblNewLabel = new JLabel("Upper Buffer");
		panel.add(lblNewLabel);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(180);
		panel.add(horizontalStrut_1);
		
		JLabel label = new JLabel("Upper Formation");
		panel.add(label);
		
		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.anchor = GridBagConstraints.WEST;
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.fill = GridBagConstraints.VERTICAL;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 2;
		frame.getContentPane().add(panel_1, gbc_panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		Component horizontalStrut_12 = Box.createHorizontalStrut(70);
		panel_1.add(horizontalStrut_12);
		
		textField = new JTextField();
		panel_1.add(textField);
		textField.setColumns(10);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(130);
		panel_1.add(horizontalStrut_2);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		panel_1.add(textField_2);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_1 = new GridBagConstraints();
		gbc_verticalStrut_1.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut_1.gridx = 1;
		gbc_verticalStrut_1.gridy = 3;
		frame.getContentPane().add(verticalStrut_1, gbc_verticalStrut_1);
		
		JPanel panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.anchor = GridBagConstraints.WEST;
		gbc_panel_2.insets = new Insets(0, 0, 5, 0);
		gbc_panel_2.fill = GridBagConstraints.VERTICAL;
		gbc_panel_2.gridx = 1;
		gbc_panel_2.gridy = 4;
		frame.getContentPane().add(panel_2, gbc_panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		Component horizontalStrut_15 = Box.createHorizontalStrut(70);
		panel_2.add(horizontalStrut_15);
		
		JLabel label_2 = new JLabel("Lower Buffer");
		panel_2.add(label_2);
		
		Component horizontalStrut_3 = Box.createHorizontalStrut(180);
		panel_2.add(horizontalStrut_3);
		
		JLabel label_1 = new JLabel("Lower Formation");
		panel_2.add(label_1);
		
		JPanel panel_3 = new JPanel();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.anchor = GridBagConstraints.WEST;
		gbc_panel_3.insets = new Insets(0, 0, 5, 0);
		gbc_panel_3.fill = GridBagConstraints.VERTICAL;
		gbc_panel_3.gridx = 1;
		gbc_panel_3.gridy = 5;
		frame.getContentPane().add(panel_3, gbc_panel_3);
		panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		Component horizontalStrut_16 = Box.createHorizontalStrut(70);
		panel_3.add(horizontalStrut_16);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		panel_3.add(textField_1);
		
		Component horizontalStrut_10 = Box.createHorizontalStrut(130);
		panel_3.add(horizontalStrut_10);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		panel_3.add(textField_3);
		
		Component verticalStrut_4 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_4 = new GridBagConstraints();
		gbc_verticalStrut_4.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut_4.gridx = 1;
		gbc_verticalStrut_4.gridy = 6;
		frame.getContentPane().add(verticalStrut_4, gbc_verticalStrut_4);
		
		JLabel label_15 = new JLabel("Township NW");
		GridBagConstraints gbc_label_15 = new GridBagConstraints();
		gbc_label_15.anchor = GridBagConstraints.WEST;
		gbc_label_15.insets = new Insets(0, 0, 5, 0);
		gbc_label_15.gridx = 1;
		gbc_label_15.gridy = 7;
		frame.getContentPane().add(label_15, gbc_label_15);
		
		JPanel panel_8 = new JPanel();
		GridBagConstraints gbc_panel_8 = new GridBagConstraints();
		gbc_panel_8.anchor = GridBagConstraints.WEST;
		gbc_panel_8.insets = new Insets(0, 0, 5, 0);
		gbc_panel_8.fill = GridBagConstraints.VERTICAL;
		gbc_panel_8.gridx = 1;
		gbc_panel_8.gridy = 8;
		frame.getContentPane().add(panel_8, gbc_panel_8);
		panel_8.setLayout(new GridLayout(2, 1, 0, 0));
		
		JPanel panel_9 = new JPanel();
		panel_8.add(panel_9);
		panel_9.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 1));
		
		textField_8 = new JTextField();
		textField_8.setColumns(3);
		panel_9.add(textField_8);
		
		JLabel label_16 = new JLabel("-");
		panel_9.add(label_16);
		
		textField_9 = new JTextField();
		textField_9.setColumns(3);
		panel_9.add(textField_9);
		
		JLabel label_17 = new JLabel("-");
		panel_9.add(label_17);
		
		textField_10 = new JTextField();
		textField_10.setColumns(3);
		panel_9.add(textField_10);
		
		JLabel label_18 = new JLabel("W");
		panel_9.add(label_18);
		
		textField_11 = new JTextField();
		textField_11.setColumns(3);
		panel_9.add(textField_11);
		
		JPanel panel_10 = new JPanel();
		panel_8.add(panel_10);
		panel_10.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 1));
		
		JLabel label_19 = new JLabel("SEC");
		panel_10.add(label_19);
		
		Component horizontalStrut_4 = Box.createHorizontalStrut(16);
		panel_10.add(horizontalStrut_4);
		
		JLabel label_20 = new JLabel("TWP");
		panel_10.add(label_20);
		
		Component horizontalStrut_5 = Box.createHorizontalStrut(16);
		panel_10.add(horizontalStrut_5);
		
		JLabel label_21 = new JLabel("RGE");
		panel_10.add(label_21);
		
		Component horizontalStrut_6 = Box.createHorizontalStrut(25);
		panel_10.add(horizontalStrut_6);
		
		JLabel label_22 = new JLabel("MER");
		panel_10.add(label_22);
		
		Component verticalStrut_3 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_3 = new GridBagConstraints();
		gbc_verticalStrut_3.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut_3.gridx = 1;
		gbc_verticalStrut_3.gridy = 9;
		frame.getContentPane().add(verticalStrut_3, gbc_verticalStrut_3);
		
		JLabel label_23 = new JLabel("Township SE");
		GridBagConstraints gbc_label_23 = new GridBagConstraints();
		gbc_label_23.anchor = GridBagConstraints.WEST;
		gbc_label_23.insets = new Insets(0, 0, 5, 0);
		gbc_label_23.gridx = 1;
		gbc_label_23.gridy = 10;
		frame.getContentPane().add(label_23, gbc_label_23);
		
		JPanel panel_11 = new JPanel();
		GridBagConstraints gbc_panel_11 = new GridBagConstraints();
		gbc_panel_11.fill = GridBagConstraints.VERTICAL;
		gbc_panel_11.anchor = GridBagConstraints.WEST;
		gbc_panel_11.insets = new Insets(0, 0, 5, 0);
		gbc_panel_11.gridx = 1;
		gbc_panel_11.gridy = 11;
		frame.getContentPane().add(panel_11, gbc_panel_11);
		panel_11.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel panel_12 = new JPanel();
		panel_11.add(panel_12);
		panel_12.setLayout(new GridLayout(2, 1, 0, 0));
		
		JPanel panel_13 = new JPanel();
		panel_12.add(panel_13);
		panel_13.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 1));
		
		textField_12 = new JTextField();
		textField_12.setColumns(3);
		panel_13.add(textField_12);
		
		JLabel label_24 = new JLabel("-");
		panel_13.add(label_24);
		
		textField_13 = new JTextField();
		textField_13.setColumns(3);
		panel_13.add(textField_13);
		
		JLabel label_25 = new JLabel("-");
		panel_13.add(label_25);
		
		textField_14 = new JTextField();
		textField_14.setColumns(3);
		panel_13.add(textField_14);
		
		JLabel label_26 = new JLabel("W");
		panel_13.add(label_26);
		
		textField_15 = new JTextField();
		textField_15.setColumns(3);
		panel_13.add(textField_15);
		
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
		
		Component horizontalStrut_13 = Box.createHorizontalStrut(100);
		panel_11.add(horizontalStrut_13);
		
		JPanel panel_15 = new JPanel();
		panel_11.add(panel_15);
		panel_15.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton button = new JButton("Previous");
		panel_15.add(button);
		
		JButton button_1 = new JButton("Run");
		panel_15.add(button_1);
		
		JButton button_2 = new JButton("Cancel");
		panel_15.add(button_2);
		
		Component horizontalStrut_14 = Box.createHorizontalStrut(30);
		panel_15.add(horizontalStrut_14);
		
		Component verticalStrut_2 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_2 = new GridBagConstraints();
		gbc_verticalStrut_2.gridx = 1;
		gbc_verticalStrut_2.gridy = 12;
		frame.getContentPane().add(verticalStrut_2, gbc_verticalStrut_2);
	}
}
