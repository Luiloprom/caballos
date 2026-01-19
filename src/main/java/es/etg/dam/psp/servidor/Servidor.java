package es.etg.dam.psp.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import es.etg.dam.psp.conexion.Conexion;
import es.etg.dam.psp.servidor.partida.Carrera;
import es.etg.dam.psp.servidor.partida.Jugador;

public class Servidor {
    public static final int PUERTO = 8888;
    public static final String HOST = "localhost";

    public static final String MSG_ESPERA = "Servidor en espera";
    public static final String MSG_OK = "OK";
    public static final String MSG_CARRILES = "Tu carril es el numero : %d";

    public static final int NUM_JUGADORES = 4;

    public static void main(String[] args) throws IOException {
        try (ServerSocket server = new ServerSocket(PUERTO)) {
            Jugador[] jugadores = new Jugador[NUM_JUGADORES];

            while (true) {
                for (int i = 0; i < NUM_JUGADORES; i++) {
                    System.out.println(MSG_ESPERA);
                    Socket cliente = server.accept();
                    String nombre = Conexion.recibir(cliente);
                    Conexion.enviar(MSG_OK, cliente);
                    Conexion.enviar(String.format(MSG_CARRILES, i), cliente);
                    jugadores[i] = new Jugador(nombre, cliente);
                    System.out.println("La carrera a empezado");
                }
                Thread carrera = new Thread(new Carrera(jugadores));
                carrera.start();
            }
        }
    }
}
