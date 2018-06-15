import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import java.awt.Component;
import javax.swing.Box;

public class LoadingGui extends JFrame {

	private JPanel contentPane;
	private JProgressBar progressBar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoadingGui frame = new LoadingGui();
					for (int i = 0 ; i <= 100 ; i++) {
						frame.updateProgress(i);
					}
				} 
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public LoadingGui() {
		progressBar = new JProgressBar();
		initialize(); 
		setVisible(true);
	}
	
	public void updateProgress(int percent) { 
		SwingUtilities.invokeLater(new Runnable() {  
			public void run() {
				progressBar.setValue(percent);
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 347, 201);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 171, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		Component verticalStrut = Box.createVerticalStrut(50);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut.gridx = 1;
		gbc_verticalStrut.gridy = 0;
		contentPane.add(verticalStrut, gbc_verticalStrut);
		
		JLabel lblWorking = new JLabel("Program Progress");
		GridBagConstraints gbc_lblWorking = new GridBagConstraints();
		gbc_lblWorking.insets = new Insets(0, 0, 5, 5);
		gbc_lblWorking.gridx = 1;
		gbc_lblWorking.gridy = 1;
		contentPane.add(lblWorking, gbc_lblWorking);
		
		Component horizontalStrut = Box.createHorizontalStrut(50);
		GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
		gbc_horizontalStrut.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalStrut.gridx = 0;
		gbc_horizontalStrut.gridy = 2;
		contentPane.add(horizontalStrut, gbc_horizontalStrut);
		
		progressBar.setMinimum(0);
		progressBar.setMaximum(100);
		progressBar.setStringPainted(true);
		GridBagConstraints gbc_progressBar = new GridBagConstraints();
		gbc_progressBar.fill = GridBagConstraints.HORIZONTAL;
		gbc_progressBar.insets = new Insets(0, 0, 5, 5);
		gbc_progressBar.gridx = 1;
		gbc_progressBar.gridy = 2;
		contentPane.add(progressBar, gbc_progressBar);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(50);
		GridBagConstraints gbc_horizontalStrut_1 = new GridBagConstraints();
		gbc_horizontalStrut_1.insets = new Insets(0, 0, 5, 0);
		gbc_horizontalStrut_1.gridx = 2;
		gbc_horizontalStrut_1.gridy = 2;
		contentPane.add(horizontalStrut_1, gbc_horizontalStrut_1);
		
		Component verticalStrut_1 = Box.createVerticalStrut(50);
		GridBagConstraints gbc_verticalStrut_1 = new GridBagConstraints();
		gbc_verticalStrut_1.insets = new Insets(0, 0, 0, 5);
		gbc_verticalStrut_1.gridx = 1;
		gbc_verticalStrut_1.gridy = 3;
		contentPane.add(verticalStrut_1, gbc_verticalStrut_1);
	}

}
