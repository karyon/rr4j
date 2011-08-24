import java.awt.Image;
import java.awt.Toolkit;

public class Button {
	
	private int type; //button for (RockRaider, Building, etc.)
	private int button; //buttonnumber
	private boolean active; //true, if available for this object;
	private Image img;
	boolean visible;

	
	public Button(int type, int button, boolean active, boolean visible){
		
		this.visible = visible;
		img = Toolkit.getDefaultToolkit().createImage("res/t"+type+"Button"+(button-1)+active+".png");
		this.type = type;
		this.button = button;
		this.setActive(active);
	}
	
	public Image getImage(){
			return img;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isActive() {
		return active;
	}

	public void callFunction(double x, double y) {
		
		if(type==1){
			if(button==1)
				System.out.println("dig");
			else if(button==2)
				System.out.println("reinforce");
			else if(button==3)
				System.out.println("dynamite");
			else if(button==4)
				System.out.println("upgrade");
			
		}
		else if(type==2){
			if(button==1)
				new RockRaider(x,y-RockRaider.getSize());
					
		}
		else if(type==3){
			
		}
	}
	
	
}
