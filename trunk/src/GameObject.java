
public abstract class GameObject {
	double x;
	double y;
	
	public GameObject(double x, double y) {
		this.x = x;
		this.y = y;
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