package ru.otus.http.server;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpHeaderValues.CLOSE;
import static io.netty.handler.codec.http.HttpHeaderValues.KEEP_ALIVE;
import static io.netty.handler.codec.http.HttpHeaderValues.TEXT_PLAIN;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;


/*
http://localhost:8080/?name=Jone
http://localhost:8080/
 */
public class HttpHelloWorldServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    private static final Logger logger = LoggerFactory.getLogger(HttpHelloWorldServerHandler.class);

    private final Map<HttpMethod, Function<FullHttpRequest, String>> handlers = new HashMap<>();

    public HttpHelloWorldServerHandler() {
        handlers.put(HttpMethod.GET, this::getHandler);
        handlers.put(HttpMethod.POST, this::postHandler);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) {
        var data = processRequest(msg);

        var response = new DefaultFullHttpResponse(msg.protocolVersion(), OK, Unpooled.copiedBuffer(data, CharsetUtil.UTF_8));
        response.headers()
                .set(CONTENT_TYPE, TEXT_PLAIN)
                .setInt(CONTENT_LENGTH, response.content().readableBytes());

        var keepAlive = HttpUtil.isKeepAlive(msg);
        if (keepAlive) {
            if (!msg.protocolVersion().isKeepAliveDefault()) {
                response.headers().set(CONNECTION, KEEP_ALIVE);
            }
        } else {
            response.headers().set(CONNECTION, CLOSE);
        }

        var channelFuture = ctx.write(response);

        if (!keepAlive) {
            channelFuture.addListener(ChannelFutureListener.CLOSE);
        }
    }

    private String processRequest(FullHttpRequest msg) {
        var method = msg.method();
        logger.info("method:{}", method);
        var handler = handlers.get(method);
        if (handler == null) {
            throw new RuntimeException("unexpected method:" + method);
        }
        return handler.apply(msg);
    }

    private String postHandler(FullHttpRequest msg) {
        try (var byteArray = new ByteArrayOutputStream()) {
            msg.content().readBytes(byteArray, msg.content().readableBytes());
            return "Hi, " + byteArray + ", now is:" + LocalTime.now();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private String getHandler(FullHttpRequest msg) {
        logger.info("msg.uri():{}", msg.uri());
        var nameList = new QueryStringDecoder(msg.uri()).parameters().get("name");
        if (nameList != null && !nameList.isEmpty()) {
            return " Hi, " + nameList.get(0) + ", now is:" + LocalTime.now();
        } else {
            return " Hi, now is:" + LocalTime.now();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error(cause.getMessage(), cause);
        ctx.close();
    }
}
