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
	private class Path implements Comparable {
		public Tile point;
		public double f;
		public double g;
		public Path parent;

		/**
		 * Default c'tor.
		 */
		public Path() {
			parent = null;
			point = null;
			g = f = 0.0;
		}

		public Path(Path parent, Tile point) {
			this.parent = parent;
			this.point = point;
			g = parent.g + 1;
			f = f(this);
		}

		/**
		 * Compare to another object using the total cost f.
		 * 
		 * @param o
		 *            The object to compare to.
		 * @see Comparable#compareTo()
		 * @return <code>less than 0</code> This object is smaller than
		 *         <code>0</code>; <code>0</code> Object are the same.
		 *         <code>bigger than 0</code> This object is bigger than o.
		 */
		public int compareTo(Object o) {
			Path p = (Path) o;
			return (int) (f - p.f);
		}

		/**
		 * Get the last point on the path.
		 * 
		 * @return The last point visited by the path.
		 */
		public Tile getPoint() {
			return point;
		}

		/**
		 * Set the
		 */
		public void setPoint(Tile p) {
			point = p;
		}
	}

	/**
	 * Check if the current node is a goal for the problem.
	 * 
	 * @param node
	 *            The node to check.
	 * @return <code>true</code> if it is a goal, <code>false</else> otherwise.
	 */
	protected boolean isGoal(Tile node) {
		return node == goal;
	}

	/**
	 * Cost for the operation to go to <code>to</code> from <code>from</from>.
	 * 
	 * @param from
	 *            The node we are leaving.
	 * @param to
	 *            The node we are reaching.
	 * @return The cost of the operation.
	 */
	protected double g(Tile from, Tile to) {
		return 1.0;
	}

	/**
	 * Estimated cost to reach a goal node. An admissible heuristic never gives
	 * a cost bigger than the real one. <code>from</from>.
	 * 
	 * @param from
	 *            The node we are leaving.
	 * @param to
	 *            The node we are reaching.
	 * @return The estimated cost to reach an object.
	 */
	protected double h(Tile from, Tile to) {
		/* Use the Manhattan distance heuristic. */
		return (Math.abs(from.x - to.x) + Math.abs(from.y - to.y))/64;
	}

	/**
	 * Generate the successors for a given node.
	 * 
	 * @param node
	 *            The node we want to expand.
	 * @return A list of possible next steps.
	 */
	protected List<Tile> generateSuccessors(Path p) {
		List<Tile> ret = Map.getMap().getAdjacentTiles(p.point);
		
		for (int i = ret.size() - 1; i >= 0; i--) {
			if (!ret.get(i).isWalkable() || (p.parent != null && ret.get(i) == p.parent.point))
				ret.remove(i);
		}

		return ret;
	}

	private PriorityQueue<Path> paths;
	private HashMap<Tile, Double> mindists;
	private double lastCost;
	private int expandedCounter;

	/**
	 * Check how many times a node was expanded.
	 * 
	 * @return A counter of how many times a node was expanded.
	 */
	public int getExpandedCounter() {
		return expandedCounter;
	}

	/**
	 * Default c'tor.
	 */
	public AStar() {
		paths = new PriorityQueue<Path>();
		mindists = new HashMap<Tile, Double>();
		expandedCounter = 0;
		lastCost = 0.0;
	}

	/**
	 * Total cost function to reach the node <code>to</code> from
	 * <code>from</code>.
	 * 
	 * The total cost is defined as: f(x) = g(x) + h(x).
	 * 
	 * @param from
	 *            The node we are leaving.
	 * @param to
	 *            The node we are reaching.
	 * @return The total cost.
	 */
	protected double f(Path p) {
		return p.g + h(p.point, goal);
	}

	/**
	 * Expand a path.
	 * 
	 * @param path
	 *            The path to expand.
	 */
	private void expand(Path path) {
		Double min = mindists.get(path.getPoint());

		/*
		 * If a better path passing for this point already exists then don't
		 * expand it.
		 */
		if (min == null || min.doubleValue() > path.f) {
			mindists.put(path.getPoint(), path.f);
//			System.out.println(mindists.size());
		}
		else {
			return;
		}

		for (Tile t : generateSuccessors(path)) {
			paths.offer(new Path(path, t));
//			System.out.println(paths.size());
		}

		expandedCounter++;
	}

	/**
	 * Get the cost to reach the last node in the path.
	 * 
	 * @return The cost for the found path.
	 */
	public double getCost() {
		return lastCost;
	}

	/**
	 * Find the shortest path to a goal starting from <code>start</code>.
	 * 
	 * @param start
	 *            The initial node.
	 * @return A list of nodes from the initial point to a goal,
	 *         <code>null</code> if a path doesn't exist.
	 */
	public List<Tile> compute(Tile start) {
			Path root = new Path();
			root.setPoint(start);
			expand(root);

			while (!paths.isEmpty()) {
				Path p = paths.poll();

				Tile last = p.getPoint();

				lastCost = p.f;

				if (isGoal(last)) {
					LinkedList<Tile> retPath = new LinkedList<Tile>();
					
					for (Path i = p; i != null; i = i.parent) {
						retPath.addFirst(i.getPoint());
					}
					return retPath;
				}
				expand(p);
			}
		return null;
	}
	
	public static List<Tile> nodes;
	public static Tile goal;
	public static void main() {

		Map.createMap(Tools.parseData(Tools.loadFile("testmap.txt")));
		goal = Map.getMap().getTileAt(14 * 64 + 1, 36 * 64 + 1);
		
		
		AStar pf = new AStar();

		long begin = System.nanoTime();

		nodes = pf.compute(Map.getMap().getTileAt(3 * 64 + 1, 10 * 64 + 1));

		long end = System.nanoTime();

		System.out.println("Time = " + (end - begin) + " ms");
		System.out.println("Expanded = " + pf.getExpandedCounter());
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