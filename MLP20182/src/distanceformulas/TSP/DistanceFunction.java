package distanceformulas.TSP;
import graph.TSP.Coord;
import graph.TSP.Vertex;

public abstract class DistanceFunction {
	
	
	public double distance(Vertex v1, Vertex v2) {
		Coord c1 = v1.getCoord();
		Coord c2 = v2.getCoord();
		
		return distance(c1, c2);
	}
	
	public abstract int distance(Coord c1, Coord c2);
	
}
