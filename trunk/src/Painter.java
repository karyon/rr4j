import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;


public class Painter extends JPanel {
	
	public static final int DIRECTION_NONE = 0;
	public static final int DIRECTION_UP = 1;
	public static final int DIRECTION_DOWN = 2;
	public static final int DIRECTION_LEFT = 3;
	public static final int DIRECTION_RIGHT = 4;
	
	private static int direction = DIRECTION_NONE;
	
	private static double offsetX = 300;
	private static double offsetY = 300;
	
	private static double speedX = 0;
	private static double speedY = 0;
	
	private static final double maxSpeed = 100;
	
	private static final int width = 800;
	private static final int height = 600;
	
	
	
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
				drawImage(g, Tools.getTileImages()[tileImageID], x * tileSize, y * tileSize);
			}
		}

		for (Building b : Building.getBuildingList())
			drawImage(g, Tools.getBuildingImage(), b.getX(), b.getY());
		

		for (RockRaider r : RockRaider.getRockRaiderList())
			drawImage(g, Tools.getRockRaiderImage(), r.getX(), r.getY());
		
		
		for (Ore o: Ore.getOreList())
			drawImage(g, Tools.getOreImage(), o.getX(), o.getY());

		for (Crystal c: Crystal.getCrystalList())
			drawImage(g, Tools.getCrystalImage(), c.getX(), c.getY());
		
		int[] selectionRect = Mousehandler.getSelectionRect();
		if (selectionRect != null) {
			g.setColor(Color.WHITE);
			g.drawRect(selectionRect[0], selectionRect[1], selectionRect[2], selectionRect[3]);
		}
		
		Menu.paint(g);
	}
	
	
	public void drawImage(Graphics g, Image img, double x, double y) {
		//zwei casts, da sonst bei negativen ergebnissen merkwuerdigkeiten auftreten
		g.drawImage(img, (int)(x - (int) offsetX), (int)(y - (int) offsetY), null);
	}
	
	public static void update(int ms) {
		
		switch (direction) {
		case DIRECTION_UP: speedY = -maxSpeed; break;
		case DIRECTION_DOWN: speedY = maxSpeed; break;
		case DIRECTION_LEFT: speedX = -maxSpeed; break;
		case DIRECTION_RIGHT: speedX = maxSpeed; break;
		case DIRECTION_NONE: speedX = speedY = 0; break;
		}
		
		offsetY += speedY / 1000.0 * ms;
		offsetX += speedX / 1000.0 * ms;
		if (offsetX < 0)
			offsetX = 0;
		else if (offsetX > Map.getMap().getWidthPx() - width)
			offsetX = Map.getMap().getWidthPx() - width;
		if (offsetY < 0)
			offsetY = 0;
		else if (offsetY > Map.getMap().getHeightPx() - width)
			offsetY = Map.getMap().getHeightPx() - width;
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}
	
	public static int getOffsetX() {
		return (int) offsetX;
	}
	
	public static int getOffsetY() {
		return (int) offsetY;
	}
	
	public static void setDirection(int direction) {
		Painter.direction = direction;
	}
	
	public static int getPanelWidth() {
		return width;
	}
}
