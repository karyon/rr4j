import java.awt.event.MouseEvent;
import java.util.ArrayList;


public class MouseHandler {

	/**
	 * Currently selected objects.
	 */
	private static ArrayList<GameObject> selection = new ArrayList<GameObject>();
	
	
	public static void mouseClick(int x, int y, int button) {
		
		if(button == MouseEvent.BUTTON1)
			singleSelect(x, y);
		else if (button == MouseEvent.BUTTON3) {
			if (selection.size() == 1 && selection.get(0).isRockRaider()){
				RockRaider r = (RockRaider)selection.get(0);
				for (Ore o: Ore.getOreList()) {
					if(o.intersects(x, y)){
						r.takeRes(o); 
						return;
					}
				}
				for (Crystal c: Crystal.getCrystalList()) {
					if(c.intersects(x, y)) {
						r.takeRes(c);
						return;
					}
				}
				Tile t = Map.getMap().getTileAt(x, y);
				switch (t.getType()) {
				case Tile.TYPE_DIRT: r.goToAndDestroy(t); break;
				case Tile.TYPE_LOOSE_ROCK: r.goToAndDestroy(t); break;
				case Tile.TYPE_RUBBLE: r.goToAndDestroy(t); break;
				case Tile.TYPE_GROUND: moveSelected(x, y); break;
				default: //do nothing
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
	public static void singleSelect(int x, int y) {
		
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
		

		if(Menu.getWait4klick() && selection.get(0).isTile()){
			Menu.getLastPressed().doit();
			selection = new ArrayList<GameObject>();
			selection.add(Menu.getLastPressed().getObject());
			return;
		}
		
		Menu.delSelected();
	}	
	
	/**
	 * Selects all RockRaiders in the specified rectangle
	 * @param x
	 * @param y
	 */
	public static void multiSelect(int x, int y, int width, int height) {
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
	private static void moveSelected(int x, int y) { 		
		for(GameObject r: selection) {
			((RockRaider) r).goToJob(x - 10, y - 10, true);
		}
	}
	


	
	public static ArrayList<GameObject> getSelection(){
		return selection;
	}


	public static void clearSelection() {
		System.out.println("Ich klearä die Sälehktion");
		selection = new ArrayList<GameObject>();
	}
}
