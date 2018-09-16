package distanceformulas.TSP;
import graph.TSP.Coord;

/**
 * Converts given geographical coordinates to corresponding terms of latitude
 * and longitude, then calculates distance between two points in kilometers
 */

public class GeoDistance extends DistanceFunction {

	public GeoDistance() {
		super();
	}

	@Override
	public int distance(Coord c1, Coord c2) {
		Coord c1_lat_long = convertToLat_Long(c1);
		Coord c2_lat_long = convertToLat_Long(c2);
		final double RADIUS = 6378.388;

		double q1 = Math.cos(c1_lat_long.getY() - c2_lat_long.getY());
		double q2 = Math.cos(c1_lat_long.getX() - c2_lat_long.getX());
		double q3 = Math.cos(c1_lat_long.getX() + c2_lat_long.getX());
		return (int) (RADIUS * Math.acos(0.5 * ((1.0 + q1) * q2 - (1.0 - q1) * q3)) + 1.0);
	}

	/**
	 * Converts given coordinate from degrees/minutes (in the form of <DDD.MM>) to
	 * terms of latitude and longitude, where the returned coordinate's x value
	 * corresponds to latitude and y value corresponds to longitude
	 * 
	 * @param c
	 *            coordinate to be converted (in the form of <DDD.MM>)
	 * @return converted coordinate (in the form of <latitude, longitude>)
	 */
	private static Coord convertToLat_Long(Coord c) {
		final double PI = 3.141592;
		int degree;
		double min;

		degree = (int) (0.5 + c.getX());
		min = c.getX() - degree;
		double latitude = PI * (degree + 5.0 * min / 3.0) / 180.0;

		degree = (int) (0.5 + c.getY());
		min = c.getY() - degree;
		double longitude = PI * (degree + 5.0 * min / 3.0) / 180.0;

		return new Coord(latitude, longitude);
	}

}
