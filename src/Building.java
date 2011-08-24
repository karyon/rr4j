import java.util.ArrayList;


public class Building extends GameObject{
	
	protected static ArrayList<Building> buildingList = new ArrayList<Building>();
	protected static final int size = Tile.getSize();
	protected final int ID;
	protected static int nextID=0;

	protected static int type;
	
	public Building(double x, double y,int type) {
		super(x, y, size, size);
		ID = nextID;
		this.type=type;
		buildingList.add(this);
		nextID++;
		
		abilities = new boolean[5];		
		
	}
		
	public static ArrayList<Building> getBuildingList(){
		return buildingList;
	}
	
	public static int getSize(){
		return size;
	}
	

}
