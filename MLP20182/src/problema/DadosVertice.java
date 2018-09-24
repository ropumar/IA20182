package problema;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import distancias.FuncaoDistancia;
import grafo.Coordenada;
import grafo.Vertice;

public class DadosVertice extends TabelaDistancia {

	private final int size;
	private final FuncaoDistancia funcaoDistancia;
	private final Map<Integer, Vertice> vertices;

	public DadosVertice(int size, FuncaoDistancia funcaoDistancia) {
		super();
		this.size = size;
		this.funcaoDistancia = funcaoDistancia;
		vertices = new HashMap<Integer, Vertice>();
	}

	protected void add(Vertice vertice) {
		vertices.put(vertice.getID(), vertice);
	}

	public Vertice get(int vertex) {
		return vertices.get(vertex);
	}

	protected void remove(int vertex) {
		vertices.remove(vertex);
	}

	protected void clear() {
		vertices.clear();
	}

	public int size() {
		return vertices.size();
	}


	public int[] listVertices() {
		int index = 0;
		int[] result = new int[size];

		for (Vertice v : vertices.values()) {
			result[index++] = v.getID();
		}

		return result;
	}


	public double getDistanceBetween(int v1, int v2) {
		Vertice vertex1 = get(v1);
		Vertice vertex2 = get(v2);

		if (vertex1 == null) {
			throw new IllegalArgumentException("vertex " + v1 + " does not exist");
		}

		if (vertex2 == null) {
			throw new IllegalArgumentException("vertex " + v2 + " does not exist");
		}
		
		return funcaoDistancia.distance(get(v1), get(v2));
	}


	public void load(BufferedReader reader) throws IOException {
		for (int i = 0; i < size; i++) {
			String line = reader.readLine();
			String[] tokens = line.trim().split("\\s+");

			if (tokens.length != 2 + 1) {
				throw new IOException("invalid number of tokens for vertex entry");
			}

			int vertex = Integer.parseInt(tokens[0]);
			Coordenada coordenada = new Coordenada(Double.parseDouble(tokens[1]), Double.parseDouble(tokens[2]));

			add(new Vertice(vertex, coordenada));
		}
	}


	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (Vertice v : vertices.values()) {
			sb.append(v.toString());
			sb.append('\n');
		}

		return sb.toString();
	}
}
