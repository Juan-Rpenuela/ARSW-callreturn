package edu.escuelaing.arsw.app.Ejercicio7;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.RemoteException;
import java.util.Scanner;

/**
 * Aplicación de chat RMI punto a punto muy simple.
 * Comandos:
 *  /connect <host> <puerto>
 *  /exit
 * Mensajes sin '/' se envían al peer conectado.
 */
public class ChatApp {

    private static final String BIND_NAME = "chatPeer";

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        try {
            System.out.print("Tu nombre: ");
            String username = in.nextLine().trim();
            if (username.isEmpty()) username = "Anon";

            System.out.print("Puerto local para este peer: ");
            int localPort = Integer.parseInt(in.nextLine().trim());

            ChatPeerImpl localPeer = new ChatPeerImpl(username);
            Registry localRegistry;
            try {
                localRegistry = LocateRegistry.createRegistry(localPort);
            } catch (RemoteException ex) {
                localRegistry = LocateRegistry.getRegistry(localPort);
            }
            localRegistry.rebind(BIND_NAME, localPeer);
            System.out.println("Peer listo. Publicado como '" + BIND_NAME + "' en puerto " + localPort);
            System.out.println("Usa /connect <host> <puerto> para conectar con otro peer, /exit para salir.");

            ChatPeer remotePeer = null;

            loop: while (true) {
                System.out.print("> ");
                String line = in.nextLine();
                if (line == null) continue;
                line = line.trim();
                if (line.isEmpty()) continue;

                if (line.startsWith("/")) {
                    String[] parts = line.split("\\s+");
                    String cmd = parts[0].toLowerCase();
                    switch (cmd) {
                        case "/connect":
                            if (parts.length < 3) {
                                System.out.println("Uso: /connect <host> <puerto>");
                                break;
                            }
                            String host = parts[1];
                            int port;
                            try { port = Integer.parseInt(parts[2]); } catch (NumberFormatException e) { System.out.println("Puerto inválido"); break; }
                            try {
                                Registry remoteRegistry = LocateRegistry.getRegistry(host, port);
                                ChatPeer stub = (ChatPeer) remoteRegistry.lookup(BIND_NAME);
                                remotePeer = stub;
                                System.out.println("Conectado a peer en " + host + ":" + port);
                            } catch (Exception e) {
                                System.out.println("No se pudo conectar: " + e.getMessage());
                            }
                            break;
                        case "/exit":
                            System.out.println("Saliendo...");
                            break loop;
                        default:
                            System.out.println("Comando desconocido: " + cmd);
                    }
                } else {
                    if (remotePeer == null) {
                        System.out.println("(Aún no conectado. Usa /connect.)");
                        continue;
                    }
                    try {
                        remotePeer.deliverMessage(username, line);
                    } catch (RemoteException e) {
                        System.out.println("Error enviando mensaje: " + e.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error fatal: " + e.getMessage());
            e.printStackTrace();
        } finally {
            in.close();
        }
    }
}
