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
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

/**
 * Clase que controla la lógica del juego
 * 
 * @author Alejandro Rey Tostado y Alberto García Izquierdo
 */
public class controladorJuego {
    private static final int TAMANO_CELDA = 30;
    private static final int MAX_FILAS = 15;
    private static final int MAX_COLUMNAS = 15;

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
    private boolean juegoTerminado = false;

    /**
     * Método que se llama al iniciar el controlador
     * 
     * @param protagonista El protagonista que se va a jugar
     */
    public void setProtagonista(Protagonista protagonista) {
        this.protagonista = protagonista;
        protagonista.setPosicion(7, 7);
        actualizarEstadisticas();

        enemigos = new ArrayList<>();
        int[][] posicionesEnemigos = {{3, 3}, {3, 11}, {11, 3}, {11, 11}};
        for (int[] pos : posicionesEnemigos) {
            Enemigo enemigo = new Enemigo(50, 5, 2, 5);
            enemigo.setPosicion(pos[0], pos[1]);
            enemigos.add(enemigo);
        }

        try {
            controladorJuego.this.cargarEscenario("/com/alejandro/alberto/recursos/escenario.txt");
        } catch (IOException e) {
            System.err.println("Error al cargar escenario: " + e.getMessage());
        }

        dibujarTablero();
    }

    /**
     * Método que se llama al inicializar el controlador
     */
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

