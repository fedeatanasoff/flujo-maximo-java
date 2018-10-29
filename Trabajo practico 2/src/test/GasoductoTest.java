package test;

import static org.junit.Assert.*;

import org.junit.Test;

import logica.Gasoducto;
import logica.Nodo;
import logica.TipoDeNodo;

public class GasoductoTest {

	@Test
	public void gasoductoVacioTest() {
		Gasoducto gasoducto = new Gasoducto();
		assertTrue(gasoducto.esVacio());
	}

	@Test
	public void agregarNodoTest() {
		Gasoducto gasoducto = new Gasoducto();
		gasoducto.agregarNodo(TipoDeNodo.PASO, 5);
		assertTrue(gasoducto.estaElNodo(0));
	}

	@Test
	public void nodoSinVecinos() {
		Gasoducto gasoducto = new Gasoducto();
		Nodo nodo = gasoducto.agregarNodo(TipoDeNodo.PRODUCTOR, 5);
		assertEquals(gasoducto.dameVecinos(nodo).size(), 0);
	}

	@Test
	public void dameNodoValidoTest() {
		Gasoducto gasoducto = new Gasoducto();
		gasoducto.agregarNodo(TipoDeNodo.CONSUMIDOR, 5);
		Nodo n = gasoducto.dameNodo(1);
		assertEquals(gasoducto.dameNodo(1), n);
	}

	@Test(expected = IllegalArgumentException.class)
	public void dameNodoInvalidoTest() {
		Gasoducto gasoducto = new Gasoducto();
		gasoducto.agregarNodo(TipoDeNodo.PASO, 5);
		gasoducto.dameNodo(2);
	}

	@Test
	public void nodoParteDeArco() {
		Gasoducto gasoducto = new Gasoducto();
		gasoducto.agregarNodo(TipoDeNodo.CONSUMIDOR, 5);
		gasoducto.agregarNodo(TipoDeNodo.PRODUCTOR, 5);
		gasoducto.agregarArco(gasoducto.dameNodo(1), gasoducto.dameNodo(2), 5);
		assertTrue(gasoducto.estaElNodoEnArco(gasoducto.dameNodo(1)));
	}

	@Test
	public void nodoNoParteDeArco() {
		Gasoducto gasoducto = new Gasoducto();
		gasoducto.agregarNodo(TipoDeNodo.CONSUMIDOR, 5);
		gasoducto.agregarNodo(TipoDeNodo.PRODUCTOR, 5);
		gasoducto.agregarNodo(TipoDeNodo.PRODUCTOR, 3);
		gasoducto.agregarArco(gasoducto.dameNodo(1), gasoducto.dameNodo(2), 5);
		assertFalse(gasoducto.estaElNodoEnArco(gasoducto.dameNodo(3)));
	}

	@Test
	public void agregarArcoTest() {
		Gasoducto gasoducto = new Gasoducto();
		gasoducto.agregarNodo(TipoDeNodo.PRODUCTOR, 5);
		gasoducto.agregarNodo(TipoDeNodo.PASO, 5);
		gasoducto.agregarArco(gasoducto.dameNodo(1), gasoducto.dameNodo(2), 7);
		assertTrue(gasoducto.existeArco(gasoducto.dameNodo(1).getId(), gasoducto.dameNodo(2).getId()));
	}

	@Test
	public void setearArcoTest() {
		Gasoducto gasoducto = new Gasoducto();
		gasoducto.agregarNodo(TipoDeNodo.PRODUCTOR, 5);
		gasoducto.agregarNodo(TipoDeNodo.PASO, 5);
		// Arco arco = new Arco(gasoducto.dameNodo(1), gasoducto.dameNodo(1), 70);
		gasoducto.agregarArco(gasoducto.dameNodo(1), gasoducto.dameNodo(2), 7);
		gasoducto.getArcos().get(0).setFlujo(8);
		assertTrue(gasoducto.getArcos().get(0).getFlujo() == 8);
	}

	@Test(expected = IllegalArgumentException.class)
	public void dameArcoInexistenteTest() {
		Gasoducto gasoducto = new Gasoducto();
		gasoducto.agregarNodo(TipoDeNodo.PRODUCTOR, 5);
		gasoducto.agregarNodo(TipoDeNodo.PASO, 5);
		gasoducto.dameArco(1, 2);
	}

	@Test
	public void eliminarArcoTest() {
		Gasoducto gasoducto = new Gasoducto();
		gasoducto.agregarNodo(TipoDeNodo.PRODUCTOR, 5);
		gasoducto.agregarNodo(TipoDeNodo.PASO, 5);

		gasoducto.agregarArco(gasoducto.dameNodo(1), gasoducto.dameNodo(2), 7);
		gasoducto.eliminarArco(gasoducto.dameNodo(1), gasoducto.dameNodo(2));
		assertFalse(gasoducto.existeArco(gasoducto.dameNodo(1).getId(), gasoducto.dameNodo(2).getId()));

	}

