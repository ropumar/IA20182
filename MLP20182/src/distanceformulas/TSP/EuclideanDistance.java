package distanceformulas.TSP;
import graph.TSP.Coord;

/**
 * Calculates Euclidean distance between given coordinates and rounds to nearest
 * integer
 */

public class EuclideanDistance extends DistanceFunction {

	public EuclideanDistance() {
		super();
	}

	@Override
	public int distance(Coord c1, Coord c2) {
		int xDist = (int) (c1.getX() - c2.getX());
		int yDist = (int) (c1.getY() - c2.getY());

		return (int) (0.5 + Math.sqrt((xDist * xDist) + (yDist * yDist)));
	}

}
