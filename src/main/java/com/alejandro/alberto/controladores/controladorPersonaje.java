package com.alejandro.alberto.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;

/**
 * Controlador de la vista CreacionPersonaje
 * Permite al jugador ingresar su nombre, salud y fuerza antes de iniciar la
 * partida
 * 
 * @author Alberto García Izquierdo y Alejandro Rey Tostado
 */
public class controladorPersonaje {
    @FXML
    private TextField campoNombre;

    @FXML
    private Spinner<Integer> saludInput;

    @FXML
    private Spinner<Integer> fuerzaInput;

    @FXML
    private Button botonIniciar;

    @FXML
    public void comenzarJuego(ActionEvent event) {
        try {
            String nombreJugador = campoNombre.getText();
            String rutaMapa = "src/main/resources/com/alejandro/alberto/data/escenario.txt";

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/alejandro/alberto/vistas/Juego.fxml"));
            Parent root = loader.load();

            // Accede al controlador y pásale los datos
            JuegoControlador juegoControlador = loader.getController();
            juegoControlador.inicializarDatos(nombreJugador, rutaMapa);

            // Cambia de escena
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
