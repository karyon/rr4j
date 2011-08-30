import java.util.ArrayList;


public class ToolStore extends Building {

	//protected final int ID;
	
	public ToolStore(double x, double y, int type) {
		super(x, y, type);

		Building.buildingLists[0].add(this);
		abilities = new boolean[2];
		abilities[0]=true;
		abilities[1]=true;
			
	}
	
	public void spawnRockRaider(){
		new RockRaider(x,y-RockRaider.getSize());
		System.out.println("SPAAAAWN");
	}
	
	public void destroyToolbox () {
		Building.buildingList.remove(ID); 
		
	}

}
