package com.alejandro.alberto.modelo;

/**
 * Clase que representa al protagonista en el juego.
 * Hereda de la clase Personaje.
 * 
 * @author Alejandro Rey Tostado y Alberto García Izquierdo
 */
public class Protagonista extends Personaje {

    /**
     * Constructor para el protagonista
     * 
     * @param nombre  nombre del protagonista
     * @param salud   salud del protagonista
     * @param fuerza  fuerza del protagonista
     * @param defensa defensa del protagonista
     */
    public Protagonista(String nombre, int salud, int fuerza, int defensa) {
        super(nombre, salud, fuerza, defensa);
    }
}
