import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class Figure
{	
	private double xPos;
	private double yPos;
	private char los; //los = line of sight  (N,E,S,W)
	
	Image img = new BufferedImage(20,20, BufferedImage.TYPE_INT_ARGB);
	final int ID;
	static int nextID = 0;
	
	private static ArrayList<Figure> allFigures = new ArrayList<Figure>();
	
	
	public Figure(double x,double y)
	{
		ID = nextID;
		Graphics g = img.getGraphics();
		g.setColor(Color.BLACK);
		g.drawRect((int)xPos, (int)yPos, 19, 19);
		g.drawString(Integer.toString(ID), (int)xPos, (int)yPos+15);
		nextID++;
		los  = 'S';
		xPos =  x;
		yPos =  y;
		allFigures.add(this);
	}
	
	public double getX()
	{
		return xPos;
	}
	public double getY()
	{
		return yPos;
	}
	public char getLoS()
	{
		return los;
	}
	
	public void paint(Graphics g) {
		g.drawImage(img, (int)xPos, (int)yPos, null);
	}
	
	public static void paintAll(Graphics g) {
		for (Figure f: allFigures)
			f.paint(g);
	}
}
