package com.alejandro.alberto.controladores;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.alejandro.alberto.modelo.Celda;
import com.alejandro.alberto.modelo.Enemigo;
import com.alejandro.alberto.modelo.Mapa;
import com.alejandro.alberto.modelo.Personaje;
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

    private Image imgProtagonista = new Image(
            getClass().getResourceAsStream("/com/alejandro/alberto/imagenes/protagonista.png"));
    private int jugadorColumna = 1;
    private int jugadorFila = 1;
    private Mapa mapa;

    public void inicializarJuego() {
        if (protagonista != null) {
            // Cargar imagen protagonista
            imgProtagonista = new Image(
                    getClass().getResourceAsStream("/com/alejandro/alberto/imagenes/protagonista.png"));
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
                mapa = new Mapa(rutaArchivo);
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

    /**
     * Mueve al jugador en la dirección indicada
     * 
     * @param dx desplazamiento en x
     * @param dy desplazamiento en y
     */
    private void moverProtagonista(int dx, int dy) {
        int nuevaX = protagonista.getX() + dx;
        int nuevaY = protagonista.getY() + dy;

        // Obtener la matriz de celdas directamente
         Celda[][] celdas = mapa.getCeldas();

        // Verificar límites del mapa (alto = filas, ancho = columnas)
        if (nuevaY < 0 || nuevaY >= celdas.length || nuevaX < 0 || nuevaX >= celdas[0].length) {
            return;
        }

        Celda destino = celdas[nuevaY][nuevaX];

        // Verificar si es pared
        if (destino.getTipo().equals("pared")) {
            return;
        }

        // Verificar si hay enemigo y atacarlo
        if (destino.estaOcupada() && destino.getPersonaje() instanceof Enemigo) {
            Enemigo enemigo = (Enemigo) destino.getPersonaje();
            enemigo.recibirDanio(protagonista.getFuerza());

            System.out.println("Atacaste a " + enemigo.getNombre() + ", salud restante: " + enemigo.getSalud());

            if (!enemigo.estaVivo()) {
                destino.setPersonaje(null);
                enemigos.remove(enemigo);
                System.out.println(enemigo.getNombre() + " ha sido derrotado.");
            }

            return;
        }

        // Mover si está libre
        Celda actual = celdas[protagonista.getY()][protagonista.getX()];
        actual.setPersonaje(null);
        protagonista.setX(nuevaX);
        protagonista.setY(nuevaY);
        destino.setPersonaje(protagonista);
    }

}