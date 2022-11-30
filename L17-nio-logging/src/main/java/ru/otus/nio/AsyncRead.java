package ru.otus.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
https://github.com/gridgain/gridgain/blob/d6222c6d892eabcbcfc60fd75fc2d38a7dd06bb6/modules/core/src/main/java/org/apache/ignite/internal/processors/cache/persistence/file/AsyncFileIO.java
 */

public class AsyncRead implements AutoCloseable {
    private static final Logger logger = LoggerFactory.getLogger(AsyncRead.class);
    private final ByteBuffer buffer = ByteBuffer.allocate(2);
    private final AsynchronousFileChannel fileChannel;
    private final List<String> fileParts = new CopyOnWriteArrayList<>();
    private final CountDownLatch latch = new CountDownLatch(1);

    private final CompletionHandler<Integer, ByteBuffer> completionHandler =
            new CompletionHandler<>() {
                private int lastPosition = 0;

                @Override
                public void completed(Integer readBytes, ByteBuffer attachment) {
                    logger.info("readBytes:{}", readBytes);
                    if (readBytes > 0) {
                        byte[] destArray = new byte[readBytes];
                        attachment.flip();
                        attachment.get(destArray, 0, destArray.length);

                        fileParts.add(new String(destArray));

                        buffer.clear();
                        var position = lastPosition += readBytes;
                        fileChannel.read(buffer, position, buffer, completionHandler);
                    } else {
                        logger.info("read completed");
                        latch.countDown();
                    }
                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    logger.error("error:{}", exc.getMessage());
                }
            };


    public static void main(String[] args) throws Exception {
        var executor = Executors.newSingleThreadExecutor();
        try (var asyncRead = new AsyncRead(executor)) {
            asyncRead.read();
            executor.shutdown();
        }
    }

    public AsyncRead(ExecutorService executor) throws IOException {
        fileChannel = AsynchronousFileChannel.open(Path.of("textFile.txt"),
                Set.of(StandardOpenOption.READ), executor);
    }

    private void read() throws InterruptedException {
        fileChannel.read(buffer, 0, buffer, completionHandler);
        logger.info("Hello");
        latch.await();

        var fileContent = String.join("", fileParts);
        logger.info("fileContent:\n{}", fileContent);
    }

    @Override
    public void close() throws Exception {
        fileChannel.close();
    }
}
