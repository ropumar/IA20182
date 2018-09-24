package problema;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class MatrizPesoAresta extends TabelaDistancia {
	
	private final int size;
	private final FormatPesoAresta format;
	private final double[][] matrix;
	
	public MatrizPesoAresta(int size, FormatPesoAresta format) {
		super();
		this.size = size;
		this.format = format;
		
		matrix = new double[size][size];
	}
	
	private void readNextLine(BufferedReader reader, Queue<Double> entries)
			throws IOException {
		String line = null;
		
		do {
			line = reader.readLine();
			
			if (line == null) {
				throw new EOFException("unexpectedly reached EOF");
			}
		} while ((line = line.trim()).isEmpty());
		
		String[] tokens = line.split("\\s+");
		
		for (int i = 0; i < tokens.length; i++) {
			entries.offer(Double.parseDouble(tokens[i]));
		}
	}


	public int[] listVertices() {
		int[] vertices = new int[size];
		
		for (int i = 1; i <= size; i++) {
			vertices[i-1] = i;
		}
		
		return vertices;
	}


	public double getArestaEntreVertices(int v1, int v2) {
		if ((v1 < 1) || (v1 > size)) {
			throw new IllegalArgumentException("vertex " + v1 + " does not exist");
		}
		
		if ((v2 < 1) || (v2 > size)) {
			throw new IllegalArgumentException("vertex " + v2 + " does not exist");
		}
		
		return matrix[v1-1][v2-1];
	}


	public void load(BufferedReader reader) throws IOException {
		Queue<Double> entries = new LinkedList<Double>();
		
		switch (format) {
		case UPPER_ROW:
			for (int i = 0; i < size-1; i++) {
				for (int j = i+1; j < size; j++) {
					if (entries.isEmpty()) {
						readNextLine(reader, entries);
					}

					matrix[i][j] = entries.poll();
					matrix[j][i] = matrix[i][j];
				}
			}
			
			break;
		case LOWER_DIAG_ROW:
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < i+1; j++) {
					if (entries.isEmpty()) {
						readNextLine(reader, entries);
					}

					matrix[i][j] = entries.poll();
					matrix[j][i] = matrix[i][j];
				}
			}
			
			break;
		default:
			throw new IOException();
		}
		
		if (!entries.isEmpty()) {
			throw new IOException();
		}
	}

}
