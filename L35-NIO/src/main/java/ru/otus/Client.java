package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Client {
    private static final Logger logger = LoggerFactory.getLogger(Client.class);

    private static final int PORT = 8080;
    private static final String HOST = "localhost";

    public static void main(String[] args) {
        var counter = new AtomicInteger(0);
        for (var idx = 0; idx < 15; idx++) {
            new Thread(() -> new Client().go("testData_" + counter.incrementAndGet())).start();
        }
    }

    private void go(String request) {
        try {
            try (var clientSocket = new Socket(HOST, PORT)) {
                var out = new PrintWriter(clientSocket.getOutputStream(), true);
                var in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                logger.info("sending to server");
                out.println(request);
                var resp = in.readLine();
                logger.info("server response: {}", resp);
                sleep();
                logger.info("stop communication");
                out.println("stop");
            }
        } catch (Exception ex) {
            logger.error("error", ex);
        }
    }

    private static void sleep() {
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(10));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
