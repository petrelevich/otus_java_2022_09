package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class ServerNIO {
    private static final Logger logger = LoggerFactory.getLogger(ServerNIO.class);

    private static final int PORT_0 = 8080;
    private static final int PORT_1 = 8081;

    public static void main(String[] args) throws IOException {
        new ServerNIO().go();
    }

    private void go() throws IOException {
        try (var serverSocketChannel0 = ServerSocketChannel.open();
             var serverSocketChannel1 = ServerSocketChannel.open()) {
            serverSocketChannel0.configureBlocking(false);
            serverSocketChannel1.configureBlocking(false);

            serverSocketChannel0.socket().bind(new InetSocketAddress(PORT_0));
            serverSocketChannel1.socket().bind(new InetSocketAddress(PORT_1));

            try (var selector = Selector.open()) {
                serverSocketChannel0.register(selector, SelectionKey.OP_ACCEPT);
                serverSocketChannel1.register(selector, SelectionKey.OP_ACCEPT);

                while (!Thread.currentThread().isInterrupted()) {
                    logger.info("waiting for client");
                    selector.select(this::performIO);
                }
            }
        }
    }

    private void performIO(SelectionKey selectedKey) {
        try {
            logger.info("something happened, key:{}", selectedKey);
            if (selectedKey.isAcceptable()) {
                acceptConnection(selectedKey);
            } else if (selectedKey.isReadable()) {
                readWriteClient(selectedKey);
            }
        } catch (Exception ex) {
            throw new NetworkException(ex);
        }
    }

    private void acceptConnection(SelectionKey key) throws IOException {
        Selector selector = key.selector();
        logger.info("accept client connection, key:{}, selector:{}", key, selector);
        // selector=sun.nio.ch.EPollSelectorImpl - Linux epoll based Selector implementation
        var serverSocketChannel = (ServerSocketChannel) key.channel();
        var socketChannel = serverSocketChannel.accept(); //The socket channel for the new connection

        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
        logger.info("socketChannel:{}", socketChannel);
    }

    private void readWriteClient(SelectionKey selectionKey) throws IOException {
        logger.info("read from client");
        var socketChannel = (SocketChannel) selectionKey.channel();

        try {
            var requestFromClient = handleRequest(socketChannel);
            if ("stop".equals(requestFromClient)) {
                socketChannel.close();
            } else {
                var responseForClient = processClientRequest(requestFromClient);
                sendResponse(socketChannel, responseForClient);
            }
        } catch (Exception ex) {
            logger.error("error sending response", ex);
            socketChannel.close();
        }
    }

    private String handleRequest(SocketChannel socketChannel) throws IOException {
        var buffer = ByteBuffer.allocate(5);
        var inputBuffer = new StringBuilder(100);

        while (socketChannel.read(buffer) > 0) {
            buffer.flip();
            var input = StandardCharsets.UTF_8.decode(buffer).toString();
            logger.info("from client: {} ", input);

            buffer.flip();
            inputBuffer.append(input);
        }

        String requestFromClient = inputBuffer.toString().replace("\n", "").replace("\r", "");
        logger.info("requestFromClient: {} ", requestFromClient);
        return requestFromClient;
    }

    private void sendResponse(SocketChannel socketChannel, String responseForClient) throws IOException {
        var buffer = ByteBuffer.allocate(5);
        var response = responseForClient.getBytes();
        for (byte b : response) {
            buffer.put(b);
            if (buffer.position() == buffer.limit()) {
                buffer.flip();
                socketChannel.write(buffer);
                buffer.flip();
            }
        }
        if (buffer.hasRemaining()) {
            buffer.flip();
            socketChannel.write(buffer);
        }
    }

    private String processClientRequest(String input) {
        if ("wait".equals(input)) {
            logger.info("waiting...");
            sleep();
        }
        return String.format("echo: %s%n", input);
    }

    private void sleep() {
        try {
            Thread.sleep(TimeUnit.MINUTES.toMillis(1));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
