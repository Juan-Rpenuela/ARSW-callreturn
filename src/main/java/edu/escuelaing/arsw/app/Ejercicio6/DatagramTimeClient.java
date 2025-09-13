package edu.escuelaing.arsw.app.Ejercicio6;

import java.net.*;

public class DatagramTimeClient {
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setSoTimeout(3000); 
            InetAddress address = InetAddress.getByName("127.0.0.1");

            String ultimaHora = null; 

            while (true) {
                byte[] envio = "TIME".getBytes();
                DatagramPacket peticion = new DatagramPacket(envio, envio.length, address, 4445);
                socket.send(peticion);

                try {
                    byte[] buffer = new byte[256];
                    DatagramPacket respuesta = new DatagramPacket(buffer, buffer.length);
                    socket.receive(respuesta); 
                    String hora = new String(respuesta.getData(), 0, respuesta.getLength());
                    ultimaHora = hora; 
                    System.out.println("Hora recibida: " + hora);
                } catch (SocketTimeoutException e) {
                    if (ultimaHora != null) {
                        System.out.println("No llegó nueva hora. Manteniendo: " + ultimaHora);
                    } else {
                        System.out.println("Aún no se ha recibido ninguna hora.");
                    }
                }

                Thread.sleep(5000); 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


