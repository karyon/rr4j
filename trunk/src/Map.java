import java.util.Random;



public class Map {
	private Tile[][] mapFields;
	private int width, height;
	
	public Map (int [] [] mapData){
		
		width = mapData.length;
		height  = mapData[0].length;
		mapFields = new Tile[width][height];
		for (int i = 0; i < width; i++){
			for (int j = 0; j < width; j++){
				mapFields[i][j]=new Tile(i,j,mapData[width][height],1);
			}
		}
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
	
	
}
