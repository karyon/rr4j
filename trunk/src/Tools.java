import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;



public class Tools {
	/**
	 * Contains all images from the res-directory 
	 * with the filenames tile0, tile1 etc.
	 */
	private static Image[] tileImages;
	
	private static Image rockRaiderImage;
	
	private static Image buildingImage;
	
	private static Image oreImage;
	
	private static Image crystalImage;
	
	
	/**
	 * Splits the specified String around spaces and new Lines into a new int[][].
	 * Example: input "1 2 3\n4 5 6" returns {1,2},{3,4}{4,5}.
	 * @param input
	 * @return
	 */
	public static int[][] loadMap() {
		String data = loadFile("testmap.txt");
		String[] lines = data.split("\n");
		
		int height = lines.length;
		int width = lines[0].split(" ").length;
		
		int[][] result = new int[lines[0].split(" ").length][lines.length];
		
		for (int i = 0; i < height; i++) {
			String[] lineSplit = lines[i].split(" ");
			for (int j = 0; j < width; j++) {
				result[j][i] = Integer.parseInt(lineSplit[j]);
			}
		}
		return result;
	}
	
	/**
	 * Returns the contents of the specified File in a String.
	 * @param filename
	 * @return
	 */
	private static String loadFile(String filename)
	{	
		String world="";
		File file = new File("res/"+filename);
		
		try{
		
			Scanner scanner = new Scanner(file); 
			while(scanner.hasNext())
			{
				world= world + scanner.nextLine()+"\n";
			}
		}
		
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}	
		
		return world;
	}
	
	/**
	 * Loads all images from the res-directory  with the 
	 * filenames tile0, tile1 etc. into tileImages
	 */
	public static void createTileset () {
		tileImages = new Image[10];
		for (int i=0;i<8;i++){
			tileImages[i] = Toolkit.getDefaultToolkit().createImage("res/tile"+i+".png");
		}
	}
	
	
	public static void createImages() {
		rockRaiderImage = new BufferedImage(RockRaider.getSize(), RockRaider.getSize(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = rockRaiderImage.getGraphics();
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, RockRaider.getSize()-1, RockRaider.getSize()-1);
		g.drawString("R",5, 15);
		
		buildingImage =  new BufferedImage(Building.getSize(), Building.getSize(), BufferedImage.TYPE_INT_ARGB);
		g = buildingImage.getGraphics();
		g.setColor(new Color(240,170,170));
		g.fillRect(0, 0, Building.getSize()-1, Building.getSize()-1);
		g.setColor(Color.BLACK);
		g.drawString("Building",5, 15);
		
		oreImage =  new BufferedImage(Ore.getSize(), Ore.getSize(), BufferedImage.TYPE_INT_ARGB);
		g = oreImage.getGraphics();
		g.setColor(new Color(30, 16, 5));
		g.fillRect(0, 0, Ore.getSize()-1, Ore.getSize()-1);
		
		crystalImage = new BufferedImage(Crystal.getSize(), Crystal.getSize(), BufferedImage.TYPE_INT_ARGB);
		g = crystalImage.getGraphics();
		g.setColor(new Color(30, 230, 7));
		g.fillRect(0, 0, Crystal.getSize()-1, Crystal.getSize()-1);
	}
	

	public static Image[] getTileImages() {
		return tileImages;
	}
	
	public static Image getRockRaiderImage() {
		return rockRaiderImage;
	}

	public static Image getBuildingImage() {
		return buildingImage;
	}

	public static Image getOreImage() {
		return oreImage;
	}

	public static Image getCrystalImage() {
		return crystalImage;
	}


	
	
	
}
