package agenda.interfaz;

import java.awt.Dialog;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import agenda.io.AgendaIO;
import agenda.modelo.AgendaContactos;
import agenda.modelo.Contacto;
import agenda.modelo.Personal;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCombination;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * Clase GuiAgenda que nos permite crear nuestra aplicación desarrollada con JavaFx. Se trata de una agenda de contactos
 * los cuales pueden ser o bien Personales o bien Profesionales. Dicha agenda tendrá muchas funciones como listarla,
 * felicitar a un contacto, exportar los contactos personales a un archivo de formato txt, etc.
 * @author Unai Pérez
 * @author Enrique Lafraya
 * @author Iñaki Tiraplegui
 *
 */
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
		scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
		stage.show();

	}
	
	/**
	 * Método que creará la parte gráfica de nuestra aplicación, por completo. Eso sí, con ayuda de los demás métodos
	 * de creación de interfaces gráficas.
	 * @return Devuelve un objeto BorderPane.
	 */
	private BorderPane crearGui() {
		BorderPane panel = new BorderPane();
		panel.setTop(crearBarraMenu());
		panel.setCenter(crearPanelPrincipal());
		return panel;
	}

	/**
	 * Método que creará la parte gráfica de nuestra aplicación. Eso sí, con ayuda de los demás métodos
	 * de creación de interfaces gráficas.
	 * @return Devuelve un objeto de tipo BorderPane.
	 */
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

	/**
	 * Método que crea el panel izquierdo de botones los cuales permitiran usar las funciones de nuestra agenda
	 * @return Devuelve un VBox
	 */
	private VBox crearPanelBotones() {
		// a completar
		VBox panel = new VBox();
		panel.setMaxSize(270, Integer.MAX_VALUE);
		panel.setMinSize(270, 500);
		panel.setPadding(new Insets(10));
		panel.setSpacing(10);
		// Introducimos el area de texto
		txtBuscar = new TextField();
		txtBuscar.setPromptText("Buscar...");
		txtBuscar.setMinSize(250, 40);
		txtBuscar.setMaxSize(250, 40);
		panel.setMargin(txtBuscar, new Insets(0, 0, 40, 0));
		txtBuscar.setOnAction(e -> buscar());
		panel.getChildren().add(txtBuscar);
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
		btnListar.setMaxSize(250, 40);
		btnListar.setMinSize(250, 40);
		btnListar.getStyleClass().add("botones");
		btnListar.setOnAction(e -> listar());
		panel.setMargin(btnListar, new Insets(0, 0, 40, 0));
		panel.getChildren().add(btnListar);
		// Creamos boton de contactos personales por letra
		btnPersonalesEnLetra = new Button("Contactos personales en letra");
		btnPersonalesEnLetra.setMaxSize(250, 40);
		btnPersonalesEnLetra.setMinSize(250, 40);
		btnPersonalesEnLetra.getStyleClass().add("botones");
		btnPersonalesEnLetra.setOnAction(e -> contactosPersonalesEnLetra());
		panel.getChildren().add(btnPersonalesEnLetra);
		// Creamos boton de contactos personales ordenados por fecha
		btnPersonalesOrdenadosPorFecha = new Button("Contacto Personales \nordenados por fecha");
		btnPersonalesOrdenadosPorFecha.setMaxSize(250, 80);
		btnPersonalesOrdenadosPorFecha.setMinSize(250, 80);
		btnPersonalesOrdenadosPorFecha.getStyleClass().add("botones");
		btnPersonalesOrdenadosPorFecha.setOnAction(e -> personalesOrdenadosPorFecha());
		panel.getChildren().add(btnPersonalesOrdenadosPorFecha);
		panel.setMargin(btnPersonalesOrdenadosPorFecha, new Insets(0, 0, 40, 0));
		// Creamos el boton para limpiar la pantalla
		btnClear = new Button("Clear");
		btnClear.setMaxSize(250, 40);
		btnClear.setMinSize(250, 40);
		btnClear.getStyleClass().add("botones");
		btnClear.setOnAction(e -> clear());
		panel.getChildren().add(btnClear);
		// Creamos el boton para salir la pantalla
		btnSalir = new Button("Salir");
		btnSalir.setMaxSize(250, 40);
		btnSalir.setMinSize(250, 40);
		btnSalir.getStyleClass().add("botones");
		btnSalir.setOnAction(e -> salir());
		panel.getChildren().add(btnSalir);
		return panel;
	}

	/**
	 * Método que nos devolverá un Panel con botones de la (a - Z)
	 * @return Devuelve el panel con los botones.
	 */
		private GridPane crearPanelLetras() {
			char[] botones = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I','J', 'K', 'L', 'M', 'N', 'Ñ', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
			GridPane panel = new GridPane();
			GridPane.setHgrow(panel, Priority.ALWAYS);
			panel.setAlignment(Pos.CENTER);
			panel.setHgap(5);
			panel.setVgap(5);
			panel.setPadding(new Insets(10));
			int i = 0;
			int j = 0;
			for(i = 0; i < botones.length; ++i) { 
				char tempo = botones[i];
				Button boton = new Button(Character.toString(tempo));
				boton.setOnAction(e -> contactosEnLetra(tempo));
				boton.getStyleClass().add("botonletra");
				boton.setMinSize(70, 40);
				if(i > 13) {
					panel.add(boton,j,1);
					j++;
				}else {
					panel.add(boton,i,0);
				}			
			}
			return panel;
		}

	/**
	 * Método que creará el panel de opciones de la parte superior de nuestra aplicación. Cada opción, tendrá 
	 * una función en específica. A su vez, dichas opciones tienen atajos con el teclado.
	 * @return Devuelve un objeto MenuBar
	 */
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
		itemBuscar.setOnAction(e -> buscar());
		itemFelicitar = new MenuItem("Felicitar");
		itemFelicitar.setAccelerator(KeyCombination.keyCombination("Ctrl+F"));
		itemFelicitar.setOnAction(e -> felicitar());
		operaciones.getItems().addAll(itemBuscar, itemFelicitar);

		Menu help = new Menu("Help");
		itemAbout = new MenuItem("About");
		itemAbout.setAccelerator(KeyCombination.keyCombination("Ctrl+A"));
		help.getItems().addAll(itemAbout);
		itemAbout.setOnAction(e -> about());

		barra.getMenus().addAll(archivo, operaciones, help);

		return barra;
	}

	/**
	 * Método que importará un archivo el cual elijamos en nuestro explorador de archivos del ordenador.
	 */
	private void importarAgenda() {
		Stage ventana = new Stage();
		AgendaIO age = new AgendaIO();
		FileChooser file = new FileChooser();
		File arch = file.showOpenDialog(ventana);
		age.importar(agenda, arch.getName());
		areaTexto.setText(age.errores() + " errores a la hora de importar");
		itemImportar.setDisable(true);
		itemExportarPersonales.setDisable(false);
	}

	/**
	 * Método que imprimirá los contactos personales de la agenda, en un fichero de texto que elijamos.
	 */
	private void exportarPersonales() {
		AgendaIO age = new AgendaIO();
		Stage ventana = new Stage();
		FileChooser file = new FileChooser();
		ExtensionFilter filtro = new ExtensionFilter("TXT", "*.txt");
		file.getExtensionFilters().add(filtro);
		File arch = file.showSaveDialog(ventana);
		age.exportarPersonales(agenda, arch.toString());
		areaTexto.setText("Se han exportado los contactos personales con éxito");
		itemExportarPersonales.setDisable(false);
	}

	/**
	 *  Método que sacará por pantalla la lista completa de los contactos de la agenda. En caso de no haber sido
	 *  importada dicha agenda, nos enseñará un mensaje para advertirnos.
	 */
	private void listar() {
		clear();
		if (!itemImportar.isDisable()) {
			areaTexto.clear();
			areaTexto.setText("La agenda no se ha importado aún");
		} else {
			if (rbtListarTodo.isSelected()) {
				areaTexto.clear();
				areaTexto.setText("\n" + agenda.toString());
			} else if (rbtListarSoloNumero.isSelected()) {
				areaTexto.clear();
				areaTexto.setText("La agenda tiene un total de " + agenda.totalContactos() + " contactos");
			}
		}
	}

	/**
	 * Método que ordenará los contactos de una letra escogida por orden de fecha de nacimiento.
	 */
	private void personalesOrdenadosPorFecha() {
		clear();
		if (itemImportar.isDisable()) {
			ChoiceDialog<Character> dialogo = new ChoiceDialog<Character>('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
					'J', 'K', 'L', 'M', 'N', 'Ñ', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z');
			dialogo.setTitle("PERSONALES ORDENADOS POR FECHA DE NACIMIENTO");
			dialogo.setHeaderText("Elija una letra");
			dialogo.setContentText(null);
			Optional<Character> resul = dialogo.showAndWait();
			if (resul.isPresent()) {
				String salida = "";
				AgendaContactos age = new AgendaContactos();
				List<Personal> contactos = agenda.personalesOrdenadosPorFechaNacimiento(resul.get());
				for (Personal p : contactos) {
					salida = salida + p.toString() + "\n";
				}
				if (salida.equals("")) {
					salida = "No hay personales con esa letra";
				}
				areaTexto.setText(salida);
			} else { 
				dialogo.close();
			}
		} else {
			areaTexto.setText("La agenda no se ha importado aun");
		}
	}

	/**
	 * Método que dependiendo que letra escojamos, nos mostrará en pantalla que contactos PERSONALES
	 * contiene dicha letra.
	 */
	private void contactosPersonalesEnLetra() {
		clear();
		if (itemImportar.isDisable()) {
			ChoiceDialog<Character> dialogo = new ChoiceDialog<Character>('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
					'J', 'K', 'L', 'M', 'N', 'Ñ', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z');
			dialogo.setTitle("CONTACTOS PERSONALES EN LETRA");
			dialogo.setHeaderText("Elija una letra");
			dialogo.setContentText(null);
			Optional<Character> resul = dialogo.showAndWait();
			if (resul.isPresent()) {
				String salida = "";
				List<Personal> contactos = agenda.personalesEnLetra(resul.get());
				for (Personal p : contactos) {
					salida = salida + p.toString() + "\n";
				}
				if (salida.equals("")) {
					salida = "No hay ningun contacto personal la letra " + resul.get();
				}
				areaTexto.setText(salida);
			} else {
				dialogo.close();
			}
		} else {
			areaTexto.setText("La agenda no se ha importado aun");
		}
	}
	/**
	 * Método que saca por pantalla una lista con los contactos que tiene una letra
	 * @param Introducimos un carácter 
	 * 
	 */
	private void contactosEnLetra(char letra) {
		clear();
		String salida = "";
		if(itemImportar.isDisable()) {
			if (agenda.contactosEnLetra2(letra) == null) {
				salida = "No Hay Contactos que comiencen con la " + letra;
			}else {
				for(Contacto contacto: agenda.contactosEnLetra2(letra))
				salida += contacto.toString() + "\n";
			}
			areaTexto.setText(letra + "\n" + salida);
		} else {
			salida = "No has importado la agenda";
		}
		areaTexto.setText(salida);
	}

	/**
	 * Método el cual felicitará a una persona de la agenda en caso de coincidir su fecha de cumpleaños con la fecha actual.
	 */
	private void felicitar() {
		clear();
		if (itemImportar.isDisable()) {
			String salida = "";
			List<Personal> contactos = agenda.felicitar();
			for (Personal p : contactos) {
				salida = salida + p.toString() + "\n";
			}
			if (salida.equals("")) {
				salida = "No hay ningun contacto que cumpla hoy";
			}
			areaTexto.setText(salida);
		} else {
			areaTexto.setText("La agenda no se ha importado aun");
		}
	}

	/**
	 * Método que a través de una barra de búsqueda, podemos encontrar los contactos cuyo apellido contenga
	 * el texto que recién hemos introducido en dicha barra.
	 */
	private void buscar() {
		clear();
		if(itemImportar.isDisable()) {
		String salida = "";
		txtBuscar.getText().toUpperCase();
		if (txtBuscar.getText().equals("")) {
			areaTexto.clear();
			areaTexto.setText("No has escrito nada para buscar, intente de nuevo escribiéndo algo");
		} else {
			areaTexto.clear();
			agenda.buscarContactos(txtBuscar.getText());
			List<Contacto> contactos = agenda.buscarContactos(txtBuscar.getText());
			for(Contacto c : contactos) {
				salida += c.toString() + "\n";
			}
			areaTexto.setText(salida);
			areaTexto.appendText("Se han encontrado " + contactos.size() + " contactos que contienen: "+ "'" + txtBuscar.getText() + "'");
			if(salida.equals("")) {
				areaTexto.setText("No se ha encontrado nada que contenga: " + "'" + txtBuscar.getText() + "'");
			}
		} 
		}else {
			areaTexto.setText("No se ha importado la agenda aun");		
		}
		cogerFoco();
	}

	/**
	 * Método que nos muestra un mensaje de alarma el cual nos muestra una ilustración sobre la información de nuestra agenda.
	 */
	private void about() {

		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		DialogPane dialogPane = alert.getDialogPane();

		dialogPane.setHeaderText(null);
		dialogPane.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
		dialogPane.getStyleClass().add("dialog-pane");

		alert.setTitle("Alerta de agenda de contactos");
		alert.setContentText("Mi agenda de contactos");
		alert.showAndWait();
	}

	/**
	 * Método para resetear el contenido del area de texto de la agenda de contactos.
	 */
	private void clear() {
		areaTexto.setText("");
	}

	/**
	 * Método usado para salir de la aplicación.
	 */
	private void salir() {
		Platform.exit();
	}

	private void cogerFoco() {
		txtBuscar.requestFocus();
		txtBuscar.selectAll();
	}

	/**
	 * Método de prueba de la aplicación.
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
