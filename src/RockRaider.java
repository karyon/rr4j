import java.util.ArrayList;
import java.util.List;


public class RockRaider extends GameObject
{
	private GameObject carryLoad = null;
	
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

	
	
	public RockRaider(double x, double y) {
		super(x, y, size, size);
		tarX = x;
		tarY = y;
		ID = nextID;
		nextID++;
		allRockRaiders.add(this);
		
		abilities = new boolean[4]; 
		abilities[0]=true;
	}
	
	/**
	 * Updates this RockRaider for the specified time.
	 * @param ms
	 */
	private void update(int ms) {
		if (carryLoad != null) {
			carryLoad.setX(x);
			carryLoad.setY(y);
		}
		if (timer > 0) {
			timer -= ms;
			if (timer <= 0) {
				timer = 0;
				jobList.jobDoneExecuteNext();
			}
			return;
		}
		move(ms);
	}
	
	
	private void move(int ms) {	
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
		
		ArrayList<Building> building = Building.getBuildingList();
		for (Building b: building){
			
			if(this.intersects(b)){
				return b;
			}
		}
		
		return null;
		
	}
	
	
	private void goToJob(List<Tile> path) {
		for (final Tile t: path) {
			jobList.addJob(new Job() {
				@Override
				public void execute() {
					tarX = t.x+20;
					tarY = t.y+20;
				}
				@Override
				public void cancel() {
					tarX = x;
					tarY = y;
					RockRaider.this.carryLoad = null;
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
	public boolean goToJob(final double x, final double y, boolean cancel) {
		if (cancel)
			jobList.cancelAll();
		
		List<Tile> path = new AStar(Map.getMap().getTileAt(RockRaider.this.x, RockRaider.this.y), Map.getMap().getTileAt(x, y)).getPath();
		if (path == null)
			return false;
		goToJob(path);
		return true;
	}
	
	
	private boolean goToObjectJob(GameObject object){
		List<Tile> possibleTargets;
		if (object instanceof Tile) {
			possibleTargets = Map.getMap().getAdjacentTiles((Tile) object);
		}
		else if (object instanceof Building) {
			possibleTargets = Map.getMap().getAdjacentTiles(Map.getMap().getTileAt(object.x, object.y));
		}
		else
			return false;
		
		List<Tile> path = new AStar(Map.getMap().getTileAt(x, y), possibleTargets).getPath();
		if (path == null)
			return false;
		goToJob(path);
		return true;
	}
	
	
	private void takeObjectJob (final GameObject object){
		jobList.addJob(new Job(){
			@Override
			public void execute() {
				RockRaider.this.carryLoad = object;
				jobList.jobDoneExecuteNext();
			};
		});
	}
	
	/**
	 * Creates four Jobs: 1. go to the object, 2. take the object, 
	 * 3. go to the ToolStore, 4. drop the object in the ToolStore
	 * @param kind 1 = Ore, 0 = Meth
	 * @param object
	 */
	public void takeRes (final GameObject object) {
		if (carryLoad == object) carryLoad = null;
		if (!KeyHandler.isCtrl()){jobList.cancelAll();}
		if (!goToJob(object.x, object.y , false))
			return;
		takeObjectJob(object);
	
		jobList.addJob(new Job(){
			public void execute() {
				ArrayList<Tile> BuildingTiles = new ArrayList<Tile>();	
				for (int i = 0;i< Building.buildingLists[0].size();i++){
					Building b = Building.buildingLists[0].get(i);
					BuildingTiles.addAll(Map.getMap().getAdjacentTiles(Map.getMap().getTileAt(b.x, b.y))); // Alles Selbsterklärend
				}
					
				AStar aStar = new AStar(Map.getMap().getTileAt(x, y),BuildingTiles);
				
				if (aStar.getPath()==null) {
					jobList.cancelAll();
					return;
				}
				RockRaider.this.goToJob(aStar.getPath());
				addResourceJob(object);
				System.out.println("Test");
				jobList.jobDoneExecuteNext();
			}
		});
	}
	
	private void addResourceJob(final GameObject res) {
		if (res instanceof Ore) {
			jobList.addJob(new Job(){
				@Override
				public void execute() {
					Player.setOre(Player.getOre()+1);
					System.out.println(Player.getOre());
					carryLoad = null;
					Ore.getOreList().remove(res);
					jobList.jobDoneExecuteNext();
					
				}
			});
		}
		else if (res instanceof Crystal) {
			jobList.addJob(new Job(){
				@Override
				public void execute() {
					Player.setCrystal(Player.getCrystal()+1);
					carryLoad = null;
					Crystal.getCrystalList().remove(res);
					jobList.jobDoneExecuteNext();
				}
			});
		}
	}
	
	
	private void waitJob(final int ms) {
		jobList.addJob(new Job() {
			@Override
			public void execute() {
				timer = ms;
			}
			
			@Override
			public void cancel() {
				timer = 0;
			}
		});
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
			if (!goToObjectJob(t))
				return;
			waitJob(300);
			break;
		case Tile.TYPE_LOOSE_ROCK:
			if (!goToObjectJob(t))
				return;
			waitJob(800);
			break;
		case Tile.TYPE_RUBBLE:
			if (!goToJob(t.x + 10, t.y + 10, false))
				return;
			waitJob(500);
			break;
		default: return;
		}
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
	
	public static int getSize() {
		return size;
	}
}
