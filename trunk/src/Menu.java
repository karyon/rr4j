import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;


public class Menu {
	
	private static Image img;
	private static int width;
	private static int height;
	private static int menuPos;
	private static Button[] buttons;
	private static boolean visible = true;
	private static int buttonHitted;
	private static GameObject o;

	
	public Menu(){
		img = Toolkit.getDefaultToolkit().createImage("res/Menu.png");
		menuPos=Main.getPanelWidth()-40;
		buttons = new Button[0];
		
	}
	
	public static void paint(Graphics g){
		g.drawImage(img,menuPos,1,null);
		
		
	}
	
	public static void paintButtons(Graphics g){
		for(int i=0;i<buttons.length;i++){
			buttons[i].paint(g);	
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
				buttons[i] = new Button(1, i+1, menuPos, abilities[i],visible);
			}
				
		}
				
		if(bm.isBuilding()) {
			for(int i=0; i< abilities.length; i++){
				buttons[i] = new Button(2, i+1, menuPos, abilities[i], visible);
			}
		}
				
		if(bm.isTile()){
			
		}
		
	}
	
	public static void menuButtonClicked(){
		if(visible)
			hide();
		else
			show();
	}
		
	public static void show(){
		visible = true;
		for(int i=0;i<buttons.length;i++){
			buttons[i].setVisibility(visible);
		}
		
	}
	
	public static void hide(){
		visible = false;
		for(int i=0;i<buttons.length;i++){
			buttons[i].setVisibility(visible);
		}
	}

	public static boolean buttonHit(int x, int y) {
		
		
		
		if(x>menuPos && x < Main.getPanelWidth()){
		
			if(y>= 0 && y< 40){
				menuButtonClicked();
				return true;
			}
			else{	
				for(int i=0;i<buttons.length;i++){
					if(y>=buttons[i].getY() && y<buttons[i].getY()+40){
						buttonHitted=i;
						buttons[i].callFunction(o.getX(),o.getY());
						return true;					
					}
				}
			}
		
		}
		return false;
		
	}
	
}
