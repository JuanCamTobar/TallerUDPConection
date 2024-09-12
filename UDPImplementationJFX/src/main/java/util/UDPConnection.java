package main.java.util;

import java.io.IOException;
import java.net.*;

public class UDPConnection extends Thread {

    private DatagramSocket socket;

    private volatile boolean running = true;

    private static UDPConnection instance;

    private UDPConnection () {}

    public static UDPConnection getInstance(){
        if (instance == null){
            instance = new UDPConnection();
        }
        return instance;
    }

    public void setPort(int port) {
        try {
            this.socket = new DatagramSocket(port);
        } catch (SocketException e) {

        }
    }

    @Override
    public void run(){
        // Recepción
        try {
            // empaquetador de la información
            //                                         un arreglo de bytes | tamaño
            DatagramPacket packet = new DatagramPacket(new byte[24], 24);

            System.out.println("Waiting ....");
            // recivir la información, y almacenarla en el paquete
            while (true) {
                this.socket.receive(packet);
                // decodificando la información
                String msj = new String(packet.getData()).trim();
                System.out.println( "(" + packet.getAddress().getHostAddress() + ":" + packet.getPort() + ") "+ msj + "\n");
            }

        } catch (SocketException e) {

        } catch (IOException e) {

        }finally{
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }

    public void stopConnection() {
        running = false;
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
    }

    public void sendDatagram(String msj, String ipDest, int portDest){
        // Envio de Infromación
        try {
            InetAddress ipAddress = InetAddress.getByName(ipDest);

            // Empaquetador de la información
            // Encapsulamiento de los datos
            //                                         el mensaje     | length     | ip dest  | puerto destino
            DatagramPacket packet = new DatagramPacket(msj.getBytes(), msj.length(), ipAddress, portDest);
            // envia la información
            socket.send(packet);

        } catch (SocketException | UnknownHostException e) {

        } catch (IOException e) {

        }
    }

}
