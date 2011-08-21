import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;


public class Mousehandler implements MouseListener{
	
	private static ArrayList<GameObject> selection = new ArrayList<GameObject>(); //for method: allIn
	private int xPos,yPos;
	
	
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(arg0.getButton()==1){
			singleSelect(xPos, yPos);
		}

		if(arg0.getButton()==3){
			moveSelected(arg0.getX(),arg0.getY());
			}
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
		if(arg0.getButton()==1){
			selection = new ArrayList<GameObject>();
		}
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
		for (RockRaider f: RockRaider.getRockRaiderList()) {
			if (f.intersects(x, y)) {
				System.out.println("Figure: " + f.getID());
				selection.add(f);
				return;
			}
		}
		
		//add clicked Tile
		selection.add(Map.getMap().getMapFields()[x/Tile.getSize()][y/Tile.getSize()]);
	}	

	private void multiSelect(double x, double y) {
		
		if(x==xPos && y==yPos)return;
		
		for(RockRaider r: RockRaider.getRockRaiderList()) {
			if (r.intersects(xPos, yPos, x - xPos, y - yPos)) {
				selection.add(r);
			}
		}
	}
	
	public void moveSelected(double x, double y)
	{ 		
		for(GameObject r: selection)
		{
			if(r.isTile()==false){
				((RockRaider) r).target(x - 10, y - 10);
			}
			
		}
	}
	
}
