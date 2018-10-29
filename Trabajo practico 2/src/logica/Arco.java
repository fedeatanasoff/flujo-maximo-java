package logica;

public class Arco {
	private Nodo desde;
	private Nodo hasta;
	private int capacidad;
	private int flujo;

	public Arco(Nodo desde, Nodo hasta, int capacidad) {
		this.desde = desde;
		this.hasta = hasta;
		this.capacidad = capacidad;
		desde.agregarVecino(hasta);
		flujo = 0;
	}

	public Nodo getDesde() {
		return desde;
	}

	public Nodo getHasta() {
		return hasta;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public int getFlujo() {
		return flujo;
	}

	public void setFlujo(int flujo) {
		this.flujo = flujo;
	}

	@Override
	public String toString() {
		return "Arco [desde=" + desde.getId() + ", hasta=" + hasta.getId() + ", capacidad=" + capacidad + ", flujo=" + flujo + "]";
	}

	
}
