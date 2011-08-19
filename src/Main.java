import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.TimerTask;

import javax.swing.*;


public class Main {
	private static JFrame frame;
	private static Timer timer;
	private static final int FPS = 60;
	private static long timeOfLastUpdate;
	private static boolean isRunning = true;
	
	public static void main(String args[]) {
		Tools.createTileset();
		new Map(Tools.parseData(Tools.getWorld("testmap.txt")));
		
		Random r = new Random();
		for (int i = 0; i < 10; i++)
			new RockRaider(r.nextInt(300), r.nextInt(300));
		
		createWindowAndPainter();
		
		
		while(isRunning) {
			doGamePlay((int)(System.currentTimeMillis() - timeOfLastUpdate));
			frame.repaint();
			long updateTime  = System.currentTimeMillis() - timeOfLastUpdate; //wie lange das update gebraucht hat
			updateTime = Math.max(0, updateTime);
			try {
				Thread.sleep(1000/FPS - updateTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void doGamePlay(int ms) {
		timeOfLastUpdate = System.currentTimeMillis();
		//do all updates here
	}
	
	
	public static void createWindowAndPainter() {
		Painter p = new Painter();
		p.addMouseListener(new Mousehandler());
		
		frame = new JFrame("Testfenster");
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 280);
		frame.setLocationRelativeTo(null);
		frame.add(p);
		frame.setVisible(true);
		frame.repaint();
	}
}
