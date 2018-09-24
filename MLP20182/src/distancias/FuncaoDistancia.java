package distancias;
import grafo.Coordenada;
import grafo.Vertice;

public abstract class FuncaoDistancia {
	
	
	public double distancia(Vertice v1, Vertice v2) {
		Coordenada c1 = v1.getCoord();
		Coordenada c2 = v2.getCoord();
		
		return distancia(c1, c2);
	}
	
	public abstract int distancia(Coordenada c1, Coordenada c2);
	
}
