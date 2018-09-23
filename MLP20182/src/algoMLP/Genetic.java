package algoMLP;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import auxiliary.Node;
import problem.TSP.DistanceTable;
import problem.TSP.TSPInstance;

public class Genetic {
	private TSPInstance tsp;
	private int[] cities;
	private double[] latencyArray;
	private int[] path;
	private DistanceTable dt;
	private List<Node> poolData = new ArrayList<>();
	private List<Node> ChildData = new ArrayList<>();
	private int[] child1;
	private int[] child2;
	

	public Genetic(TSPInstance problem) throws Exception {
		this.tsp = problem;
		dt = tsp.getDistanceTable();
		cities = dt.listVertices();
		latencyArray = new double[cities.length];
		child1= new int[cities.length];
		child2= new int[cities.length];
		path = GeneticSolve(30);
		//printPool();
	}


	public int[] GeneticSolve(int nGen) {
		GenerateIndividuals(50);
		for (int i=0; i<nGen;i++) {
			sortRemoveDuplicates();
			System.out.println("Generation: " + i +" Numbers of individuals: " + poolData.size() + " best individual latency: "+poolData.get(0).totalLatency );
			selectIndividuals(10);
			//printPool(poolData);
			procriation();
			poolData.clear();
			for (int j=0;j<ChildData.size();j++) {
				poolData.add(ChildData.get(j));
			}
			ChildData.clear();
		}
		selectIndividuals(3);
		printPool(poolData);
		int[] bestPath = toPrimitive(poolData.get(0).path);
		return bestPath;
	}
	
	private void GenerateIndividuals(int number){
		int peep[] = new int[cities.length];
		peep = cities;
		System.out.println("Generated Random individuals ");
		for (int i=0;i<number;i++) {
			int[] poolTourTemp = shuffleArray(peep);
			Integer[] TourTempInteger = toObject(poolTourTemp);
			poolData.add(fillData(TourTempInteger));
		}
	}
	
	// Convert int[] to Integer[]
	public static Integer[] toObject(int[] intArray) {

		Integer[] result = new Integer[intArray.length];
		for (int i = 0; i < intArray.length; i++) {
			result[i] = Integer.valueOf(intArray[i]);
		}
		return result;

	}
	
	// Convert Integer[] to int[]
	public static int[] toPrimitive(Integer[] IntegerArray) {

		int[] result = new int[IntegerArray.length];
		for (int i = 0; i < IntegerArray.length; i++) {
			result[i] = IntegerArray[i].intValue();
		}
		return result;
	}
	
	private Node fillData(Integer[] tour) {
		double [] latency = fillLatency(tour);
		double totLatency = getTourLatency(tour,latency);
		return new Node(tour,latency,totLatency);
	}
	
	private void printPool(List<Node> poolData) {
		System.out.println(" ");
		for (int i=0;i<poolData.size();i++) {
			for(int j=0;j<cities.length;j++) {

				System.out.print("[" + poolData.get(i).path[j] + " , "+ poolData.get(i).latency[j] + "] ");
			}
			System.out.println(" ");
			System.out.println("Total Latency:" + poolData.get(i).totalLatency + " | pool index:" +i);
		}
	}
	
	
	private void selectIndividuals(int nsurvivors){
		int lastindex=poolData.size();
		for (int i=nsurvivors;i<lastindex;i++) {
			poolData.remove(nsurvivors);
		}
	}
	
	private void sortRemoveDuplicates(){
		System.out.println("Selected individuals ");
		Collections.sort(poolData);
		int j=0;
		while (j<poolData.size()-1) 
		{
			if (poolData.get(j).myEquals(poolData.get(j+1))){
				poolData.remove(j+1);
			}else j++;
		}
	}

