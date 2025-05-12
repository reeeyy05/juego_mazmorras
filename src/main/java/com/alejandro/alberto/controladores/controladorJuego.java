package com.alejandro.alberto.controladores;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import com.alejandro.alberto.modelo.Enemigo;
import com.alejandro.alberto.modelo.Protagonista;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

public class controladorJuego {
    private static final int TAMANO_CELDA = 30;
    private static final int TAMANO_TABLERO = 15;

    @FXML
    private GridPane mapaGrid;
    @FXML
    private Label nombreLabel;
    @FXML
    private Label saludLabel;
    @FXML
    private Label fuerzaLabel;
    @FXML
    private Label defensaLabel;

    private Protagonista protagonista;
    private List<Enemigo> enemigos;
    private Image imgProta;
    private Image imgEnemigo;
    private Image imgSuelo;
    private Image imgPared;
    private Random random = new Random();

    public void setProtagonista(Protagonista protagonista) {
        this.protagonista = protagonista;
        protagonista.setPosicion(1, 1); // Posición inicial del protagonista
        actualizarEstadisticas();
        dibujarTablero();
    }

    public void initialize() {
        try {
            imgProta = new Image("/com/alejandro/alberto/recursos/personaje.png");
            imgEnemigo = new Image("/com/alejandro/alberto/recursos/enemigo.png");
            imgSuelo = new Image("/com/alejandro/alberto/recursos/suelo.png");
            imgPared = new Image("/com/alejandro/alberto/recursos/pared.png");

            cargarEscenario("src/main/resources/com/alejandro/alberto/recursos/escenario.txt");

            // Solicita el foco para capturar las teclas
            mapaGrid.setFocusTraversable(true);
            mapaGrid.requestFocus();

        } catch (Exception e) {
            System.err.println("Error al inicializar el juego: " + e.getMessage());
        }
    }

    private void cargarEscenario(String rutaArchivo) throws IOException {
        mapaGrid.getChildren().clear();

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            int fila = 0;

            while ((linea = br.readLine()) != null) {
                for (int columna = 0; columna < linea.length(); columna++) {
                    char celda = linea.charAt(columna);
                    ImageView imageView = null;

                    // Determinar la imagen según el carácter
                    if (celda == 'P') {
                        imageView = new ImageView(imgPared);
                    } else if (celda == 'S') {
                        imageView = new ImageView(imgSuelo);
                    }

                    if (imageView != null) {
                        imageView.setFitWidth(TAMANO_CELDA - 2);
                        imageView.setFitHeight(TAMANO_CELDA - 2);
                        mapaGrid.add(imageView, columna, fila); // Agregar la imagen al GridPane
                    }
                }
                fila++;
            }
        }
    }

    private void dibujarTablero() {
        // Dibujar al protagonista
        if (protagonista != null) {
            ImageView jugadorView = new ImageView(imgProta);
            jugadorView.setFitWidth(TAMANO_CELDA - 2);
            jugadorView.setFitHeight(TAMANO_CELDA - 2);
            mapaGrid.add(jugadorView, protagonista.getX(), protagonista.getY());
        }

        // Dibujar enemigos
        if (enemigos != null) {
            for (Enemigo enemigo : enemigos) {
                if (enemigo.estaVivo()) {
                    ImageView enemigoView = new ImageView(imgEnemigo);
                    enemigoView.setFitWidth(TAMANO_CELDA - 2);
                    enemigoView.setFitHeight(TAMANO_CELDA - 2);
                    mapaGrid.add(enemigoView, enemigo.getX(), enemigo.getY());
                }
            }
        }
    }

    private void actualizarEstadisticas() {
        if (protagonista != null) {
            nombreLabel.setText("Nombre: " + protagonista.getNombre());
            saludLabel.setText("Salud: " + protagonista.getSalud());
            fuerzaLabel.setText("Fuerza: " + protagonista.getFuerza());
            defensaLabel.setText("Defensa: " + protagonista.getDefensa());
        }
    }

    @FXML
    public void manejarTeclas(KeyEvent event) {
        switch (event.getCode()) {
            case UP:
                moverProtagonista(0, -1);
                break;
            case DOWN:
                moverProtagonista(0, 1);
                break;
            case LEFT:
                moverProtagonista(-1, 0);
                break;
            case RIGHT:
                moverProtagonista(1, 0);
                break;
            default:
                return;
        }
        dibujarTablero();
        actualizarEstadisticas();
    }

    private void moverProtagonista(int dx, int dy) {
        int nuevaX = protagonista.getX() + dx;
        int nuevaY = protagonista.getY() + dy;

        if (nuevaX < 0 || nuevaY < 0 || nuevaX >= TAMANO_TABLERO || nuevaY >= TAMANO_TABLERO) {
            return;
        }

        protagonista.setPosicion(nuevaX, nuevaY);
    }
}
