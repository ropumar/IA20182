package graph.TSP;

/**
 * Definition of vertex
 */
public class Vertex {
	
	private final int ID;
	private final Coord coord;
	
	public Vertex(int v, Coord c) {
		super();
		this.ID = v;
		this.coord = c;
	}
	
	public int getID() {
		return this.ID;
	}
	
	public Coord getCoord() {
		return this.coord;
	}
	
	@Override
	public String toString() {
		return this.ID + ": " + this.coord.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (obj == null) {
			return false;
		}
		
		if (getClass() != obj.getClass()) {
			return false;
		}
		
		Vertex other = (Vertex) obj;
		
		if (ID != other.ID) {
			return false;
		}
		
		if (!coord.equals(other.coord)) {
			return false;
		}
		
		return true;
	}
	
}
