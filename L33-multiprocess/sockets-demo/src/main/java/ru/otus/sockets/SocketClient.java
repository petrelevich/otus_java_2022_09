package ru.otus.sockets;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class SocketClient {
    private static final int PORT = 8090;
    private static final String HOST = "localhost";

    public static void main(String[] args) {
        new SocketClient().go();
    }

    private void go() {
        try {
            try (Socket clientSocket = new Socket(HOST, PORT)) {
                PrintWriter outputStream = new PrintWriter(clientSocket.getOutputStream(), true);

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                for (int idx = 0; idx < 3; idx++) {
                    String msg = String.format("##%d - I Believe", idx);
                    System.out.printf("sending to server: %s%n", msg);
                    outputStream.println(msg);

                    String responseMsg = in.readLine();
                    System.out.printf("server response: %s%n", responseMsg);
                    Thread.sleep(TimeUnit.SECONDS.toMillis(3));

                    System.out.println();
                }

                System.out.println("\nstop communication");
                outputStream.println("stop");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
