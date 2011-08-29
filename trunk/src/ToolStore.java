
public class ToolStore extends Building {

	//protected final int ID;
	public ToolStore(double x, double y, int type) {
		super(x, y, type);

		abilities = new boolean[5];
		abilities[0]=true;
		abilities[1]=true;
		for(int i=1;i<abilities.length;i++){
			abilities[i]=false;
		}		
	}
	
	public void spawnRockRaider(){
		new RockRaider(x,y-RockRaider.getSize());
		System.out.println("SPAAAAWN");
	}
	
	public void destroyToolbox () {
		Building.buildingList.remove(ID); 
		
	}

}
