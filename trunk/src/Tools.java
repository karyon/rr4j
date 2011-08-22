import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.awt.Image;
import java.awt.Toolkit;



public class Tools {
	/**
	 * Contains all images from the res-directory 
	 * with the filenames tile0, tile1 etc.
	 */
	private static Image[] tileImages;
	
	
	/**
	 * Splits the specified String around spaces and new Lines into a new int[][].
	 * Example: input "1 2 3\n4 5 6" returns {1,2},{3,4}{4,5}.
	 * @param input
	 * @return
	 */
	public static int[][] parseData(String input) {
		String[] lines = input.split("\n");
		
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
	public static String loadFile(String filename)
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
		for (int i=0;i<4;i++){
			tileImages[i] = Toolkit.getDefaultToolkit().createImage("res/tile"+i+".png");
		}
	}
	

	public static Image[] getTileImages() {
		return tileImages;
	}
	
	
	
}
