package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import logica.FlujoMaximo;
import logica.Gasoducto;
import logica.TipoDeNodo;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import javax.swing.ListSelectionModel;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import javax.swing.JMenu;

public class Interfaz
{
	private JFrame frame;
	private JTextField textFieldProdCons;
	private JTextField textFieldCapacidadMaxima;
	private Gasoducto gasoducto;
	private TipoDeNodo tipo;
	private JTextField textFieldDesde;
	private JTextField textFieldHasta;
	private JTable tablaDeNodos;
	private DefaultTableModel modelo;
	private String[] dato;

	public static void main ( String[] args )
	{
		EventQueue.invokeLater( new Runnable()
		{
			public void run ()
			{
				try
				{
					Interfaz window = new Interfaz();
					window.frame.setVisible( true );
				}
				catch ( Exception e )
				{
					e.printStackTrace();
				}
			}
		} );
	}

	public Interfaz ()
	{
		initialize();
	}
	
	private void initialize ()
	{
		iniciar();
		
		agregarNodo();
		
		agregarArco();
		
		FlujoMaximo();
		
		//Guardar();
		
		tablaDeNodoCreados();
	}

	private void iniciar ()
	{
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(240, 240, 240));
		frame.setBounds( 100 , 100 , 720 , 480 );
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setBackground(new Color(140, 134, 160));
		
