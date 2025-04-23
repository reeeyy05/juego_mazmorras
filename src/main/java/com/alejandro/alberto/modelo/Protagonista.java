package com.alejandro.alberto.modelo;

public class Protagonista extends Personaje {
    

    /**
     * Constructor para el protagonista
     * 
     * @param nombre nombre del protagonista
     * @param salud salud del protagonista
     * @param fuerza fuerza del protagonista
     * @param defensa defensa del protagonista
     * @param velocidad velocidad del protagonista
     */
    public Protagonista(String nombre, int salud, int fuerza, int defensa, int velocidad) {
        super(nombre, salud, fuerza, defensa, velocidad);
    }
}
