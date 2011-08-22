import java.awt.Graphics;


public class Map {
	private Tile[][] mapFields;
	private int width, height;
	
	private static Map map;
	
	private Map (int[][] mapData){
		width = mapData.length;
		height  = mapData[0].length;
		mapFields = new Tile[width][height];
		for (int i = 0; i < width; i++){
			for (int j = 0; j < height; j++){
				mapFields[i][j]=new Tile(i * Tile.getSize(), j * Tile.getSize() ,mapData[i][j],1);
			}
		}
	}
	
	
	public void paintAll(Graphics g) {
		int tileSize = Tile.getSize();
		for (int x = 0; x < mapFields.length; x++) {
			for (int y = 0; y < mapFields[0].length; y++) {
				Tile currTile = mapFields[x][y];
				g.drawImage(Tools.getTileImages()[currTile.getType()], x * tileSize, y * tileSize, null);
			}
		}
	}
	
	
	public Tile getTileAt(double x, double y) {
		return getMapFields()[(int) x / Tile.getSize()][(int) y / Tile.getSize()];
	}
	
	public static void createMap(int[][] mapData) {
		if (map != null)
			return;
		map = new Map(mapData);
	}
	
	public static Map getMap() {
		return map;
	}
	
	public Tile[][] getMapFields() {
		return mapFields;
	}
	
	public int getWidthPx() {
		return width * Tile.getSize();
	}
	
	public int getHeightPx() {
		return height * Tile.getSize();
	}
	
}
