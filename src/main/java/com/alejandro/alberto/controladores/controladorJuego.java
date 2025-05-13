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
 * Controlador del juego.
 * Maneja la logica del juego, el movimiento del protagonista y los enemigos,
 * y la interaccion con el escenario.
 * 
 * @author Alejandro Rey Tostado y Alberto Garcia Izquierdo
 */
public class controladorJuego {
    private static final int TAMANO_CELDA = 30;
    private static final int MAX_FILAS = 15;
    private static final int MAX_COLUMNAS = 15;

    @FXML private GridPane mapaGrid;
    @FXML private Label turnoLabel;
    @FXML private Label nombreLabel;
    @FXML private Label saludLabel;
    @FXML private Label fuerzaLabel;
    @FXML private Label defensaLabel;

    private boolean turnoJugador = true;
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
     * Establece el protagonista del juego.
     * Inicializa el escenario y los enemigos.
     * 
     * @param protagonista El protagonista del juego.
     */
    public void setProtagonista(Protagonista protagonista) {
        this.protagonista = protagonista;
        protagonista.setPosicion(7, 7);
        actualizarEstadisticas();

        cargarEnemigosDesdeArchivo("/com/alejandro/alberto/recursos/enemigos.txt");

        try {
            cargarEscenario("/com/alejandro/alberto/recursos/escenario.txt");
        } catch (IOException e) {
            System.err.println("Error al cargar escenario: " + e.getMessage());
        }

        dibujarTablero();
    }

    /**
     * Inicializa los recursos del juego.
     * Carga las imagenes y establece el foco en el GridPane.
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
     * Carga los enemigos desde un archivo de texto.
     * Cada linea del archivo debe contener la fila y columna del enemigo separados por una coma.
     * 
     * @param ruta Ruta del archivo de texto con los enemigos.
     */
    private void cargarEnemigosDesdeArchivo(String ruta) {
        enemigos = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream(ruta)))) {

            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 2) {
                    int fila = Integer.parseInt(partes[0].trim());
                    int columna = Integer.parseInt(partes[1].trim());

                    Enemigo enemigo = new Enemigo(50, 10, 5, 5); // valores por defecto
                    enemigo.setPosicion(columna, fila); // columna = x, fila = y
                    enemigos.add(enemigo);
                }
            }

        } catch (IOException e) {
            System.err.println("Error al cargar enemigos: " + e.getMessage());
        }
    }

    /**
     * Carga el escenario desde un archivo de texto.
     * Cada linea del archivo representa una fila del escenario.
     * 
     * @param rutaArchivo Ruta del archivo de texto con el escenario.
     * @throws IOException Si ocurre un error al leer el archivo.
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
     * Carga el escenario en el GridPane.
     * Limpia el GridPane y dibuja el escenario basado en la lista de mapas.
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
     * Dibuja el tablero en el GridPane.
     * Limpia el GridPane y dibuja el escenario, los enemigos y el protagonista.
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
     * Actualiza las estadisticas del protagonista en la interfaz.
     * Muestra el nombre, salud, fuerza y defensa del protagonista.
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
     * Maneja las teclas presionadas por el jugador.
     * Mueve al protagonista en la direccion correspondiente.
     * Si el juego ha terminado o no es el turno del jugador, no hace nada.
     * 
     * @param event El evento de la tecla presionada.
     */
    @FXML
    public void manejarTeclas(KeyEvent event) {
        if (!protagonista.estaVivo() || juegoTerminado || !turnoJugador)
            return;

        switch (event.getCode()) {
            case UP: moverProtagonista(0, -1); break;
            case DOWN: moverProtagonista(0, 1); break;
            case LEFT: moverProtagonista(-1, 0); break;
            case RIGHT: moverProtagonista(1, 0); break;
            default: return;
        }

        actualizarEstadisticas();
        dibujarTablero();

        turnoJugador = false;
        turnoLabel.setText("Turno: Enemigos");

        if (!protagonista.estaVivo()) {
            juegoTerminado = true;
            mostrarGameOver();
        } else {
            new Thread(() -> {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {}
                javafx.application.Platform.runLater(() -> {
                    moverEnemigos();
                    actualizarEstadisticas();
                    dibujarTablero();
                    turnoJugador = true;
                    turnoLabel.setText("Turno: " + protagonista.getNombre());

                    if (!protagonista.estaVivo()) {
                        juegoTerminado = true;
                        mostrarGameOver();
                    } else if (enemigos.stream().noneMatch(Enemigo::estaVivo)) {
                        juegoTerminado = true;
                        mostrarVictoria();
                    }
                });
            }).start();
        }
    }

    /**
     * Mueve al protagonista en la direccion especificada.
     * Verifica si el movimiento es valido y si hay enemigos en la nueva posicion.
     * 
     * @param dx Desplazamiento en el eje X.
     * @param dy Desplazamiento en el eje Y.
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
     * Mueve a los enemigos en el tablero.
     * Si un enemigo se encuentra adyacente al protagonista, lo ataca.
     */
    private void moverEnemigos() {
        for (Enemigo enemigo : enemigos) {
            if (!enemigo.estaVivo()) continue;

            int dx = 0, dy = 0;
            int distancia = Math.abs(enemigo.getX() - protagonista.getX())
                    + Math.abs(enemigo.getY() - protagonista.getY());

            if (distancia == 1) {
                protagonista.recibirDanio(enemigo.getFuerza());
                actualizarEstadisticas();
                continue;
            }

            if (distancia <= enemigo.getPercepcion()) {
                dx = (enemigo.getX() < protagonista.getX()) ? 1 : (enemigo.getX() > protagonista.getX()) ? -1 : 0;
                dy = (enemigo.getY() < protagonista.getY()) ? 1 : (enemigo.getY() > protagonista.getY()) ? -1 : 0;
            } else {
                switch (random.nextInt(4)) {
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

            boolean ocupado = enemigos.stream().anyMatch(e -> e != enemigo && e.getX() == nuevaX && e.getY() == nuevaY && e.estaVivo());
            if (!ocupado) enemigo.setPosicion(nuevaX, nuevaY);
        }
    }

    /**
     * Muestra un mensaje de Game Over.
     * Informa al jugador que ha sido derrotado y termina el juego.
     */
    private void mostrarGameOver() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Fin del juego");
        alert.setHeaderText("Has sido derrotado");
        alert.setContentText("Tu personaje ha perdido toda la vida");
        alert.showAndWait();
        System.exit(0); // Termina el juego
    }

    /**
     * Muestra un mensaje de victoria.
     * Informa al jugador que ha derrotado a todos los enemigos y termina el juego.
     */
    private void mostrarVictoria() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("¡Victoria!");
        alert.setHeaderText("Todos los enemigos han sido derrotados");
        alert.showAndWait();
        System.exit(0); // Termina el juego
    }
}