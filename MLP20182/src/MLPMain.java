import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;


import algorithms.TSP.ImprovedAlgorithm;
import problem.TSP.TSPInstance;
import algoMLP.GreedyStandard;


public class MLPMain {

	public static void main(String[] args) throws Exception {
		
		//Add MLP problems to execution list
		ArrayList<String> tspProblems = new ArrayList<String>();
		tspProblems.add(new String("instancias/brazil58.tsp"));
		tspProblems.add(new String("instancias/dantzig42.tsp"));
		tspProblems.add(new String("instancias/gr120.tsp"));
		tspProblems.add(new String("instancias/gr48.tsp"));
		tspProblems.add(new String("instancias/pa561.tsp"));

		 // List best known solutions and average times for problems

		HashMap<String, Integer> bestPaths = new HashMap<String, Integer>();
		bestPaths.put("instancias/brazil58.tsp", new Integer(512361));
		bestPaths.put("instancias/dantzig42.tsp", new Integer(12528));
		bestPaths.put("instancias/gr120.tsp", new Integer(363454));
		bestPaths.put("instancias/gr48.tsp", new Integer(102378));
		bestPaths.put("instancias/pa561.tsp", new Integer(658870));
		
		HashMap<String, Float> avarageTimes = new HashMap<String, Float>();
		avarageTimes.put("instancias/brazil58.tsp", new Float(0.55));
		avarageTimes.put("instancias/dantzig42.tsp", new Float(0.17));
		avarageTimes.put("instancias/gr120.tsp", new Float(9.54));
		avarageTimes.put("instancias/gr48.tsp", new Float(0.31));
		avarageTimes.put("instancias/pa561.tsp", new Float(1155.32));

		
		for (String s : tspProblems) {
			timeGreedy(s, bestPaths,avarageTimes);
			//timeIA(s, bestPaths, avarageTimes);
		}

	}
	
	/*
	 * Solves a TSP instance with the greedy Standard nearest neighbor approach and prints
	 * the path, time to generate a path, and the best known path.
	 */
	public static void timeGreedy(String tspName, HashMap<String, Integer> bestCost, HashMap<String, Float> avarageTimes) throws Exception {
		long startTime, endTime, duration;
		double ms;
		GreedyStandard greedyStandard;
		TSPInstance tsp = new TSPInstance(new File(tspName));
		System.out.println("Solving TSP instance: " + tspName + "-------------------------------");

		System.out.println("Greedy Standard Nearest Neighbor Algorithm:");
		startTime = System.nanoTime();
		greedyStandard = new GreedyStandard(tsp);
		endTime = System.nanoTime();
		duration=(endTime - startTime);
		ms = duration / 1000000.0;
		greedyStandard.printSolution();
		System.out.println("Greedy Standard time in ms: " + ms );
		System.out.println("Average time: " + avarageTimes.get(tspName));
		System.out.println("Best known latency for path: " + bestCost.get(tspName)+ "\n");
	}
	
	
	public static void timeIA(String tspName, HashMap<String, Integer> bestCost, HashMap<String, Float> avarageTimes) throws Exception {
		long startTime, endTime, duration;
		double ms;

		TSPInstance tsp = new TSPInstance(new File(tspName));
		System.out.println("Solving TSP instance: " + tspName + "-------------------------------");

		System.out.println("Improved Algorithm:");
		startTime = System.nanoTime();	
		ImprovedAlgorithm ia = new ImprovedAlgorithm(tsp);
		endTime = System.nanoTime();
		duration = (endTime - startTime);
		ms = duration / 1000000.0;
		System.out.println("Improved Algorithm time in ms: " + ms);
		System.out.println("Average Time: " + avarageTimes.get(tspName) );
		System.out.println("Best known path cost: " + bestCost.get(tspName)+ "\n" );
	}

}
