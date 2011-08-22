import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;


public class Mousehandler implements MouseListener{
	
	/**
	 * Currently selected objects.
	 */
	private static ArrayList<GameObject> selection = new ArrayList<GameObject>();
	/**
	 * Coordinates of the last press of a mouse button. May be changed to
	 * the coordinates of the upper left corner of the selection-rectangle.
	 */
	private int xPos,yPos;
	
	
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		
		if(arg0.getButton() == 1)
			singleSelect(xPos, yPos);
		else if (arg0.getButton() == 3 && selection.size() == 1 && selection.get(0).isRockRaider()){
			Tile t = Map.getMap().getTileAt(arg0.getX(), arg0.getY());
			if (t.getType() == Tile.TYPE_STONE)
				((RockRaider)selection.get(0)).goToAndDestroy(t);
			else if (t.getType() == Tile.TYPE_RUBBLE)
				((RockRaider)selection.get(0)).goToAndDestroy(t);
			else
				moveSelected(arg0.getX(),arg0.getY());
		}
		else
			moveSelected(arg0.getX(),arg0.getY());
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
		if (arg0.getButton() == 1) {
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

	/**
	 * Selects the GameObject at the specified coordinates.
	 * @param x
	 * @param y
	 */
	private void singleSelect(int x, int y)
	{
		for (RockRaider f: RockRaider.getRockRaiderList()) {
			if (f.intersects(x, y)) {
				selection.add(f);
				return;
			}
		}
		
		//add clicked Tile
		selection.add(Map.getMap().getTileAt(x, y));
	}	
	
	/**
	 * Selects all RockRaiders in the rectangle from x/y to xPos/yPos
	 * @param x
	 * @param y
	 */
	private void multiSelect(double x, double y) {
		
		if (x == xPos && y == yPos)
			return;

		for(RockRaider r: RockRaider.getRockRaiderList()) {
			if (r.intersects(xPos, yPos, x - xPos, y - yPos)) {
				selection.add(r);
			}
		}
	}
	
	/**
	 * Moves all selected RockRaiders to the specified position.
	 * @param x
	 * @param y
	 */
	public void moveSelected(double x, double y)
	{ 		
		for(GameObject r: selection) {
			if(r.isRockRaider()) {
				((RockRaider) r).goToJob(x - 10, y - 10);
			}
		}
	}
	
}
