package es.etg.dam.psp.cliente;

import java.io.IOException;
import java.net.Socket;

import es.etg.dam.psp.conexion.Conexion;
import es.etg.dam.psp.servidor.Servidor;
import static es.etg.dam.psp.servidor.partida.Carrera.MSG_GANADOR;
import static es.etg.dam.psp.servidor.partida.Carrera.MSG_PERDEDOR;

public class Cliente {

    public static final int PARAM_NOMBRE = 0;

    public static void main(String[] args) {
        try (Socket servidor = new Socket(Servidor.HOST, Servidor.PUERTO)) {

            Conexion.enviar(args[PARAM_NOMBRE], servidor);

            while (true) {
                String msg = Conexion.recibir(servidor);
                if (MSG_GANADOR.equals(msg) || MSG_PERDEDOR.equals(msg)) {
                    System.out.println(msg);
                    break;
                } else {
                    System.out.println(msg);
                }
            }
        } catch (IOException e) {

        }
    }
}
