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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

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
	


	private final PriorityQueue<Path> openList = new PriorityQueue<Path>();
	private final HashMap<Tile, Double> mindists = new HashMap<Tile, Double>();
	private double lastCost = Double.MAX_VALUE;
	
	private Tile goal;
	private List<Tile> result;
	
	/**
	 * Computes and saves the shortest path from start to goal.
	 * @param start
	 * @param goal
	 */
	public AStar(Tile start, Tile goal) {
		result = compute(start, goal);
	}
	
	/**
	 * Computes the shortest paths from start to all goals and saves the shortest one.
	 * @param start
	 * @param goals
	 */
	public AStar(Tile start, List<Tile> goals) {
		//TODO could be optimized by defining one isGoal function, defining one
		//point for the h() function and calling compute only once
		double bestCost = Double.MAX_VALUE;
		for (Tile t: goals) {
			List<Tile> temp = compute(start, t);
			if (lastCost < bestCost) {
				result = temp;
				bestCost = lastCost;
			}
		}
		lastCost = bestCost;
	}

	/**
	 * Cost for the operation to go to <code>to</code> from <code>from</from>.
	 * 
	 * @param from 	The tile we are leaving.
	 * @param to 	The node we are reaching.
	 * @return 		The cost of the operation.
	 */
	private double g(Tile from, Tile to) {
		return 1.0;
	}

	/**
	 * Minimum/best-case cost to reach the goal tile.
	 * Uses the Manhattan distance heuristic. 
	 * 
	 * @param from 	The node we are leaving.
	 * @return 		The estimated cost to reach the goal node.
	 */
	private double h(Tile from) {
		return (Math.abs(from.x - goal.x) + Math.abs(from.y - goal.y))/64;
	}

	/**
	 * Total cost function to reach the goal from the start via the specified path.
	 * 
	 * The total cost is defined as: f(x) = g(x) + h(x).
	 */
	private double f(Path p) {
		return p.g + h(p.point);
	}

	/**
	 * Expands a path.
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
		
		
		ArrayList<Tile> successors = Map.getMap().getAdjacentTiles(path.point);
		successors.remove(path.parent.point); //don't go back to the parent
		
		for (Tile t : successors) {
			if (t.isWalkable())
				openList.offer(new Path(path, t));
		}
	}

	/**
	 * Returns the cost to reach the last tile in the path.
	 * 
	 * @return The cost for the found path.
	 */
	public double getCost() {
		return lastCost;
	}
	
	/**
	 * Returns the computed path.
	 * 
	 * @return the computed path.
	 */
	public List<Tile> getPath() {
		return result;
	}

	/**
	 * Finds and returns the shortest path from start to goal.
	 * 
	 * @param start The initial tile.
	 * @param goal The goal tile.
	 * @return A list of tiles from the initial point to a goal,
	 *         <code>null</code> if a path doesn't exist.
	 */
	private List<Tile> compute(Tile start, Tile goal) {
		if (!goal.isWalkable())
			return null;
		if (start == goal) {
			lastCost = 0;
			return new LinkedList<Tile>();
		}
		this.goal = goal;
		//expand the first tile manually
		Path root = new Path(start);
		for (Tile t : Map.getMap().getAdjacentTiles(start)) {
			if (t.isWalkable())
				openList.offer(new Path(root, t));
		}

		while (!openList.isEmpty()) {
			Path p = openList.poll();
			
			if (p.point == goal) {
				lastCost = p.f;
				LinkedList<Tile> retPath = new LinkedList<Tile>();
				
				for (Path i = p; i.parent != null; i = i.parent) {
					retPath.addFirst(i.point);
				}
				return retPath;
			}
			expand(p);
		}
		return null;
	}
}