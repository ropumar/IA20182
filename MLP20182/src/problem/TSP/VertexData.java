package problem.TSP;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import distanceformulas.TSP.DistanceFunction;
import graph.TSP.Coord;
import graph.TSP.Vertex;

/**
 * Class loading, creating, and accessing Vertex data
 */
public class VertexData extends DistanceTable {

	private final int size;
	private final DistanceFunction distanceFunction;
	private final Map<Integer, Vertex> vertices;

	public VertexData(int size, EdgeWeightType edgeWeightType) {
		this(size, edgeWeightType.getDistanceFunction());
	}

	public VertexData(int size, DistanceFunction distanceFunction) {
		super();
		this.size = size;
		this.distanceFunction = distanceFunction;
		vertices = new HashMap<Integer, Vertex>();
	}

	protected void add(Vertex vertex) {
		vertices.put(vertex.getID(), vertex);
	}

	public Vertex get(int vertex) {
		return vertices.get(vertex);
	}

	protected void remove(int vertex) {
		vertices.remove(vertex);
	}

	protected void clear() {
		vertices.clear();
	}

	public int size() {
		return vertices.size();
	}

	@Override
	public int[] listVertices() {
		int index = 0;
		int[] result = new int[size];

		for (Vertex v : vertices.values()) {
			result[index++] = v.getID();
		}

		return result;
	}

	@Override
	public int[] getNeighborsOf(int vertex) {
		int index = 0;
		int[] neighbors = new int[size - 1];

		if (!vertices.containsKey(vertex)) {
			throw new IllegalArgumentException("vertex " + vertex + "does not exist");
		}

		for (Vertex v : vertices.values()) {
			if (v.getID() != vertex) {
				neighbors[index++] = v.getID();
			}
		}

		return neighbors;
	}

	@Override
	public double getDistanceBetween(int v1, int v2) {
		Vertex vertex1 = get(v1);
		Vertex vertex2 = get(v2);

		if (vertex1 == null) {
			throw new IllegalArgumentException("vertex " + v1 + " does not exist");
		}

		if (vertex2 == null) {
			throw new IllegalArgumentException("vertex " + v2 + " does not exist");
		}

		return distanceFunction.distance(get(v1), get(v2));
	}

	@Override
	public void load(BufferedReader reader) throws IOException {
		for (int i = 0; i < size; i++) {
			String line = reader.readLine();
			String[] tokens = line.trim().split("\\s+");

			if (tokens.length != 2 + 1) { // 2 is used for 2D coords
				throw new IOException("invalid number of tokens for vertex entry");
			}

			int vertex = Integer.parseInt(tokens[0]);
			Coord coord = new Coord(Double.parseDouble(tokens[1]), Double.parseDouble(tokens[2]));

			add(new Vertex(vertex, coord));
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (Vertex v : vertices.values()) {
			sb.append(v.toString());
			sb.append('\n');
		}

		return sb.toString();
	}

}
