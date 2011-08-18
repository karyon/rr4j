import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;


public class Mousehandler implements MouseListener{

	private static ArrayList<Figure> list;
	
	
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		int x = arg0.getX();
		int y = arg0.getY();
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void compare(int x, int y)
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
	
}
