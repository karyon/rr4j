import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;


public class Menu {
	
	private Image img = new BufferedImage(15, 15, BufferedImage.TYPE_INT_ARGB);
	private static int width;
	private static int height;
	
	public Menu(){
		
		Graphics g = img.getGraphics();
		g.setColor(Color.YELLOW);
		g.fillRect(Main.getPanelWidth()-14, 0, 14, 14);
		
	}
	
	public static void paint(Graphics g){
	}
	
}