	@Test(expected = IllegalArgumentException.class)
	public void eliminarArcoInexistenteTest() {
		Gasoducto gasoducto = new Gasoducto();
		gasoducto.agregarNodo(TipoDeNodo.PRODUCTOR, 5);
		gasoducto.agregarNodo(TipoDeNodo.PASO, 5);

		// gasoducto.agregarArco( gasoducto.dameNodo(1) , gasoducto.dameNodo(2), 7 );
		gasoducto.eliminarArco(gasoducto.dameNodo(1), gasoducto.dameNodo(2));
		// assertTrue( gasoducto.existeArco(gasoducto.dameNodo(1).getId(),
		// gasoducto.dameNodo(2).getId()) );

	}

	@Test
	public void agregarOrigenTest() {
		Gasoducto gasoducto = new Gasoducto();
		gasoducto.agregarNodo(TipoDeNodo.PRODUCTOR, 5);
		gasoducto.agregarNodo(TipoDeNodo.PASO, 5);
		gasoducto.agregarOrigenDestino();
		assertTrue(gasoducto.dameNodo(0).getId() == 0);
	}

	@Test
	public void agregarDestinoTest() {
		Gasoducto gasoducto = new Gasoducto();
		gasoducto.agregarNodo(TipoDeNodo.PRODUCTOR, 5);
		gasoducto.agregarNodo(TipoDeNodo.PASO, 5);
		gasoducto.agregarOrigenDestino();
		assertTrue(gasoducto.dameNodo(3).getId() == (gasoducto.cantidadDeNodos() - 1));
	}

	@Test
	public void arcoOrigenTest() {
		Gasoducto gasoducto = new Gasoducto();
		gasoducto.agregarNodo(TipoDeNodo.PRODUCTOR, 5);
		gasoducto.agregarNodo(TipoDeNodo.PASO, 5);
		gasoducto.agregarOrigenDestino();
		assertTrue(gasoducto.existeArco(gasoducto.getOrigen().getId(), gasoducto.dameNodo(1).getId()));
	}

	@Test
	public void arcoDestinoTest() {
		Gasoducto gasoducto = new Gasoducto();
		gasoducto.agregarNodo(TipoDeNodo.PRODUCTOR, 5);
		gasoducto.agregarNodo(TipoDeNodo.PASO, 5);
		gasoducto.agregarNodo(TipoDeNodo.CONSUMIDOR, 5);
		gasoducto.agregarOrigenDestino();
		assertTrue(gasoducto.existeArco(gasoducto.dameNodo(3).getId(),
				gasoducto.dameNodo(gasoducto.getDestino().getId()).getId()));
		// assertTrue( gasoducto.dameNodo(gasoducto.getDestino().getId()).getId() ==
		// (gasoducto.cantidadDeNodos() -1) );
	}

	@Test
	public void nodosConsumidoresTest() {
		Gasoducto gasoducto = new Gasoducto();
		gasoducto.agregarNodo(TipoDeNodo.CONSUMIDOR, 5);
		assertTrue(gasoducto.getConsumidores().size() == 1);
	}

	@Test
	public void nodosProductoresTest() {
		Gasoducto gasoducto = new Gasoducto();
		gasoducto.agregarNodo(TipoDeNodo.PRODUCTOR, 5);
		assertTrue(gasoducto.getProductores().size() == 1);
	}
	
	@Test
	public void compararGasoducto() {
		Gasoducto gasoducto = new Gasoducto();

		Nodo productor1 = gasoducto.agregarNodo(TipoDeNodo.PRODUCTOR, 15);
		Nodo productor2 = gasoducto.agregarNodo(TipoDeNodo.PRODUCTOR, 10);
		Nodo productor3 = gasoducto.agregarNodo(TipoDeNodo.PRODUCTOR, 12);
		Nodo paso4 = gasoducto.agregarNodo(TipoDeNodo.PASO, 0);
		Nodo paso5 = gasoducto.agregarNodo(TipoDeNodo.PASO, 0);
		Nodo consumidor6 = gasoducto.agregarNodo(TipoDeNodo.CONSUMIDOR, 5);
		Nodo consumidor7 = gasoducto.agregarNodo(TipoDeNodo.CONSUMIDOR, 13);

		gasoducto.agregarArco(productor1, paso4, 10);
		gasoducto.agregarArco(productor2, paso5, 12);
		gasoducto.agregarArco(productor3, paso5, 15);
		gasoducto.agregarArco(paso5, consumidor7, 19);
		gasoducto.agregarArco(paso4, consumidor6, 10);
		
		String gasoductoJson = gasoducto.generarJSONPretty();
		gasoducto.generarJSON(gasoductoJson, "gasoducto.json");
		
		Gasoducto GasJson = Gasoducto.leerJSON("gasoducto.JSON");
		
		assertTrue(gasoducto.compararGasoducto(GasJson));
	}

}
