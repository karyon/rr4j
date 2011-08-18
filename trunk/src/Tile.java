
public class Tile {
	private int x,y;   //Koordinaten
	private int type;  //Tiel Typ
	private int  walkable;
	private int imageID;  
	
	public Tile (int xco,  int yco, int typeData, int tile){
		x = xco;
		y = yco;
		type = typeData;
		this.imageID = tile;
		
		switch (type){
	    default: walkable = 1;
		break;
		}
		
	}

}
