package com.alejandro.alberto;

import java.io.IOException;

import com.alejandro.alberto.controladores.controladorJuego;
import com.alejandro.alberto.modelo.Protagonista;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Clase principal de la aplicación
 * 
 * @author  Alejandro Rey Tostado y Alberto García Izquierdo
 */

public class App extends Application {

    private static Stage primaryStage;
    private static Protagonista protagonistaActual;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        mostrarVistaCreacionPersonaje();
        stage.setTitle("Juego de Mazmorras - Creación de Personaje");
        stage.setResizable(false);
        stage.show();
    }

    public static void mostrarVistaCreacionPersonaje() throws IOException {
        Parent root = FXMLLoader.load(App.class.getResource("/com/alejandro/alberto/vistas/CreacionPersonaje.fxml"));
        primaryStage.setScene(new Scene(root, 768, 512));
    }

    public static void mostrarVistaJuego(Protagonista protagonista) throws IOException {
        protagonistaActual = protagonista;
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/com/alejandro/alberto/vistas/Juego.fxml"));
        Parent root = loader.load();
        
        controladorJuego controller = loader.getController();
        controller.setProtagonista(protagonistaActual); // Configura el protagonista

        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.setTitle("Juego de Mazmorras - " + protagonistaActual.getNombre());
        root.requestFocus();
    }

    public static void main(String[] args) {
        launch(args);
    }
}