import java.util.ArrayList;


public class Building extends GameObject{
	public static final int toolStore = 0;
	protected static ArrayList<Building>[] buildingLists = new ArrayList[5];	
	protected static ArrayList<Building> buildingList = new ArrayList<Building>();
	protected static final int size = Tile.getSize();
	protected final int ID;
	protected static int nextID=0;

	protected static int type;
	static {
		System.out.println("Johannes ist doof");
		for (int i = 0; i < buildingLists.length; i++){
		buildingLists[i] = new ArrayList<Building>();}
	}
	
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

	public void destroyBuilding() {
		// TODO Auto-generated method stub
		buildingList.remove(this);
		MouseHandler.clearSelection();
		
	}
	

}
