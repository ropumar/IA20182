package problem.TSP;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Abstract class for copmuting distances between vertices, finding neighbors of
 * vertices, and auxillary functions
 */

public abstract class DistanceTable {

	public DistanceTable() {
		super();
	}

	public abstract int[] listVertices();

	public abstract int[] getNeighborsOf(int id);

	public abstract double getDistanceBetween(int id1, int id2);

	public abstract void load(BufferedReader reader) throws IOException;

	public boolean isNeighbor(int id1, int id2) {
		int[] neighbors = getNeighborsOf(id1);

		for (int i = 0; i < neighbors.length; i++) {
			if (neighbors[i] == id2) {
				return true;
			}
		}

		return false;
	}

}
