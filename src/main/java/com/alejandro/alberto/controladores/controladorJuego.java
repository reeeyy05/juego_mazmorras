package com.alejandro.alberto.controladores;

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
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class controladorJuego {
    private static final int TAMANO_TABLERO = 15;

    @FXML private GridPane mapaGrid;
    @FXML private Label nombreLabel;
    @FXML private Label saludLabel;
    @FXML private Label fuerzaLabel;
    @FXML private Label defensaLabel;
    
    private Protagonista protagonista;
    private List<Enemigo> enemigos;
    private Image imgProta;
    private Image imgEnemigo;
    private Image imgTablero;
    private Random random = new Random();

    public void setProtagonista(Protagonista protagonista) {
        this.protagonista = protagonista;
        protagonista.setPosicion(1, 1);
        actualizarEstadisticas();
    }

    public void initialize() {
        try {
            // Cargar imágenes
            imgProta = new Image(getClass().getResourceAsStream("/com/alejandro/alberto/data/protagonista.png"));
            imgEnemigo = new Image(getClass().getResourceAsStream("/com/alejandro/alberto/data/enemigo.png"));
            imgTablero = new Image(getClass().getResourceAsStream("/com/alejandro/alberto/data/tablero.png"));
            
            // Inicializar enemigos
            enemigos = new ArrayList<>();
            int[][] posicionesEnemigos = {{3,3}, {3,11}, {11,3}, {11,11}};
            for (int[] pos : posicionesEnemigos) {
                Enemigo enemigo = new Enemigo(50, 5, 2, 5);
                enemigo.setPosicion(pos[0], pos[1]);
                enemigos.add(enemigo);
            }
            
            dibujarTablero();
            
        } catch (Exception e) {
            System.err.println("Error al inicializar el juego: " + e.getMessage());
        }
    }

    private void dibujarTablero() {
        mapaGrid.getChildren().clear();
        
        // Configurar GridPane
        for (int i = 0; i < TAMANO_TABLERO; i++) {
            mapaGrid.getRowConstraints().add(new RowConstraints(30));
            mapaGrid.getColumnConstraints().add(new ColumnConstraints(30));
        }

        // Dibujar tablero
        ImageView tableroView = new ImageView(imgTablero);
        tableroView.setFitWidth(450);
        tableroView.setFitHeight(450);
        mapaGrid.add(tableroView, 0, 0, TAMANO_TABLERO, TAMANO_TABLERO);

        // Dibujar protagonista
        ImageView jugadorView = new ImageView(imgProta);
        jugadorView.setFitWidth(28);
        jugadorView.setFitHeight(28);
        mapaGrid.add(jugadorView, protagonista.getX(), protagonista.getY());

        // Dibujar enemigos
        for (Enemigo enemigo : enemigos) {
            if (enemigo.estaVivo()) {
                ImageView enemigoView = new ImageView(imgEnemigo);
                enemigoView.setFitWidth(28);
                enemigoView.setFitHeight(28);
                mapaGrid.add(enemigoView, enemigo.getX(), enemigo.getY());
            }
        }
    }

    private void actualizarEstadisticas() {
        nombreLabel.setText("Nombre: " + protagonista.getNombre());
        saludLabel.setText("Salud: " + protagonista.getSalud());
        fuerzaLabel.setText("Fuerza: " + protagonista.getFuerza());
        defensaLabel.setText("Defensa: " + protagonista.getDefensa());
    }

    @FXML
    public void manejarTeclas(KeyEvent event) {
        switch(event.getCode()) {
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
        
        if (nuevaX < 0 || nuevaY < 0 || nuevaX >= TAMANO_TABLERO || nuevaY >= TAMANO_TABLERO) {
            return;
        }
        
        for (Enemigo enemigo : enemigos) {
            if (enemigo.getX() == nuevaX && enemigo.getY() == nuevaY && enemigo.estaVivo()) {
                protagonista.atacar(enemigo);
                if (!enemigo.estaVivo()) {
                    System.out.println("¡Enemigo eliminado!");
                }
                return;
            }
        }
        
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
                switch(direccion) {
                    case 0: dy = -1; break;
                    case 1: dy = 1; break;
                    case 2: dx = -1; break;
                    case 3: dx = 1; break;
                }
            }
            
            int nuevaX = enemigo.getX() + dx;
            int nuevaY = enemigo.getY() + dy;
            
            if (nuevaX >= 0 && nuevaY >= 0 && nuevaX < TAMANO_TABLERO && nuevaY < TAMANO_TABLERO) {
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
}