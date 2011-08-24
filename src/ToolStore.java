
public class ToolStore extends Building {

	//protected final int ID;
	public ToolStore(double x, double y, int type) {
		super(x, y, type);

		
	}
	
	public static void spawnRockRaider(double x, double y){
		new RockRaider(x,y);
	}

}
