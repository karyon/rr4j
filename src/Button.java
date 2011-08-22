import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;


public class Button {
	
	private int type; //button for (RockRaider, Building, etc.)
	private int button; //buttonnumber
	private boolean active; //true, if available for this object
	private static int x;
	private static int y;
	private static Image img;

	
	public Button(int type, int button, int pos, boolean active){
		
		img = Toolkit.getDefaultToolkit().createImage("res/t"+type+"Button"+(button-1)+active+".png");
		this.type = type;
		this.button = button;
		this.setActive(active);
		
		setX(pos);
		setY(15*this.button);
		
		}
	
	public static void paint(Graphics g){
		if(img != null){
			g.drawImage(img,x,y,null);
		}
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isActive() {
		return active;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getX() {
		return x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getY() {
		return y;
	}
	
}
