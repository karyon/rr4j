import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;


public class Painter extends JPanel {
	public void paintComponent(Graphics g) {
		Tile[][] mapFields = Map.getMap().getMapFields();
		int tileSize = Tile.getSize();
		for (int x = 0; x < mapFields.length; x++) {
			for (int y = 0; y < mapFields[0].length; y++) {
				Tile currTile = mapFields[x][y];
				int tileImageID;
				switch (currTile.getType()) {
				case Tile.TYPE_GROUND:       tileImageID = 0; break;
				case Tile.TYPE_RUBBLE:       tileImageID = 3; break;
				case Tile.TYPE_SOLID_ROCK:   tileImageID = 4; break;
				case Tile.TYPE_HARD_ROCK:    tileImageID = 5; break;
				case Tile.TYPE_LOOSE_ROCK:   tileImageID = 6; break;
				case Tile.TYPE_DIRT:         tileImageID = 7; break;
				case Tile.TYPE_WATER:        tileImageID = 1; break;
				default:                     tileImageID = -1;
				}
				g.drawImage(Tools.getTileImages()[tileImageID], x * tileSize, y * tileSize, null);
			}
		}

		for (Building b : Building.getBuildingList())
			g.drawImage(Tools.getBuildingImage(), (int) b.getX(), (int) b.getY(), null);
		

		for (RockRaider r : RockRaider.getRockRaiderList())
			g.drawImage(Tools.getRockRaiderImage(), (int) r.getX(), (int) r.getY(), null);
		
		
		for (Ore o: Ore.getOreList())
			g.drawImage(Tools.getOreImage(), (int) o.getX(), (int) o.getY(), null);

		for (Crystal c: Crystal.getCrystalList())
			g.drawImage(Tools.getCrystalImage(), (int) c.getX(), (int) c.getY(), null);
		
		int[] selectionRect = Mousehandler.getSelectionRect();
		if (selectionRect != null) {
			g.setColor(Color.WHITE);
			g.drawRect(selectionRect[0], selectionRect[1], selectionRect[2], selectionRect[3]);
		}
		
		Menu.paint(g);
	}
}
