package grafo;

/**
 * Definition of vertex
 */
public class Vertice {
	
	private final int ID;
	private final Coordenada coordenada;
	
	public Vertice(int v, Coordenada c) {
		super();
		this.ID = v;
		this.coordenada = c;
	}
	
	public int getID() {
		return this.ID;
	}
	
	public Coordenada getCoord() {
		return this.coordenada;
	}
	

	public String toString() {
		return this.ID + ": " + this.coordenada.toString();
	}
	
	
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (obj == null) {
			return false;
		}
		
		if (getClass() != obj.getClass()) {
			return false;
		}
		
		Vertice other = (Vertice) obj;
		
		if (ID != other.ID) {
			return false;
		}
		
		if (!coordenada.equals(other.coordenada)) {
			return false;
		}
		
		return true;
	}
	
}
