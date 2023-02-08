package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class ClientNIO {
    private static final Logger logger = LoggerFactory.getLogger(ClientNIO.class);

    private static final int PORT = 8080;
    private static final String HOST = "localhost";

    public static void main(String[] args) throws InterruptedException {
      //    new Thread(() -> new ClientNIO().go("wait")).start();
        Thread.sleep(10);
        new Thread(() -> new ClientNIO().go("testData_1")).start();
        new Thread(() -> new ClientNIO().go("testData_2")).start();
    }

    private void go(String request) {
        try {
            try (var socketChannel = SocketChannel.open()) {
                socketChannel.configureBlocking(false);

                socketChannel.connect(new InetSocketAddress(HOST, PORT));

                logger.info("connecting to server");
                while (!socketChannel.finishConnect()) {
                    logger.info("connection established");
                }
                send(socketChannel, request);
                handleResponse(socketChannel);
                sleep();
                logger.info("stop communication");
                send(socketChannel, "stop");
            }
        } catch (Exception ex) {
            logger.error("error", ex);
        }
    }

    private void send(SocketChannel socketChannel, String request) throws IOException {
        var buffer = ByteBuffer.allocate(1000);
        buffer.put(request.getBytes());
        buffer.flip();
        logger.info("sending to server");
        socketChannel.write(buffer);
    }

    private void handleResponse(SocketChannel socketChannel) throws IOException {
        var selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_READ);
        logger.info("waiting for response");
        selector.select(selectionKey -> {
            if (selectionKey.isReadable()) {
                processServerResponse((SocketChannel) selectionKey.channel());
            }
        });
    }

    private void processServerResponse(SocketChannel socketChannel) {
        try {
            logger.info("process ServerResponse");
            var buffer = ByteBuffer.allocate(200);

            var response = new StringBuilder();
            while (socketChannel.read(buffer) > 0) {
                buffer.flip();
                var responsePart = StandardCharsets.UTF_8.decode(buffer).toString();
                logger.info("responsePart: {}", responsePart);
                response.append(responsePart);
                buffer.flip();
            }
            logger.info("response: {}", response);
        } catch (Exception ex) {
            throw new NetworkException(ex);
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

