package algoMLP;

import java.util.HashSet;
import problem.TSP.DistanceTable;
import problem.TSP.TSPInstance;

public class GreedyStandard {

	private TSPInstance tsp;
	private int[] cities;
	private double[] latencyArray;
	private int[] path;
	private DistanceTable dt;

	/*
	 * Constructs an instance of a MLP problem and applies greedy nearest neighbor.
	 */
	public GreedyStandard(TSPInstance problem) throws Exception {
		this.tsp = problem;
		dt = tsp.getDistanceTable();
		cities = dt.listVertices();
		latencyArray = new double[cities.length];
		path = getGreedyTour(0, cities, dt, latencyArray);
	}

	/*
	 * Constructs a greedy nearest neighbor tour throughout the cities based on a
	 * given starting city. start - index of starting city in cities array. cities -
	 * array of unique cities in the MLP instance.
	 */
	public int[] getGreedyTour(int start, int[] cities, DistanceTable dt, double[] latencyArray) throws Exception {
		if (start > cities.length)
			throw new Exception();
		HashSet<Integer> unvisited = new HashSet<Integer>();
		for (int i : cities)
			unvisited.add(new Integer(i));
		unvisited.remove(new Integer(cities[start]));

		// set starting point in greedy nearest neighbor path
		int[] tour = new int[cities.length];
		tour[0] = cities[start];
		latencyArray[0]=0f;
		for (int i = 1; i < tour.length; i++) {
			int predecessor = tour[i - 1];
			//System.out.println(tour[i - 1]);
			double minDist = Double.MAX_VALUE;
			int nextCity = -1;
			// get nearest neighbor city
			for (Integer city : unvisited) {
				int currCity = city.intValue();
				double dist = dt.getDistanceBetween(predecessor, currCity);
				if (dist < minDist) {
					minDist = dist;
					nextCity = currCity;
				}
			}
			tour[i] = nextCity;
			latencyArray[nextCity-1]=latencyArray[predecessor-1] + minDist;
			unvisited.remove(new Integer(nextCity));
		}
		return tour;
	}

	/*
	 * Calculates the total distance of tour
	 */
	private static double getTourDist(int[] tour, DistanceTable dt) {
		double dist = 0;
		for (int i = 0; i < tour.length - 1; i++) {
			dist += dt.getDistanceBetween(tour[i], tour[i + 1]);
		}
		dist += dt.getDistanceBetween(tour[tour.length - 1], tour[0]);
		return dist;
	}
	
	/*
	 * Calculates the total latency of tour
	 */
	private static double getTourLatency(int[] tour, double[] latencyArray) {
		double totlatency=0;
		for (int i = 0; i < tour.length - 1; i++) {
			totlatency +=latencyArray[i];
		}

		return totlatency;
	}

	/*
	 * Prints latency and distance cost
	 */
	private void printTour(int[] tour, DistanceTable dt, double[] latencyArray) {
		System.out.print("Best path found: ");
		for (int i = 0; i < tour.length; i++) {
			System.out.print(tour[i] + " ");
		}
		System.out.println(tour[0]);
		
		System.out.println("Cost of greedy Standard Latency: " + getTourLatency(tour,latencyArray));
		System.out.println("Cost of greedy Standard path: " + getTourDist(tour, dt));

	}
	
	/*
	 * public print
	 */
	public void printSolution() {
		printTour(path, dt, latencyArray);
	}

}
