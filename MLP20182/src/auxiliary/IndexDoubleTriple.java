package auxiliary;

import algoMLP.Genetic;

public class IndexDoubleTriple implements Comparable <IndexDoubleTriple>{
	public double[] latency;
	public int[] tour;
	public double totalLatency;
	public IndexDoubleTriple(int[] newTour, double[] newLatency,double newTotalLatency) {
		this.tour = newTour;
		this.latency =newLatency;
		this.totalLatency=newTotalLatency;
		System.out.println("Dentro do index");
		for(int n=0;n<tour.length;n++) {

			System.out.print(" " + tour[n]);
		}
		System.out.println("");
	}

	public int compareTo(IndexDoubleTriple value){
	    if(this.totalLatency<value.totalLatency)
	          return -1;
	    else if(value.totalLatency<this.totalLatency)
	          return 1;
	    return 0;
	}
}
