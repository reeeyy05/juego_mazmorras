package com.alejandro.alberto.modelo;

import java.util.Random;

/**
 * Clase que representa a un enemigo en el juego.
 * Hereda de la clase Personaje.
 * 
 * @author Alejandro Rey Tostado y Alberto García Izquierdo
 */

public class Enemigo extends Personaje {
    private int percepcion; // rango de vision del enemigo
    
    /**
     * Constructor para los enemigos
     * 
     * @param salud salud del enemigo
     * @param fuerza fuerza del enemigo
     * @param defensa defensa del enemigo
     * @param velocidad velocidad del enemigo
     * @param percepcion rango de vision del enemigo
     */
    public Enemigo(int salud, int fuerza, int defensa, int velocidad, int percepcion) {
        super("Enemigo", salud, fuerza, defensa, velocidad); // le asignamos un nombre por defecto
        this.percepcion = percepcion;
    }

    /**
     * Calcula la distancia entre el enemigo y el protagonista
     * 
     * @param prota el personaje en este caso el protagonista
     * @return la distacia calculada se puede ajustar segun el diseño del mapa
     */
    public int calcularDistancia(Protagonista prota) {
        Random random = new Random();
        return random.nextInt(10) + 1; // Simulamos una distancia aleatoria entre 1 y 10
    }

    /**
     * Mueve al enemigo automaticamente
     * Si el protagonista esta dentro del rango de percepcion se mueve hacia el
     * Si no, se mueve aleatoriamente
     * 
     * @param prota el personaje en este caso el protagonista
     */
    public void moverAutomaticamente(Protagonista prota) {
        Random random = new Random();
        
        int distancia = calcularDistancia(prota);
        if (distancia <= this.percepcion) {
            System.out.println();
        } else {
            int direccion = random.nextInt(4); // 0: arriba, 1: abajo, 2: izquierda, 3: derecha
        }
    }

    // GETTERS Y SETTERS
    public int getPercepcion() {
        return this.percepcion;
    }

    public void setPercepcion(int percepcion) {
        this.percepcion = percepcion;
    }
    
}
