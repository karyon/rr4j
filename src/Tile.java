
public class Tile extends GameObject{

	private int type;  //Tiel Typ
	private boolean walkable;
	private int imageID;
	
	final static int TYPE_GROUND = 0;
	final static int TYPE_WATER = 1;
	final static int TYPE_STONE = 2;
	
	
	public Tile (int x, int y, int type, int imgageID){
		super(x, y);
		this.type = type;
		this.imageID = imgageID;
		
		walkable = type == TYPE_GROUND;
		
	}
	

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isWalkable() {
		return walkable;
	}

	public int getImageID() {
		return imageID;
	}

	public void setImageID(int imageID) {
		this.imageID = imageID;
	}

}
