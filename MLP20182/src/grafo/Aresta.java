package grafo;

/**
 * Definition of an edge
 */
public class Aresta {

	private final double V1;
	private final double V2;

	public Aresta(double v1, double v2) {
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

		Aresta other = (Aresta) obj;

		return ((V1 == other.V1) && (V2 == other.V2)) || ((V1 == other.V2) && (V2 == other.V1));
	}

}
