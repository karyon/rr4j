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
	 * Coordinates of the last press of a mouse button.
	 */
	private static int mousePressedX, mousePressedY;
	/**
	 * Current coordinates of the mouse. Only updated when a mouse button is pressed and dragged.
	 */
	private static int mouseCurrX, mouseCurrY;
	
	private static int buttonPressed = MouseEvent.NOBUTTON;
	
	private static boolean drawSelectionRect = false;
	
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}
	
	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}
	
	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}
	
	@Override
	public void mousePressed(MouseEvent arg0) {
		//if some button is already pressed, ignore this additional press.
		if (buttonPressed != MouseEvent.NOBUTTON)
			return;
		buttonPressed = arg0.getButton();
		
		mousePressedX = arg0.getX();
		mousePressedY = arg0.getY();
	}
	
	@Override
	public void mouseReleased(MouseEvent arg0) {
		//if a button is released which is not in buttonPressed, ignore this release.
		if (arg0.getButton() != buttonPressed)
			return;
		buttonPressed = MouseEvent.NOBUTTON;
		
		if (arg0.getButton() == MouseEvent.BUTTON1) {
			if (Menu.buttonHit(arg0.getX(),arg0.getY())){
				return;
			}
			selection = new ArrayList<GameObject>();
		}
		
		int[] selectionRect = getSelectionRect();
		drawSelectionRect = false;
		
		//handle a small drag like a click.
		if (selectionRect == null || selectionRect[2] + selectionRect[3] < 4) {
			mouseClick(arg0.getX() + Painter.getOffsetX(), arg0.getY() + Painter.getOffsetY(), arg0.getButton());
			return;
		}
		
		if (arg0.getButton() == MouseEvent.BUTTON1) {
			multiSelect(selectionRect[0] + Painter.getOffsetX(), selectionRect[1] + Painter.getOffsetY(), selectionRect[2], selectionRect[3]);
		}
	}
	
	
	private void mouseClick(int x, int y, int button) {
		if(button == MouseEvent.BUTTON1)
			singleSelect(x, y);
		else if (button == MouseEvent.BUTTON3) {
			if (selection.size() == 1 && selection.get(0).isRockRaider()){
				boolean objectCollision = false;
				RockRaider r = (RockRaider)selection.get(0);
				for (Ore o: Ore.getOreList()) {
					if(o.intersects(x, y)){
						objectCollision = true;
						r.takeRes(1, o);
					}
				}
				for (Crystal c: Crystal.getCrystalList()) {
					if(c.intersects(x, y)) {
						objectCollision = true;
						r.takeRes(0, c);
					}
				}
				if (objectCollision == false){
					Tile t = Map.getMap().getTileAt(x, y);
					switch (t.getType()) {
					case Tile.TYPE_DIRT: r.goToAndDestroy(t); break;
					case Tile.TYPE_LOOSE_ROCK: r.goToAndDestroy(t); break;
					case Tile.TYPE_RUBBLE: r.goToAndDestroy(t); break;
					case Tile.TYPE_GROUND: moveSelected(x, y); break;
					default: //do nothing
					}
				}
			}
			//assuming the selection consists solely of RockRaiders
			else if (selection.size() > 1 && selection.get(0).isRockRaider())
				moveSelected(x, y); 
		}
	}

	/**
	 * Selects the GameObject at the specified coordinates.
	 * @param x
	 * @param y
	 */
	private void singleSelect(int x, int y) {
		
		for (RockRaider r: RockRaider.getRockRaiderList()) {
			if (r.intersects(x, y)) {
				selection.add(r);
				Menu.createButtons(r);
				return;
			}
		}
		for(Building b: Building.getBuildingList()) {
			if(b.intersects(x,y)){
				selection.add(b);
				Menu.createButtons(b);
				return;
			}
		}
		selection.add(Map.getMap().getTileAt(x, y));
		
		Menu.delSelected();
	}	
	
	/**
	 * Selects all RockRaiders in the specified rectangle
	 * @param x
	 * @param y
	 */
	private void multiSelect(int x, int y, int width, int height) {
		for(RockRaider r: RockRaider.getRockRaiderList()) {
			if (r.intersects(x, y, width, height)) {
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
	public void moveSelected(int x, int y) { 		
		for(GameObject r: selection) {
			((RockRaider) r).goToJob(x - 10, y - 10);
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if (buttonPressed != MouseEvent.BUTTON1)
			return;
		drawSelectionRect = true;
		mouseCurrX = e.getX();
		mouseCurrY = e.getY();
	}
	
	@Override
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
