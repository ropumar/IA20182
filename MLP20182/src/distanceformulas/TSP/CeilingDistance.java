package distanceformulas.TSP;
import graph.TSP.Coord;

/**
 * Calculates Euclidean distance between given coordinates and rounds up to next
 * integer
 */
public class CeilingDistance extends DistanceFunction {

	public CeilingDistance() {
		super();
	}

	@Override
	public int distance(Coord c1, Coord c2) {
		int xDist = (int) (c1.getX() - c2.getX());
		int yDist = (int) (c1.getY() - c2.getY());

		return (int) Math.ceil(Math.sqrt((xDist * xDist) + (yDist * yDist)));
	}

}
