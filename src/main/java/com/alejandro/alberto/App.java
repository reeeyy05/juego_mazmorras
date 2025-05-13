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
 * @author Alejandro Rey Tostado y Alberto García Izquierdo
 */
public class App extends Application {

    private static Stage scenePerso;
    private static Protagonista protagonistaActual;

    /**
     * Método principal de la aplicación
     * 
     * @param args Argumentos de la línea de comandos
     */
    @Override
    public void start(Stage stage) throws IOException {
        scenePerso = stage;
        mostrarVistaCreacionPersonaje();
        stage.setTitle("Juego de Mazmorras - Creación de Personaje");
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Método para mostrar la vista de creación de personaje
     * 
     * @throws IOException Si ocurre un error al cargar la vista
     */
    public static void mostrarVistaCreacionPersonaje() throws IOException {
        Parent root = FXMLLoader.load(App.class.getResource("/com/alejandro/alberto/vistas/CreacionPersonaje.fxml"));
        scenePerso.setScene(new Scene(root, 768, 512));
    }

    /**
     * Método para mostrar la vista del juego
     * 
     * @param protagonista El protagonista que se va a jugar
     * @throws IOException Si ocurre un error al cargar la vista
     */
    public static void mostrarVistaJuego(Protagonista protagonista) throws IOException {
        protagonistaActual = protagonista;
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/com/alejandro/alberto/vistas/Juego.fxml"));
        Parent root = loader.load();

        controladorJuego controller = loader.getController();
        controller.setProtagonista(protagonistaActual);

        scenePerso.setScene(new Scene(root, 800, 600));
        root.requestFocus();
        scenePerso.setTitle("Juego de Mazmorras - " + protagonistaActual.getNombre());
    }

    public static void main(String[] args) {
        launch(args);
    }
}