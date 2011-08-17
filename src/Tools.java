import java.util.Arrays;


public class Tools {
	
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
}
