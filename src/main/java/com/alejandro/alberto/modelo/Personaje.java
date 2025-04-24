package com.alejandro.alberto.modelo;

/**
 * Clase abstracta que define un personaje del juego
 * 
 * @author Alejandro Rey Tostado y Alberto García Izquierdo
 */

public abstract class Personaje {
    protected String nombre;
    protected int salud;
    protected int fuerza;
    protected int defensa;
    protected int velocidad;

    /**
     * Constructor para todos los personajes
     * 
     * @param nombre nombre del personaje
     * @param salud salud del personaje
     * @param fuerza fuerza del personaje
     * @param defensa defensa del personaje
     * @param velocidad velocidad del personaje
     */
    public Personaje(String nombre, int salud, int fuerza, int defensa, int velocidad) {
        this.nombre = nombre;
        this.salud = salud;
        this.fuerza = fuerza;
        this.defensa = defensa;
        this.velocidad = velocidad;
    }

    /**
     * Mueve al personaje por el mapa en la direccion que se indique
     */
    public void mover() {

    }

    /**
     * Metodo en el que se introduce la cantidad de daño que ha recibido el
     * personaje y se le resta a la salud
     * 
     * @param cantidad cantidad de daño recibido
     */
    public void recibirDanio(int cantidad) {

    }

    /**
     * Realiza un ataque sobre otro personaje
     * 
     * @param objetivo es el personaje sobre el que se va a realizar el ataque
     */
    public void atacar(Personaje objetivo) {
        objetivo.recibirDanio(this.fuerza);
    }

    // GETTERS Y SETTERS
    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getSalud() {
        return this.salud;
    }

    public void setSalud(int salud) {
        this.salud = salud;
    }

    public int getFuerza() {
        return this.fuerza;
    }

    public void setFuerza(int fuerza) {
        this.fuerza = fuerza;
    }

    public int getDefensa() {
        return this.defensa;
    }

    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    public int getVelocidad() {
        return this.velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }
}