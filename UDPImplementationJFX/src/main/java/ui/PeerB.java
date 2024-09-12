package ui;

import main.java.util.UDPConnection;
import java.util.Scanner;

public class PeerB {

    public static final Scanner sc = new Scanner(System.in);


    public static void main(String[] args) {
        UDPConnection connection = UDPConnection.getInstance();
        // puerto de escucha | recepción
        connection.setPort(3489);

        String ipDest = "127.0.0.1";

        connection.start();

        while (true) {
            // se inicializa el hilo de escucha | hilo de recepción

            System.out.println("Ingrese el mensaje a enviar: ");
            String mensaje = sc.nextLine();

            //puerto de envio
            connection.sendDatagram(mensaje, ipDest, 3490);

            if (mensaje.equals("exit")) {
                break;
            }
        }
    }
}