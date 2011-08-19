import java.awt.Graphics;
import java.util.Random;



public class Map {
	private Tile[][] mapFields;
	private int width, height;
	
	static Map map;
	
	public Map (int [] [] mapData){
		if (map != null) //SINGLETON!
			throw new RuntimeException();
		width = mapData.length;
		height  = mapData[0].length;
		mapFields = new Tile[width][height];
		for (int i = 0; i < width; i++){
			for (int j = 0; j < height; j++){
				mapFields[i][j]=new Tile(i,j,mapData[i][j],1);
			}
		}
		map = this;
	}
	
	public void createTiles()
	{
		Random r = new Random();
		int type = r.nextInt(3);
		
		for (int i = 0; i < Tile.getSize(); i++) 
		{
			for (int j = 0; j < Tile.getSize(); j++) 
			{
				mapFields[i][j] = new Tile(i * Tile.getSize(), j * Tile.getSize(), type, 1);
			}
		}
	}
	
	
	public void paintAll(Graphics g) {
		for (int x = 0; x < mapFields.length; x++) {
			for (int y = 0; y < mapFields[0].length; y++) {
				Tile currTile = mapFields[x][y];
				g.drawImage(Tools.getTileImages()[currTile.getType()], x * Tile.getSize(), y * Tile.getSize(), null);
			}
		}
	}
	
	
	public static Map getMap() {
		return map;
	}
	
	
	public Tile[][] getMapFields() {
		return mapFields;
	}
	
	
}
