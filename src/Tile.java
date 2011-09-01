import java.util.Random;


public class Tile extends GameObject{

	private int type;  //Tile Typ
	private boolean walkable;
	private static final int size = 64;
	private boolean visible = false;
	
	final static int TYPE_GROUND = 0;
	final static int TYPE_WATER = 1;
	final static int TYPE_RUBBLE = 3;
	final static int TYPE_SOLID_ROCK = 4;
	final static int TYPE_HARD_ROCK = 5;
	final static int TYPE_LOOSE_ROCK = 6;
	final static int TYPE_DIRT = 7;

	
	
	public Tile (int x, int y, int type){
		super(x, y, size, size);
		abilities = new boolean[1]; 
		abilities[0]=true;
		
		this.type = type;
		
		walkable = type == TYPE_GROUND || type == TYPE_RUBBLE;
	}
	
	
	public static int getSize() {
		return size;
	}
	public int getType() {
		return type;
	}

	public boolean isWalkable() {
		return walkable;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	
	public void destroy() {
		switch (type) {
		case TYPE_DIRT:
		case TYPE_LOOSE_ROCK:
			type = TYPE_RUBBLE;
			walkable = true;
			for (Tile t: Map.getMap().getAdjacentTiles(this))
				t.setVisible(true);
			break;
		case TYPE_RUBBLE:
			Random r = new Random();
			type = TYPE_GROUND;
			//one crystal and one ore on random locations
			new Ore(x + r.nextInt(getSize()-Ore.getSize()), y + r.nextInt(getSize()-Ore.getSize()));
			new Crystal(x + r.nextInt(getSize()-Crystal.getSize()), y + r.nextInt(getSize()-Crystal.getSize()));
			break;
		}
	}
	
	public String toString() {
		return "(" + x/64 + ", " + y/64 + ")";
	}


	public void createToolStore(double x,double y) {
		new ToolStore(x, y, 1);
		
	}

}