		gasoducto = new Gasoducto();
		modelo = new DefaultTableModel();
		dato = new String[3];
	}
	
	private void agregarNodo ()
	{
		JLabel lblAgregarNodos = new JLabel("-----Agregar nodos-----");
		lblAgregarNodos.setBounds(10, 33, 132, 14);
		frame.getContentPane().add(lblAgregarNodos);
		
		JComboBox<String> comboBoxTipoDeNodo = new JComboBox<String>();
		comboBoxTipoDeNodo.setModel(new DefaultComboBoxModel<String>(new String[] {"Tipos de nodo", "Productor", "Consumidor", "Paso"}));
		comboBoxTipoDeNodo.setBounds(10, 58, 122, 20);
		frame.getContentPane().add(comboBoxTipoDeNodo);
		
		textFieldProdCons = new JTextField();
		textFieldProdCons.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evento)
			{
				char c = evento.getKeyChar();
				if(c < '0' || c > '9')
					evento.consume();
			}
		});
		textFieldProdCons.setBounds(142, 58, 122, 20);
		frame.getContentPane().add(textFieldProdCons);
		textFieldProdCons.setColumns(10);
		
		JButton btnAgregarNodo = new JButton("Crear nodo");
		btnAgregarNodo.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try
				{
					int itemCombo = comboBoxTipoDeNodo.getSelectedIndex();
					double produceConsume = Double.parseDouble( textFieldProdCons.getText() );
					
					if (itemCombo != 0)
					{
						if(itemCombo == 1)
							tipo = TipoDeNodo.PRODUCTOR;
						if(itemCombo == 2)
							tipo = TipoDeNodo.CONSUMIDOR;
						if(itemCombo == 3)
							tipo = TipoDeNodo.PASO;
						
						gasoducto.agregarNodo( tipo ,(int) produceConsume );
						dato[0] = gasoducto.cantidadDeNodos()+"";
						dato[1] = tipo.toString();
						dato[2] = Integer.toString(gasoducto.getNodos().get(gasoducto.cantidadDeNodos()-1).getCapacidad());
						
						/*dato[0] = tipo.toString();
						dato[1] = gasoducto.cantidadDeNodos()+"";
						dato[2] = Integer.toString(gasoducto.getNodos().get(gasoducto.cantidadDeNodos()-1).getCapacidad());*/
						modelo.addRow( dato );
						
						String n = Integer.toString(gasoducto.getNodos().get(gasoducto.cantidadDeNodos()-1).getCapacidad());
						System.out.println(n);
						
						JOptionPane.showMessageDialog(frame, "Se creo el nodo: "+ tipo.toString()+ " "+ gasoducto.cantidadDeNodos()
						, "Nodo creado!",
						        JOptionPane.INFORMATION_MESSAGE);
					}
					else
						 JOptionPane.showMessageDialog(frame, "Seleccione un tipo de nodo!", "Warning",
							        JOptionPane.WARNING_MESSAGE);
				}
				catch(Exception x)
				{
					JOptionPane.showMessageDialog(frame, "Debes ingresar la cantidad a producir o consumir", "Warning",
					        JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnAgregarNodo.setBounds(274, 57, 118, 23);
		btnAgregarNodo.setBorder(null);
		frame.getContentPane().add(btnAgregarNodo);
		
	}
	
	private void agregarArco ()
	{
		JLabel lblAgregarArcos = new JLabel("-----Agregar arcos-----");
		lblAgregarArcos.setBounds(10, 130, 226, 14);
		frame.getContentPane().add(lblAgregarArcos);
		
		JLabel lblDesde = new JLabel("Desde el nodo");
		lblDesde.setFont(new Font("Arial", Font.BOLD, 10));
		lblDesde.setBounds(10, 155, 87, 14);
		frame.getContentPane().add(lblDesde);
		
		textFieldDesde = new JTextField();
		textFieldDesde.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evento)
			{
				char c = evento.getKeyChar();
				if(c < '0' || c > '9')
					evento.consume();
			}
		});
		textFieldDesde.setBounds(10, 168, 70, 20);
		frame.getContentPane().add(textFieldDesde);
		textFieldDesde.setColumns(10);
		
		JLabel lblHastaElNodo = new JLabel("Hasta el nodo");
		lblHastaElNodo.setFont(new Font("Arial", Font.BOLD, 10));
		lblHastaElNodo.setBounds(98, 155, 89, 14);
		frame.getContentPane().add(lblHastaElNodo);
		
		textFieldHasta = new JTextField();
		textFieldHasta.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evento)
			{
				char c = evento.getKeyChar();
				if(c < '0' || c > '9')
					evento.consume();
			}
		});
		textFieldHasta.setBounds(99, 168, 72, 20);
		frame.getContentPane().add(textFieldHasta);
		textFieldHasta.setColumns(10);
		
		JLabel lblCapacidadMxima = new JLabel("Capacidad m\u00E1xima");
		lblCapacidadMxima.setFont(new Font("Arial", Font.BOLD, 10));
		lblCapacidadMxima.setBounds(181, 155, 132, 14);
		frame.getContentPane().add(lblCapacidadMxima);
		
		textFieldCapacidadMaxima = new JTextField();
		textFieldCapacidadMaxima.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evento)
			{
				char c = evento.getKeyChar();
				if(c < '0' || c > '9')
					evento.consume();
			}
		});
		textFieldCapacidadMaxima.setBounds(181, 168, 97, 20);
		frame.getContentPane().add(textFieldCapacidadMaxima);
		textFieldCapacidadMaxima.setColumns(10);
		
		JButton btnAgregarArco = new JButton("Crear arco");
		btnAgregarArco.setBounds(288, 167, 104, 23);
		btnAgregarArco.addActionListener( new ActionListener()
		{
			@Override
			public void actionPerformed ( ActionEvent e )
			{
				try 
				{
					double desde = Double.parseDouble( textFieldDesde.getText() );
					double hasta = Double.parseDouble( textFieldHasta.getText() );
					double capacidad = Double.parseDouble( textFieldCapacidadMaxima.getText() );
					if(gasoducto.existeArco(gasoducto.dameNodo( (int)desde ).getId() , gasoducto.dameNodo( (int)hasta ).getId())){
						JOptionPane.showMessageDialog(frame, "El arco ya existe!!", "Warning",
						        JOptionPane.WARNING_MESSAGE);
					}else {
						gasoducto.agregarArco( gasoducto.dameNodo( (int)desde ) , gasoducto.dameNodo( (int)hasta ) , (int)capacidad );
						System.out.println( gasoducto.getArcos().size() );
						JOptionPane.showMessageDialog(frame, "Se creo el Arco desde el Nodo "+ gasoducto.dameNodo( (int)desde ).getId()+ " hasta el Nodo "+ gasoducto.dameNodo( (int)hasta ).getId()+" con la capacidad "+ gasoducto.dameNodo( (int)desde ).getCapacidad() , "Arco Creado",
						        JOptionPane.INFORMATION_MESSAGE);
					}
					
				}
				catch(Exception x)
				{
					JOptionPane.showMessageDialog(frame, "Nodo inexistente!!", "Warning",
					        JOptionPane.WARNING_MESSAGE);
				}
			}
		} );
		frame.getContentPane().add(btnAgregarArco);
	}
	
	private void FlujoMaximo ()
	{
		JButton btnFlujoMaximo = new JButton("Flujo m\u00E1ximo");
		btnFlujoMaximo.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				FlujoMaximo flujo = new FlujoMaximo( gasoducto );
				JOptionPane.showMessageDialog(frame, "El flujo maximo es: " + flujo.fordFulkerson(), "FLUJO MAXIMO",
				        JOptionPane.QUESTION_MESSAGE);
			}
		});
		btnFlujoMaximo.setBounds(10, 212, 122, 23);
		frame.getContentPane().add(btnFlujoMaximo);
	}
	
	private void Guardar ()
	{
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 2, 58, 21);
		frame.getContentPane().add(menuBar);
		
		JMenu mnArchivo = new JMenu("Archivo");
		menuBar.add(mnArchivo);
		
		JMenuItem guardar = new JMenuItem("Guardar");
		guardar.addActionListener( new ActionListener()
		{
			@Override
			public void actionPerformed ( ActionEvent e )
			{
				
			}
		});
		mnArchivo.add(guardar);
		
		JMenuItem abrir = new JMenuItem("Abrir");
		abrir.addActionListener( new ActionListener()
		{
			@Override
			public void actionPerformed ( ActionEvent e )
			{
				
			}
		} );
		mnArchivo.add(abrir);
		
		JMenuItem cerrar = new JMenuItem("Cerrar");
		cerrar.addActionListener( new ActionListener()
		{
			@Override
			public void actionPerformed ( ActionEvent e )
			{
				System.exit(0);
			}
		} );
		mnArchivo.add(cerrar);
	}
	
	private void tablaDeNodoCreados ()
	{
		tablaDeNodos = new JTable();
		tablaDeNodos.setSurrendersFocusOnKeystroke(true);
		tablaDeNodos.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		tablaDeNodos.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		tablaDeNodos.setBounds(413, 40, 270, 230);
		frame.getContentPane().add(tablaDeNodos);
		
		modelo.addColumn( "ID" );
		modelo.addColumn( "Tipo" );
		modelo.addColumn( "Capacidad" );
		String[] s = new String[3];
		s[0] = "ID";
		s[1] = "Tipo";
		s[2] = "Capacidad";
		modelo.addRow( s );
		tablaDeNodos.setModel(modelo);
		
		JLabel lblCapacidad = new JLabel("Capacidad");
		lblCapacidad.setFont(new Font("Arial", Font.BOLD, 10));
		lblCapacidad.setBounds(142, 44, 122, 14);
		frame.getContentPane().add(lblCapacidad);
		
	}
}
