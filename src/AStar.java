/*    
 * A* algorithm implementation.
 * Copyright (C) 2007, 2009 Giuseppe Scrivano <gscrivano@gnu.org>

 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
                        
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License along
 * with this program; if not, see <http://www.gnu.org/licenses/>.
 */

import java.util.*;

/**
 * A* algorithm implementation using the method design pattern.
 * 
 * @author Giuseppe Scrivano
 */
public class AStar
 {
	private class Path implements Comparable<Path> {
		public Tile point;
		public double f;
		public double g;
		public Path parent;
		
		
		public Path(Tile point) {
			this.point = point;
			parent = null;
			g = f = 0.0;
		}

		public Path(Path parent, Tile point) {
			this.parent = parent;
			this.point = point;
			g = parent.g + g(parent.point, point);
			f = f(this);
		}
		
		
		public int compareTo(Path p) {
			return (int) (f - p.f);
		}
	}
	


	private PriorityQueue<Path> paths;
	private HashMap<Tile, Double> mindists;
	private double lastCost;
	
	public static List<Tile> nodes;
	private Tile goal;
	private List<Tile> result;
	
	
	public AStar(Tile start, Tile goal) {
		paths = new PriorityQueue<Path>();
		mindists = new HashMap<Tile, Double>();
		lastCost = 0.0;
		this.goal = goal;
		result = compute(start, goal);
	}

	/**
	 * Cost for the operation to go to <code>to</code> from <code>from</from>.
	 * 
	 * @param from 	The node we are leaving.
	 * @param to 	The node we are reaching.
	 * @return 		The cost of the operation.
	 */
	private double g(Tile from, Tile to) {
		return 1.0;
	}

	/**
	 * Estimated cost to reach a goal node. An admissible heuristic never gives
	 * a cost bigger than the real one. <code>from</from>.
	 * 
	 * @param from 	The node we are leaving.
	 * @param to 	The node we are reaching.
	 * @return 		The estimated cost to reach an object.
	 */
	private double h(Tile from, Tile to) {
		/* Use the Manhattan distance heuristic. */
		return (Math.abs(from.x - to.x) + Math.abs(from.y - to.y))/64;
	}

	/**
	 * Total cost function to reach the node <code>to</code> from
	 * <code>from</code>.
	 * 
	 * The total cost is defined as: f(x) = g(x) + h(x).
	 * 
	 * @param from 	The node we are leaving.
	 * @param to 	The node we are reaching.
	 * @return 		The total cost.
	 */
	protected double f(Path p) {
		return p.g + h(p.point, goal);
	}

	/**
	 * Generate the successors for a given node.
	 * 
	 * @param node 	The node we want to expand.
	 * @return 		A list of possible next steps.
	 */
	private List<Tile> generateSuccessors(Path p) {
		List<Tile> ret = Map.getMap().getAdjacentTiles(p.point);
		
		for (int i = ret.size() - 1; i >= 0; i--) {
			if (!ret.get(i).isWalkable() || (p.parent != null && ret.get(i) == p.parent.point))
				ret.remove(i);
		}

		return ret;
	}

	/**
	 * Expand a path.
	 * 
	 * @param path 	The path to expand.
	 */
	private void expand(Path path) {
		Double min = mindists.get(path.point);

		//If a better path to this point already exists then don't expand it.
		if (min == null || min.doubleValue() > path.f)
			mindists.put(path.point, path.f);
		else
			return;

		for (Tile t : generateSuccessors(path)) {
			paths.offer(new Path(path, t));
		}
	}

	/**
	 * Get the cost to reach the last node in the path.
	 * 
	 * @return The cost for the found path.
	 */
	public double getCost() {
		return lastCost;
	}
	
	public List<Tile> getPath() {
		return result;
	}

	/**
	 * Find the shortest path to a goal starting from <code>start</code>.
	 * 
	 * @param start The initial node.
	 * @return A list of nodes from the initial point to a goal,
	 *         <code>null</code> if a path doesn't exist.
	 */
	public List<Tile> compute(Tile start, Tile goal) {
		this.goal = goal;
		Path root = new Path(start);
		expand(root);

		while (!paths.isEmpty()) {
			Path p = paths.poll();

			Tile last = p.point;

			lastCost = p.f;

			if (last == goal) {
				LinkedList<Tile> retPath = new LinkedList<Tile>();
				
				for (Path i = p; i != null; i = i.parent) {
					retPath.addFirst(i.point);
				}
				retPath.removeFirst();
				nodes = retPath;
				return retPath;
			}
			expand(p);
		}
		return null;
	}
	
	public static void main() {
		
		long begin = System.nanoTime();

		AStar pf = new AStar(Map.getMap().getMapFields()[3][10], Map.getMap().getMapFields()[14][36]);

		long end = System.nanoTime();

		System.out.println("Time = " + (end - begin) + " ms");
		System.out.println("Cost = " + pf.getCost());

		if (nodes == null)
			System.out.println("No path");
		else {
			System.out.print("Path = ");
			for (Tile n : nodes)
				System.out.print(n);
			System.out.println();
		}
	}
}