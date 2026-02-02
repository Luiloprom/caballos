package es.etg.dam.psp.servidor.partida;

import java.net.Socket;

import lombok.Data;

@Data
public class Jugador {
    public static final int MAX_PUNTOS = 100;

    private String nombre;
    private int puntos = 0;
    private Socket conexion;

    public Jugador(String nombre, Socket conexion) {
        this.nombre = nombre;
        this.conexion = conexion;
    }

    public void sumar(int puntos) {
        this.puntos += puntos;
    }

    public boolean esGanador() {
        return (puntos >= MAX_PUNTOS);
    }
}
