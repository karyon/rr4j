import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;


public class Painter extends JPanel {
	private static double offsetX = 300;
	private static double offsetY = 300;
	
	private double speedX = 0;
	private double speedY = 0;
	
	private static final double maxSpeed = 300;
	
	private static final int width = 800;
	private static final int height = 600;
	
	
	@Override
	public void paintComponent(Graphics g) {
		Tile[][] mapFields = Map.getMap().getMapFields();
		Image[] tileImages = Tools.getTileImages();
		int tileSize = Tile.getSize();
		for (int x = 0; x < mapFields.length; x++) {
			for (int y = 0; y < mapFields[0].length; y++) {
				Tile currTile = mapFields[x][y];
				if (!currTile.isVisible()) {
					drawImage(g, Tools.getFogImage(), x * tileSize, y * tileSize);
					continue;
				}
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
				drawImage(g, tileImages[tileImageID], x * tileSize, y * tileSize);
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
		
		for (Dynamite d: Dynamite.getDynamiteList())
			drawImage(g, Tools.getDynamiteImage(),d.getX(), d.getY());
		
		int[] selectionRect = MouseEventListener.getSelectionRect();
		if (selectionRect != null) {
			g.setColor(Color.WHITE);
			g.drawRect(selectionRect[0], selectionRect[1], selectionRect[2], selectionRect[3]);
		}

			
		Menu.paint(g);
	}
	
	
	private static void drawImage(Graphics g, Image img, double x, double y) {
		int height = img.getHeight(null);
		int width = img.getWidth(null);
		//zwei casts, da sonst bei negativen ergebnissen merkwuerdigkeiten auftreten
		int x2 = (int)(x - (int) offsetX);
		int y2 = (int)(y - (int) offsetY);
//		if (-x2 > width || -y2 > height || x2 > Painter.width || y2 > Painter.height)
//			return;
		g.drawImage(img, x2, y2, null);
	}
	
	public void update(int ms) {
		if (KeyHandler.isUp())
			speedY = -maxSpeed;
		else if (KeyHandler.isDown())
			speedY = maxSpeed;
		else
			speedY = 0;
		
		if (KeyHandler.isLeft())
			speedX = -maxSpeed;
		else if (KeyHandler.isRight())
			speedX = maxSpeed;
		else
			speedX = 0;
		
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
	
	public static int getPanelWidth() {
		return width;
	}
	public static int getPanelHeight() {
		return height;
	}
}
