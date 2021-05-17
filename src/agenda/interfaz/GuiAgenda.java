package agenda.interfaz;

import agenda.io.AgendaIO;
import agenda.modelo.AgendaContactos;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
<<<<<<< HEAD
import javafx.scene.input.KeyCombination;
=======
import javafx.scene.control.ToggleGroup;
>>>>>>> refs/remotes/origin/master
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GuiAgenda extends Application {
	private AgendaContactos agenda;
	private MenuItem itemImportar;
	private MenuItem itemExportarPersonales;
	private MenuItem itemSalir;

	private MenuItem itemBuscar;
	private MenuItem itemFelicitar;

	private MenuItem itemAbout;

	private TextArea areaTexto;

	private RadioButton rbtListarTodo;
	private RadioButton rbtListarSoloNumero;
	private Button btnListar;

	private Button btnPersonalesEnLetra;
	private Button btnPersonalesOrdenadosPorFecha;

	private TextField txtBuscar;

	private Button btnClear;
	private Button btnSalir;

	@Override
	public void start(Stage stage) {
		agenda = new AgendaContactos(); // el modelo

		BorderPane root = crearGui();

		Scene scene = new Scene(root, 1100, 700);
		stage.setScene(scene);
		stage.setTitle("Agenda de contactos");
		scene.getStylesheets().add(getClass().getResource("/application.css")
		                    .toExternalForm());
		stage.show();

	}

	private BorderPane crearGui() {
		BorderPane panel = new BorderPane();
		panel.setTop(crearBarraMenu());
		panel.setCenter(crearPanelPrincipal());
		return panel; 
	}

	private BorderPane crearPanelPrincipal() {
		BorderPane panel = new BorderPane();
		panel.setPadding(new Insets(10));
		panel.setTop(crearPanelLetras());

		areaTexto = new TextArea();
		areaTexto.getStyleClass().add("textarea");
		panel.setCenter(areaTexto);

		panel.setLeft(crearPanelBotones());
		return panel;
	}

	private VBox crearPanelBotones() {
		// a completar
		VBox panel = new VBox();
		panel.setMaxSize(270, Integer.MAX_VALUE);
		panel.setMinSize(270, 500);
		panel.setPadding(new Insets(10));
		panel.setSpacing(10);
		// Introducimos el area de texto
		TextField text = new TextField("Buscar...");
		text.setMinSize(250, 40);
		text.setMaxSize(250, 40);
		panel.setMargin(text, new Insets(0,0,40,0));
		panel.getChildren().add(text);
		// Creamos un grupo para seleccionar solo una a la vez
		ToggleGroup grupo = new ToggleGroup();
		// Primer boton circular para opcion
		rbtListarTodo = new RadioButton("Listar toda la agenda");
		rbtListarTodo.setMaxSize(270, 20);
		rbtListarTodo.setMinSize(270, 20);
		rbtListarTodo.setToggleGroup(grupo);
		rbtListarTodo.setSelected(true);
		panel.getChildren().add(rbtListarTodo);
		// Segundo boton circular para opcion
		rbtListarSoloNumero = new RadioButton("Lista nº contactos");
		rbtListarSoloNumero.setMaxSize(270, 20);
		rbtListarSoloNumero.setMinSize(270, 20);
		rbtListarSoloNumero.setToggleGroup(grupo);	
		panel.getChildren().add(rbtListarSoloNumero);
		// Creamos el boton de listar
		btnListar = new Button("Listar");
		btnListar.setMaxSize(250,40);
		btnListar.setMinSize(250,40);
		btnListar.getStyleClass().add("botones");
		btnListar.setOnAction(e -> listar());
		panel.setMargin(btnListar,new Insets(0,0,40,0));
		panel.getChildren().add(btnListar);
		//Creamos boton de contactos personales por letra
		btnPersonalesEnLetra = new Button("Contactos personales en letra");
		btnPersonalesEnLetra.setMaxSize(250,40);
		btnPersonalesEnLetra.setMinSize(250,40);
		btnPersonalesEnLetra.getStyleClass().add("botones");
		btnPersonalesEnLetra.setOnAction(e -> contactosPersonalesEnLetra());
		panel.getChildren().add(btnPersonalesEnLetra);
		//Creamos boton de contactos personales ordenados por fecha
		btnPersonalesOrdenadosPorFecha = new Button("Contacto Personales \nordenados por fecha");
		btnPersonalesOrdenadosPorFecha.setMaxSize(250,80);
		btnPersonalesOrdenadosPorFecha.setMinSize(250,80);
		btnPersonalesOrdenadosPorFecha.getStyleClass().add("botones");
		btnPersonalesOrdenadosPorFecha.setOnAction(e -> personalesOrdenadosPorFecha());
		panel.getChildren().add(btnPersonalesOrdenadosPorFecha);
		panel.setMargin(btnPersonalesOrdenadosPorFecha,new Insets(0,0,40,0));
		//Creamos el boton para limpiar la pantalla
		btnClear = new Button("Clear");
		btnClear.setMaxSize(250,40);
		btnClear.setMinSize(250,40);
		btnClear.getStyleClass().add("botones");
		btnClear.setOnAction(e -> clear());
		panel.getChildren().add(btnClear);
		//Creamos el boton para salir la pantalla
		btnSalir = new Button("Salir");
		btnSalir.setMaxSize(250,40);
		btnSalir.setMinSize(250,40);
		btnSalir.getStyleClass().add("botones");
		btnSalir.setOnAction(e -> salir());
		panel.getChildren().add(btnSalir);
		return panel;

		
	}

	private GridPane crearPanelLetras() {
		// a completar
		GridPane panel = new GridPane();

		return panel;
	}

	private MenuBar crearBarraMenu() {

		MenuBar barra = new MenuBar();
		
		Menu archivo = new Menu("Archivo");
		itemImportar = new MenuItem("Importar agenda");
		itemImportar.setAccelerator(KeyCombination.keyCombination("Ctrl+I"));
		itemExportarPersonales = new MenuItem("Exportar personales");
		itemExportarPersonales.setAccelerator(KeyCombination.keyCombination("Ctrl+E"));
		itemSalir = new MenuItem("Salir");
		itemSalir.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
		archivo.getItems().addAll(itemImportar, itemExportarPersonales, itemSalir);
		itemSalir.setOnAction(e -> salir());
		itemExportarPersonales.setOnAction(e -> exportarPersonales());
		itemExportarPersonales.setDisable(true);
		itemImportar.setOnAction(e -> importarAgenda());
		
		Menu operaciones = new Menu("Operaciones");
		itemBuscar = new MenuItem("Buscar");
		itemBuscar.setAccelerator(KeyCombination.keyCombination("Ctrl+B"));
		itemFelicitar = new MenuItem("Felicitar");
		itemFelicitar.setAccelerator(KeyCombination.keyCombination("Ctrl+F"));
		operaciones.getItems().addAll(itemBuscar, itemFelicitar);
		
		Menu help = new Menu("Help");
		itemAbout = new MenuItem("About");
		itemAbout.setAccelerator(KeyCombination.keyCombination("Ctrl+A"));
		help.getItems().addAll(itemAbout);
		
		barra.getMenus().addAll(archivo,operaciones,help);
		
		return barra;
	}

	private void importarAgenda() {
		
		AgendaIO age = new AgendaIO();
		age.importar(agenda, "agenda.csv");
		itemImportar.setDisable(true);
		itemExportarPersonales.setDisable(false);
	}

	private void exportarPersonales() {
		// a completar

	}

	/**
	 *  
	 */
	private void listar() {
		clear();
<<<<<<< HEAD

=======
		System.out.println("hola");
		// a completar
>>>>>>> refs/remotes/origin/master

	}

	private void personalesOrdenadosPorFecha() {
		clear();
		// a completar

	}

	private void contactosPersonalesEnLetra() {
		clear();
		// a completar

	}

	private void contactosEnLetra(char letra) {
		clear();
		// a completar
	}

	private void felicitar() {
		clear();
		// a completar

	}

	private void buscar() {
		clear();
		// a completar

		cogerFoco();

	}

	private void about() {
		// a completar

	}

	private void clear() {
		areaTexto.setText("");
	}

	private void salir() {
		Platform.exit();
	}

	private void cogerFoco() {
		txtBuscar.requestFocus();
		txtBuscar.selectAll();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
