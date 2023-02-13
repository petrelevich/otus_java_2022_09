package ru.otus.echo.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import java.util.concurrent.Executor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(EchoServerHandler.class);
    private final ByteBuf bufForInMsg = new PooledByteBufAllocator(true).directBuffer(5);

    private final Executor longActionExecutor;
    public EchoServerHandler(Executor longActionExecutor) {
        logger.info("new EchoServerHandler created");
        this.longActionExecutor = longActionExecutor;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws IOException {
        var in = (ByteBuf) msg;

        if (in.readableBytes() > bufForInMsg.capacity()) {
            bufForInMsg.capacity(in.readableBytes());
        }

        bufForInMsg.resetReaderIndex();
        bufForInMsg.resetWriterIndex();
        try {
            while (in.isReadable()) {
                in.readBytes(bufForInMsg, in.readableBytes());
            }
        } finally {
            ReferenceCountUtil.release(msg);
        }

        //Для демонстрации полученных данных и чтения из буфера
        try (var byteArray = new ByteArrayOutputStream()) {
            bufForInMsg.readBytes(byteArray, bufForInMsg.readableBytes());

            handleRequest(ctx, byteArray);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error(cause.getMessage(), cause);
        ctx.close();
    }

    private void handleRequest(ChannelHandlerContext ctx, ByteArrayOutputStream byteArray) {
        logger.info("this:{}, client:{}, data:{}", this, ctx.channel().remoteAddress(), byteArray);

        var request = byteArray.toString();
        var normalizedString = request.replace("\r", "").replace("\n", "");
        if ("wait".equals(normalizedString)) {
            longActionExecutor.execute(() -> {
                someAction(request);
                ctx.writeAndFlush(Unpooled.copiedBuffer(request, CharsetUtil.UTF_8));
            });
        } else {
            ctx.writeAndFlush(Unpooled.copiedBuffer(request, CharsetUtil.UTF_8));
        }
    }

    private void someAction(String request) {
        try {
            logger.info("some long action, request:{}", request);
            Thread.sleep(TimeUnit.MINUTES.toMillis(1));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
