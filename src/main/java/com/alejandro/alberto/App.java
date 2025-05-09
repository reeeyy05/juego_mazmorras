package com.alejandro.alberto;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Clase principal que inicia la aplicación del juego de mazmorras.
 * Gestiona el cambio entre vistas
 */
public class App extends Application {

    private static Stage primaryStage;
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        
        // Cargar la vista inicial de creación de personaje
        cargarVistaCreacionPersonaje();
        
        stage.setTitle("Juego de Mazmorras - Creación de Personaje");
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Carga la vista de creación de personaje
     * @throws IOException Si hay error al cargar el FXML
     */
    public static void cargarVistaCreacionPersonaje() throws IOException {
        Parent root = FXMLLoader.load(App.class.getResource("/com/alejandro/alberto/vistas/CreacionPersonaje.fxml"));
        scene = new Scene(root, 768, 512);
        primaryStage.setScene(scene);
    }

    /**
     * Cambia a la vista del juego principal
     * @throws IOException Si hay error al cargar el FXML
     */
    public static void cargarVistaJuego() throws IOException {
        Parent root = FXMLLoader.load(App.class.getResource("/com/alejandro/alberto/vistas/Juego.fxml"));
        scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Juego de Mazmorras");
        
        // Asegurar que el foco esté en la escena para el manejo de teclado
        scene.getRoot().requestFocus();
    }

    public static void main(String[] args) {
        launch(args);
    }
}