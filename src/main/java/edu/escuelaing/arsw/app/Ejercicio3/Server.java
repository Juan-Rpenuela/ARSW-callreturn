package edu.escuelaing.arsw.app.Ejercicio3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
     public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(35000)) {
            System.out.println("Servidor escuchando en el puerto 35000...");
            Socket clientSocket = serverSocket.accept();

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                Integer number = Integer.parseInt(inputLine); 
                System.out.println("Mensaje recibido: " + inputLine);
                out.println("Respuesta: " + (number * number));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
