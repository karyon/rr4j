import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class RockRaider extends GameObject
{
	private char los; //los = line of sight  (N,E,S,W)
	private double tarX,tarY;
	private Image img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
	private final int ID;
	private int moveSpeed = 100; //pixel per second
	private JobList jobList= new JobList();
	private int timer = 0;
	
	
	private static final int size = 20;
	
	private static int nextID = 0;
	
	private static ArrayList<RockRaider> allRockRaiders = new ArrayList<RockRaider>();
	
	
	public RockRaider(double x, double y)
	{
		super(x, y, size, size);
		tarX = x;
		tarY = y;
		ID = nextID;
		Graphics g = img.getGraphics();
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, size-1, size-1);
		g.drawString(Integer.toString(ID),5, 15);
		nextID++;
		los  = 'S';
		allRockRaiders.add(this);
	}
	
	
	public void update(int ms) {
		if (tarX == x && tarY == y)
			return;
		double maxMovement = moveSpeed/1000.0 * ms;

		double distX = tarX - x;
		double distY = tarY - y;

		double distance = Math.sqrt(distX * distX + distY * distY);

		double ratio = distance / maxMovement;
		x += distX / ratio;
		y += distY / ratio;
		
		if (this.intersectsUnpassableObject()) {
			// one step back, then stop.
			x -= distX / ratio;
			y -= distY / ratio;
			tarX = x;
			tarY = y;
			jobList.jobDoneCancelNext();
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
	
	
	public boolean intersectsUnpassableObject() {
		int tileX = (int)x/Tile.getSize();
		int tileY = (int)y/Tile.getSize();
		Tile tiles[][] = Map.getMap().getMapFields();
		//gehe durch die vier Tiles, die geschnitten werden k�nnen
		for (int x = tileX; x <= tileX + 1 && x < tiles.length; x++) {
			for (int y = tileY; y <= tileY + 1 && y < tiles[0].length; y++) {
				if (!tiles[x][y].isWalkable() && this.intersects(tiles[x][y]))
					return true;
			}
		}
		for (RockRaider r : allRockRaiders) {
			if (r == this)
				continue;
			if (this.intersects(r))
				return true;
		}
		return false;
	}
	
	
	public void goTo(final double x, final double y) {
		jobList.addJob(new Job() {
			@Override
			public void execute() {
				RockRaider.this.setTarget(x, y);
			}
		});
	}

	private void setTarget(double x, double y) {
		tarX = x;
		tarY = y;
	}

	public char getLoS() {
		return los;
	}
	
	public int getID() {
		return ID;
	}
	
	public void paint(Graphics g) {
		g.drawImage(img, (int) x, (int) y, null);
	}
	
	public static ArrayList<RockRaider> getRockRaiderList() {
		return allRockRaiders;
	}

	public static void paintAll(Graphics g) {
		for (RockRaider f : allRockRaiders)
			f.paint(g);
	}

	public static void updateAll(int ms) {
		for (RockRaider f : allRockRaiders)
			f.update(ms);
	}


	public void destroy(final Tile t) {
		switch (t.getType()) {
		case Tile.TYPE_STONE:
			if (x < t.x - size)
				goTo(t.x - size-2, t.y + 21);
			else if (x > t.x + Tile.getSize())
				goTo(t.x + Tile.getSize()+2, t.y + 21);
			else 
				goTo(t.x + 20, (y < t.y) ?  t.y - size - 2 : t.y + Tile.getSize() + 2);
			break;
		case Tile.TYPE_RUBBLE:
			goTo(t.x + 10, t.y + 10);
			break;
		default: return;
		}
		jobList.addJob(new Job(){
			@Override
			public void execute() {
				t.destroy();
				jobList.jobDoneExecuteNext();
			}
		});
	}
	
}
