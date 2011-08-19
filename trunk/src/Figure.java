import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class Figure extends GameObject
{
	private char los; //los = line of sight  (N,E,S,W)
	
	Image img = new BufferedImage(20,20, BufferedImage.TYPE_INT_ARGB);
	private final int ID;
	static int nextID = 0;
	
	private static ArrayList<Figure> allFigures = new ArrayList<Figure>();
	
	
	public Figure(double x,double y)
	{
		super(x, y);
		ID = nextID;
		Graphics g = img.getGraphics();
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, 19, 19);
		g.drawString(Integer.toString(ID), 0, 15);
		nextID++;
		los  = 'S';
		allFigures.add(this);
	}
	
	
	public char getLoS()
	{
		return los;
	}
	
	public static ArrayList<Figure> getFigureList()
	{
		return allFigures;
	}
	
	public void paint(Graphics g) {
		g.drawImage(img, (int)x, (int)y, null);
	}
	
	public static void paintAll(Graphics g) {
		for (Figure f: allFigures)
			f.paint(g);
	}
	
	public int getID(){
		return ID;
	}
}
