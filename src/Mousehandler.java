import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;


public class Mousehandler implements MouseListener{
	
	private static ArrayList<GameObject> selection = new ArrayList<GameObject>(); //for method: allIn
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
		selection = new ArrayList<GameObject>();
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
		ArrayList<RockRaider> list = RockRaider.getRockRaiderList();
		for (RockRaider f: list) {
			double fX = f.getX();
			double fY = f.getY();
			if(x>=fX && x<=fX+20) {
				if(y>=fY && y<=fY+20) {
					System.out.println("Figure: " + f.getID());
					selection.add(f);
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
						selection.add(t);
						System.out.println("Tile: " + tileX + " " + tileY);
						return;
					}
				}
			}
		}
		
		
	}	

	private void multiSelect(double x, double y) {
		
		if(x==xPos && y==yPos)return;
		
		int xPos2 = xPos -20; //not only top left corner, also the others
		int yPos2 = yPos -20; // same as xPos2
		
		
		ArrayList<RockRaider> list = RockRaider.getRockRaiderList();
		
		System.out.print("Figures: ");
		
		for(RockRaider f: list)
		{
			if(xPos2 < f.getX() && x > f.getX())
			{
				if(yPos2 < f.getY() && y > f.getY())
				{
					selection.add(f);
					System.out.print(f.getID() + " ");
					
				}
			}
		}
		System.out.println();
	}
	
	
	
}
