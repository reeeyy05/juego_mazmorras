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

    /**
     * Constructor para todos los personajes
     * 
     * @param nombre nombre del personaje
     * @param salud salud del personaje
     * @param fuerza fuerza del personaje
     * @param defensa defensa del personaje
     */
    public Personaje(String nombre, int salud, int fuerza, int defensa) {
        this.nombre = nombre;
        this.salud = salud;
        this.fuerza = fuerza;
        this.defensa = defensa;
    }

    /**
     * Mueve al personaje por el mapa en la direccion que se indique
     * Este metodo debe ser implementado por las clases hijas
     */
    public abstract void mover();

    /**
     * Metodo en el que se introduce la cantidad de daño que ha recibido el
     * personaje y se le resta a la salud
     * 
     * @param cantidad cantidad de daño recibido
     */
    public void recibirDanio(int cantidad) {
        int danioReal = cantidad - this.defensa;
        if (danioReal > 0 ) {
            this.salud -= danioReal;
            System.out.println(this.nombre + " ha recibido " + danioReal + " puntos de daño. Salud restante: " + this.salud);
        } else System.out.println(this.nombre + " ha recibido " + cantidad + " puntos de daño. No le afecta por su defensa.");

        if (this.salud <=0) {
            this.salud = 0;
            System.out.println(this.nombre + " ha muerto");
        }
    }

    /**
     * Realiza un ataque sobre otro personaje
     * 
     * @param objetivo es el personaje sobre el que se va a realizar el ataque
     */
    public void atacar(Personaje objetivo) {
        System.out.println(this.nombre + " ataca a " + objetivo.getNombre() + " con " + this.fuerza + " de fuerza.");
        objetivo.recibirDanio(this.fuerza);
    }

    public boolean esProtagonista() {
        return this instanceof Protagonista;
    }

    /**
     * Comprueba si el personaje esta vivo o no
     * 
     * @return TRUE si el personaje esta vivo, FALSE si no lo esta
     */
    public boolean estaVivo() {
        return this.salud > 0;
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
}