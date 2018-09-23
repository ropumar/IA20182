package algoMLP;

import java.util.*;

import auxiliary.Node;
import problem.TSP.DistanceTable;
import problem.TSP.TSPInstance;

public class Genetic {
	private TSPInstance tsp;
	private int[] cities;
	private double[] latencyArray;
	private Integer[] path;
	private DistanceTable dt;
	private List<Node> poolData = new ArrayList<>();
	private List<Node> ChildData = new ArrayList<>();
	private int[] child1;
	private int[] child2;
	private Random rand = new Random();
	private int numberOfGens;
	public Genetic(TSPInstance problem) throws Exception {
		this.tsp = problem;
		dt = tsp.getDistanceTable();
		cities = dt.listVertices();
		latencyArray = new double[cities.length];
		child1= new int[cities.length];
		child2= new int[cities.length];
		numberOfGens=0;
		path = GeneticSolve(40);
		latencyArray=fillLatency(path);
//		if (distinctValues(path)) {
//			System.out.println("O caminho possui todos os nos unicos entao deve estar CERTO");
//		}
		
	}


	public Integer[] GeneticSolve(int nGen) {
		GenerateIndividuals(50);
		for (int i=0; i<nGen;i++) {
			numberOfGens++;
			//System.out.println("Generation: " + i +" Numbers of individuals: " + poolData.size() + " best individual latency: "+poolData.get(0).totalLatency );
			sortRemoveDuplicates();
			selectIndividuals(20);
			procriation();
			sortRemoveDuplicates();
			selectIndividuals(3);//keep only best parents for next generation, killing others	
			for (int j=0;j<ChildData.size();j++) {
				poolData.add(ChildData.get(j));	//new childs are adults added to genetic pool
			}
			ChildData.clear(); //childs are adults, remove then from child list
		}
		sortRemoveDuplicates();
//		selectIndividuals(3);
//		printPool(poolData);
		return poolData.get(0).path;
	}
	
	private void GenerateIndividuals(int number){
		int peep[] = new int[cities.length];
		peep = cities.clone();
		//System.out.println("Generated Random individuals ");
		for (int i=0;i<number;i++) {
			int[] poolTourTemp = shuffleArray(peep);
			Integer[] TourTempInteger = toObject(poolTourTemp);
			poolData.add(fillData(TourTempInteger));
		}
		int[] gulosoPath=getTourGuloso(0);
		Integer[] gulosoInteger = toObject(gulosoPath);
		poolData.add(fillData(gulosoInteger));
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
		double totalLatencythis = getTourLatency(tour,latency);
		return new Node(tour,latency,totalLatencythis);
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
		Collections.sort(poolData);
		int j=0;
		while (j<poolData.size()-1) 
		{
			if (poolData.get(j).myEquals(poolData.get(j+1))){
				poolData.remove(j+1);
			}else j++;
		}
	}

	private void PMXsex(int[] parent1, int[] parent2) {
		int[] replacement1 = new int[cities.length];
		int[] replacement2 = new int[cities.length];
		Arrays.fill(child1,0);
		Arrays.fill(child2,0);
		Arrays.fill(replacement1, -1);
		Arrays.fill(replacement2, -1);
        
        int bound = (cities.length) - 1;

        //select segment cutofpoints
        int number1 = rand.nextInt(bound-1);
        int number2 = rand.nextInt(bound);
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
		int mutationrate=5;
		if(numberOfGens>20) {
			mutationrate=3;
		} //because is better to do more mutation in the end
		boolean prob = rand.nextInt(mutationrate)==0;
	    if(!prob) {
			for( int i=0;i<(int)rand.nextInt(cities.length)/3;i++) {
//				System.out.println("Mutate "+ i);
				mutate(child1);
				mutate(child2);
			}
	    }

		Integer[] childInt1 = toObject(child1);
		Integer[] childInt2 = toObject(child2);
		ChildData.add(fillData(childInt1));
		ChildData.add(fillData(childInt2));
	}
	
