import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

public class TestSwingWorker {

    public void go(UserInput user) {
    	Controller controller = new Controller(user);
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Loading...");
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
        new Task_IntegerUpdate(jpb, label, controller, frame).execute();
    }

    static class Task_IntegerUpdate extends SwingWorker<Void, Integer> {

        JProgressBar jpb;
        int max;
        JLabel label;
        Controller controller;
        JFrame frame;
        
        public Task_IntegerUpdate(JProgressBar jpb, JLabel label, Controller controller, JFrame frame) {
            this.jpb = jpb;
            this.max = 100;
            this.label = label;
            this.controller = controller;
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
            	//frame.dispose();
                get();
                controller.writeToFile();
                JOptionPane.showMessageDialog(jpb.getParent(), "Success", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    } 
}
