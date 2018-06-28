import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import org.apache.log4j.chainsaw.Main;

public class RunLoadingScreen {

    public void go(UserInput user) {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Reading Files...");
        JProgressBar jpb = new JProgressBar();
        jpb.setIndeterminate(false);
        int max = 100;
        jpb.setMaximum(max);
        jpb.setStringPainted(true);
        panel.add(label);
        panel.add(jpb);
        frame.add(panel);
        frame.pack();
        frame.setSize(250,100);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	URL url = Main.class.getResource("/resources/forgeIcon.png");
		ImageIcon forgeIcon = new ImageIcon(url);
		frame.setIconImage(forgeIcon.getImage());
		
        new Task_IntegerUpdate(jpb, label, user, frame).execute();
    }

    static class Task_IntegerUpdate extends SwingWorker<Void, Integer> {
    	
    	UserInput user;
        JProgressBar jpb;
        int max;
        JLabel label;
        Controller controller;
        JFrame frame;
        
        public Task_IntegerUpdate(JProgressBar jpb, JLabel label, UserInput user, JFrame frame) {
            this.jpb = jpb;
            this.max = 100;
            this.label = label;
            this.user = user;
            this.frame = frame;
        }

        @Override
        protected void process(List<Integer> chunks) {
            int i = chunks.get(chunks.size()-1);
            jpb.setValue(i); // The last value in this array is all we care about.
            label.setText("Working Well: " + controller.getCurrentUwi());
        }

        @Override
        protected Void doInBackground() throws Exception {
        	controller = new Controller(user);
            while (controller.getWorkingWellRow() < controller.getSize()) {
            	controller.formatWellData();
            	int percent = (int) Math.round( (((double) controller.getWorkingWellRow()) / ((double) controller.getSize())) * 100.0 );
                publish(percent);
            }
            return null;
        }

        @Override
        protected void done() {
            try {
                get();
                controller.writeToFile();
                JOptionPane.showMessageDialog(jpb.getParent(), "Success", "Success", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
                new OutputGui(controller);
            } 
            catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    } 
}