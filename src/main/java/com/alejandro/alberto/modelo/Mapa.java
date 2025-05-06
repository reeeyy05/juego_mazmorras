package com.alejandro.alberto.modelo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    private void cargarMapa(String rutaArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            // Leer todas las lineas
            List<String> lineas = new ArrayList<>();
            String linea;
            while ((linea = br.readLine()) != null) {
                lineas.add(linea);
            }

            // Inicializar mapa
            mapa = new char[lineas.size()][];
            for (int i = 0; i < lineas.size(); i++) {
                mapa[i] = lineas.get(i).toCharArray();
            }

        } catch (IOException e) {
            System.err.println("Error al cargar el mapa: " + e.getMessage());
        }
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