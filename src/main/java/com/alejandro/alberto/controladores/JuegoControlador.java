package com.alejandro.alberto.controladores;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.alejandro.alberto.modelo.Enemigo;
import com.alejandro.alberto.modelo.Mapa;
import com.alejandro.alberto.modelo.Protagonista;

/**
 * Esta clase controla la lógica principal del juego:
 * movimiento del jugador, combate y carga de enemigos.
 * 
 * @author Alberto García Izquierdo y Alejandro Rey Tostado.
 */
public class JuegoControlador {

    private Mapa mapa; // El escenario del juego (paredes y suelos)
    private Protagonista protagonista; // El jugador
    private List<Enemigo> enemigos; // Lista de enemigos

    /**
     * Constructor: crea el mapa, el protagonista y carga los enemigos.
     * 
     * @param rutaMapa      Archivo de texto del escenario
     * @param rutaEnemigos  Archivo de texto con enemigos
     * @param nombreJugador Nombre que el usuario ingresó
     */
    public JuegoControlador(String rutaMapa, String rutaEnemigos, String nombreJugador) throws IOException {
        mapa = new Mapa(rutaMapa);
        protagonista = new Protagonista(nombreJugador, 100, 10, 5); // Valores por defecto
        enemigos = cargarEnemigos(rutaEnemigos);
    }

    /**
     * Carga los enemigos desde un archivo:
     * nombre,salud,fuerza,defensa,velocidad,percepcion.
     */
    private List<Enemigo> cargarEnemigos(String archivo) throws IOException {
        List<Enemigo> lista = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(archivo));
        String linea;
        while ((linea = br.readLine()) != null) {
            String[] datos = linea.split(",");
            String nombre = datos[0];
            int salud = Integer.parseInt(datos[3]);
            int fuerza = Integer.parseInt(datos[4]);
            int defensa = 10; // Valor fijo por defecto
            int velocidad = 5; // Valor fijo por defecto
            int percepcion = 3; // Valor fijo por defecto

        }
        br.close();
        return lista;
    }

    public Mapa getMapa() {
        return this.mapa;
    }

    public void setMapa(Mapa mapa) {
        this.mapa = mapa;
    }

    public Protagonista getProtagonista() {
        return this.protagonista;
    }

    public void setProtagonista(Protagonista protagonista) {
        this.protagonista = protagonista;
    }

    public List<Enemigo> getEnemigos() {
        return this.enemigos;
    }

    public void setEnemigos(List<Enemigo> enemigos) {
        this.enemigos = enemigos;
    }

}