package grafo;

/**
 * 
 * Definition for Coordinates, to be used at vertices
 */
public class Coordenada {

	private final double X;
	private final double Y;

	public Coordenada() {
		this(0, 0);
	}

	public Coordenada(int x, int y) {
		this.X = (double) x;
		this.Y = (double) y;
	}

	public Coordenada(double x, double y) {
		this.X = x;
		this.Y = y;
	}

	public double getX() {
		return X;
	}

	public double getY() {
		return Y;
	}

	@Override
	public String toString() {
		return "(" + this.X + ", " + this.Y + ")";
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

		Coordenada other = (Coordenada) obj;

		if (X != other.X) {
			return false;
		}

		if (Y != other.Y) {
			return false;
		}
		return true;
	}

}
