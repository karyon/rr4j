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
		ArrayList<RockRaider> list = RockRaider.getRockRaiderList();
		for (RockRaider f: list) {
			double fX = f.getX();
			double fY = f.getY();
			if(x>=fX && x<=fX+Tools.getTileSize()) {
				if(y>=fY && y<=fY+Tools.getTileSize()) {
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
				if(x>=tileX*Tools.getTileSize() && x<=tileX*Tools.getTileSize()+Tools.getTileSize()) {
					if(y>=tileY*Tools.getTileSize() && y<=tileY*Tools.getTileSize()+Tools.getTileSize()) {
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
		
		int xPos2 = xPos -Tools.getTileSize(); //not only top left corner, also the others
		int yPos2 = yPos -Tools.getTileSize(); // same as xPos2
		
		
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
	
	public void moveSelected(int x, int y)
	{ 		
		for(GameObject r: selection)
		{
			if(r.isTile()==false){
				((RockRaider)r).move(x-10,y-10);
				System.out.println("RockRaider(s) "+((RockRaider)r).getID()+ " moved");
			}
			
		}
	}
	
}
