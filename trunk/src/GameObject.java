
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
	
}
