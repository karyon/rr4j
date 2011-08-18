import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;



public class Map {
	private Tile[][] mapFields;
	private int width, height;
	
	static Map map;
	
	public Map (int [] [] mapData){
		if (this == null) //SINGLETON!
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
	
	public void tileSet()
	{
		Random r = new Random();
		int type = r.nextInt(3);
		
		for(int i=0;i<20;i++)
		{
			for(int j=0;j<20;j++)
			{
				mapFields[i][j] = new Tile(i*20,j*20,type,1);
			}
		}
	}
	
	
	public void paintAll(Graphics g) {
		for (int x = 0; x < mapFields.length; x++) {
			for (int y = 0; y < mapFields[0].length; y++) {
				Tile currTile = mapFields[x][y];
				g.drawImage(Tools.getTileImages()[currTile.getType()], x*20, y*20, null);
			}
		}
	}
	
	
	public static Map getMap() {
		return map;
	}
	
	
}
