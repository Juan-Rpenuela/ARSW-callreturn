package edu.escuelaing.arsw.app.Ejercicio4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import static java.lang.Math.*;

public class Server {
    public static String function = "sen";
     public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(35000)) {
            System.out.println("Servidor escuchando en el puerto 35000...");
            Socket clientSocket = serverSocket.accept();

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Mensaje recibido: " + inputLine);
                if (inputLine.startsWith("fun:")) {
                String param =  inputLine.split(":")[1];
                if (param.equals("sen")) {
                    function = "sen";
                    out.println("Función cambiada a sen");
                } else if (param.equals("cos")) {
                    function = "cos";
                    out.println("Función cambiada a cos");
                } else if (param.equals("tan")) {
                    function = "tan";
                    out.println("Función cambiada a tan");
                } else {
                    out.println("Error: Función no reconocida");
                }
            } else if (inputLine.startsWith("")) {
                Integer number = Integer.parseInt(inputLine);
                if (function.equals("sen")) {
                    out.println("Respuesta: " + sin(number));
                } else if (function.equals("cos")) {
                    out.println("Respuesta: " + cos(number));
                } else if (function.equals("tan")) {
                    out.println("Respuesta: " + tan(number));
                }
            }
        }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