	private void mutate(int[] xmen) {

		//System.out.println("Mutate Actually happened");
        int bound = (xmen.length) - 1;
        int i = rand.nextInt(bound-2)+1;
        int j = rand.nextInt(bound-1)+1;
        if(i == j){
        	j = i+1;
        }
		int temp=xmen[i];
		xmen[i]=xmen[j];
		xmen[j]=temp;
	}

	
	private void procriation() {
		for (int i=0;i<poolData.size();i++) {
			for(int j=i+1;j<poolData.size();j++) {
				if(i!=j) {
					int[] parent1 = toPrimitive(poolData.get(i).path);
					int[] parent2 = toPrimitive(poolData.get(j).path);
//					int[] parent1=generatesPermutation(generatesInversion(i));
//					int[] parent2=generatesPermutation(generatesInversion(j));
					PMXsex(parent1,parent2);
				}
			}
		}
	}

	// Fisher–Yates shuffle
	  private int[] shuffleArray(int[] ar)
	  {
	    for (int i = ar.length - 1; i > 1; i--)
	    {
	      int index = rand.nextInt(i + 1);
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
			for (int i = 1; i < tourTempInteger.length; i++) {
				dist = dt.getDistanceBetween(tourTempInteger[i-1], tourTempInteger[i]);
				latency[tourTempInteger[i]-1]=latency[tourTempInteger[i-1]-1] + dist;
			}
			return latency;
		}
		// calcula latencia total do percurso
		private static double getTourLatency(Integer[] tourTempInteger, double[] latencythis) {
			double totlatency=0;
			for (int i = 0; i < tourTempInteger.length; i++) {
				totlatency +=latencythis[i];
			}
			return totlatency;
		}
		// agoritimo guloso
		public int[] getTourGuloso(int start){
			HashSet<Integer> unvisited = new HashSet<Integer>();
			for (int i : cities)
				unvisited.add(new Integer(i));
			unvisited.remove(new Integer(cities[start]));
			int[] tour = new int[cities.length];
			tour[0] = cities[start];

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
				unvisited.remove(new Integer(nextCity));
			}
			return tour;
		}
		
		
		// this function was not used, because it didnt yield results expected
		//inverts parent DNA permutation as in http://user.ceng.metu.edu.tr/~ucoluk/research/publications/tspnew.pdf
		private int[] generatesInversion(int index) {
			int[] inversion = new int[cities.length];
			int[] perm = toPrimitive(poolData.get(index).path);
			int m=0;
			for (int i=0;i<cities.length;i++) {
				inversion[i]=1;
				m=0;
				while (perm[m]!=i+1) {
					if (perm[m]>i+1) {
						inversion[i]++;
					}
					m++;
				}
			}
			return inversion;
		}
		// this function was not used, because it didnt yield results expected
		//generates parent DNA permutation as inhttp://user.ceng.metu.edu.tr/~ucoluk/research/publications/tspnew.pdf
		private int[] generatesPermutation(int[] inversion) {
			int[] perm = new int[cities.length];
			int[] pos = new int[cities.length];
			for (int i=inversion.length-1;i>-1;i--) {
				for(int m=i;m<inversion.length;m++) {
					if (pos[m]>=inversion[i]) {
						pos[m]=pos[m]+1;
					}
				}
				pos[i]=inversion[i];
			}
			for (int i=0;i<cities.length;i++) {
				perm[pos[i]-1]=i+1;
			}
			return perm;
		}


		//imprime percurso, latencia e distancia
		public void printSolution() {
			System.out.print("Percurso achado: ");
			for (int i = 0; i < path.length; i++) {
				System.out.print(path[i] + " ");
			}
			System.out.println(path[0]);
//			System.out.print("Latencia achada: ");
//			for (int i = 0; i < path.length - 1; i++) {
//				System.out.print(latencyArray[path[i]-1] + " ");
//			}
//			System.out.println("");
			System.out.println("Latencia total do problema MLP por Algortimo Genetico: " + getTourLatency(path,latencyArray));

		}
		public static boolean distinctValues(Integer[] arr){
		    for (int i = 0;i < arr.length-1; i++) {
		        for (int j = i+1; j < arr.length; j++) {
		             if (arr[i] == arr[j]) {
		                 return false;
		             }
		        }
		    }              
		    return true;          
		}
		
}
