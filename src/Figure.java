
public class Figure
{	
	private double xPos;
	private double yPos;
	private char los; //los = line of sight  (N,E,S,W)
	
	
	public Figure(double x,double y)
	{
		los  = 'S';
		xPos =  x;
		yPos =  y;
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
	
	
}
