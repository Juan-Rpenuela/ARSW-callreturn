package edu.escuelaing.arsw.app.Ejercicio1;

import java.net.*;

public class URLInfo {
    public static void main(String[] args) {
        try {
            URL url = new URL("http://www.google.com:80/index.html?id=123#section1");
            System.out.println("Protocolo: " + url.getProtocol());
            System.out.println("Host: " + url.getHost());
            System.out.println("Puerto: " + url.getPort());
            System.out.println("Path: " + url.getPath());
            System.out.println("File: " + url.getFile());
            System.out.println("Query: " + url.getQuery());
            System.out.println("Ref: " + url.getRef());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}