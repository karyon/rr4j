import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;


public class Menu {
	
	private static Image img;
	private static int menuPos;
	private static Button[] buttons;
	private static boolean visible = true;
	private static GameObject o;
	private static int bgroesse;
	private static Button lastPressed;
	private static boolean wait4klick=false;
	private static boolean disaim=true;
	
	public Menu(){
		img = Toolkit.getDefaultToolkit().createImage("res/Menu.png");
		menuPos=Painter.getPanelWidth()-40;
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
		
		String txt="Player: " + "  Ore = " + Player.getOre() + "  Cristals = " + Player.getCrystal();
		char[] c= txt.toCharArray();
		int size=0;
		for(int i=0;i<c.length;i++){
			size++;
		}
		int y=Painter.getPanelHeight();
		g.setColor(Color.black);
		g.fillRect(0, y-20, size*6+6, 20);
		g.setColor(Color.yellow);
		g.drawRect(0,y-20,size*6 + 6, 20 );
		g.drawString(txt,5,y-5);
		
		
	}	
	
	public static void createButtons(GameObject bm){
		o=bm;
		boolean[] abilities = bm.getAbilities();
		
		buttons = new Button[abilities.length];	
		
		if(bm.isRockRaider()){
			for(int i=0; i < abilities.length; i++){
				buttons[i] = new Button(1, i+1, abilities[i],o);
			}
			return;
				
		}
				
		if(bm.isBuilding()) {
			for(int i=0; i< abilities.length; i++){
				buttons[i] = new Button(2, i+1,abilities[i],o);
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

	public static boolean buttonHit(int x, int y) {
		
		disaim=true;
		
		if(x>menuPos && x < Painter.getPanelWidth()){
		
			if(y>= 0 && y< 40){
				menuButtonClicked();
				return true;
			}
			else{	
				for(int i=0;i<buttons.length;i++){
					if(y>=(i+1)*bgroesse && y<(i+1)*bgroesse+40){
						if(visible)
							buttons[i].callFunction(o.getX(),o.getY());
						return true;					
					}
				}
			}
		
		}
		return false;
		
	}
	
	public static void setLastPressed(Button lastPressed) {
		Menu.lastPressed = lastPressed;
	}

	public static Button getLastPressed() {
		return lastPressed;
	}
	
	
	public static void setWait4klick(boolean wait4klick) {
		Menu.wait4klick = wait4klick;
	}

	public static boolean getWait4klick() {
		return wait4klick;
	}

	public static void setDisaim(boolean disaim) {
		Menu.disaim = disaim;
	}

	public static boolean getDisaim() {
		return disaim;
	}
}
