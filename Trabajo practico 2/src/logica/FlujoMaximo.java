package logica;

import java.util.ArrayList;

public class FlujoMaximo {
	ArrayList<Nodo> camino;
	ArrayList<Integer> caminoAumento;
	Gasoducto gasoducto;
	Gasoducto residual;
	Nodo anterior;
	int[] vecinosAgregados;
	boolean[] marcados;

	/* constructor */
	public FlujoMaximo(Gasoducto gasoducto) 
	{
		camino = new ArrayList<Nodo>();
		this.gasoducto = gasoducto;
	}

	public ArrayList<Nodo> getCamino() 
	{
		return camino;
	}

	public boolean[] getMarcados() 
	{
		return marcados;
	}

	public int[] getVecinosAgregados()
	{
		return vecinosAgregados;
	}
	
	public ArrayList<Integer> getCaminoAumento() 
	{
		return caminoAumento;
	}
	
	public Gasoducto getGasoducto()
	{
		return gasoducto;
	}	

	public Gasoducto getResidual() 
	{
		return residual;
	}

	public void setResidual(Gasoducto residual) 
	{
		this.residual = residual;
	}
	
	public int fordFulkerson() 
	{
		gasoducto.agregarOrigenDestino();
		residual = crearResidual();
		
		int min = 0;
		int flujoMaximo = 0;
		
		while (existeCaminoDeAumento(residual)) 
			
		{
			System.out.println("entro!");
			min = minimo(residual);
			flujoMaximo += min;
			for (int i = 0; i < caminoAumento.size() - 1; i++) 
			{
				if (gasoducto.existeArco(caminoAumento.get(i), caminoAumento.get(i + 1))) 
				{
					Arco e = gasoducto.dameArco(caminoAumento.get(i), caminoAumento.get(i + 1));
					e.setFlujo(e.getFlujo() + min);
				}

				if (gasoducto.existeArco(caminoAumento.get(i + 1), caminoAumento.get(i))) 
				{
					Arco e = gasoducto.dameArco(caminoAumento.get(i + 1), caminoAumento.get(i));
					e.setFlujo(e.getFlujo() - min);
				}
			}

			vaciarMarcados();
			vaciarVecinosAgregados();
			vaciarCaminoAumento();
			residual = crearResidual();
		}
		return flujoMaximo;
	}
	
	public Gasoducto crearResidual() 
	{
		Gasoducto res = new Gasoducto();

		for (Nodo n : gasoducto.getNodos())
			res.getNodos().add(n);

		for (Arco e : gasoducto.getArcos()) {
			if (e.getFlujo() < e.getCapacidad())
				res.agregarArco(e.getDesde(), e.getHasta(), e.getCapacidad());

			if (e.getFlujo() > 0)
				res.agregarArco(e.getHasta(), e.getDesde(), e.getFlujo());

			if (e.getFlujo() == e.getCapacidad())
				e.getDesde().getNodosVecinos().remove(e.getHasta());

		}
		return res;
	}
	
	public boolean existeCaminoDeAumento(Gasoducto g) 
	{
		iniciar(g);

		while (camino.size() > 0)
		{
			int i = camino.get(0).getId();
			marcados[i] = true;

			agregarVecinosPendientes(camino.get(0));
			camino.remove(0);
		}
		
		caminoDeAumento();
		
		return marcados[gasoducto.cantidadDeNodos() - 1];
	}
	
	public void iniciar(Gasoducto g) 
	{
		vecinosAgregados = new int[gasoducto.cantidadDeNodos()];
		marcados = new boolean[gasoducto.cantidadDeNodos()];
		caminoAumento = new ArrayList<Integer>();

		camino.add(g.dameNodo(0));
	}

	public void agregarVecinosPendientes(Nodo nodo) 
	{
		for (Nodo n : nodo.getNodosVecinos())
			if (marcados[n.getId()] == false && camino.contains(n) == false) 
			{
				vecinosAgregados[n.getId()] = nodo.getId();
				camino.add(n);
			}
	}	

	public int minimo(Gasoducto g) 
	{
		int min = g.dameArco(caminoAumento.get(0), caminoAumento.get(1)).getCapacidad();
		for (int i = 0; i < caminoAumento.size() - 1; i++) 
		{
			Arco e = g.dameArco(caminoAumento.get(i), caminoAumento.get(i + 1));
			if (e.getCapacidad() < min)
				min = e.getCapacidad();
		}
		
		return min;
	}
	
	public void caminoDeAumento()
	{
		ArrayList<Integer> cam = caminoDeAumentoAlRevez();
		for (int i = cam.size() - 1; i >= 0; i--)
			caminoAumento.add(caminoDeAumentoAlRevez().get(i));
	}
	
	private ArrayList<Integer> caminoDeAumentoAlRevez() 
	{
		ArrayList<Integer> aumento = new ArrayList<Integer>();
		int parcial = 0;
		for (int i = vecinosAgregados.length - 1; i >= 0; i--)
		{
			if (i != 0) 
			{
				if (i == vecinosAgregados.length - 1) 
				{
					parcial = vecinosAgregados[i];
					aumento.add(i);
					aumento.add(parcial);
				}
				if (i == parcial) 
				{
					parcial = vecinosAgregados[i];
					aumento.add(parcial);
				}
			}
		}
		return aumento;
	}

	public void vaciarMarcados() 
	{
		for (int i = 0; i < marcados.length; i++) 
			marcados[i] = false;
	}

	public void vaciarVecinosAgregados() 
	{
		for (int i = 0; i < vecinosAgregados.length; i++)
			vecinosAgregados[i] = 0;
	}

	public void vaciarCaminoAumento()
	{
		for (int i = 0; i < caminoAumento.size(); i++) 
			caminoAumento.set(i, null);
	}

}







