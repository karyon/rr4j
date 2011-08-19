import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;


public class Mousehandler implements MouseListener{

	private static ArrayList<Figure> list;
	private static ArrayList<Figure> multiChoose=new ArrayList<Figure>(); //for method: allIn
	private int xPos,yPos;
	
	
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		double x = arg0.getX();
		double y = arg0.getY();
		compare(x,y);
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
		xPos = arg0.getX();
		yPos = arg0.getY();
		
		
	}


	@Override
	public void mouseReleased(MouseEvent arg0) {
		double x = arg0.getX();
		double y = arg0.getY();
		multiSelect(x,y);
		
	}

	public void compare(double x, double y)
	{
		double fX;
		double fY;
		
		System.out.println(x+" "+y);
		list = Figure.getFigureList();
		
		for(int i=0;i<list.size();i++)
		{
			fX = list.get(i).getX();
			fY = list.get(i).getY();
			if(x>=fX && x<=fX+20)
			{
				if(y>=fY && y<=fY+20)
				{
					System.out.println("Figure: " + list.get(i).getID());
					break;
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
