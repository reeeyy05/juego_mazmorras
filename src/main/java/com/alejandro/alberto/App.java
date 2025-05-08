package com.alejandro.alberto;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Cargar el FXML usando getResource con la ruta correcta
        Parent root = FXMLLoader.load(getClass().getResource("/com/alejandro/alberto/vistas/CreacionPersonaje.fxml"));
        
        Scene scene = new Scene(root, 640, 480);
        stage.setTitle("Creación de Personaje");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}