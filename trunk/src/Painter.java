import java.awt.Graphics;

import javax.swing.JPanel;


public class Painter extends JPanel {
	@Override
	public void paintComponent(Graphics g) {
		/*for (int y = 0; y < data.length; y++) {
			for (int x = 0; x < data[0].length; x++) {
				g.drawRect(x*40, y*40, g.getFont().getSize(), 15);
				g.drawString(Integer.toString(data[y][x]), x*40, y*40+g.getFont().getSize());
				g.getFont().getSize();
			}
		}*/
		Figure.paintAll(g);
		Map.getMap().paintAll(g);
	}
}
