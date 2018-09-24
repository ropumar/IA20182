package distancias;
import grafo.Coordenada;
import grafo.Vertice;

public abstract class FuncaoDistancia {
	
	
	public double distance(Vertice v1, Vertice v2) {
		Coordenada c1 = v1.getCoord();
		Coordenada c2 = v2.getCoord();
		
		return distance(c1, c2);
	}
	
	public abstract int distance(Coordenada c1, Coordenada c2);
	
}
