import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class Crystal extends GameObject{

	private final static int size = 10;
	
	private static Image img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
	
	private static ArrayList<Crystal> crystalList = new ArrayList<Crystal>();
	
	static {
		Graphics g = img.getGraphics();
		g.setColor(new Color(30, 230, 7));
		g.fillRect(0, 0, size-1, size-1);
	}
	
	
	public Crystal(double x, double y) {
		super(x, y, size, size);
		crystalList.add(this);
		// TODO Auto-generated constructor stub
	}
	
	

	
	public void paint(Graphics g) {
		g.drawImage(img, (int) x, (int) y, null);
	}
	
	public static ArrayList<Crystal> getOreList() {
		return crystalList;
	}

	public static void paintAll(Graphics g) {
		for (Crystal c: crystalList)
			c.paint(g);
	}
	
	public static int getSize() {
		return size;
	}
}
