package com.alejandro.alberto.controladores;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.alejandro.alberto.modelo.Enemigo;
import com.alejandro.alberto.modelo.Protagonista;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
 * Esta clase controla la lógica principal del juego:
 * movimiento del jugador, combate y carga de enemigos.
 * 
 * @author Alberto García Izquierdo y Alejandro Rey Tostado.
 */
public class controladorJuego {

    @FXML
    private GridPane mapaGrid; // El escenario del juego (paredes y suelos)

    @FXML
    private Protagonista protagonista; // El jugador

    @FXML
    private List<Enemigo> enemigos; // Lista de enemigos

    private Image imgProtagonista = new Image(getClass().getResourceAsStream("/com/alejandro/alberto/imagenes/protagonista.png"));
    private int jugadorColumna = 1;
    private int jugadorFila = 1;
    private List<String> mapa = new ArrayList<>();

    public void inicializarJuego() {
        if (protagonista != null) {
            // Cargar imagen protagonista
            imgProtagonista = new Image(getClass().getResourceAsStream("/com/alejandro/alberto/imagenes/protagonista.png"));
            // Cargar mapa
            cargarMapa("src/main/resources/data/escenario.txt");
            // Crear jugador
            crearJugador();
        }
    }

    /**
     * Carga el mapa desde el archivo
     * Lee cada linea del archivo y almacena las filas en una lista
     * 
     * @param rutaArchivo ruta del archivo que contiene el mapa
     */
    public void cargarMapa(String rutaArchivo) {
        try (BufferedReader buffer = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = buffer.readLine()) != null) {
                mapa.add(linea);
            }
        } catch (IOException e) {
            System.out.println("Error al cargar el mapa" + e.getMessage());
        }
    }

    /**
     * Crea el jugador en su posicion actual en el mapa
     */
    public void crearJugador() {
        mapaGrid.getChildren().clear(); // limpiamos el GridPane para dibujar nuevamente
        ImageView jugador = new ImageView(imgProtagonista); // usamos la imagen cargada previamente
        jugador.setFitWidth(32); // tamaño del sprite
        jugador.setFitHeight(32);
        mapaGrid.add(jugador, jugadorColumna, jugadorFila);
    }
}