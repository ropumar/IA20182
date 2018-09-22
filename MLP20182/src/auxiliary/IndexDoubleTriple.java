package auxiliary;

import algoMLP.Genetic;

public class IndexDoubleTriple implements Comparable <IndexDoubleTriple>{
	public double[] latency;
	public int[] tour;
	public double totalLatency;
	public IndexDoubleTriple(int[] newTour, double[] newLatency,double newTotalLatency) {
		this.latency =newLatency;
		this.tour = newTour;
		this.totalLatency=newTotalLatency;
	}

	public int compareTo(IndexDoubleTriple value){
	    if(this.totalLatency<value.totalLatency)
	          return -1;
	    else if(value.totalLatency<this.totalLatency)
	          return 1;
	    return 0;
	}
}
