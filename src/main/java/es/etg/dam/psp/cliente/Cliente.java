package es.etg.dam.psp.cliente;

import java.io.IOException;
import java.net.Socket;

import es.etg.dam.psp.conexion.Conexion;
import es.etg.dam.psp.servidor.Servidor;
import static es.etg.dam.psp.servidor.partida.Carrera.MSG_GANADOR;
import static es.etg.dam.psp.servidor.partida.Carrera.MSG_PERDEDOR;

public class Cliente {

    public static final String MSG_ERROR_SERVER = "A ocurrido un error al establecer la conexion";
    public static final int PARAM_NOMBRE = 0;

    public static void main(String[] args) {
        try (Socket servidor = new Socket(Servidor.HOST, Servidor.PUERTO)) {

            Conexion.enviar(args[PARAM_NOMBRE], servidor);

            boolean comprobacion = false;
            while (!comprobacion) {
                String msg = Conexion.recibir(servidor);
                if (MSG_GANADOR.equals(msg) || MSG_PERDEDOR.equals(msg)) {
                    comprobacion = true;
                }
                System.out.println(msg);
            }
        } catch (IOException e) {
            System.out.println(MSG_ERROR_SERVER);
        }
    }
}
