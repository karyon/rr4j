import java.awt.*;
import javax.swing.*;


public class Main {
	
	
	public static void main(String args[]) {
		Main main = new Main();
		main.showMap();
	}
	
	
	public void showMap() {
		JFrame frame = new JFrame("Testfenster");
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 280);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.add(new Painter());
	}
	
}
