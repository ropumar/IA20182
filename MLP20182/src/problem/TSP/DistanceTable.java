package problem.TSP;

import java.io.BufferedReader;
import java.io.IOException;

public abstract class DistanceTable {

	public DistanceTable() {
		super();
	}

	public abstract int[] listVertices();


	public abstract double getDistanceBetween(int id1, int id2);

	public abstract void load(BufferedReader reader) throws IOException;

}
