package auxiliary;

public class Node implements Comparable <Node>{
	public double[] latency;
	public int[] path;
	public double totalLatency;
	public Node(int[] inputpath, double[] newLatency,double newTotalLatency) {
		this.path = inputpath;
		this.latency =newLatency;
		this.totalLatency=newTotalLatency;
		System.out.println("Dentro de Node tour");
		for(int n=0;n<path.length;n++) {

			System.out.print(" " + path[n]);
		}
		System.out.println("");
		System.out.println("Dentro de Node latency");
		for(int n=0;n<path.length;n++) {

			System.out.print(" " + latency[n]);
		}
		System.out.println("");
	}

	public int compareTo(Node value){
	    if(this.totalLatency<value.totalLatency)
	          return -1;
	    else if(value.totalLatency<this.totalLatency)
	          return 1;
	    return 0;
	}
}
