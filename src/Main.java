import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;


public class Main {
	private static Painter painter;
	private static final int FPS = 60;
	private static boolean isRunning = true;
	
	public static void main(String args[]) {
		Tools.createTileset();
		Tools.createImages();
		Map.createMap(Tools.loadMap());
		
		new ToolStore(4*Tile.getSize(),3*Tile.getSize(),1);
		new ToolStore(6*Tile.getSize(),4*Tile.getSize(),1);
		new Menu();
		
		Random r = new Random();
		ArrayList<RockRaider> list = RockRaider.getRockRaiderList();
		for (int i = 0; i < 10; i++) {
			new RockRaider(r.nextInt(Map.getMap().getWidthPx() - 20), r.nextInt(Map.getMap().getHeightPx() - 20));
			//remove the created RockRaider if it... intersects an unpassable object
			if (list.get(list.size()-1).intersectsUnpassableObject()!=null) {
				list.remove(list.size()-1);
				i--;
			}
		
		}
		
		createWindowAndPainter();
		
		long timeOfLastUpdate = System.currentTimeMillis() - 16;
		while(isRunning) {
			long frameBegin = System.currentTimeMillis();
			
			doGamePlay((int)(frameBegin - timeOfLastUpdate));
			painter.repaint();
			
			long timeTaken = System.currentTimeMillis() - frameBegin;
			long sleepTime  = Math.max(0, 1000/FPS - timeTaken);
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			timeOfLastUpdate = frameBegin;
		}
	}
	
	private static void doGamePlay(int ms) {
		//do all updates here
		RockRaider.updateAll(ms);
		painter.update(ms);
	}
	
	
	private static void createWindowAndPainter() {
		painter = new Painter();
		
		MouseEventListener m = new MouseEventListener();
		painter.addMouseListener(m);
		painter.addMouseMotionListener(m);
		
		JFrame frame = new JFrame("Testfenster");
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setLocationRelativeTo(null); //topleft corner of the window, -> at center of screen
		frame.setResizable(false);
		frame.add(painter);
		frame.pack();
		frame.setVisible(true);
		frame.repaint();
		frame.addKeyListener(new KeyHandler());
	}
}
