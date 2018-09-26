package auxiliary;

public class Node implements Comparable <Node>{
	public double[] latency;
	public Integer[] path;
	public double totalLatency;
	public Node(Integer[] tourInt, double[] newLatency,double newTotalLatency) {
		this.path = tourInt;
		this.latency =newLatency;
		this.totalLatency=newTotalLatency;
	}

	public int compareTo(Node value){
	    if(this.totalLatency<value.totalLatency)
	          return -1;
	    else if(value.totalLatency<this.totalLatency)
	          return 1;
	    return 0;
	}
	
	public boolean myEquals(Node value){
		if (this.totalLatency ==value.totalLatency) {
			for (int i=0;i<this.path.length;i++) {
				if (this.path[i]!=value.path[i]) return false;
			}
			return true;
		}else return false;
	}
	
	
}
