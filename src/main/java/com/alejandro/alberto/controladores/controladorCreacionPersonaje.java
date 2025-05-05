package com.alejandro.alberto.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controlador de la vista CreacionPersonaje.fxml.
 * Permite al jugador ingresar su nombre antes de iniciar la partida.
 * 
 * @author Alberto García Izquierdo y Alejandro Rey Tostado
 */
public class controladorCreacionPersonaje {

    @FXML
    private TextField campoNombre; // Campo de texto donde el jugador escribe su nombre

    @FXML
    private Button botonAceptar;   // Botón para confirmar el nombre

    private String nombreJugador;  // Variable que guarda el nombre escrito

    /**
     * Método que se llama al hacer clic en el botón "Aceptar".
     * Guarda el nombre ingresado y cierra la ventana.
     */
    @FXML
    private void manejarAceptar() {
        nombreJugador = campoNombre.getText().trim(); // Obtener texto quitando espacios
        if (!nombreJugador.isEmpty()) {
            System.out.println("Nombre ingresado: " + nombreJugador);
            cerrarVentana();
        } else {
            System.out.println("Por favor, escribe un nombre.");
        }
    }

    /**
     * Cierra la ventana actual.
     */
    private void cerrarVentana() {
        Stage stage = (Stage) botonAceptar.getScene().getWindow();
        stage.close();
    }

    /**
     * Método para recuperar el nombre que se introdujo.
     * @return nombre del jugador
     */
    public String getNombreJugador() {
        return nombreJugador;
    }
}