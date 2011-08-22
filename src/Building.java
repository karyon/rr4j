import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class Building extends GameObject{
	
	private static ArrayList<Building> buildingList = new ArrayList<Building>();
	private static final int size = Tile.getSize();
	private Image img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
	private final int ID;
	private static int nextID=0;

	final int toolstore = 0;
	final int powerstation = 1;
	private static int type;
	
	public Building(double x, double y,int type) {
		super(x, y, size, size);
		ID = nextID;
		this.type=type;
		Graphics g = img.getGraphics();
		g.setColor(new Color(240,170,170));
		g.fillRect(0, 0, size-1, size-1);
		g.setColor(Color.BLACK);
		g.drawString(Integer.toString(ID),5, 15);
		buildingList.add(this);
		nextID++;
		
		
		
		
	}
	public static ArrayList<Building> getBuildingList(){
		return buildingList;
	}
	
	public static int getSize(){
		return size;
	}
	
	public static void paintAll(Graphics g) {
		for (Building f : buildingList)
			f.paint(g);
	}
	
	public void paint(Graphics g) {
		g.drawImage(img, (int) x, (int) y, null);
	}

	
}
