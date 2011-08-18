import java.awt.*;
import java.util.Random;

import javax.swing.*;


public class Main {
	
	static Random r = new Random();
	
	public static void main(String args[]) {
		Main main = new Main();
		main.showMap();
		for (int i = 0; i < 10; i++)
			new Figure(r.nextInt(300), r.nextInt(300));
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
