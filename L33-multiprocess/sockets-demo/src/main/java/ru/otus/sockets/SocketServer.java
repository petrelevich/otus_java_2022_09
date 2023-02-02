package ru.otus.sockets;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    private static final int PORT = 8090;

    public static void main(String[] args) {
        new SocketServer().go();
    }

    private void go() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("waiting for client connection");
                try (Socket clientSocket = serverSocket.accept()) {
                    handleClientConnection(clientSocket);

                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void handleClientConnection(Socket clientSocket) {
        try (
                PrintWriter outptStream = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
        ) {
            String input = null;
            while (!"stop".equals(input)) {
                input = in.readLine();
                if (input != null) {
                    System.out.println(String.format("from client: %s", input));
                    outptStream.println(String.format("%s I Can Fly!", input));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println();
    }


}
