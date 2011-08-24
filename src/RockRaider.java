import java.util.ArrayList;


public class RockRaider extends GameObject
{
	
	/** The target coordinates this RockRaider is moving to. 
	 * Equal to x and y if this RockRaider is not moving. */
	private double tarX,tarY;
	
	/** ID of this RockRaider. Each RockRaider has a unique ID. */
	private final int ID;
	
	/** Maximum speed in pixel per second. */
	private int moveSpeed = 100;
	
	/** List of all jobs this RockRaider has to execute. jobList.get(0) (if it exists) is the currently active one. */
	private JobList jobList= new JobList();
	
	/** The time in ms this RockRaider still should do nothing. */
	private int timer = 0;
	
	
	
	/** Width and height of all RockRaiders. */
	private static final int size = 20;
	
	/** The next unused ID. */
	private static int nextID = 0;
	
	/** A list of all existing RockRaiders. */
	private static ArrayList<RockRaider> allRockRaiders = new ArrayList<RockRaider>();

	
	
	public RockRaider(double x, double y)
	{
		super(x, y, size, size);
		tarX = x;
		tarY = y;
		ID = nextID;
		nextID++;
		allRockRaiders.add(this);
		
		abilities = new boolean[4]; 
		abilities[0]=true;
		for(int i=1;i<abilities.length;i++){
			abilities[i]=false;
		}
		
	}
	
	/**
	 * Moves this RockRaider by the specified time.
	 * @param ms
	 */
	public void update(int ms) {
		if (timer > 0) {
			timer -= ms;
			if (timer <= 0) {
				timer = 0;
				jobList.jobDoneExecuteNext();
			}
			return;
		}
			
		if (tarX == x && tarY == y)
			return;
		double maxMovement = moveSpeed/1000.0 * ms;

		double distX = tarX - x;
		double distY = tarY - y;

		double distance = Math.sqrt(distX * distX + distY * distY);

		double ratio = distance / maxMovement;
		x += distX / ratio;
		y += distY / ratio;
		
		
		if (this.intersectsUnpassableObject()!=null) {
			
			GameObject unpassableObject = this.intersectsUnpassableObject();
			// one step back, then stop.
			x -= distX / ratio;
			y -= distY / ratio;
			findPath(unpassableObject,ratio);
			return;
		}
		
		if (Math.abs(distX / ratio) >= Math.abs(distX)) {
			x = tarX;
		}
		if (Math.abs(distY / ratio) >= Math.abs(distY)) {
			y = tarY;
		}
		if (tarX == x && tarY == y)
			jobList.jobDoneExecuteNext();
	}
	
	/**
	 * Checks if this RockRaider intersects an Object he cannot pass,
	 * such as other RockRaiders, Water etc.
	 * @return true if this RockRaider intersects an unpassable object.
	 */
	public GameObject intersectsUnpassableObject() {
		
		int tileX = (int)x/Tile.getSize();
		int tileY = (int)y/Tile.getSize();
		Tile tiles[][] = Map.getMap().getMapFields();
		//gehe durch die vier Tiles, die geschnitten werden k�nnen
		for (int x = tileX; x <= tileX + 1 && x < tiles.length; x++) {
			for (int y = tileY; y <= tileY + 1 && y < tiles[0].length; y++) {
				if (!tiles[x][y].isWalkable() && this.intersects(tiles[x][y]))
					return tiles[x][y];
			}
		}
		/*
		for (RockRaider r : allRockRaiders) {
			if (r == this)
				continue;
			if (this.intersects(r))
				return r;
		}*/
		
		ArrayList<Building> building = Building.getBuildingList();
		for (Building b: building){
			
			if(this.intersects(b)){
				return b;
			}
		}
		
		return null;
		
	}
	
	/**
	 * Adds a new Job to jobList, which sets the target of this 
	 * RockRaider to the specified coordinates. 
	 * @param x
	 * @param y
	 */
	public void goToJob(final double x, final double y) {
		
		
			jobList.addJob(new Job() {
			@Override
			public void execute() {
				RockRaider.this.setTarget(x, y);
			}
		});
	}
	
