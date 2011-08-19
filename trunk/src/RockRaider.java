import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class RockRaider extends GameObject
{
	private char los; //los = line of sight  (N,E,S,W)
	private double tarX,tarY;
	
	private Image img = new BufferedImage(Tools.getTileSize(),Tools.getTileSize(), BufferedImage.TYPE_INT_ARGB);
	private final int ID;
	private static int nextID = 0;
	
	private static ArrayList<RockRaider> allRockRaiders = new ArrayList<RockRaider>();
	
	
	public RockRaider(double x,double y)
	{
		super(x, y);
		tarX=x;
		tarY=y;
		ID = nextID;
		Graphics g = img.getGraphics();
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, 63, 63);
		g.drawString(Integer.toString(ID), 0, 15);
		nextID++;
		los  = 'S';
		allRockRaiders.add(this);
	}
	
	
	public char getLoS()
	{
		return los;
	}
	
	public static ArrayList<RockRaider> getRockRaiderList()
	{
		return allRockRaiders;
	}
	
	public void paint(Graphics g) {
		g.drawImage(img, (int)x, (int)y, null);
	}
	
	public static void paintAll(Graphics g) {
		for (RockRaider f: allRockRaiders)
			f.paint(g);
	}
	
	public int getID(){
		return ID;
	}
	
	public void target(double newX,double newY) {	
		tarX=newX;
		tarY=newY;
		
	}
	
	public static void updateAll(int ms) {
		for (RockRaider f: allRockRaiders)
			f.update(ms);
		
	}
	
	public void update(int ms) {
		if (tarX == x && tarY == y)
			return;
		double distX,distY;
		double maxMovement=0.1*ms;
		
		distX = tarX-x;		
		distY = tarY-y;
		if (ID == 0)
			System.out.println(x + " " + distX + " " + tarX);
		
		double distance = Math.sqrt(distX*distX + distY*distY);
		
		double v = distance / maxMovement;
		
		//Math.abs(distX/v)

			
			
				x= x + distX/v;
				y= y + distY/v;
				if(Math.abs(distX/v) >= Math.abs(distX)) { x= tarX; }
				if(Math.abs(distY/v) >= Math.abs(distY)) { y= tarY; }
			
			
//		}
		
	}
	
}
