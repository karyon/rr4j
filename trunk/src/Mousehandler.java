import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;


public class Mousehandler implements MouseListener{

	private static ArrayList<Figure> list;
	private static ArrayList<Figure> multiChoose=new ArrayList<Figure>(); //for method: allIn
	private int xPos,yPos;
	
	
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		singleSelect(xPos, yPos);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		multiChoose=new ArrayList<Figure>();
		xPos = arg0.getX();
		yPos = arg0.getY();
		
	}


	@Override
	public void mouseReleased(MouseEvent arg0) {
		int x = arg0.getX();
		int y = arg0.getY();
		if(xPos>x)
		{ 
			int d=x; x=xPos; xPos=d; 
		}
		if(yPos>y)
		{ 
			int d=y; y=yPos; yPos=d; 
		}
		multiSelect(x,y);
		
	}

	
	private void singleSelect(int x, int y)
	{
		list = Figure.getFigureList();
		for (Figure f: list) {
			double fX = f.getX();
			double fY = f.getY();
			if(x>=fX && x<=fX+20) {
				if(y>=fY && y<=fY+20) {
					System.out.println("Figure: " + f.getID());
					multiChoose.add(f);
					return;
				}
			}
		}
		
		
		Tile[][] mapFields = Map.getMap().getMapFields();
		for (Tile[] temp: mapFields) {
			for(Tile t: temp) {
				double tileX = t.getX();
				double tileY = t.getY();
				if(x>=tileX*20 && x<=tileX*20+20) {
					if(y>=tileY*20 && y<=tileY*20+20) {
						System.out.println("Tile: " + tileX + " " + tileY);
						return;
					}
				}
			}
		}
		
		
	}	

	private void multiSelect(double x, double y) {
		
		list = Figure.getFigureList();
		
		System.out.print("Figures: ");
		
		for(Figure f: list)
		{
			if(xPos < f.getX() && x > f.getX())
			{
				if(yPos < f.getY() && y > f.getY())
				{
					multiChoose.add(f);
					System.out.print(f.getID() + " ");
					
				}
			}
		}
		System.out.println();
	}
	
	
	
}
