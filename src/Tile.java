
public class Tile extends GameObject{

	private int type;  //Tiel Typ
	private int walkable;
	private int imageID;
	
	public Tile (int x,  int y, int typeData, int imgageID){
		super(x, y);
		type = typeData;
		this.imageID = imgageID;
		
		switch (type){
	    default: walkable = 1;
		break;
		}
		
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
