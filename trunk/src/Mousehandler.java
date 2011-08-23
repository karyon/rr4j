import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;


public class Mousehandler implements MouseListener, MouseMotionListener{
	
	/**
	 * Currently selected objects.
	 */
	private static ArrayList<GameObject> selection = new ArrayList<GameObject>();
	/**
	 * Coordinates of the last press of a mouse button. May be changed to
	 * the coordinates of the upper left corner of the selection-rectangle.
	 */
	private static int mousePressedX, mousePressedY;
	/**
	 * Current coordinates of the mouse. Only updated when a mouse button is pressed.
	 * the coordinates of the upper left corner of the selection-rectangle.
	 */
	private static int mouseCurrX, mouseCurrY;
	
	private static boolean drawSelectionRect = false;
	
	
	
	
	public void mouseClicked(MouseEvent e) {
		boolean objectCollision=false;
		
		if(e.getButton() == 1)
			singleSelect(mousePressedX, mousePressedY);
		else if (e.getButton() == 3) {
			if (selection.size() == 1 && selection.get(0).isRockRaider()){
				RockRaider r = (RockRaider)selection.get(0);
				Tile t = Map.getMap().getTileAt(e.getX(), e.getY());
				for (int i =0;i < Ore.getOreList().size();i++){
					if(Ore.getOreList().get(i).intersects(e.getX(),e.getY())){
						objectCollision = true;
						r.takeRes(1, Ore.getOreList().get(i));
						
					}
					}
				for (int i = 0; i < Crystal.getCrystalList().size(); i++){
					if(Crystal.getCrystalList().get(i).intersects(e.getX(),e.getY())){
						objectCollision = true;
						r.takeRes(0, Crystal.getCrystalList().get(i));
						
					}
				}	
				
				if(objectCollision) {
					System.out.println("TEST");
					
				}
				if (objectCollision == false){
				switch (t.getType()) {
				case Tile.TYPE_DIRT: r.goToAndDestroy(t); break;
				case Tile.TYPE_LOOSE_ROCK: r.goToAndDestroy(t); break;
				case Tile.TYPE_RUBBLE: r.goToAndDestroy(t); break;
				case Tile.TYPE_GROUND: r.goToJob(e.getX(), e.getY()); break;
				default: //do nothing
				}}
				else {}
			}
			//assuming the selection consists solely of RockRaiders
			else if (selection.size() > 1 && selection.get(0).isRockRaider())
				moveSelected(e.getX(),e.getY()); 
		}
	}

	public void mouseEntered(MouseEvent arg0) {
		
	}

	public void mouseExited(MouseEvent arg0) {
		
	}

	public void mousePressed(MouseEvent arg0) {
		if (arg0.getButton() == 1) {
			selection = new ArrayList<GameObject>();
		}
		mousePressedX = arg0.getX();
		mousePressedY = arg0.getY();
	}


	public void mouseReleased(MouseEvent arg0) {
		int x = arg0.getX();
		int y = arg0.getY();
		
		if (x != mousePressedX && y != mousePressedY) {
			// multiSelect needs the lower left corner of the selection
			//rectangle, so swap the variables if necessary.
			if (mousePressedX > x) {
				int d = x;
				x = mousePressedX;
				mousePressedX = d;
			}
			if (mousePressedY > y) {
				int d = y;
				y = mousePressedY;
				mousePressedY = d;
			}
			multiSelect(x, y);
		}
		drawSelectionRect = false;
	}

	/**
	 * Selects the GameObject at the specified coordinates.
	 * @param x
	 * @param y
	 */
	private void singleSelect(int x, int y) {
		
		if(Menu.buttonHit(x,y)){
			return;
		}
		
		else{
			for (RockRaider r: RockRaider.getRockRaiderList()) {
				if (r.intersects(x, y)) {
					selection.add(r);
					Menu.createButtons(selection.get(0));
					return;
				}
			}
			for(Building b: Building.getBuildingList())
				if(b.intersects(x,y)){
					selection.add(b);
					Menu.createButtons(selection.get(0));
					return;
				}
			
			selection.add(Map.getMap().getTileAt(x, y));
			
		}
	}	
	
	/**
	 * Selects all RockRaiders in the rectangle from x/y to xPos/yPos
	 * @param x
	 * @param y
	 */
	private void multiSelect(double x, double y) {
		for(RockRaider r: RockRaider.getRockRaiderList()) {
			if (r.intersects(mousePressedX, mousePressedY, x - mousePressedX, y - mousePressedY)) {
				selection.add(r);
			}
		}
		if(selection.size()==1){ 
			Menu.createButtons(selection.get(0));
		}
	}
	
	/**
	 * Moves all selected RockRaiders to the specified position. selection 
	 * must not contain any non-RockRaider-objects when calling this method.
	 * @param x
	 * @param y
	 */
	public void moveSelected(double x, double y) { 		
		for(GameObject r: selection) {
			((RockRaider) r).goToJob(x - 10, y - 10);
		}
	}

	
	public void mouseDragged(MouseEvent e) {
		drawSelectionRect = true;
		mouseCurrX = e.getX();
		mouseCurrY = e.getY();
	}

	
	public void mouseMoved(MouseEvent e) {
		
	}
	
	public static int[] getSelectionRect() {
		if (!drawSelectionRect)
			return null;
		int startX = (mousePressedX < mouseCurrX) ? mousePressedX : mouseCurrX;
		int startY = (mousePressedY < mouseCurrY) ? mousePressedY : mouseCurrY;
		int width = (mousePressedX >= mouseCurrX) ? mousePressedX - startX: mouseCurrX - startX;
		int height = (mousePressedY >= mouseCurrY) ? mousePressedY - startY: mouseCurrY - startY;
		return new int[] {startX, startY, width, height};
	}
	
}
