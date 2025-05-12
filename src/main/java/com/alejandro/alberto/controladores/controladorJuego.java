package com.alejandro.alberto.controladores;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
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

    @FXML private GridPane mapaGrid;
    @FXML private Label nombreLabel;
    @FXML private Label saludLabel;
    @FXML private Label fuerzaLabel;
    @FXML private Label defensaLabel;

    private Protagonista protagonista;
    private List<Enemigo> enemigos;
    private Image imgProta;
    private Image imgEnemigo;
    private Image imgSuelo;
    private Image imgPared;
    private Random random = new Random();
    private List<String> mapa = new ArrayList<>();

    public void setProtagonista(Protagonista protagonista) {
        this.protagonista = protagonista;
        protagonista.setPosicion(1, 1);
        actualizarEstadisticas();

        enemigos = new ArrayList<>();
        int[][] posicionesEnemigos = {{3, 3}, {3, 11}, {11, 3}, {11, 11}};
        for (int[] pos : posicionesEnemigos) {
            Enemigo enemigo = new Enemigo(50, 5, 2, 5);
            enemigo.setPosicion(pos[0], pos[1]);
            enemigos.add(enemigo);
        }

        try {
            cargarEscenario("/com/alejandro/alberto/recursos/escenario.txt");
        } catch (IOException e) {
            System.err.println("Error al cargar escenario: " + e.getMessage());
        }

        dibujarTablero();
    }

    public void initialize() {
        try {
            imgProta = new Image(getClass().getResource("/com/alejandro/alberto/recursos/personaje.png").toExternalForm());
            imgEnemigo = new Image(getClass().getResource("/com/alejandro/alberto/recursos/enemigo.png").toExternalForm());
            imgSuelo = new Image(getClass().getResource("/com/alejandro/alberto/recursos/suelo.png").toExternalForm());
            imgPared = new Image(getClass().getResource("/com/alejandro/alberto/recursos/pared.png").toExternalForm());

            mapaGrid.setFocusTraversable(true);
            mapaGrid.requestFocus();

        } catch (Exception e) {
            System.err.println("Error al inicializar el juego: " + e.getMessage());
        }
    }

    private void cargarEscenario(String rutaArchivo) throws IOException {
        mapa.clear();
        mapaGrid.getChildren().clear();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream(rutaArchivo)))) {
            String linea;
            int fila = 0;

            while ((linea = br.readLine()) != null) {
                mapa.add(linea);
                for (int columna = 0; columna < linea.length(); columna++) {
                    char celda = linea.charAt(columna);
                    ImageView imageView = null;

                    if (celda == '#') {
                        imageView = new ImageView(imgPared);
                    } else if (celda == '-') {
                        imageView = new ImageView(imgSuelo);
                    }

                    if (imageView != null) {
                        imageView.setFitWidth(TAMANO_CELDA);
                        imageView.setFitHeight(TAMANO_CELDA);
                        mapaGrid.add(imageView, columna, fila);
                    }
                }
                fila++;
            }
        }
    }

    private void dibujarTablero() {
        for (Enemigo enemigo : enemigos) {
            if (enemigo.estaVivo()) {
                ImageView enemigoView = new ImageView(imgEnemigo);
                enemigoView.setFitWidth(TAMANO_CELDA);
                enemigoView.setFitHeight(TAMANO_CELDA);
                mapaGrid.add(enemigoView, enemigo.getX(), enemigo.getY());
            }
        }

        if (protagonista != null) {
            ImageView jugadorView = new ImageView(imgProta);
            jugadorView.setFitWidth(TAMANO_CELDA);
            jugadorView.setFitHeight(TAMANO_CELDA);
            mapaGrid.add(jugadorView, protagonista.getX(), protagonista.getY());
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
            case UP: moverProtagonista(0, -1); break;
            case DOWN: moverProtagonista(0, 1); break;
            case LEFT: moverProtagonista(-1, 0); break;
            case RIGHT: moverProtagonista(1, 0); break;
            default: return;
        }
        moverEnemigos();
        dibujarTablero();
        actualizarEstadisticas();
    }

    private void moverProtagonista(int dx, int dy) {
        int nuevaX = protagonista.getX() + dx;
        int nuevaY = protagonista.getY() + dy;

        if (nuevaX < 0 || nuevaY < 0 || nuevaY >= mapa.size() || nuevaX >= mapa.get(nuevaY).length()) {
            return;
        }

        char destino = mapa.get(nuevaY).charAt(nuevaX);
        if (destino == '#') return;

        protagonista.setPosicion(nuevaX, nuevaY);
    }

    private void moverEnemigos() {
        for (Enemigo enemigo : enemigos) {
            if (!enemigo.estaVivo()) continue;

            int dx = 0, dy = 0;
            int distancia = Math.abs(enemigo.getX() - protagonista.getX()) + 
                            Math.abs(enemigo.getY() - protagonista.getY());

            if (distancia <= enemigo.getPercepcion()) {
                if (enemigo.getX() != protagonista.getX()) {
                    dx = (enemigo.getX() < protagonista.getX()) ? 1 : -1;
                } else {
                    dy = (enemigo.getY() < protagonista.getY()) ? 1 : -1;
                }

                if (distancia == 1) {
                    enemigo.atacar(protagonista);
                    continue;
                }
            } else {
                int direccion = random.nextInt(4);
                switch (direccion) {
                    case 0: dy = -1; break;
                    case 1: dy = 1; break;
                    case 2: dx = -1; break;
                    case 3: dx = 1; break;
                }
            }

            int nuevaX = enemigo.getX() + dx;
            int nuevaY = enemigo.getY() + dy;

            if (nuevaX < 0 || nuevaY < 0 || nuevaY >= mapa.size() || nuevaX >= mapa.get(nuevaY).length()) {
                continue;
            }

            char destino = mapa.get(nuevaY).charAt(nuevaX);
            if (destino == '#') continue;

            boolean posicionOcupada = false;
            for (Enemigo otro : enemigos) {
                if (otro != enemigo && otro.getX() == nuevaX && otro.getY() == nuevaY && otro.estaVivo()) {
                    posicionOcupada = true;
                    break;
                }
            }

            if (!posicionOcupada && !(nuevaX == protagonista.getX() && nuevaY == protagonista.getY())) {
                enemigo.setPosicion(nuevaX, nuevaY);
            }
        }
    }
}
