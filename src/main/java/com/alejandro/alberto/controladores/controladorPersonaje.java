package com.alejandro.alberto.controladores;

import com.alejandro.alberto.modelo.Protagonista;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private TextField nombre;
    @FXML
    private TextField salud;
    @FXML
    private TextField fuerza;
    @FXML
    private ImageView personaje;

    @FXML
    private void initialize() {
        try {
            // Cargar la imagen del personaje
            Image image = new Image(getClass().getResourceAsStream("/com/alejandro/alberto/data/personaje.png"));
            personaje.setImage(image);

        } catch (Exception e) {
            System.err.println("Error al cargar la imagen: " + e.getMessage());
        }
    }

    @FXML
    public void comenzar(ActionEvent event) {
        try {
            String nombrePersonaje = nombre.getText();

            // Validar nombre
            if (nombrePersonaje.isEmpty()) {
                mostrarAlerta("Error", "Por favor, ingresa un nombre para tu personaje.");
                return;
            }

            // Validar y convertir valores numéricos
            int saludPersonaje = Integer.parseInt(salud.getText());
            int fuerzaPersonaje = Integer.parseInt(fuerza.getText());

            // Validar valores positivos
            if (saludPersonaje <= 0 || fuerzaPersonaje <= 0) {
                mostrarAlerta("Error", "Salud y fuerza deben ser valores positivos.");
                return;
            }

            // Crear protagonista
            Protagonista protagonista = new Protagonista(nombrePersonaje, saludPersonaje, fuerzaPersonaje, 5);

            // Cargar vista del juego
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/alejandro/alberto/vistas/Juego.fxml"));
            Parent root = loader.load();

            // Cambiar escena
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Juego");
            stage.show();

        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Por favor ingresa valores numéricos válidos para salud y fuerza.");
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al iniciar la partida: " + e.getMessage());
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}