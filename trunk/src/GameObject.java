

public abstract class GameObject {
	protected double x;
	protected double y;
	
	private final int width;
	private final int height;
	
	protected boolean[] abilities;
	
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
	
	public boolean isBuilding() {
		return this instanceof Building;
	}
	
	public boolean isToolStore() {
		return this instanceof ToolStore;
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	/**
	 * @param another
	 * @return true, if this GameObject intersects with the specified GameObject.
	 */
	public boolean intersects (GameObject another) {
		return this.intersects(another.x, another.y, another.width, another.height);
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @return true, if the specified coordinates lie on this GameObject.
	 */
	public boolean intersects(double x, double y) {
		return (x > this.x && y > this.y && x < this.x + width && y < this.y + height);
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return true, if this GameObject intersects with the specified rectangle.
	 */
	public boolean intersects(double x, double y, double width, double height) {
		return (x > this.x - width && y > this.y - height && x < this.x + this.width && y < this.y + this.height);
	}

	public boolean[] getAbilities() {
		return abilities;
	}
	
}
