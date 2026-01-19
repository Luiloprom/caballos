package es.etg.dam.psp.cliente;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import es.etg.dam.psp.conexion.Conexion;
import es.etg.dam.psp.servidor.Servidor;
import es.etg.dam.psp.servidor.partida.Carrera;

public class Cliente {

    public static void main(String[] args) {
        try (Socket servidor = new Socket(Servidor.HOST, Servidor.PUERTO)) {
            Scanner sc = new Scanner(System.in);

            System.out.println("Ingrese su nombre por favor");
            Conexion.enviar(sc.nextLine(), servidor);

            System.out.println(Conexion.recibir(servidor));
            System.out.println(Conexion.recibir(servidor));

            while (true) {
                String msg = Conexion.recibir(servidor);
                if (msg.equals(Carrera.MSG_GANADOR) || msg.equals(Carrera.MSG_PERDEDOR)) {
                    System.out.println(msg);
                    sc.close();
                    servidor.close();
                    break;
                } else {
                    System.out.println(msg);
                }
            }
        } catch (IOException e) {

        }
    }
}
