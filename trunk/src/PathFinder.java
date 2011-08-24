
import java.util.*;

/*
 * Example.
 */
public class PathFinder extends AStar<Tile>
{
//                private int[][] map;

//                public static class Node{
//                                public int x;
//                                public int y;
//                                Node(int x, int y){
//                                                this.x = x; 
//                                                this.y = y;
//                                }
//                                public String toString(){
//                                                return "(" + x + ", " + y + ") ";
//                                } 
//                }
                public PathFinder(){
//                                this.map = map;
                }

                protected boolean isGoal(Tile node){
                                return node == Map.getMap().getTileAt(8*64+1, 36*64+1);
                }

                protected Double g(Tile from, Tile to){

                                if(from.x == to.x && from.y == to.y)
                                                return 0.0;

                                if(Map.getMap().getTileAt(from.x, from.y).isWalkable())
                                                return 1.0;

                                return Double.MAX_VALUE;
                }

                protected Double h(Tile from, Tile to){
                                /* Use the Manhattan distance heuristic.  */
                                return new Double(Math.abs(from.x - to.x) + Math.abs(from.y - to.y));
                }

                protected List<Tile> generateSuccessors(Tile node){
                                List<Tile> ret = new LinkedList<Tile>();
                                int x = (int) node.getX();
                                int y = (int) node.getY();
//                                if(y < map.length - 1 && map[y+1][x] == 1)
                                     ret.add(Map.getMap().getTileAt(x, y + 70));
                                if (!ret.get(0).isWalkable())
                                	ret.remove(0);
                                                ret.add(Map.getMap().getTileAt(x + 70, y));
                                if (!ret.get(ret.size()-1).isWalkable())
                                	ret.remove(ret.size()-1);
                                ret.add(Map.getMap().getTileAt(x - 60, y));
                                if (!ret.get(ret.size()-1).isWalkable())
                                	ret.remove(ret.size()-1);
                                ret.add(Map.getMap().getTileAt(x, y-60));
                                if (!ret.get(ret.size()-1).isWalkable())
                                	ret.remove(ret.size()-1);
                                

                                return ret;
                }

                public static void main(String [] args){

            		Map.createMap(Tools.parseData(Tools.loadFile("testmap.txt")));
                                PathFinder pf = new PathFinder();


                                long begin = System.currentTimeMillis();

                                List<Tile> nodes = pf.compute(Map.getMap().getTileAt(3*64+1, 10*64+1));
                                
                                long end = System.currentTimeMillis();


                                System.out.println("Time = " + (end - begin) + " ms" );
                                System.out.println("Expanded = " + pf.getExpandedCounter());
                                System.out.println("Cost = " + pf.getCost());
                                
                                if(nodes == null)
                                                System.out.println("No path");
                                else{
                                                System.out.print("Path = ");
                                                for(Tile n : nodes)
                                                                System.out.print(n);
                                                System.out.println();
                                }
                }

}