	private void PMXsex(int indexparent1, int indexparent2) {
		int[] parent1 = toPrimitive(poolData.get(indexparent1).path);
		int[] parent2 = toPrimitive(poolData.get(indexparent2).path);
//		System.out.println("Parent1");
//		for(int n=0;n<cities.length;n++) {
//
//			System.out.print(" " + parent1[n]);
//		}
//		System.out.println("");
//		System.out.println("Parent2");
//		for(int n=0;n<cities.length;n++) {
//
//			System.out.print(" " + parent2[n]);
//		}
//		System.out.println("");
		int[] replacement1 = new int[cities.length];
		int[] replacement2 = new int[cities.length];
		Arrays.fill(child1,0);
		Arrays.fill(child2,0);
		Arrays.fill(replacement1, -1);
		Arrays.fill(replacement2, -1);
        Random firstRNum  = new Random();
        Random secondRNum = new Random();
        
        int bound = (cities.length) - 1;

        //select segment cutofpoints
        int number1 = firstRNum.nextInt(bound-1);
        int number2 = secondRNum.nextInt(bound);
        if(number1 == number2){
        	number2 = number1+1;
        }
        final int start = Math.min(number1, number2);
        final int end = Math.max(number1, number2);
        //copy segment to child
   
		for (int k = start; k <= end; k++) {
            child1[k]=parent2[k];
            child2[k]=parent1[k];
			replacement1[parent2[k]-1] = parent1[k]-1;
			replacement2[parent1[k]-1] = parent2[k]-1;
        }
//		System.out.println("CHILD start a end");
//		for(int n=0;n<cities.length;n++) {
//
//			System.out.print(" " + child1[n]);
//		}
//		System.out.println("");
//		System.out.println("REPLA1");
//		for(int n=0;n<cities.length;n++) {
//
//			System.out.print(" " + replacement1[n]);
//		}
//		System.out.println("");
//        System.out.println("CHILD1 DEPOIS");
		// fill in remaining slots with replacements
		for (int i = 0; i < cities.length; i++) {
			if ((i < start) || (i > end)) {
				int n1 = parent1[i]-1;
				int m1 = replacement1[n1];

				int n2 = parent2[i]-1;
				int m2 = replacement2[n2];

				while (m1 != -1) {
					n1 = m1;
					m1 = replacement1[m1];
				}

				while (m2 != -1) {
					n2 = m2;
					m2 = replacement2[m2];
				}

				child1[i] = n1+1;
				child2[i] = n2+1;
			}
		}
//		System.out.println("");
//		System.out.println("CHILD1 listado");
//		for(int n=0;n<cities.length;n++) {
//
//			System.out.print(" " + child1[n]);
//		}
//		System.out.println("");
//		System.out.println("CHILD2");
//		for(int n=0;n<cities.length;n++) {
//
//			System.out.print(" " + child2[n]);
//		}
		Integer[] childInt1 = toObject(child1);
		Integer[] childInt2 = toObject(child2);
		ChildData.add(fillData(childInt1));
		ChildData.add(fillData(childInt2));
	}
	
	
//	private int[] generatesInversion(int index) {
//		int[] inversion = new int[cities.length];
//		int[] parent = toPrimitive(poolData.get(index).path);
//		int m=0;
//		for (int i=1;i<cities.length;i++) {
//			inversion[i-1]=0;
//			m=1;
//			while (parent[m-1]!=i) {
//				if (parent[m-1]>i) {
//					inversion[i-1]=inversion[i-1]+1;
//				}
//				m++;
//			}
//		}
//		return inversion;
//	}
//	
//	private int[] generatesChild(int[] inversion, int index) {
//		int[] child = new int[cities.length];
//		int[] pos = new int[cities.length];
//		for (int i=cities.length;i>0;i--) {
//			for(int m=i+1;i<cities.length;i++) {
//				if (pos[m-1]>inversion[i-1]+1) {
//					pos[m-1]=pos[m-1]+1;
//				}
//				pos[i-1]=inversion[i-1]+1;
//			}
//		}
//		for (int i=1;i<cities.length;i++) {
//			child[pos[i-1]-1]=i;
//		}
//		return child;
//	}
	
	private void procriation() {
		System.out.println("procriation start");
		int k=0;
		for (int i=0;i<poolData.size();i++) {
			for(int j=0;j<poolData.size();j++) {
				if(i!=j) {
					PMXsex(i,j);
//					System.out.println("TESTE CHILD no proc ");
//					for(int n=0;n<cities.length;n++) {
//
//						System.out.print(" " + child1[n]);
//					}
//					System.out.println("");
					//System.out.println("procriation number "+ k +"Total Latency:" + ChildData.get(k).totalLatency);
					k++;
				}
			}
		}
	}

	// Fisher–Yates shuffle
	  static int[] shuffleArray(int[] ar)
	  {
	    Random rnd = ThreadLocalRandom.current();
	    for (int i = ar.length - 1; i > 1; i--)
	    {
	      int index = rnd.nextInt(i + 1);
	      if (index==0) continue; //pois o primeiro noh eh sempre o noh 1
	      int a = ar[index];
	      ar[index] = ar[i];
	      ar[i] = a;
	    }
	    return ar;
	  }
		// calcula distancia do percurso
		private double[] fillLatency(Integer[] tourTempInteger) {
			double dist = 0;
			double[] latency = new double[tourTempInteger.length];
			latency[0]= 0;
			for (int i = 1; i < tourTempInteger.length - 1; i++) {
				dist = dt.getDistanceBetween(tourTempInteger[i-1], tourTempInteger[i]);
				latency[i]= latency[i-1]+dist;
			}
			return latency;
		}
		// calcula latencia total do percurso
		private static double getTourLatency(Integer[] tourTempInteger, double[] latencyArray) {
			double totlatency=0;
			//System.out.println("Latencia");
			for (int i = 0; i < tourTempInteger.length - 1; i++) {
				totlatency +=latencyArray[i];
			//	System.out.print(latencyArray[tour[i]-1] + " ");
			}
//			System.out.println("");
			return totlatency;
		}
	
}
