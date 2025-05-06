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

    private Celda[][] celda;

    /**
     * Constructor: carga el mapa desde un archivo de texto.
     * 
     * @param rutaArchivo Ruta del archivo del mapa.
     */
    public Mapa(String rutaArchivo) throws IOException {
        cargarMapa(rutaArchivo);
    }

    /**
     * Carga el mapa desde un archivo de texto.
     * El archivo debe contener 'P' para paredes y 'S' para suelos.
     * 
     * @param rutaArchivo Ruta del archivo del mapa.
     */
    private void cargarMapa(String rutaArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            // Leer todas las lineas
            List<String> lineas = new ArrayList<>();
            String linea;
            while ((linea = br.readLine()) != null) {
                lineas.add(linea);
            }

            int filas = lineas.size();
            int columnas = lineas.get(0).split(" ").length;

            celda = new Celda[filas][columnas];

            for (int i = 0; i < filas; i++) {
                String[] elementos = lineas.get(i).split(" ");
                for (int j = 0; j < columnas; j++) {
                    String tipo = elementos[j].equals("P") ? "pared" : "suelo";
                    celda[i][j] = new Celda(tipo, i, j);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al cargar el mapa: " + e.getMessage());
        }
    }

    public Celda[][] getCelda() {
        return this.celda;
    }

    public void setCelda(Celda[][] celda) {
        this.celda = celda;
    }
}