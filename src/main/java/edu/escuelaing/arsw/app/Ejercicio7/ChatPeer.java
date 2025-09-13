package edu.escuelaing.arsw.app.Ejercicio7;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface ChatPeer extends Remote {
    void deliverMessage(String from, String message) throws RemoteException;
}
