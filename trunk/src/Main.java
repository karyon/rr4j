import java.awt.*;
import java.util.Random;

import javax.swing.*;


public class Main {
	
	static Random r = new Random();
	
	public static void main(String args[]) {
		Main main = new Main();
		Tools.createTileset();
		new Map(Tools.parseData(Tools.getWorld("testmap.txt")));
		for (int i = 0; i < 10; i++)
			new RockRaider(r.nextInt(300), r.nextInt(300));
		main.showMap();
		
	}
	
	
	public void showMap() {
		Painter p = new Painter();
		p.addMouseListener(new Mousehandler());
		
		JFrame frame = new JFrame("Testfenster");
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 280);
		frame.setLocationRelativeTo(null);
		frame.add(p);
		frame.setVisible(true);
		frame.repaint();
	}
	
}
