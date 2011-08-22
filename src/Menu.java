import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;


public class Menu {
	
	private static Image img;
	private static int width;
	private static int height;
	private static int menuPos;
	private static Button[] buttons;

	
	public Menu(){
		img = Toolkit.getDefaultToolkit().createImage("res/Menu.png");
		menuPos=Main.getPanelWidth()-15;
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
	
	public static void getButtons(GameObject bm){
		int c=0;
		boolean[] abilities = bm.getAbilities();
		
		
		if(bm.isRockRaider())  
			c=1;
		
		if(bm.isBuilding())
			c=2;
		
		if(bm.isTile())
			c=3;
			
		buttons = new Button[abilities.length];
		
		
		switch (c){
			case 1:
				for(int i=0; i < abilities.length; i++){
					buttons[i] = new Button(1, i+1, menuPos, abilities[i]);
				}
				break;
			case 2:  break;
			case 3:  break;
			default:
		}
	}
		
	public static void show(){
		
	}
	
	public static void hide(){
		
	}
	
}
