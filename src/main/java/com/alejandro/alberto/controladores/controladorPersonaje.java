package com.alejandro.alberto.controladores;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
    private Button botonIniciar;

    /**
     * Se ejecuta cuando se presiona el botón de iniciar juego.
     * Carga la interfaz principal del juego (juego.fxml).
     */
    @FXML
    private void iniciarJuego(ActionEvent event) {
        String nombreJugador = campoNombre.getText();

        if (nombreJugador == null || nombreJugador.trim().isEmpty()) {
            System.out.println("Por favor, introduce un nombre.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/alejandro/alberto/vistas/juego.fxml"));
            Parent root = loader.load();


            Stage stage = (Stage) botonIniciar.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
