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
		path = getTourGuloso();
	}

	// agoritimo guloso
	public int[] getTourGuloso(){
		HashSet<Integer> unvisited = new HashSet<Integer>();
		for (int i : cities)
			unvisited.add(new Integer(i));
		unvisited.remove(new Integer(cities[0]));
		int[] tour = new int[cities.length];
		tour[0] = cities[0];
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
	private  double getTourDist(int[] tour) {
		double dist = 0;
		for (int i = 0; i < tour.length; i++) {
			dist += dt.getDistanceBetween(tour[i], tour[i + 1]);
		}
		dist += dt.getDistanceBetween(tour[tour.length - 1], tour[0]);
		return dist;
	}
	
	// calcula latencia total do percurso
	private static double getTourLatency(int[] tour, double[] latencyArray) {
		double totlatency=0;
		for (int i = 0; i < tour.length; i++) {
			totlatency +=latencyArray[i];
		}
		return totlatency;
	}

	//imprime percurso, latencia e distancia
	public void printSolution() {
		System.out.print("Percurso achado: ");
		for (int i = 0; i < path.length; i++) {
			System.out.print(path[i] + " ");
		}
		System.out.println(path[0]);
		System.out.println("Latencia total do problema MLP por Algortimo guloso: " + getTourLatency(path,latencyArray));

	}

}
