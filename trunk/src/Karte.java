
public class Karte {
	public Tile [][] mapFields;
	public int wide, hight;
	public Karte (int [] [] mapData){
		
		wide = mapData.length;
		hight  = mapData[0].length;
		mapFields = new Tile[wide][hight];
		for (int i = 0; i < wide; i++){
			for (int j = 0; j < wide; j++){
				mapFields[i][j]=new Tile(i,j,mapData[wide][hight]);
				
			}
		}
	}
}
