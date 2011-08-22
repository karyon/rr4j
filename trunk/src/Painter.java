import java.awt.Graphics;
import javax.swing.JPanel;


public class Painter extends JPanel {
	@Override
	public void paintComponent(Graphics g) {
		Map.getMap().paintAll(g);
		Building.paintAll(g);
		RockRaider.paintAll(g);
	}
}