	public void goToObjectJob(GameObject object){
		int height = object.getHeight();
		int width = object.getWidth();
		if (x < object.x -size)
			goToJob(object.x - size-2, object.y + 21);
		else if (x > object.x + width)
			goToJob(object.x + width+2, object.y + 21);
		else 
			goToJob(object.x + 20, (y < object.y) ?  object.y - size - 2 : object.y + height + 2);
		
		
	}
	
	/**
	 * Method allows RockRaiders to take rescources based on the given kind of rescource while creating 2 goToJobs (one to the res one back to the ToolStore)
	 * @param kind 1 = Ore, 0 = Meth
	 * @param object
	 */
	public void takeRes (final int kind, final GameObject object) {
		if (!KeyHandler.isCtrl()){jobList.cancelAll();}
		goToJob(object.x, object.y );
		
		goToObjectJob(Building.getBuildingList().get(0));
 System.out.println("da");
		switch (kind){
		case 1:
			jobList.addJob(new Job(){
				@Override
				public void execute() {
					Player.setOre(Player.getOre()+1);
					System.out.println(Player.getOre());
					Ore.getOreList().remove(object);
					jobList.jobDoneExecuteNext();
				}
			});

			break;
		case 0:jobList.addJob(new Job(){
			@Override
			public void execute() {
				Player.setCrystal(Player.getCrystal()+1);
				Crystal.getCrystalList().remove(object);
				System.out.println("bis hierhin?");
				jobList.jobDoneExecuteNext();
				}
			});
			break;
			
		}
		
	}
		
	
	
	public void waitJob(final int ms) {
		jobList.addJob(new Job() {
			@Override
			public void execute() {
				setTimer(ms);
			}
			
			@Override
			public void cancel() {
				timer = 0;
			}
		});
	}

	private void setTarget(double x, double y) {
		tarX = x;
		tarY = y;
	}
	
	private void setTimer(int ms) {
		timer = ms;
	}
	
	public int getID() {
		return ID;
	}
	
	public static ArrayList<RockRaider> getRockRaiderList() {
		return allRockRaiders;
	}

	public static void updateAll(int ms) {
		for (RockRaider f : allRockRaiders)
			f.update(ms);
	}

	
	/**
	 * Adds two Jobs to jobList: the first one lets this RockRaider 
	 * goTo the specified Tile, the second one destroys that Tile.
	 * @param t The Tile this RockRaider should goTo and destroy.
	 */
	public void goToAndDestroy(Tile t) {
		jobList.cancelAll();
		//goTo the Tile t
		switch (t.getType()) {
		case Tile.TYPE_DIRT:
		case Tile.TYPE_LOOSE_ROCK:
			if (x < t.x - size)
				goToJob(t.x - size-2, t.y + 21);
			else if (x > t.x + Tile.getSize())
				goToJob(t.x + Tile.getSize()+2, t.y + 21);
			else 
				goToJob(t.x + 20, (y < t.y) ?  t.y - size - 2 : t.y + Tile.getSize() + 2);
			break;
		case Tile.TYPE_RUBBLE:
			goToJob(t.x + 10, t.y + 10);
			break;
		default: return;
		}
		switch (t.getType()) {
		case Tile.TYPE_DIRT: waitJob(300); break;
		case Tile.TYPE_LOOSE_ROCK: waitJob(800); break;
		case Tile.TYPE_RUBBLE: waitJob(500); break;
		default: return;
		}
		waitJob(500);
		//new Job: destroy t
		destroy(t);
	}
	
	private void destroy(final Tile t) {
		jobList.addJob(new Job() {
			@Override
			public void execute() {
				t.destroy();
				jobList.jobDoneExecuteNext();
			}
		});
	}
	
	public void findPath(GameObject object, double ratio) {
		
		
		
		
		/*if(object.isRockRaider()) {
			
			double otherX = object.getX();
			double otherY = object.getY();
			
						
			
		}*/
		//else{
			tarX = x;
			tarY = y;
			jobList.jobDoneCancelNext();
		//}
		
	} 
	
	
	public static int getSize() {
		return size;
	}
	
}
