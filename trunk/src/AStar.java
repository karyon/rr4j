import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;



public class AStar {
	
	private class Node implements Comparable<Node>{
		public double g;
		public double f;
		public Tile t;
		public Node parent;
		
		public Node(Tile t, double g, double h, Node parent) {
			this.t = t;
			this.g = g;
			this.f = g + h;
			this.parent = parent;
		}
		@Override
		public int compareTo(Node n) {
			return (int)(f - n.f);
		}
		
	}
	
	private final HashMap<Tile, Double> f = new HashMap<Tile, Double>(150, 1);
	private final PriorityQueue<Node> openList = new PriorityQueue<Node>(50);
	
	private List<Tile> result;
	private double cost = Double.MAX_VALUE;
	
	private Tile goal;
	
	
	
	
	public AStar(Tile start, Tile goal) {
		this.goal = goal;
		result = findPath(start);
		start.hashCode();
	}
	
	public AStar(Tile start, List<Tile> goals) {

		double bestCost = Double.MAX_VALUE;
		for (Tile t: goals) {
			this.goal = t;
			List<Tile> temp = findPath(start);
			if (cost < bestCost) {
				result = temp;
				bestCost = cost;
			}
			openList.clear();
//			g.clear(); //TODO is this really necessary?
		}
		cost = bestCost;
	}
	
	
	
	public double getCost() {
		return cost;
	}
	
	public List <Tile> getPath() {
		return result;
	}

	private double h(Tile from) {
		return (Math.abs(from.x - goal.x) + Math.abs(from.y - goal.y))/64;
	}
	
	private double g(Tile from, Tile to) {
		return 1.0;
	}
	
	
	private void expand(Node n) {
		Double mindist = f.get(n.t);
		if (mindist == null || mindist > n.f) {
			f.put(n.t, n.f);
		}
		else
			return;
		
		for (Tile curr: Map.getMap().getAdjacentTiles(n.t)) {
			if (curr != n.parent.t && curr.isWalkable()) {
				Node n2 = new Node(curr, n.g + g(n.t, curr), h(curr), n);
				Double g2 = f.get(curr);
				if(g2 == null || g2 > n2.f) {
					openList.offer(n2);
				}
			}
		}
	}
	
	
	
	private List<Tile> findPath(Tile start) {
		if (!goal.isWalkable()) {
			return null;
		}
		if (start == goal) {
			cost = 0;
			return new LinkedList<Tile>();
		}
		
		Node startNode = new Node(start, 0, 0, null);
		for (Tile t: Map.getMap().getAdjacentTiles(start)) {
			if (t.isWalkable())
				openList.offer(new Node(t, 1, h(t), startNode));
		}
		
		
		while(!openList.isEmpty()) {
			Node curr = openList.poll();
			if (curr.t == goal) {
				LinkedList<Tile> result = new LinkedList<Tile>();
				cost = curr.f;
				while (curr.parent != null) {
					result.addFirst(curr.t);
					curr = curr.parent;
				}
				return result;
			}
			
			expand(curr);
		}
		return null;
	}
	
	
	public static List<Tile> getReachableTiles(Tile start) {
		Tile curr = start;
		List<Tile> result = new ArrayList<Tile>(50);
		LinkedList<Tile> openList = new LinkedList<Tile>();
		openList.add(start);
		while(!openList.isEmpty()) {
			curr = openList.removeFirst();
			if (!curr.isWalkable() && curr.getType() != Tile.TYPE_WATER)
				continue;
			for (Tile t: Map.getMap().getAdjacentTiles(curr)) {
				if (!result.contains(t)) {
					openList.add(t);
					result.add(t);
				}
			}
		}
		return result;
	}

}