import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class Ore extends GameObject{
	
	private final static int size = 10;
	
	private static Image img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
	
	private static ArrayList<Ore> oreList = new ArrayList<Ore>();
	
	static {
		Graphics g = img.getGraphics();
		g.setColor(new Color(30, 16, 5));
		g.fillRect(0, 0, size-1, size-1);
	}
	
	
	public Ore(double x, double y) {
		super(x, y, size, size);
		oreList.add(this);
		// TODO Auto-generated constructor stub
	}
	
	

	
	public void paint(Graphics g) {
		g.drawImage(img, (int) x, (int) y, null);
	}
	
	public static ArrayList<Ore> getOreList() {
		return oreList;
	}

	public static void paintAll(Graphics g) {
		for (Ore o: oreList)
			o.paint(g);
	}
	
	public static int getSize() {
		return size;
	}

}
