import java.awt.Rectangle;


public abstract class GameObject {
	protected double x;
	protected double y;
	
	private final int width;
	private final int height;
	
	public GameObject(double x, double y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	
	public boolean isRockRaider() {
		return this instanceof RockRaider;
	}
	
	public boolean isTile() {
		return this instanceof Tile;
	}
	
	
	public boolean intersects (GameObject another) {
		Rectangle thisRect = new Rectangle((int) x, (int) y, width, height);
		Rectangle anotherRect = new Rectangle((int) another.x, (int) another.y, another.width, another.height);
		return thisRect.intersects(anotherRect);
	}
	
	public boolean intersects(double x, double y) {
		return (x > this.x && y > this.y && x < this.x + width && y < this.y + height);
	}
	
	public boolean intersects(double x, double y, double width, double height) {
		return (x > this.x - width && y > this.y - height && x < this.x + this.width && y < this.y + this.height);
	}
	
}
