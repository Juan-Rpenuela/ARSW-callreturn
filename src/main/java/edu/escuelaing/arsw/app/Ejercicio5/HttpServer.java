package edu.escuelaing.arsw.app.Ejercicio5;

import java.net.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HttpServer {

    public static final String WEB_ROOT = "src/main/java/edu/escuelaing/arsw/app/Ejercicio5/resources";

    public static void main(String[] args) throws IOException, URISyntaxException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
        Socket clientSocket = null;
        boolean running = true;
        while (running) {
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }

            OutputStream out = clientSocket.getOutputStream();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));
            String inputLine, outputLine;

            //Obtain the uri from the request
            boolean isFirstLine = true;
            URI requestURI = null;
            while ((inputLine = in.readLine()) != null) {
                if (isFirstLine) {
                    requestURI = new URI(inputLine.split(" ")[1]);
                    System.out.println("Path: " + requestURI.getPath());
                    isFirstLine = false;
                }
                System.out.println("Received: " + inputLine);
                if (!in.ready()) {
                    break;
                }
            }
            String requestFile = requestURI.getPath();
            if (requestURI.getPath().equals("/")) {
                serverFile("index.html", out);
            }
            else {
                    serverFile(requestFile, out);
            }
            in.close();
            clientSocket.close();
        }
        serverSocket.close();
    }

    private static void serverFile(String requestedFile, OutputStream out) throws IOException {
        Path filePath  =  Paths.get(WEB_ROOT, requestedFile);
        if (Files.exists(filePath)) {
            try {
                String mimeType = getMimeType(requestedFile);
                byte[] fileContent = Files.readAllBytes(filePath);
                sendResponse(out, 200, "OK", mimeType, fileContent);
            } catch (IOException e) {
                sendResponse(out, 500, "Internal Server Error", "text/plain", "500 Internal Server Error".getBytes());
            }
        } else {
            sendResponse(out, 404, "Not Found", "text/plain", "404 File not found".getBytes());
        }
    }

    private static void sendResponse( OutputStream out, int statusCode, String statusText, String mimeType, byte[] content) throws IOException {
        String headers = sendHttpHeaders(mimeType, statusCode, statusText);
        out.write(headers.getBytes());
        if (content != null) {
            out.write(content);
        }
        out.flush();
    }

    private static String sendHttpHeaders(String MimeType, int statusCode, String statusText) {
        System.out.println("Sending a response...");
        String headers = "HTTP/1.1 " + statusCode + " " + statusText + "\r\n"
                + "Content-Type: " + MimeType + "\r\n"
                + "\r\n";
        return headers;
    }

    private static String getMimeType(String filename) {
        if (filename.endsWith(".html")) {
            return "text/html";
        } else if (filename.endsWith(".txt")) {
            return "text/plain";
        } else if (filename.endsWith(".css")) {
            return "text/css";
        } else if (filename.endsWith(".js")) {
            return "application/javascript";
        } else if (filename.endsWith(".json")) {
            return "application/json";
        } else if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (filename.endsWith(".png")) {
            return "image/png";
        } else if (filename.endsWith(".gif")) {
            return "image/gif";
        } else {
            return "application/octet-stream";
        }
    }
}