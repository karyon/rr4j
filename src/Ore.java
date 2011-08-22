import java.util.ArrayList;


public class Ore extends GameObject{
	
	private final static int size = 10;
	
	private static ArrayList<Ore> oreList = new ArrayList<Ore>();
	
	
	public Ore(double x, double y) {
		super(x, y, size, size);
		oreList.add(this);
		// TODO Auto-generated constructor stub
	}
	
	
	public static ArrayList<Ore> getOreList() {
		return oreList;
	}
	
	public static int getSize() {
		return size;
	}

}
