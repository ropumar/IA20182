package algoMLP;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import problem.TSP.DistanceTable;
import problem.TSP.TSPInstance;

public class Genetic {
	private TSPInstance tsp;
	private int[] cities;
	private double[] latencyArray;
	private int[] path;
	private DistanceTable dt;
	private List<int[]> poolTour = new ArrayList<>();
	private List<double[]> poolLatency = new ArrayList<>();
	

	public Genetic(TSPInstance problem) throws Exception {
		this.tsp = problem;
		dt = tsp.getDistanceTable();
		cities = dt.listVertices();
		latencyArray = new double[cities.length];
		GenerateIndividuals(100);
		//path = GeneticSolve(50);
	}


//	public int[] GeneticSolve(int nGenS) {
//		
//	}
	
	private void GenerateIndividuals(int number){
		int peep[] = new int[cities.length];
		peep = cities;
		for (int i=0;i<number;i++) {
			poolTour.add(shuffleArray(peep));
			poolLatency.add(fillLatency(poolTour.get(i)));
			System.out.println(" ");
			for(int j=0;j<cities.length;j++) {
				int[] tour = poolTour.get(i);
				System.out.print("[" + tour[j] + " , "+ poolLatency.get(i)[j] + "] ");
			}
			System.out.println(" ");
			System.out.println(getTourLatency(poolTour.get(i),poolLatency.get(i)));
		}


	}


	// Implementing Fisher–Yates shuffle
	  static int[] shuffleArray(int[] ar)
	  {
	    // If running on Java 6 or older, use `new Random()` on RHS here
	    Random rnd = ThreadLocalRandom.current();
	    for (int i = ar.length - 1; i > 1; i--)
	    {
	      int index = rnd.nextInt(i + 1);
	      if (index==0) continue;
	      // Simple swap
	      int a = ar[index];
	      ar[index] = ar[i];
	      ar[i] = a;
	    }
	    return ar;
	  }
		// calcula distancia do percurso
		private double[] fillLatency(int[] tour) {
			double dist = 0;
			double[] latency = new double[tour.length];
			latency[0]= 0;
			for (int i = 1; i < tour.length - 1; i++) {
				dist = dt.getDistanceBetween(tour[i-1], tour[i]);
				latency[i]= latency[i-1]+dist;
			}
			return latency;
		}
		// calcula latencia total do percurso
		private static double getTourLatency(int[] tour, double[] latencyArray) {
			double totlatency=0;
			//System.out.println("Latencia");
			for (int i = 0; i < tour.length - 1; i++) {
				totlatency +=latencyArray[i];
			//	System.out.print(latencyArray[tour[i]-1] + " ");
			}
			System.out.println("");
			return totlatency;
		}
	
}
