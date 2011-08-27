import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;


public class Main {
	private static JFrame frame;
	private static final int FPS = 60;
	private static long timeOfLastUpdate;
	private static boolean isRunning = true;
	
	public static void main(String args[]) {
		Tools.createTileset();
		Tools.createImages();
		Map.createMap(Tools.parseData(Tools.loadFile("testmap.txt")));
		
		new ToolStore(4*Tile.getSize(),3*Tile.getSize(),1);
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
		
		timeOfLastUpdate = System.currentTimeMillis() - 16;
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
		RockRaider.updateAll(ms);
		Painter.update(ms);
	}
	
	
	public static void createWindowAndPainter() {
		Painter p = new Painter();
		
		Mousehandler m = new Mousehandler();
		p.addMouseListener(m);
		p.addMouseMotionListener(m);
		
		frame = new JFrame("Testfenster");
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setLocationRelativeTo(null); //topleft corner of the window, -> at center of screen
		frame.setResizable(false);
		frame.add(p);
		frame.pack();
		frame.setVisible(true);
		frame.repaint();
		frame.addKeyListener(new KeyHandler());
	}
}
