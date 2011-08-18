
public class Map {
	public Tile [][] mapFields;
	public int width, height;
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
}
