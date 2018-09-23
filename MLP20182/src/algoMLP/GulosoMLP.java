package algoMLP;

import java.util.HashSet;
import problem.TSP.DistanceTable;
import problem.TSP.TSPInstance;

public class GulosoMLP {

	private TSPInstance tsp;
	private int[] cities;
	private double[] latencyArray;
	private int[] path;
	private DistanceTable dt;

	//Controi instancia do problema MLP e aplica algoritimo guloso

	public GulosoMLP(TSPInstance problem){
		this.tsp = problem;
		dt = tsp.getDistanceTable();
		cities = dt.listVertices();
		latencyArray = new double[cities.length];
		path = getTourGuloso(0, cities, dt, latencyArray);
	}

	// agoritimo guloso
	public int[] getTourGuloso(int start, int[] cities, DistanceTable dt, double[] latencyArray){
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

}
