import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import algorithms.TSP.BruteForce;
import algorithms.TSP.GreedyNN;
import algorithms.TSP.ImprovedAlgorithm;
import problem.TSP.TSPInstance;

public class MLPMain {

	public static void main(String[] args) throws Exception {

		////////////////////////////////////////////////////////////////////////
		// this will test all files in instancias folder to make sure they can be read
		// comment out the distance table to save space in console
		// comment out writeDataToFile if matrices are already stored locally
		////////////////////////////////////////////////////////////////////////

		File dir = new File("instancias/");
		File[] directoryListing = dir.listFiles();
		int numberOfFiles = 0;
		
		boolean showProblems = false; // set to false to skip

		if (directoryListing != null && showProblems) {
			for (File child : directoryListing) {
				if (child.getName().charAt(0) != '.') {

					System.out.println("**************************************");
					TSPInstance t = new TSPInstance(child);
					System.out.println("Name: " + t.getName());
					System.out.println("Comment: " + t.getComment());
					System.out.println("Dimension: " + t.getDimension());
					System.out.println("Edge_Weight_Format: " + t.getEdgeWeightFormat());
					System.out.println("Edge_Weight_Type: " + t.getEdgeWeightType());
					System.out.println(t.getDistanceTable());
					writeDataToFile(t);
					System.out.println("**************************************");

					numberOfFiles++;
				}
			}
			
			System.out.println("Total number of files: " + numberOfFiles);
		}

		

		////////////////////////////////////////////////////////////////////////
		// testing individual files
		////////////////////////////////////////////////////////////////////////

		/*
		 * Add selected TSP Problems and their best known path costs
		 */

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

		ArrayList<String> tspProblems = new ArrayList<String>();
		tspProblems.add(new String("instancias/brazil58.tsp"));
		tspProblems.add(new String("instancias/dantzig42.tsp"));
		tspProblems.add(new String("instancias/gr120.tsp"));
		tspProblems.add(new String("instancias/gr48.tsp"));
		tspProblems.add(new String("instancias/pa561.tsp"));
		
		for (String s : tspProblems) {
//			BruteForce bf = new BruteForce(t);
			timeNN(s, bestPaths,avarageTimes);
//			System.out.println("Improved Algorithm: \n");
			timeIA(s, bestPaths, avarageTimes);
		}

	}

	/*
	 * Solves a TSP instance with the greedy nearest neighbor approach and prints
	 * the path, time to generate a path, and the best known path.
	 */
	public static void timeNN(String tspName, HashMap<String, Integer> bestCost, HashMap<String, Float> avarageTimes) throws Exception {
		long startTime, endTime, duration;
		double ms;

		TSPInstance tsp = new TSPInstance(new File(tspName));
		System.out.println("Solving TSP instance: " + tspName + "-------------------------------");

		System.out.println("Greedy Nearest Neighbor Algorithm:");
		startTime = System.nanoTime();
		GreedyNN tspa = new GreedyNN(tsp);
		endTime = System.nanoTime();
		duration = (endTime - startTime);
		ms = duration / 1000000.0;
		System.out.println("Greedy NN time in ms: " + ms );
		System.out.println("Avarage time: " + avarageTimes.get(tspName));
		System.out.println("Best known path cost: " + bestCost.get(tspName)+ "\n");

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
		System.out.println("Avarage Time: " + avarageTimes.get(tspName) );
		System.out.println("Best known path cost: " + bestCost.get(tspName)+ "\n" );
	}
	

	/*
	 * Writes data from TSP problem for testing and visual purposes
	 */
	private static void writeDataToFile(TSPInstance t) throws FileNotFoundException {
		PrintWriter out = new PrintWriter("matrizes/" + t.getName() + "_matrix.txt");
		out.print(t.getDistanceTable());
		out.close();
	}

}
