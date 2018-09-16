package algorithms.TSP;

import java.math.BigInteger;
import java.util.Arrays;

import problem.TSP.TSPInstance;

/**
 * Brute force implementation of TSP problem.
 * Attempts to solve problem by finding distance of every
 * combination of vertices which generates a candidate solution,
 * resolving the least cost path out of entire set.
 */
public class BruteForce {

	long startTime;
	long endTime;
	long deltaTime;
	long totalTime;

	private TSPInstance tsp;
	private int[] solutionPath;
	private int[] possiblePath;
	private double solutionDistance;
	private BigInteger permutation = BigInteger.ONE;
	private BigInteger fac16 = new BigInteger("20922790000000");

	public BruteForce(TSPInstance t) {
		this.tsp = t;
		solutionPath = new int[this.tsp.getDimension()];
		possiblePath = Arrays.copyOf(tsp.getDistanceTable().listVertices(), tsp.getDimension());
		solutionDistance = Integer.MAX_VALUE;
		solve();
		System.out.println("solution distance is " + solutionDistance);
		print(solutionPath);
	}

	public void solve() {
		startTime = System.currentTimeMillis();
		perm(1);
		endTime = System.currentTimeMillis();
		totalTime = endTime - startTime;
		System.out.println("time: " + totalTime + " milliseconds");
	}

	private void perm(int index) {

		if (index >= possiblePath.length - 1) {
			double tempDist = findDistance(possiblePath);
			permutation = permutation.add(BigInteger.ONE);
			
			if (tempDist < solutionDistance) {
				solutionDistance = tempDist;
				solutionPath = Arrays.copyOf(possiblePath, possiblePath.length);
			}

			if (permutation.mod(new BigInteger("1000000")) == BigInteger.ZERO) {
				long tempTime = System.currentTimeMillis();
				deltaTime = (tempTime - startTime) / 1000;

				System.out.println("Total distance is " + solutionDistance);
				print(solutionPath);
				System.out.println(permutation + " permutations in " + deltaTime + " seconds");
				System.out.println(permutation.divide(fac16) + "% complete \n\n");
			}

			

			return;
		}

		for (int index2 = index; index2 < possiblePath.length; index2++) {
			swap(index2, index);
			perm(index + 1);
			swap(index2, index);
		}
	}

	private void swap(int a, int b) {
		int temp = possiblePath[a];
		possiblePath[a] = possiblePath[b];
		possiblePath[b] = temp;

	}

	private double findDistance(int[] path) {
		double total = 0;
		for (int index = 0; index < path.length - 1; index++) {
			total += tsp.getDistanceTable().getDistanceBetween(path[index], path[index + 1]);
		}

		total += tsp.getDistanceTable().getDistanceBetween(path[path.length - 1], 1);
		return total;
	}

	private void print(int[] arr) {

		System.out.println(Arrays.toString(arr));
	}

}
