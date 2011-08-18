import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.awt.Image;
import java.awt.Toolkit;



public class Tools {
	
	private static Scanner scanner;
	private static File file;
	private static Image[] tileImages;
	public static int[][] parseData(String input) {
		String[] lines = input.split("\n");
		
		int height = lines.length;
		int width = lines[0].split(" ").length;
		
		int[][] result = new int[lines.length][lines[0].split(" ").length];
		
		for (int i = 0; i < height; i++) {
			String[] lineSplit = lines[i].split(" ");
			for (int j = 0; j < width; j++) {
				result[i][j] = Integer.parseInt(lineSplit[j]);
			}
		}
		System.out.println(Arrays.toString(result[0]));
		return result;
	}
	
	public static String getWorld(String filename)
	{	
		String world="";
		file = new File("res/"+filename);
		
		try{
		
			scanner = new Scanner(file); 
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
	
	public static void createTileset () {
		tileImages = new Image[10];
		for (int i=0;i<3;i++){
			tileImages[i] = Toolkit.getDefaultToolkit().createImage("res/tile"+i+".png");
		}
	}
	

	public static Image[] getTileImages() {
		return tileImages;
	}
	
	
	
}
