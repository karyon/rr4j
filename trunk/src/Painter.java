import java.awt.Graphics;
import javax.swing.JPanel;


public class Painter extends JPanel {
	public void paintComponent(Graphics g) {
		Map.getMap().paintAll(g);
		Building.paintAll(g);
		RockRaider.paintAll(g);
		Ore.paintAll(g);
		Crystal.paintAll(g);
		Mousehandler.paintDragRect(g);
		Menu.paint(g);
		//Menu.paintButtons(g);
		Button.paint(g);
	}
}
