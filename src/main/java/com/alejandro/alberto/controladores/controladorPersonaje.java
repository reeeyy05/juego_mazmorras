package com.alejandro.alberto.controladores;

import com.alejandro.alberto.modelo.Protagonista;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
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
    /**
     * Campo que representa el nombre del personaje
     */
    @FXML
    private TextField nombre;

    /**
     * Campo que representa la salud del personaje
     */
    @FXML
    private Spinner<Integer> salud;

    /**
     * Campo que representa la fuerza del personaje
     */
    @FXML
    private Spinner<Integer> fuerza;

    /**
     * Campo que representa la imagen del personaje
     * Se carga desde el archivo de data
     */
    @FXML
    private ImageView personaje;

    @FXML
    private void initialize() {
        // Cargar la imagen del personaje desde el archivo de data
        personaje.setImage(new ImageView("data/Personaje.png").getImage());
        // Inicializar los valores de salud y fuerza
        salud.getValueFactory().setValue(100); // Valor por defecto de salud
        fuerza.getValueFactory().setValue(10); // Valor por defecto de fuerza
    }

    @FXML
    public  void comenzar(ActionEvent event) {
        try {
            // Obtener los valores ingresados por el usuario
            String nombrePersonaje = nombre.getText();
            int saludPersonaje = salud.getValue();
            int fuerzaPersonaje = fuerza.getValue();

            // Validar que el nombre no esté vacío
            if (nombrePersonaje.isEmpty()) {
                System.out.println("Por favor, ingresa un nombre para tu personaje.");
                return;
            }
            // Crear el protagonista con los valores ingresados
            Protagonista protagonista = new Protagonista(nombrePersonaje, saludPersonaje, fuerzaPersonaje, 5); // Defensa por defecto
            
            // Cargar nueva vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/alejandro/alberto/vistas/Juego.fxml"));
            // Esto nos permite cargar la vista de Juego
            Parent root = loader.load();

            // Cambiar la escena
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Juego");
            stage.show();

        } catch (Exception e) {
            System.out.println("Error al iniciar la partida: " + e.getMessage());
        }
    }
    
}