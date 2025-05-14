package com.alejandro.alberto.modelo;

/**
 * Clase que representa a un enemigo en el juego.
 * Hereda de la clase Personaje.
 * 
 * @author Alejandro Rey Tostado y Alberto García Izquierdo
 */

public class Enemigo extends Personaje {
    protected int percepcion; // rango de vision del enemigo

    /**
     * Constructor para los enemigos
     * 
     * @param salud      salud del enemigo
     * @param fuerza     fuerza del enemigo
     * @param defensa    defensa del enemigo
     * @param percepcion rango de vision del enemigo
     */
    public Enemigo(int salud, int fuerza, int defensa, int percepcion) {
        super("Enemigo", salud, fuerza, defensa); // le asignamos un nombre por defecto
        this.percepcion = percepcion;
    }

    // GETTERS Y SETTERS
    public int Percepcion() {
        return this.percepcion;
    }

    public void setPercepcion(int percepcion) {
        this.percepcion = percepcion;
    }

    private int velocidad;

    public int getVelocidad() {
        return velocidad;
    }

    public int getPercepcion() {
        return percepcion;
    }
}
