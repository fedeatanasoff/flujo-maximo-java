package test;

import static org.junit.Assert.*;

import org.junit.Test;

import logica.Nodo;
import logica.TipoDeNodo;

public class NodoTest {
	@Test
	public void tipoProductorTest() {
		Nodo nodo = new Nodo(TipoDeNodo.PRODUCTOR, 0, 6);
		assertEquals(TipoDeNodo.PRODUCTOR, nodo.getTipoDeNodo());
	}

	@Test
	public void tipoPasoTest() {
		Nodo nodo = new Nodo(TipoDeNodo.PASO, 0, 5);
		assertEquals(TipoDeNodo.PASO, nodo.getTipoDeNodo());
	}

	@Test
	public void tipoConsumidorTest() {
		Nodo nodo = new Nodo(TipoDeNodo.CONSUMIDOR, 0, 7);
		assertEquals(TipoDeNodo.CONSUMIDOR, nodo.getTipoDeNodo());
	}

	@Test
	public void agregarVecino() {
		Nodo nodo = new Nodo(TipoDeNodo.PRODUCTOR, 0, 6);
		Nodo aAgregar = new Nodo(TipoDeNodo.PASO, 1, 7);
		nodo.agregarVecino(aAgregar);
		assertTrue(nodo.getNodosVecinos().contains(aAgregar));
	}

	@Test
	public void agregarVecinoTest() {
		Nodo nodo = new Nodo(TipoDeNodo.PRODUCTOR, 0, 6);
		Nodo nodoVecino = new Nodo(TipoDeNodo.PASO, 2, 16);
		nodo.agregarVecino(nodoVecino);
		assertTrue(nodo.getNodosVecinos().contains(nodoVecino));
	}

}