    /**
     * Método para cargar el escenario desde un archivo
     * 
     * @param rutaArchivo Ruta del archivo del escenario
     * @throws IOException Si ocurre un error al leer el archivo
     */
    private void cargarEscenario(String rutaArchivo) throws IOException {
        mapa.clear();
        mapaGrid.getChildren().clear();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream(rutaArchivo)))) {

            for (int fila = 0; fila < MAX_FILAS; fila++) {
                String linea = br.readLine();
                if (linea == null) linea = "";

                StringBuilder procesada = new StringBuilder();
                for (int columna = 0; columna < MAX_COLUMNAS; columna++) {
                    char c = (columna < linea.length()) ? linea.charAt(columna) : '-';

                    if (fila == 0 || fila == MAX_FILAS - 1 || columna == 0 || columna == MAX_COLUMNAS - 1) {
                        c = '#';
                    } else if (c != '#' && c != '-') {
                        c = '-';
                    }

                    procesada.append(c);
                    ImageView imageView = new ImageView((c == '#') ? imgPared : imgSuelo);
                    imageView.setFitWidth(TAMANO_CELDA);
                    imageView.setFitHeight(TAMANO_CELDA);
                    mapaGrid.add(imageView, columna, fila);
                }
                mapa.add(procesada.toString());
            }
        }
    }

    /**
     * Método para cargar el escenario
     */
    private void cargarEscenario() {
        mapaGrid.getChildren().clear();
        for (int fila = 0; fila < mapa.size(); fila++) {
            for (int columna = 0; columna < mapa.get(fila).length(); columna++) {
                char c = mapa.get(fila).charAt(columna);
                ImageView imageView = new ImageView((c == '#') ? imgPared : imgSuelo);
                imageView.setFitWidth(TAMANO_CELDA);
                imageView.setFitHeight(TAMANO_CELDA);
                mapaGrid.add(imageView, columna, fila);
            }
        }
    }

    /**
     * Método para dibujar el tablero
     */
    private void dibujarTablero() {
        cargarEscenario();

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

    /**
     * Método para actualizar las estadísticas del protagonista
     */
    private void actualizarEstadisticas() {
        if (protagonista != null) {
            nombreLabel.setText("Nombre: " + protagonista.getNombre());
            saludLabel.setText("Salud: " + protagonista.getSalud());
            fuerzaLabel.setText("Fuerza: " + protagonista.getFuerza());
            defensaLabel.setText("Defensa: " + protagonista.getDefensa());
        }
    }

    /**
     * Método para manejar las teclas presionadas
     * 
     * @param event Evento de la tecla presionada
     */
    @FXML
    public void manejarTeclas(KeyEvent event) {
        if (!protagonista.estaVivo() || juegoTerminado) return;

        switch (event.getCode()) {
            case UP: moverProtagonista(0, -1); break;
            case DOWN: moverProtagonista(0, 1); break;
            case LEFT: moverProtagonista(-1, 0); break;
            case RIGHT: moverProtagonista(1, 0); break;
            default: return;
        }

        moverEnemigos();
        actualizarEstadisticas();
        dibujarTablero();

        if (!protagonista.estaVivo()) {
            juegoTerminado = true;
            mostrarGameOver();
        } else if (enemigos.stream().noneMatch(Enemigo::estaVivo)) {
            juegoTerminado = true;
            mostrarVictoria();
        }
    }

    /**
     * Método para mover al protagonista
     * 
     * @param dx Cambio en la posición X
     * @param dy Cambio en la posición Y
     */
    private void moverProtagonista(int dx, int dy) {
        int nuevaX = protagonista.getX() + dx;
        int nuevaY = protagonista.getY() + dy;

        if (nuevaX < 0 || nuevaY < 0 || nuevaY >= mapa.size() || nuevaX >= mapa.get(nuevaY).length()) return;
        if (mapa.get(nuevaY).charAt(nuevaX) == '#') return;

        for (Enemigo enemigo : enemigos) {
            if (enemigo.getX() == nuevaX && enemigo.getY() == nuevaY && enemigo.estaVivo()) {
                protagonista.atacar(enemigo);
                actualizarEstadisticas();
                return;
            }
        }

        protagonista.setPosicion(nuevaX, nuevaY);
    }

    /**
     * Método para mover a los enemigos
     */
    private void moverEnemigos() {
        for (Enemigo enemigo : enemigos) {
            if (!enemigo.estaVivo()) continue;

            int dx = 0, dy = 0;
            int distancia = Math.abs(enemigo.getX() - protagonista.getX()) + Math.abs(enemigo.getY() - protagonista.getY());

            if (distancia == 1) {
                protagonista.recibirDanio(enemigo.getFuerza());
                actualizarEstadisticas();
                continue;
            }

            if (distancia <= enemigo.getPercepcion()) {
                if (enemigo.getX() != protagonista.getX()) {
                    dx = (enemigo.getX() < protagonista.getX()) ? 1 : -1;
                } else {
                    dy = (enemigo.getY() < protagonista.getY()) ? 1 : -1;
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

            if (nuevaX < 0 || nuevaY < 0 || nuevaY >= mapa.size() || nuevaX >= mapa.get(nuevaY).length()) continue;
            if (mapa.get(nuevaY).charAt(nuevaX) == '#') continue;

            if (nuevaX == protagonista.getX() && nuevaY == protagonista.getY()) {
                protagonista.recibirDanio(enemigo.getFuerza());
                actualizarEstadisticas();
                continue;
            }

            boolean ocupado = false;
            for (Enemigo otro : enemigos) {
                if (otro != enemigo && otro.getX() == nuevaX && otro.getY() == nuevaY && otro.estaVivo()) {
                    ocupado = true;
                    break;
                }
            }

            if (!ocupado) enemigo.setPosicion(nuevaX, nuevaY);
        }
    }

    /**
     * Método para mostrar el mensaje de Game Over
     */
    private void mostrarGameOver() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Fin del juego");
        alert.setHeaderText("Has sido derrotado");
        alert.setContentText("Tu personaje ha perdido toda la vida");
        alert.showAndWait();
        mapaGrid.setDisable(true);
    }

    /**
     * Método para mostrar el mensaje de victoria
     */
    private void mostrarVictoria() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("¡Victoria!");
        alert.setHeaderText("Todos los enemigos han sido derrotados");
        alert.setContentText("¡Has ganado la partida!");
        alert.showAndWait();
        mapaGrid.setDisable(true);
    }
}