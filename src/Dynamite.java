import java.util.ArrayList;


public class Dynamite extends GameObject{

	private final static int size = 10;
	private static int timeToExplosion=500;
	
	private static ArrayList<Dynamite> dynamiteList = new ArrayList<Dynamite>();
	
	public Dynamite(double x, double y){
		super(x, y, size, size);
		dynamiteList.add(this);
		
	}
	
	public static ArrayList<Dynamite> getDynamiteList() {
		return dynamiteList;
	}
	
	public static int getSize() {
		return size;
	}
}
