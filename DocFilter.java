import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

class MyIntFilter extends DocumentFilter {
	private int textLimit; 
	
	public MyIntFilter(int textLimit) {
		this.textLimit = textLimit; 
	}
	
      	private boolean isValid(String testText) {
          if (testText.length() > textLimit) {
             return false;
          }
          if (testText.isEmpty()) {
             return true;
          }
          if (testText.equals("-")) { 
      		  return true;
      	  }
          int intValue = 0;
          try {
             intValue = Integer.parseInt(testText.trim());
          } catch (NumberFormatException e) {
             return false;
          }
          return true; 
       }

       @Override
       public void insertString(FilterBypass fb, int offset, String text,
             AttributeSet attr) throws BadLocationException {
          StringBuilder sb = new StringBuilder();
          sb.append(fb.getDocument().getText(0, fb.getDocument().getLength()));
          sb.insert(offset, text);
          if (isValid(sb.toString())) {
             super.insertString(fb, offset, text, attr);
          }
       }

       @Override
       public void replace(FilterBypass fb, int offset, int length,
             String text, AttributeSet attrs) throws BadLocationException {
          StringBuilder sb = new StringBuilder();
          sb.append(fb.getDocument().getText(0, fb.getDocument().getLength()));
          int end = offset + length;
          sb.replace(offset, end, text);
          if (isValid(sb.toString())) {
             super.replace(fb, offset, length, text, attrs);
          }
       }

       @Override
       public void remove(FilterBypass fb, int offset, int length)
             throws BadLocationException {
          StringBuilder sb = new StringBuilder();
          sb.append(fb.getDocument().getText(0, fb.getDocument().getLength()));
          int end = offset + length;
          sb.delete(offset, end);
          if (isValid(sb.toString())) {
             super.remove(fb, offset, length);
          }
       }
}