import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class RockRaider extends GameObject
{
	private char los; //los = line of sight  (N,E,S,W)
	
	private Image img = new BufferedImage(Tools.getTileSize(),Tools.getTileSize(), BufferedImage.TYPE_INT_ARGB);
	private final int ID;
	private static int nextID = 0;
	
	private static ArrayList<RockRaider> allRockRaiders = new ArrayList<RockRaider>();
	
	
	public RockRaider(double x,double y)
	{
		super(x, y);
		ID = nextID;
		Graphics g = img.getGraphics();
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, 19, 19);
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
	
	public void move(double newX,double newY){
		x=newX;
		y=newY;
		
	}
	
	
}
