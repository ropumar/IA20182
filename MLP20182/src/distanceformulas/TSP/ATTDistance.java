package distanceformulas.TSP;
import graph.TSP.Coord;

/**
 * Calculates "pseudo-Euclidean" distance, for ATT problems, between given
 * coordinates and rounds to nearest integer
 */
public class ATTDistance extends DistanceFunction {

	public ATTDistance() {
		super();
	}

	@Override
	public int distance(Coord c1, Coord c2) {
		int xDist = (int) (c1.getX() - c2.getX());
		int yDist = (int) (c1.getY() - c2.getY());
		double temp1 = Math.sqrt(((xDist * xDist) + (yDist * yDist)) / 10.0);
		int temp2 = (int) (0.5 + temp1);

		if (temp2 < temp1) {
			return ++temp2;
		}

		return temp2;
	}

}
