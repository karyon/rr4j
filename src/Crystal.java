import java.util.ArrayList;


public class Crystal extends GameObject{

	private final static int size = 10;
	
	private static ArrayList<Crystal> crystalList = new ArrayList<Crystal>();
	
	
	public Crystal(double x, double y) {
		super(x, y, size, size);
		crystalList.add(this);
		// TODO Auto-generated constructor stub
	}
	
	
	
	public static ArrayList<Crystal> getCrystalList() {
		return crystalList;
	}
	
	public static int getSize() {
		return size;
	}
}
