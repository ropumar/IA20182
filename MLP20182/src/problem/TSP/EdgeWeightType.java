package problem.TSP;

import distanceformulas.TSP.ATTDistance;
import distanceformulas.TSP.CeilingDistance;
import distanceformulas.TSP.DistanceFunction;
import distanceformulas.TSP.EuclideanDistance;
import distanceformulas.TSP.GeoDistance;



public enum EdgeWeightType {
	EXPLICIT, ATT, CEIL_2D, EUC_2D, GEO, MATRIX;
	
	public DistanceFunction getDistanceFunction() {
		switch (this) {
		case EUC_2D:
			return new EuclideanDistance();
		case CEIL_2D:
			return new CeilingDistance();
		case GEO:
			return new GeoDistance();
		case ATT:
			return new ATTDistance();
		default:
			throw new IllegalArgumentException(
					"no distance function defined for " + this);
		}
	}
	
}
