package algoMLP;

import java.util.*;

import problem.TSP.DistanceTable;
import problem.TSP.TSPInstance;

/**
 * Chris Anderson, Chris Benton Algorithms - TSP Project
 */
public class ImprovedAlgorithm {

	private TSPInstance tsp;

	/*
	 * Constructs an instance of a TSPAgent and solves the TSP problem provided as a
	 * parameter.
	 */
	public ImprovedAlgorithm(TSPInstance problem) throws Exception {
		this.tsp = problem;
		solve();
	}

	/*
	 * Solves the TSP problem by applying greedy nearest neighbor, 2-opt heuristic,
	 * and simulated annealing search techniques.
	 */
	public void solve() throws Exception {
		DistanceTable dt = tsp.getDistanceTable();
		int[] cities = dt.listVertices();
		int[] bestPath = null;

		// run greedy nearest neighbor algorithm combined with 2-opt for pre-processing
		double minTourCost = Double.MAX_VALUE;
		for (int i = 0; i < cities.length; i++) {
			int[] tour = getGreedyTour(i, cities, dt);

			for (int k : cities) {
				if (!twoOpt(tour, dt)) {
					break;
				}
			}

			double cost = getTourDist(tour, dt);
			if (cost < minTourCost) {
				bestPath = tour;
				minTourCost = cost;
			}
		}

//		System.out.println("Path generated from greedy and 2-opt pre-processing:");
//		printTour(bestPath, dt);

//		System.out.println("Path generated from simulated annealing:");
		bestPath = simulatedAnnealing(bestPath, dt, 10000, 0.005);
		printTour(bestPath, dt);
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
		shiftArray(tour);

		System.out.print("Path found: ");
		for (int i = 0; i < tour.length; i++) {
			System.out.print(tour[i] + ", ");
		}

		System.out.println(tour[0]);
		System.out.println("Cost of path: " + getTourDist(tour, dt));
	}

	/*
	 * Perform circular shift on tour so starting vertex is vertex 1.
	 */
	private void shiftArray(int[] tour) {
		int shift = 0;

		while (tour[shift] != 1 && shift < tour.length) {
			shift++;
		}

		reverse(tour, 0, shift - 1);
		reverse(tour, shift, tour.length - 1);
		reverse(tour, 0, tour.length - 1);

	}

	/*
	 * Reverse tour between given vertices.
	 */
	private void reverse(int[] tour, int left, int right) {
		while (left < right) {
			int temp = tour[left];
			tour[left] = tour[right];
			tour[right] = temp;
			left++;
			right--;
		}
	}

	/*
	 * Returns true if a 2-opt improvement exists in the tour and has been executed.
	 */
	private static boolean twoOpt(int[] tour, DistanceTable dt) {

		for (int i = 0; i < tour.length - 3; i++) {
			for (int j = i + 2; j < tour.length - 1; j++) {
				double currDist = dt.getDistanceBetween(tour[i], tour[i + 1])
						+ dt.getDistanceBetween(tour[j], tour[j + 1]);
				double newDist = dt.getDistanceBetween(tour[i], tour[j])
						+ dt.getDistanceBetween(tour[i + 1], tour[j + 1]);
				if (currDist > newDist) {
					reverseSubPath(i + 1, j, tour);
					return true;
				}
			}
		}
		return false;
	}

	/*
	 * Reverses the path between idx1 and idx2; used in conjunction with twoOpt
	 * heuristic. idx1 - the lower index going to a higher index idx2 - the higher
	 * index going to a lower index
	 */
	private static void reverseSubPath(int idx1, int idx2, int[] path) {
		while (idx1 < idx2) {
			int temp = path[idx1];
			path[idx1] = path[idx2];
			path[idx2] = temp;
			idx1++;
			idx2--;
		}
	}

	/*
	 * Uses simulated annealing local search technique to find a solution to the TSP
	 * problem. Returns a new path if the cost is lower than the input, or returns
	 * the input path if a lower cost was able to be generated.
	 */
	public static int[] simulatedAnnealing(int[] input, DistanceTable dt, double temp, double decayRate) {
		int[] currPath = Arrays.copyOf(input, input.length);
		double currDist = getTourDist(currPath, dt);

		while (temp > 1) {
			int[] succPath = successor(currPath, dt);
			double succDist = getTourDist(succPath, dt);
			if (succDist < currDist) {
				// System.out.println("Better path is found");
				currPath = succPath;
				currDist = succDist;
			} else if (Math.random() < probFunction(currDist, succDist, temp)) {
				// System.out.println("Accepting longer path in sim annealing");
				currPath = succPath;
				currDist = succDist;
			}
			temp *= (1 - decayRate);
		}

		// only return new path from simulated annealing if it lowers total path cost
		if (currDist > getTourDist(input, dt))
			return input;
		else
			return currPath;
	}

	/*
	 * Generates a successor path to be used in conjunction with the simulated
	 * annealing algorithm Calculates two random indices in the path, and forms a
	 * new path with the subpath between the randomly generated indices reversed.
	 * After reversal, the 2-opt heuristic is applied.
	 */
	public static int[] successor(int[] input, DistanceTable dt) {
		int[] path = Arrays.copyOf(input, input.length);
		// randomly generate indices for reversing subpath
		Random rand = new Random();
		int idx1 = rand.nextInt(input.length);
		int idx2 = rand.nextInt(input.length);
		reverseSubPath(Math.min(idx1, idx2), Math.max(idx1, idx2), path);
		
		for (int i : dt.listVertices()) {
			if (!twoOpt(path, dt)) {
				break;
			}
		}
		return path;
	}

	/*
	 * Returns the probability simulated annealing accepts the successor cost when
	 * worse than current. curr - current path cost (shorter than generated
	 * successor cost) succ - successor path cost (longer than current path cost)
	 */
	private static double probFunction(double curr, double succ, double temp) {
		double delta = curr - succ; // should always be negative
		return Math.pow(Math.E, (delta / temp));
	}

}
