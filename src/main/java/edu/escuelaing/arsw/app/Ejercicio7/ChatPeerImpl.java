package edu.escuelaing.arsw.app.Ejercicio7;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ChatPeerImpl extends UnicastRemoteObject implements ChatPeer {

    private final String username;
    private final DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("HH:mm:ss");

    protected ChatPeerImpl(String username) throws RemoteException {
        super();
        this.username = username;
    }

    @Override
    public void deliverMessage(String from, String message) throws RemoteException {
        String ts = LocalTime.now().format(timeFmt);
        System.out.println("[" + ts + "] " + from + ": " + message);
    }

    public String getUsername() {
        return username;
    }
}
