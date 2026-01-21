package es.etg.dam.psp.servidor.partida;

import java.io.IOException;
import java.net.Socket;
import java.util.Random;

import es.etg.dam.psp.conexion.Conexion;
import es.etg.dam.psp.servidor.Servidor;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Carrera implements Runnable {
    private final int MAX_PUNTOS = 100;
    private final int MAX_RANGO = 11;

    public static final String MSG_GANADOR = "Has ganadoooo";
    public static final String MSG_PERDEDOR = "Game over";

    private final String MSG_ERROR = "Ha ocurrido un error";

    private final Random random = new Random();

    private final Jugador[] jugadores;

    @Override
    public void run() {
        while (true) {
            Jugador jugador = avanzar();
            try {
                if (esGanador(jugador)) {
                    finPartida(jugador);
                    break;
                }
                notificar(jugador);
            } catch (Exception e) {
                System.out.println(MSG_ERROR);
            }
        }
    }

    private Jugador avanzar() {
        int jug = random.nextInt(Servidor.NUM_JUGADORES);
        int puntos = random.nextInt(MAX_RANGO);
        jugadores[jug].sumar(puntos);
        return jugadores[jug];
    }

    private void notificar(Jugador jugador) throws IOException {
        Socket jug = jugador.getConexion();
        String msg = obtenerPuntos();
        Conexion.enviar(msg, jug);
    }

    private String obtenerPuntos() throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("Marcador = ");
        for (Jugador jugador : jugadores) {
            sb.append(String.format("%s : %d, ", jugador.getNombre(), jugador.getPuntos()));
        }
        return sb.toString();
    }

    private boolean esGanador(Jugador jugador) {
        return jugador.getPuntos() >= MAX_PUNTOS;
    }

    private void finPartida(Jugador jugador) throws Exception {
        for (Jugador j : jugadores) {
            if (j == jugador) {
                Conexion.enviar(MSG_GANADOR, jugador.getConexion());
            } else {
                Conexion.enviar(MSG_PERDEDOR, j.getConexion());
            }
        }
    }
}
