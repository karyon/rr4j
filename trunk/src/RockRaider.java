import java.util.ArrayList;
import java.util.List;


public class RockRaider extends GameObject
{
	private GameObject carryLoad = this;
	
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
		carryLoad.setX(x);
		carryLoad.setY(y);
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
			// one step back, then stop.
			x -= distX / ratio;
			y -= distY / ratio;
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
	
	
	public void goToJob(List<Tile> path) {
		for (final Tile t: path) {
			jobList.addJob(new Job() {
				@Override
				public void execute() {
					RockRaider.this.setTarget(t.x+20, t.y+20);
				}
			});
		}
	}
	
	/**
	 * Adds a new Job to jobList, which sets the target of this 
	 * RockRaider to the specified coordinates. 
	 * @param x
	 * @param y
	 */
	public void goToJob(final double x, final double y) {
		
		List<Tile> path = new AStar(Map.getMap().getTileAt(RockRaider.this.x, RockRaider.this.y), Map.getMap().getTileAt(x, y)).getPath();
		if (path == null)
			return;
		goToJob(path);
		
	}
	
	
	public void goToObjectJob(GameObject object){
		int height = object.getHeight();
		int width = object.getWidth();
		System.out.println("Object taken");
		ArrayList<Tile> possibleTargets = new ArrayList<Tile>();
		possibleTargets.add(Map.getMap().getTileAt(object.x - size-2, object.y + 21));
		possibleTargets.add(Map.getMap().getTileAt(object.x + width+2, object.y + 21));
		possibleTargets.add(Map.getMap().getTileAt(object.x + 20, object.y - size - 2));
		possibleTargets.add(Map.getMap().getTileAt(object.x + 20, object.y + height + 2));
		AStar path = new AStar(Map.getMap().getTileAt(x, y), possibleTargets);
		goToJob(path.getPath());
	}
	
	public void takeObjectJob (final GameObject object){
		
		jobList.addJob(new Job(){
			@Override
			public void execute() {
				RockRaider.this.carryLoad = object;
				goToObjectJob(Building.getBuildingList().get(0));
				jobList.jobDoneExecuteNext();
				
			};
			});
	}
	
	/**
	 * Method allows RockRaiders to take resources based on the given kind of resource while creating 2 goToJobs (one to the res one back to the ToolStore)
	 * @param kind 1 = Ore, 0 = Meth
	 * @param object
	 */
	public void takeRes (final int kind, final GameObject object) {
		if (carryLoad == object) carryLoad = this;
		if (!KeyHandler.isCtrl()){jobList.cancelAll();}
		goToJob(object.x, object.y );
		takeObjectJob (object);
		switch (kind){
		case 1:
			jobList.addJob(new Job(){
				@Override
				public void execute() {
					Player.setOre(Player.getOre()+1);
					System.out.println(Player.getOre());
					carryLoad = RockRaider.this;
					Ore.getOreList().remove(object);
					jobList.jobDoneExecuteNext();
					
				}
			});

			break;
		case 0:jobList.addJob(new Job(){
			@Override
			public void execute() {
				Player.setCrystal(Player.getCrystal()+1);
				carryLoad = RockRaider.this;
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
		System.out.println(x + " " + y);
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
		System.out.println("goToAndDestroy aufgerufen");
		jobList.cancelAll();
		//goTo the Tile t
		switch (t.getType()) {
		case Tile.TYPE_DIRT:
		case Tile.TYPE_LOOSE_ROCK:
			goToObjectJob(t);
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
	
	
	public static int getSize() {
		return size;
	}
	
}
