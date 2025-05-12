package com.alejandro.alberto.modelo;

/**
 * Esta clase representa una celda individual en el mapa del juego.
 * Cada celda puede ser un suelo o una pared.
 * 
 * @author Alberto García Izquierdo y Alejandro Rey Tostado
 */
public class Celda {
    private String tipo; // puede ser "suelo" o "pared"
    private int fila;
    private int columna;
    private Personaje personaje; // Personaje que ocupa la celda, si lo hay

    /**
     * Crea una celda con la posicion y el tipo
     * 
     * @param tipo    "suelo" o "pared"
     * @param fila    numero de fila de la celda
     * @param columna numero de columna de la celda
     */
    public Celda(String tipo, int fila, int columna) {
        this.tipo = tipo;
        this.fila = fila;
        this.columna = columna;
        this.personaje = null; // Inicialmente no hay personaje
    }

    // Metodos
    /**
     * Verifica si la celda está ocupada por un personaje.
     * 
     * @return true si hay un personaje, false en caso contrario
     */
    public boolean estaOcupada() {
        return this.personaje != null;
    }

    public boolean esTransitable() {
        return tipo.equals("suelo") && !estaOcupada();
    }

    public boolean contieneEnemigo() {
        return estaOcupada() && !personaje.esProtagonista();
    }

    // Getters y Setters

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getFila() {
        return this.fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return this.columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public Personaje getPersonaje() {
        return this.personaje;
    }

    public void setPersonaje(Personaje personaje) {
        this.personaje = personaje;
    }
}
