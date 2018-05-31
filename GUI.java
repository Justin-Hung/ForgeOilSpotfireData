import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GUI extends JFrame {
	
	
	public GUI() {
		setSize(500, 500);
		setMinimumSize(new Dimension(500, 500));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel mainLabel = new JLabel("SPOTFIRE DATA GENERATOR");
		mainLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		panel.add(mainLabel);
		
		setVisible(true);
	}
	
	public static void main (String [] args) {
		GUI gui = new GUI();
	}

}
