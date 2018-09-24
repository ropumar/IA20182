package problema;

import java.io.BufferedReader;
import java.io.IOException;

public abstract class TabelaDistancia {

	public TabelaDistancia() {
		super();
	}

	public abstract int[] listVertices();


	public abstract double getArestaEntreVertices(int id1, int id2);

	public abstract void load(BufferedReader reader) throws IOException;

}
