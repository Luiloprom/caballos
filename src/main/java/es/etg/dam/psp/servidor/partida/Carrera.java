package es.etg.dam.psp.servidor.partida;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import es.etg.dam.psp.conexion.Conexion;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Carrera implements Runnable {
    private final int MAX_RANGO = 11;

    public static final int NUM_JUGADORES = 4;

    public static final String MSG_GANADOR = "Has ganadoooo";
    public static final String MSG_PERDEDOR = "Game over";

    public static final String MSG_MARCADOR = "Marcador = ";
    public static final String FORMATO_NOMBRE = "* %s";

    public static final String FORMATO_DEFAULT = "%s : %d | ";

    private final String MSG_ERROR = "Ha ocurrido un error";

    private final List<Jugador> jugadores = new ArrayList<>(NUM_JUGADORES);

    @Override
    public void run() {
        boolean fin = false;
        while (!fin) {
            Jugador jugador = avanzar();
            try {
                if (jugador.esGanador()) {
                    finPartida(jugador);
                    fin = true;
                }
                notificar(jugador);
            } catch (Exception e) {
                System.out.println(MSG_ERROR);
            }
        }
    }

    private Jugador avanzar() {
        int jug = generarAleatorio(NUM_JUGADORES);
        int puntos = generarAleatorio(MAX_RANGO);
        Jugador jugador = jugadores.get(jug);
        jugador.sumar(puntos);
        return jugador;
    }

    private void notificar(Jugador jugador) throws IOException {
        Socket jug = jugador.getConexion();
        String msg = obtenerPuntos(jugador);
        Conexion.enviar(msg, jug);
    }

    private String obtenerPuntos(Jugador jugadorAvanza) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(MSG_MARCADOR);
        for (Jugador jugador : jugadores) {
            String nombre = (jugador == jugadorAvanza) ? String.format(FORMATO_NOMBRE, jugador.getNombre())
                    : jugador.getNombre();
            sb.append(String.format(FORMATO_DEFAULT, nombre, jugador.getPuntos()));
        }
        return sb.toString();
    }

    private void finPartida(Jugador jugador) throws Exception {
        for (Jugador j : jugadores) {
            String mensaje = (j == jugador) ? MSG_GANADOR : MSG_PERDEDOR;
            Conexion.enviar(mensaje, j.getConexion());
        }
    }

    public void addJugador(String nombre, Socket socket) {
        jugadores.add(new Jugador(nombre, socket));
    }

    private int generarAleatorio(int max) {
        Random random = new Random();
        return random.nextInt(max);
    }
}
