package graph.TSP;

/**
 * Definition of an edge
 */
public class Edge {

	private final double V1;
	private final double V2;

	public Edge(double v1, double v2) {
		super();
		this.V1 = v1;
		this.V2 = v2;
	}

	public double getV1() {
		return V1;
	}

	public double getV2() {
		return V2;
	}

	public boolean hasEndpoint(int v) {
		return (v == V1) || (v == V2);
	}

	public double getOppositeEndpoint(double v) {
		if (v == V1) {
			return V2;
		} else if (v == V2) {
			return V1;
		} else {
			throw new IllegalArgumentException("edge does not have endpoint " + v);
		}
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

		Edge other = (Edge) obj;

		return ((V1 == other.V1) && (V2 == other.V2)) || ((V1 == other.V2) && (V2 == other.V1));
	}

}
