import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.ImageObserver;


public class Menu {
	
	private static Image img;
	private static int menuPos;
	private static Button[] buttons;
	private static boolean visible = true;
	private static int buttonHitted;
	private static GameObject o;
	private static int bgroesse;

	
	
	public Menu(){
		img = Toolkit.getDefaultToolkit().createImage("res/Menu.png");
		menuPos=Main.getPanelWidth()-40;
		buttons = new Button[0];
		bgroesse=40;
		
	}
	
	public static void paint(Graphics g){
		g.drawImage(img,menuPos,1,null);
		if(visible){
			for(int i=0;i<buttons.length;i++){
				Button b=buttons[i];
				if(b.getImage() != null)
					g.drawImage(b.getImage(),menuPos,(i+1)*bgroesse,null);	
				
				
			}
		}
		
		
	}	
	
	public static void createButtons(GameObject bm){
		int c=0;
		o=bm;
		boolean[] abilities = bm.getAbilities();
		
		
		if(bm.isRockRaider())  
			c=1;
		
		if(bm.isBuilding())
			c=2;
		
		if(bm.isTile())
			c=3;
			
		buttons = new Button[abilities.length];
		
		
		if(bm.isRockRaider()){
			for(int i=0; i < abilities.length; i++){
				buttons[i] = new Button(1, i+1, abilities[i],visible);
			}
			return;
				
		}
				
		if(bm.isBuilding()) {
			for(int i=0; i< abilities.length; i++){
				buttons[i] = new Button(2, i+1,abilities[i], visible);
			}
			return;
		}
				
		if(bm.isTile()){
			
			return;
		}
		
	}
	
	public static void delSelected(){
		buttons= new Button[0];
	}
	
	public static void menuButtonClicked(){
		if(visible)
			visible=false;
		else
			visible=true;
	}

	public static boolean buttonHit(int x, int y,boolean change) {
		
		
		if(x>menuPos && x < Main.getPanelWidth()){
		
			if(y>= 0 && y< 40){
				if(change)
					menuButtonClicked();
				return true;
			}
			else{	
				for(int i=0;i<buttons.length;i++){
					if(y>=(i+1)*bgroesse && y<(i+1)*bgroesse+40){
						buttonHitted=i;
						if(visible)
							buttons[i].callFunction(o.getX(),o.getY());
						return true;					
					}
				}
			}
		
		}
		return false;
		
	}
	
}
