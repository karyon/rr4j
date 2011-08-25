
public class ToolStore extends Building {

	//protected final int ID;
	public ToolStore(double x, double y, int type) {
		super(x, y, type);

		abilities = new boolean[5];
		abilities[0]=true;
		for(int i=1;i<abilities.length;i++){
			abilities[i]=false;
		}		
	}
	
	public static void spawnRockRaider(double x, double y){
		new RockRaider(x,y);
	}

}
