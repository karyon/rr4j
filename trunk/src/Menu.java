import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;


public class Menu {
	
	private static Image img;
	private static int menuPos; // y position of the first button of menu (show-/hidebutton)
	private static Button[] buttons;
	private static boolean visible = true;
	private static GameObject o;
	private static int bgroesse;
	private static Button lastPressed;
	private static boolean wait4klick=false; //if next mouse click call another function of a button by clicking at any point of the map
	private static boolean disaim=true; //if true, by next click -> clear current target

	public Menu(){
		img = Toolkit.getDefaultToolkit().createImage("res/Menu.png");
		menuPos=Painter.getPanelWidth()-40;
		buttons = new Button[0];
		bgroesse=40;
		
	}
	
	/**
	 * Draw images and the Playermenu.
	 * (Menubutton + images of all active buttons)
	 */
	public static void paint(Graphics g){
		g.drawImage(img,menuPos,1,null);
		if(visible){
			for(int i=0;i<buttons.length;i++){
				Button b=buttons[i];
				g.drawImage(b.getImage(),menuPos,(buttons.length-i)*bgroesse,null);	
			}
		}
		
		String txt="Player:   Ore = " + Player.getOre() + "  Crystals = " + Player.getCrystal();
		int size=txt.length();
		int y=Painter.getPanelHeight();
		g.setColor(Color.black);
		g.fillRect(0, y-20, size*6+6, 20);
		g.setColor(Color.yellow);
		g.drawRect(0,y-20,size*6 + 6, 20 );
		g.drawString(txt,5,y-5);
	}	
	
	/**
	 * creates buttons for the current target
	 * @param gameObject current target
	 */
	public static void createButtons(GameObject gameObject){
		o=gameObject;
		boolean[] abilities = o.getAbilities();
		disaim=false;
		buttons = new Button[abilities.length];	
		
		if(o.isRockRaider()){
			for(int i=0; i < abilities.length; i++){
				buttons[i] = new Button(1, i, abilities[i],o);
			}
			return;
				
		}
				
		if(o.isBuilding()) {
			for(int i=0; i< abilities.length; i++){
				buttons[i] = new Button(2, i,abilities[i],o);
			}
			return;
		}
				
		if(o.isTile()){
			
			return;
		}
		
	}
	
	/**
	 * removes buttons by creating a clear list
	 */
	public static void delSelected(){
		buttons= new Button[0];
	}
	
	/**
	 * Change the visibility of all button of the menu (except the show-/hidebutton ;P )
	 */
	public static void menuButtonClicked(){
		if(visible)
			visible=false;
		else
			visible=true;
	}

	/**
	 * If a button is hit this method call the buttons function and returns true
	 * @param x x position of the mouseclick
	 * @param y y position of the mouseclick
	 * @return returns true if one button is clicked
	 */
	public static boolean buttonHit(int x, int y) {
		
		disaim=false;
		
		if(x>menuPos && x < Painter.getPanelWidth()){
		
			if(y>= 0 && y< 40){
				menuButtonClicked();
				return true;
			}
			else{	
				for(int i=0;i<buttons.length;i++){
					if(y>=(buttons.length-(i))*bgroesse && y<(buttons.length-(i))*bgroesse+40){
						if(visible)
							buttons[i].callFunction();
						return true;					
					}
				}
			}
		
		}
		disaim=true;
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

	public static boolean isDisaim() {
		return disaim;
	}
}
