import java.util.ArrayList;


public class Building extends GameObject{
	
	private static ArrayList<Building> buildingList = new ArrayList<Building>();
	private static final int size = Tile.getSize();
	private final int ID;
	private static int nextID=0;

	final int toolstore = 0;
	final int powerstation = 1;
	private static int type;
	
	public Building(double x, double y,int type) {
		super(x, y, size, size);
		ID = nextID;
		this.type=type;
		buildingList.add(this);
		nextID++;
		
		
		
		
	}
	public static ArrayList<Building> getBuildingList(){
		return buildingList;
	}
	
	public static int getSize(){
		return size;
	}
}
