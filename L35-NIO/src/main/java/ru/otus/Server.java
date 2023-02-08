package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final Logger logger = LoggerFactory.getLogger(Server.class);
    private static final int PORT = 8080;

    public static void main(String[] args) {
        new Server().go();
    }

    private void go() {
        try (var serverSocket = new ServerSocket(PORT, 1000, InetAddress.getLoopbackAddress())) {
            while (!Thread.currentThread().isInterrupted()) {
                logger.info("waiting for client connection");
                try (var clientSocket = serverSocket.accept()) {
                    clientHandler(clientSocket);
                }
            }
        } catch (Exception ex) {
            logger.error("error", ex);
        }
    }

    private void clientHandler(Socket clientSocket) {
        try (var out = new PrintWriter(clientSocket.getOutputStream(), true);
             var in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
        ) {
            while (!Thread.currentThread().isInterrupted()) {
                var input = in.readLine();
                if (input == null || "stop".equals(input)) {
                    logger.info("client finished");
                    return;
                }
                logger.info("from client: {} ", input);
                out.println("echo:" + input);
            }
        } catch (Exception ex) {
            logger.error("error", ex);
        }
    }
}
