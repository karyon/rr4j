import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

public class Button {
	
	private int type; //button for (RockRaider, Building, etc.)
	private int button; //buttonnumber
	private boolean active; //true, if available for this object;
	private Image img;
	private GameObject o;
	
	public Button(int type, int button, boolean active,GameObject o){
		
		img = Toolkit.getDefaultToolkit().createImage("res/t"+type+"Button"+(button)+active+".png");
		this.type = type;
		this.button = button;
		this.o=o;
		this.setActive(active);
	}
	
	public Image getImage(){
			return img;
	}
	
	public GameObject getObject(){
		return o;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	/**
	 * execute the prepared function
	 */
	public void doit(){
		ArrayList<GameObject> s= MouseHandler.getSelection();
		Menu.setWait4klick(false);
		
		System.out.println(o.isRockRaider());
		
		if(o.isRockRaider())
			if(button==0){
				System.out.println(o.isRockRaider());
				RockRaider r = (RockRaider)o;
				System.out.println(o.isRockRaider());
				if(s.get(0).isTile())
					r.goToAndDestroy(Map.getMap().getTileAt(s.get(0).getX(),s.get(0).getY()));				
				return;
			}
	}

	/**
	 * execute the buttons function or
	 * prepares the execution
	 */
	public void callFunction() {
		if(o.isTile()) {
			((Tile )o).createToolStore(o.x,o.y);
		}
			
		
		
		if(o.isRockRaider()){
			if(button==0){
				Menu.setLastPressed(this);
				Menu.setWait4klick(true);
				Menu.setDisaim(false);
			}
			else if(button==1)
				System.out.println("reinforce");
			else if(button==2)
				System.out.println("dynamite");
			else if(button==3)
				System.out.println("upgrade");
			
		}
		else if(o.isBuilding()){
			if(button == 1){
				((Building)o).destroyBuilding();
				Menu.setDisaim(true);
				
			}
				if(o.isToolStore())
					if(button==0)
						((ToolStore)o).spawnRockRaider();
					
				    
				
				
		}
		else if(type==3){
			
		}
	}
	
	
}
