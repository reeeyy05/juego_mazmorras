package com.alejandro.alberto.modelo;

public class Cobarde extends Enemigo {
    public Cobarde(int salud, int fuerza, int defensa, int percepcion) {
        super(salud, fuerza, defensa, percepcion);
    }

    public int[] calcualDireccion(int protaX, int protaY) {
        int dx = 0;
        int dy = 0;

        // Huimos en direccion opuesta del Protagonista
        if(this.getX() < protaX) {
            dx = -1;
        } else if (this.getX() > protaX) {
            dx = 1;
        }
        
        if (this.getY() < protaY) {
            dy = -1;
        } else if (this.getY() > protaY) {
            dy = 1;
        }

        return new int[]{dx,dy};
    }

}
