package com.alejandro.alberto.modelo;

/**
 * Clase abstracta que define un personaje del juego
 * 
 * @author Alejandro Rey Tostado y Alberto Garcia Izquierdo
 */

public abstract class Personaje {
    protected String nombre;
    protected int salud;
    protected int fuerza;
    protected int defensa;

    protected int x;
    protected int y;

    /**
     * Constructor para todos los personajes
     * 
     * @param nombre  nombre del personaje
     * @param salud   salud del personaje
     * @param fuerza  fuerza del personaje
     * @param defensa defensa del personaje
     */
    public Personaje(String nombre, int salud, int fuerza, int defensa) {
        this.nombre = nombre;
        this.salud = salud;
        this.fuerza = fuerza;
        this.defensa = defensa;
    }

    // METODOS
    /**
     * Mueve al personaje a una nueva posicion
     * 
     * @param x nueva posicion en el eje X
     * @param y nueva posicion en el eje Y
     */
    public void setPosicion(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Metodo en el que se introduce la cantidad de danio que ha recibido el
     * personaje y se le resta
     * 
     * @param cantidad cantidad de daño recibido
     */
    public void recibirDanio(int cantidad) {
        if (defensa > 0) {
            int restante = cantidad - defensa;
            defensa -= cantidad;
            if (defensa < 0)
                defensa = 0;

            if (restante > 0) {
                salud -= restante;
            }
        } else {
            salud -= cantidad;
        }

        if (salud < 0)
            salud = 0;

        System.out.println(nombre + " - Salud: " + salud + ", Defensa: " + defensa);

        if (!estaVivo()) {
            System.out.println(nombre + " ha muerto");
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

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

}