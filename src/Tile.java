
public class Tile {

	private int x,y;   //Koordinaten
	private int type;  //Tiel Typ
	private int  walkable;
	private int imageID;  
	
	public Tile (int xco,  int yco, int typeData, int imgageID){
		x = xco;
		y = yco;
		type = typeData;
		this.imageID = imgageID;
		
		switch (type){
	    default: walkable = 1;
		break;
		}
		
	}
	

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getWalkable() {
		return walkable;
	}

	public void setWalkable(int walkable) {
		this.walkable = walkable;
	}

	public int getImageID() {
		return imageID;
	}

	public void setImageID(int imageID) {
		this.imageID = imageID;
	}

}
