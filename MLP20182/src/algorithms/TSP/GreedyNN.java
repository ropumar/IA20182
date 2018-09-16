package algorithms.TSP;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import problem.TSP.DistanceTable;
import problem.TSP.TSPInstance;

public class GreedyNN {

	private TSPInstance tsp;

	/*
	 * Constructs an instance of a TSP problem and applies greedy nearest neighbor.
	 */
	public GreedyNN(TSPInstance problem) throws Exception {
		this.tsp = problem;
		solve();
	}

	/*
	 * Solves the TSP instance applying the greedy nearest neighbor algorithm.
	 */
	public void solve() throws Exception {
		DistanceTable dt = tsp.getDistanceTable();
		int[] cities = dt.listVertices();
		int[] path = getGreedyTour(0, cities, dt);
		printTour(path, dt);
	}

	/*
	 * Constructs a greedy nearest neighbor tour throughout the cities based on a
	 * given starting city. start - index of starting city in cities array. cities -
	 * array of unique cities in the TSP instance.
	 */
	public int[] getGreedyTour(int start, int[] cities, DistanceTable dt) throws Exception {
		if (start > cities.length)
			throw new Exception();
		HashSet<Integer> unvisited = new HashSet<Integer>();
		for (int i : cities)
			unvisited.add(new Integer(i));
		unvisited.remove(new Integer(cities[start]));

		// set starting point in greedy nearest neighbor path
		int[] tour = new int[cities.length];
		tour[0] = cities[start];

		for (int i = 1; i < tour.length; i++) {
			int predecessor = tour[i - 1];
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
			unvisited.remove(new Integer(nextCity));
		}
		return tour;
	}

	/*
	 * Calculates the total distance to complete a hamiltonian cycle throughout the
	 * tour.
	 */
	private static double getTourDist(int[] tour, DistanceTable dt) {
		double dist = 0;
		for (int i = 0; i < tour.length - 1; i++)
			dist += dt.getDistanceBetween(tour[i], tour[i + 1]);
		dist += dt.getDistanceBetween(tour[tour.length - 1], tour[0]);
		return dist;
	}

	/*
	 * Prints tour path and total cost.
	 */
	private void printTour(int[] tour, DistanceTable dt) {

		System.out.print("Best path found: ");
		for (int i = 0; i < tour.length; i++) {
			System.out.print(tour[i] + " ");
		}
		System.out.println(tour[0]);
		System.out.println("Cost of greedy NN path: " + getTourDist(tour, dt));
	}

}
