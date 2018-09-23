import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import problem.TSP.TSPInstance;
import algoMLP.Genetic;
import algoMLP.GulosoMLP;


public class MLPMain {

	public static void main(String[] args) throws Exception {
		
		//adiciona problema MLP a lista de execucao
		ArrayList<String> problemasMLP = new ArrayList<String>();
		problemasMLP.add(new String("instancias/brazil58.tsp"));
		problemasMLP.add(new String("instancias/dantzig42.tsp"));
		problemasMLP.add(new String("instancias/gr120.tsp"));
		problemasMLP.add(new String("instancias/gr48.tsp"));
		problemasMLP.add(new String("instancias/pa561.tsp"));

		 // lista melhores solucao e tempos de execucao

		HashMap<String, Integer> bestLatency = new HashMap<String, Integer>();
		bestLatency.put("instancias/brazil58.tsp", new Integer(512361));
		bestLatency.put("instancias/dantzig42.tsp", new Integer(12528));
		bestLatency.put("instancias/gr120.tsp", new Integer(363454));
		bestLatency.put("instancias/gr48.tsp", new Integer(102378));
		bestLatency.put("instancias/pa561.tsp", new Integer(658870));
		
		HashMap<String, Float> avarageTimes = new HashMap<String, Float>();
		avarageTimes.put("instancias/brazil58.tsp", new Float(0.55));
		avarageTimes.put("instancias/dantzig42.tsp", new Float(0.17));
		avarageTimes.put("instancias/gr120.tsp", new Float(9.54));
		avarageTimes.put("instancias/gr48.tsp", new Float(0.31));
		avarageTimes.put("instancias/pa561.tsp", new Float(1155.32));

		
		for (String s : problemasMLP) {
			tempoGuloso(s, bestLatency,avarageTimes);
			tempoGenetic(s, bestLatency,avarageTimes);
		}

	}
	
	// chamada solucao algortimo guloso
	public static void tempoGuloso(String tspName, HashMap<String, Integer> bestLatency, HashMap<String, Float> averageTimes) throws Exception {
		double ms;
		long startTime, endTime, duration;
		GulosoMLP gulosoMLP;
		TSPInstance tsp = new TSPInstance(new File(tspName));
		System.out.println("Algoritimo Guloso" + "------------------"+ "Instancia: " + tspName );

		startTime = System.nanoTime();
		gulosoMLP = new GulosoMLP(tsp);
		endTime = System.nanoTime();
		duration=(endTime - startTime);
		ms = duration / 1000000.0;
		gulosoMLP.printSolution();
		System.out.println("Melhor latencia conhecida: " + bestLatency.get(tspName));
		System.out.println("Tempo de execucao do Algoritimo Guloso em ms: " + ms );
		System.out.println("Tempo medio para solucao de melhor latencia conhecida em ms: " + averageTimes.get(tspName)*1000 + "\n" );

	}
	
	// chamada solucao algortimo IA
	public static void tempoGenetic(String tspName, HashMap<String, Integer> bestLatency, HashMap<String, Float> averageTimes) throws Exception {
		double ms;
		long startTime, endTime, duration;
		Genetic geneticMLP;
		TSPInstance tsp = new TSPInstance(new File(tspName));
		System.out.println("Algoritimo genetico:"+ "------------------"+ "Instancia: " + tspName );
		
		startTime = System.nanoTime();	
		geneticMLP = new Genetic(tsp);
		endTime = System.nanoTime();
		duration = (endTime - startTime);
		ms = duration / 1000000.0;
		geneticMLP.printSolution();
		System.out.println("Melhor latencia conhecida: " + bestLatency.get(tspName));
		System.out.println("Tempo de execucao do Algortimo Genetico em ms: " + ms);
		System.out.println("Tempo medio para solucao de melhor latencia conhecida em ms: " + averageTimes.get(tspName)*1000 + "\n" );

	}

}
