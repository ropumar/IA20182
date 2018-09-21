package algoMLP;

import java.util.HashSet;
import problem.TSP.DistanceTable;
import problem.TSP.TSPInstance;

public class GulosoMLPimproved {

	private TSPInstance tsp;
	private int[] cities;
	private double[] latencyArray;
	private int[] path;
	private DistanceTable dt;

	//Controi instancia do problema MLP e aplica algoritimo guloso

	public GulosoMLPimproved(TSPInstance problem) throws Exception {
		this.tsp = problem;
		dt = tsp.getDistanceTable();
		cities = dt.listVertices();
		latencyArray = new double[cities.length];
		path = getGreedyTour(0, cities, latencyArray);
		solve();
	}

	public void solve() {
		for (int k : cities) {
			if (!twoOpt(path)) {
				break;
			}
		}	
		for(int i=1;i<path.length;i++) {
			latencyArray[path[i]-1]= latencyArray[path[i-1]-1]+dt.getDistanceBetween(path[i-1],path[i]);
		}
	}
	// agoritimo guloso
	public int[] getGreedyTour(int start, int[] cities, double[] latencyArray) throws Exception {
		if (start > cities.length)
			throw new Exception();
		HashSet<Integer> unvisited = new HashSet<Integer>();
		for (int i : cities)
			unvisited.add(new Integer(i));
		unvisited.remove(new Integer(cities[start]));
		int[] tour = new int[cities.length];
		tour[0] = cities[start];
		latencyArray[0]=0f;

		for (int i = 1; i < tour.length; i++) {
			int predecessor = tour[i - 1];
			double minDist = Double.MAX_VALUE;
			int nextCity = -1;
			// vizinho mais proximo
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

	// calcula distancia do percurso
	private static double getTourDist(int[] tour, DistanceTable dt) {
		double dist = 0;
		for (int i = 0; i < tour.length - 1; i++) {
			dist += dt.getDistanceBetween(tour[i], tour[i + 1]);
		}
		dist += dt.getDistanceBetween(tour[tour.length - 1], tour[0]);
		return dist;
	}
	
	// calcula latencia total do percurso
	private static double getTourLatency(int[] tour, double[] latencyArray) {
		double totlatency=0;
		System.out.println("Latencia");
		for (int i = 0; i < tour.length - 1; i++) {
			totlatency +=latencyArray[i];
			System.out.print(latencyArray[tour[i]-1] + " ");
		}
		System.out.println("");
		return totlatency;
	}

	//imprime percurso, latencia e distancia
	private void printTour(int[] tour, DistanceTable dt, double[] latencyArray) {
		System.out.print("Percurso achado: ");
		for (int i = 0; i < tour.length; i++) {
			System.out.print(tour[i] + " ");
		}
		System.out.println(tour[0]);
		
		System.out.println("Latencia total do problema MLP por Algortimo guloso: " + getTourLatency(tour,latencyArray));
		System.out.println("Distancia total do problema TSP por Algortimo guloso " + getTourDist(tour, dt));

	}
	
	// print publico
	public void printSolution() {
		printTour(path, dt, latencyArray);
	}

	/*
	 * Returns true if a 2-opt improvement exists in the tour and has been executed.
	 */
	private boolean twoOpt(int[] tour) {

		for (int i = 0; i < tour.length - 3; i++) {
			for (int j = i + 2; j < tour.length - 1; j++) {
//				double old1= dt.getDistanceBetween(tour[i], tour[i + 1]);
//				double old2=dt.getDistanceBetween(tour[j], tour[j + 1]);
//				double new1=dt.getDistanceBetween(tour[i], tour[j]);
//				double new2=dt.getDistanceBetween(tour[i + 1], tour[j + 1]);
//				double currDist = old1+old2;
//				double newDist = new1+new2;
				
				double currDist = dt.getDistanceBetween(tour[i], tour[i + 1])
						+ dt.getDistanceBetween(tour[j], tour[j + 1]);
				double newDist = dt.getDistanceBetween(tour[i], tour[j])
						+ dt.getDistanceBetween(tour[i + 1], tour[j + 1]);
				if (currDist > newDist) {
					double changeDist=newDist-currDist;
					reverseSubPath(i + 1, j, tour, changeDist);
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
	private  void reverseSubPath(int idx1, int idx2, int[] path, double changeDist) {
		while (idx1 < idx2) {
			int temp = path[idx1];
			path[idx1] = path[idx2];
			path[idx2] = temp;
			idx1++;
			idx2--;
		}

	}
}
