package com.alejandro.alberto.modelo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Clase que representa el escenario del juego.
 * Usa una matriz de caracteres donde 'P' es pared y 'S' es suelo.
 * 
 * @author Alejandro Rey Tostado y Alberto García Izquierdo
 */
public class Mapa {

    private char[][] mapa;

    /**
     * Constructor: carga el mapa desde un archivo de texto.
     * 
     * @param rutaArchivo Ruta del archivo del mapa.
     */
    public Mapa(String rutaArchivo) throws IOException {
        cargarMapa(rutaArchivo);
    }

    /**
     * Carga el mapa línea por línea en una matriz.
     */
    private void cargarMapa(String rutaArchivo) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(rutaArchivo));
        String linea;
        int filas = 0;

        // Contar las filas y columnas
        while ((linea = br.readLine()) != null) {
            filas++;
        }
        br.close();

        // Leer de nuevo
        mapa = new char[filas][];
        br = new BufferedReader(new FileReader(rutaArchivo));
        int i = 0;
        while ((linea = br.readLine()) != null) {
            mapa[i] = linea.toCharArray();
            i++;
        }
        br.close();
    }

    /**
     * Verifica si una celda es una pared "P".
     */
    public boolean esPared(int x, int y) {
        return mapa[x][y] == 'P';
    }

    /**
     * Devuelve true si la celda es suelo "S".
     */
    public boolean esSuelo(int x, int y) {
        return mapa[x][y] == 'S';
    }

    /**
     * Devuelve el alto del mapa.
     */
    public int getAlto() {
        return mapa.length;
    }

    /**
     * Devuelve el ancho del mapa.
     */
    public int getAncho() {
        return mapa[0].length;
    }

    /**
     * Devuelve el mapa completo.
     */
    public char[][] getMapa() {
        return mapa;
    }
}