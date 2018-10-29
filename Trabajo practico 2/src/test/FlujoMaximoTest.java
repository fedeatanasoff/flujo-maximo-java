package test;

import static org.junit.Assert.*;


import org.junit.Test;

import logica.FlujoMaximo;
import logica.Gasoducto;
import logica.Nodo;
import logica.TipoDeNodo;

public class FlujoMaximoTest 
{
	public Gasoducto crearGasoducto() 
	{
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

		return gasoducto;
	}

	@Test
	public void compararResidualTest() {
		Gasoducto g1 = crearGasoducto();
		FlujoMaximo flujo = new FlujoMaximo(g1);
		
		assertTrue(g1.compararGasoducto(flujo.crearResidual()));
	}
	
	@Test
	public void residualFFTest()
	{
		Gasoducto g1 = crearGasoducto();
		FlujoMaximo flujo = new FlujoMaximo(g1);
		flujo.fordFulkerson();
		
		assertFalse(g1.compararGasoducto(flujo.crearResidual()));
	}
	
	@Test
	public void existeArcoDeVueltaEnResidualTest()
	{
		Gasoducto g = crearGasoducto();
		g.dameArco( 2 , 5 ).setFlujo( 5 );
		Gasoducto residual = new FlujoMaximo(g).crearResidual();
		
		assertTrue(residual.existeArco( 5 , 2 ));
	}
	
	@Test
	public void noExisteArcoDeIdaEnResidualTest()
	{
		Gasoducto g = crearGasoducto();
		g.dameArco( 2 , 5 ).setFlujo( 12 );
		Gasoducto residual = new FlujoMaximo(g).crearResidual();
		assertFalse(residual.existeArco( 2 , 5 ));
	}
	
	@Test
	public void agregarVecinosPendientes()
	{
		Gasoducto g = crearGasoducto();
		g.agregarOrigenDestino();
		FlujoMaximo flujo = new FlujoMaximo( g );
		flujo.iniciar( g );
		flujo.agregarVecinosPendientes( g.dameNodo( 1 ) );
		
		int[] vecinos = flujo.getVecinosAgregados();
		
		assertTrue(vecinos[4] == g.dameNodo( 1 ).getId());
	 }
	
	@Test
	public void noAgregarVecinosPendientes()
	{
		Gasoducto g = crearGasoducto();
		g.agregarOrigenDestino();
		FlujoMaximo flujo = new FlujoMaximo( g );
		flujo.iniciar( g );
		flujo.agregarVecinosPendientes( g.dameNodo( 1 ) );
		
		int[] vecinos = flujo.getVecinosAgregados();
		
		assertFalse(vecinos[5] == g.dameNodo( 1 ).getId());
	}
	
	@Test
	public void FordFulkersonTest() 
	{
		Gasoducto g1 = crearGasoducto();

		FlujoMaximo flujo = new FlujoMaximo(g1);

		assertTrue(flujo.fordFulkerson() == 27);
	}

	@Test
	public void existeCaminoAumentoTest()
	{
		/*Gasoducto g1 = crearGasoducto();
		FlujoMaximo flujo = new FlujoMaximo(g1);
		flujo.getGasoducto().agregarOrigenDestino();
		flujo.setResidual(flujo.crearResidual());
		assertTrue(flujo.existeCaminoDeAumento(flujo.getResidual()));*/
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
		
		// ---- Serializando el gasoducto ---- //
		String serializado = Gasoducto.serializableGasoducto(gasoducto);
		
		// ---- Guardando gasoducto ----
		gasoducto.generarJSON(serializado, "gasoducto.json");
		
		// ---- Leyendo Gasoducto ----
		Gasoducto gasoductoDesdeJson = Gasoducto.leerJSON("gasoducto.json");
		gasoductoDesdeJson.agregarOrigenDestino();
		assertTrue(gasoductoDesdeJson.dameNodo(0).getId() == 0);
		
		/*FlujoMaximo flujo = new FlujoMaximo(gasoductoDesdeJson);
		gasoducto.agregarOrigenDestino();
		assertTrue(gasoducto.dameNodo(0).getId() == 0);
		
		flujo.fordFulkerson();
		assertTrue(flujo.existeCaminoDeAumento(flujo.getResidual()));*/
	}
	
	@Test 
	public void noExisteCaminoDeAumentoTest()
	{
		Gasoducto g = crearGasoducto();
		g.agregarOrigenDestino();
		g.dameArco( 6 , g.getDestino().getId() ).setFlujo( 5 );
		g.dameArco( 7 , g.getDestino().getId() ).setFlujo( 13 );
		
		FlujoMaximo flujo = new FlujoMaximo(g);
		Gasoducto residual = flujo.crearResidual();
		
		
		assertFalse(flujo.existeCaminoDeAumento( residual ));
	}

	@Test
	public void grafoParticualar()
	{
		Gasoducto g = new Gasoducto();
		Nodo pr1 = g.agregarNodo( TipoDeNodo.PRODUCTOR , 7 );
		Nodo pr2 = g.agregarNodo( TipoDeNodo.PRODUCTOR , 8 );
		Nodo ps3 = g.agregarNodo( TipoDeNodo.PASO , 0 );
		Nodo ps4 = g.agregarNodo( TipoDeNodo.PASO , 0 );
		Nodo c5 = g.agregarNodo( TipoDeNodo.CONSUMIDOR , 20 );
		Nodo c6 = g.agregarNodo( TipoDeNodo.CONSUMIDOR , 4 );
		
		g.agregarArco( pr1 , ps3 , 4 );
		g.agregarArco( pr2 , ps4 , 10 );
		g.agregarArco( ps4 , pr1 , 3 );
		g.agregarArco( ps4 , c6 , 15 );
		g.agregarArco( ps3 , c5 , 7 );
		
		FlujoMaximo flujo = new FlujoMaximo( g );
		
		assertTrue(flujo.fordFulkerson() == 8);
	}

}








