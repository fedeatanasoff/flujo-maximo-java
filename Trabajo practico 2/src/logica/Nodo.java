package logica;

import java.util.HashSet;

public class Nodo {
	private HashSet<Nodo> nodosVecinos;
	private TipoDeNodo tipoDeNodo;
	private int id;
	private int capacidad;

	/* constructor */
	public Nodo(TipoDeNodo tipo, int idem, int cap) {
		tipoDeNodo = tipo;
		nodosVecinos = new HashSet<Nodo>();
		this.id = idem;
		capacidad = cap;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public int getId() {
		return id;
	}

	/*
	 * @Override public String toString () { return "tipoDeNodo=" + tipoDeNodo +
	 * ", id=" + id + ", capacidad=" + capacidad + "]"; }
	 */

	public TipoDeNodo getTipoDeNodo() {
		return tipoDeNodo;
	}

	public void agregarVecino(Nodo nodo) {
		nodosVecinos.add(nodo);
	}

	public HashSet<Nodo> getNodosVecinos() {
		return nodosVecinos;
	}

	@Override
	public String toString() {
		return "Nodo [tipoDeNodo=" + tipoDeNodo + ", id=" + id + ", capacidad=" + capacidad + "]";
	}

	
	
	

}
