package logica;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class Gasoducto {
	private ArrayList<Nodo> nodos;
	private ArrayList<Nodo> productores;
	private ArrayList<Nodo> consumidores;
	private ArrayList<Arco> arcos;
	Nodo origen;
	Nodo destino;
	private int nodoId;

	/* constructor */
	public Gasoducto() {
		nodos = new ArrayList<Nodo>();
		arcos = new ArrayList<Arco>();
		nodoId = 0;
		productores = new ArrayList<Nodo>();
		consumidores = new ArrayList<Nodo>();
	}

	public void agregarOrigenDestino() {
		origen = new Nodo(TipoDeNodo.PRODUCTOR, 0, 222);
		destino = new Nodo(TipoDeNodo.CONSUMIDOR, nodos.size() + 1, 555);
		for (Nodo i : productores) {
			crearArcoOrigen(i);
		}

		for (Nodo i : consumidores) {
			crearArcoDestino(i);
		}
		nodos.add(origen);
		nodos.add(destino);
	}

	private void crearArcoDestino(Nodo i) {
		agregarArco(i, destino, i.getCapacidad());
	}

	private void crearArcoOrigen(Nodo i) {
		agregarArco(origen, i, i.getCapacidad());
	}

	public Nodo getOrigen() {
		return origen;
	}
	
	public Nodo getDestino() {
		return destino;
	}

	public ArrayList<Nodo> getProductores() {
		return productores;
	}

	public ArrayList<Nodo> getConsumidores() {
		return consumidores;
	}

	public ArrayList<Nodo> getNodos() {
		return nodos;
	}

	public ArrayList<Arco> getArcos() {
		return arcos;
	}

	public int cantidadDeNodos() {
		return nodos.size();
	}

	public boolean esVacio() {
		return (nodos.size() == 0) ? true : false;
	}

	public ArrayList<Nodo> dameVecinos(Nodo nodo) {
		return new ArrayList<Nodo>(nodo.getNodosVecinos());
	}

	public Nodo agregarNodo(TipoDeNodo tipo, int capacidad) {
		nodoId++;
		Nodo nodo = new Nodo(tipo, nodoId, capacidad);
		nodos.add(nodo);
		if (tipo == TipoDeNodo.PRODUCTOR)
			productores.add(nodo);
		if (tipo == TipoDeNodo.CONSUMIDOR)
			consumidores.add(nodo);
		return nodo;
	}

	public Nodo dameNodo(int id) {
		for (Nodo n : nodos)
			if (n.getId() == id)
				return n;
		throw new IllegalArgumentException("El nodo "+ id + " no existe");
	}

	public boolean estaElNodo(int indiceNodo) {
		return nodos.get(indiceNodo) != null;
	}

	public void agregarArco(Nodo desde, Nodo hasta, int capacidad) {
		Arco nuevoArco = new Arco(desde, hasta, capacidad);
		desde.agregarVecino(hasta);
		arcos.add(nuevoArco);
	}
 
	public void eliminarArco(Nodo desde, Nodo hasta) {
		if (existeArco(desde.getId(), hasta.getId())) {
			desde.getNodosVecinos().remove(hasta);
			arcos.remove(dameArco(desde.getId(), hasta.getId()));
		} else
			throw new IllegalArgumentException("arco inexistente");
	}

	public Arco dameArco(int desde, int hasta) {
		for (Arco e : arcos) {
			if (e.getDesde().getId() == desde && e.getHasta().getId() == hasta)
				return e;
		}
		throw new IllegalArgumentException("Arco inexistente del " + desde + " hasta " + hasta);
	}

	public boolean estaElNodoEnArco(Nodo nodo) {
		for (Arco i : arcos) {
			if (i.getDesde().getId() == nodo.getId() || i.getHasta().getId() == nodo.getId())
				return true;
		}
		return false;
	}

	public boolean existeArco(int delNodo, int alNodo) {
		for (Arco e : arcos)
			if (e.getDesde().getId() == delNodo && e.getHasta().getId() == alNodo)
				return true;
		return false;
	}
	
	public boolean compararGasoducto(Gasoducto g)
	{
		if(cantidadDeNodos() == g.cantidadDeNodos())
		{
			for( Nodo n : nodos )
				if(!n.equals(g.dameNodo(n.getId())))
					return false;
			
			for( Arco e : arcos )
				if(!g.existeArco(e.getDesde().getId(), e.getHasta().getId()))
					return false;
		}
		else 
			return false;
		return true;
	}
	
	public String generarJSONPretty() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(this);

		return json;
	}
	
	public void generarJSON(String json, String archivo) {
		try {
			FileWriter writer = new FileWriter(archivo);
			writer.write(json);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Gasoducto leerJSON(String archivo) {
		Gson gson = new Gson();
		Gasoducto ret = null;

		try {
			BufferedReader br = new BufferedReader(new FileReader(archivo));
			ret = gson.fromJson(br, Gasoducto.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return ret;
	}
	
	public static void main(String[] args) {
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
		String serializado = serializableGasoducto(gasoducto);
		
		// ---- Guardando gasoducto ----
		gasoducto.generarJSON(serializado, "gasoducto.json");
		
		// ---- Leyendo Gasoducto ----
		Gasoducto gasoductoDesdeJson = Gasoducto.leerJSON("gasoducto.json");
			
		System.out.println("NODOS ");
		for(Nodo n : gasoducto.nodos) {
			System.out.print(n.toString()+" ");
		}
		System.out.println(" ");
		for(Nodo n : gasoductoDesdeJson.nodos) {
			System.out.print(n.toString() + " ");
		}
		
		System.out.println(" ");
		System.out.println(" ");
		System.out.println("ARCOS ");
		
		for(Arco a : gasoducto.arcos) {
			System.out.print(a.toString() +" ");
		}
		System.out.println(" ");
	
		for(Arco a : gasoductoDesdeJson.arcos) {
			System.out.print(a.toString() +" ");
		}
		
		System.out.println(" ");
		System.out.println(" ");
		System.out.println("CONSUMIDORES ");
		
		for(Nodo n : gasoducto.consumidores) {
			System.out.print(n.toString() +" ");
		}
		System.out.println(" ");
	
		for(Nodo n : gasoductoDesdeJson.consumidores) {
			System.out.print(n.toString() +" ");
		}
		
		System.out.println(" ");
		System.out.println(" ");
		System.out.println("PRODUCTORES ");
		
		for(Nodo n : gasoducto.productores) {
			System.out.print(n.toString() +" ");
		}
		System.out.println(" ");
	
		for(Nodo n : gasoductoDesdeJson.productores) {
			System.out.print(n.toString() +" ");
		}
		System.out.println(" ");

		System.out.println(" ");
		System.out.println("NODOS ORIGEN");
		System.out.println("NODO ORIGEN: "+ gasoducto.origen);
		System.out.println("NODO ORIGEN: "+ gasoductoDesdeJson.origen);
		
		System.out.println(" ");
		System.out.println("NODOS DESTINO");
		System.out.println("NODO DESTINO: "+ gasoducto.destino);
		System.out.println("NODO DESTIBO: "+ gasoductoDesdeJson.destino);
		
		System.out.println(" ");
		System.out.println("NODO ID");
		System.out.println("NODO ID: "+ gasoducto.nodoId);
		System.out.println("NODO ID: "+ gasoductoDesdeJson.nodoId);
				
		//FlujoMaximo f1 = new FlujoMaximo(gasoducto);
		FlujoMaximo f2 = new FlujoMaximo(gasoductoDesdeJson);
		//System.out.println(f1.fordFulkerson());
		System.out.println("Flujo maximo: "+f2.fordFulkerson());
		
	}
	
	public  static String serializableGasoducto(Gasoducto g) {
		Gasoducto g1 = g;
		Gson gson = new Gson();
		
		String json = gson.toJson(g1);
		
		//System.out.println(json);
		return json;
	}
	
	public static Gasoducto deserializableGasoducto(String json) {
		String gasoductoJson = json;
		
		Gson gson = new Gson();
		Gasoducto gaso = gson.fromJson(gasoductoJson, Gasoducto.class);
		return gaso;
	}

	@Override
	public String toString() {
		return "Gasoducto [nodos=" + nodos + ", productores=" + productores + ", consumidores=" + consumidores
				+ ", arcos=" + arcos + ", origen=" + origen + ", destino=" + destino + ", nodoId=" + nodoId + "]";
	}

	

	
	
	
	
	
	
	
	
	
	
	
	
	
}