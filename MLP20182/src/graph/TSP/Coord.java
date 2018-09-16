package graph.TSP;

/**
 * 
 * Definition for Coordinates, to be used at vertices
 */
public class Coord {

	private final double X;
	private final double Y;

	public Coord() {
		this(0, 0);
	}

	public Coord(int x, int y) {
		this.X = (double) x;
		this.Y = (double) y;
	}

	public Coord(double x, double y) {
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

		Coord other = (Coord) obj;

		if (X != other.X) {
			return false;
		}

		if (Y != other.Y) {
			return false;
		}
		return true;
	}

}
