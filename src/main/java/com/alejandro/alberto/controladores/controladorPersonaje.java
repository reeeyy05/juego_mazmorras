package com.alejandro.alberto.controladores;

import com.alejandro.alberto.App;
import com.alejandro.alberto.modelo.Protagonista;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Clase controladorPersonaje que maneja la creacion del personaje
 *
 * @author Alejandro Rey Tostado y Alberto Garcia Izquierdo
 */
public class controladorPersonaje {
    @FXML
    private TextField nombre;
    @FXML
    private TextField salud;
    @FXML
    private TextField fuerza;
    @FXML
    private ImageView personaje;

    /**
     * Metodo que se ejecuta al inicializar la vista
     */
    @FXML
    private void initialize() {
        try {
            Image image = new Image(getClass().getResourceAsStream("/com/alejandro/alberto/recursos/personaje.png"));
            personaje.setImage(image);
        } catch (Exception e) {
            System.err.println("Error al cargar la imagen: " + e.getMessage());
        }
    }

    /**
     * Metodo que se ejecuta al pulsar el boton de comenzar
     *
     * @param event evento de pulsar el boton
     */
    @FXML
    public void comenzar(ActionEvent event) {
        try {
            String nombrePersonaje = nombre.getText();
            if (nombrePersonaje.isEmpty()) {
                mostrarAlerta("Error", "Por favor, ingresa un nombre para tu personaje.");
                return;
            }

            int saludPersonaje = salud.getText().isEmpty() ? 100 : Integer.parseInt(salud.getText());
            int fuerzaPersonaje = fuerza.getText().isEmpty() ? 10 : Integer.parseInt(fuerza.getText());

            if (saludPersonaje <= 0 || fuerzaPersonaje <= 0) {
                mostrarAlerta("Error", "Salud y fuerza deben ser valores positivos.");
                return;
            }

            Protagonista protagonista = new Protagonista(nombrePersonaje, saludPersonaje, fuerzaPersonaje, 5);
            App.mostrarVistaJuego(protagonista);

        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Por favor ingresa valores numéricos válidos.");
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al iniciar el juego: " + e.getMessage());
        }
    }

    /**
     * Muestra una alerta de tipo error con un titulo especificado y un mensaje informativo.
     *
     * @param titulo el título de la alerta
     * @param mensaje el mensaje de contenido de la alerta
     */
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}