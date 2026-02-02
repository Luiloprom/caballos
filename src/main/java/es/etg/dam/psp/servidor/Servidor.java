package es.etg.dam.psp.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import es.etg.dam.psp.conexion.Conexion;
import es.etg.dam.psp.servidor.partida.Carrera;

public class Servidor {
    public static final int PUERTO = 8888;
    public static final String HOST = "localhost";

    public static final String MSG_COMIENZO = "La carrera a empezado";
    public static final String MSG_ESPERA = "Servidor en espera";
    public static final String MSG_OK = "OK";

    public static void main(String[] args) throws IOException {
        try (ServerSocket server = new ServerSocket(PUERTO)) {
            while (true) {
                Carrera carrera = new Carrera();
                for (int i = 0; i < Carrera.NUM_JUGADORES; i++) {
                    System.out.println(MSG_ESPERA);
                    Socket cliente = server.accept();
                    String nombre = Conexion.recibir(cliente);
                    Conexion.enviar(MSG_OK, cliente);
                    carrera.addJugador(nombre, cliente);
                    System.out.println(MSG_COMIENZO);
                }
                new Thread(carrera).start();
            }
        }
    }
}
