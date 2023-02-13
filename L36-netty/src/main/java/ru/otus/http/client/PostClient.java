package ru.otus.http.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

import java.net.URI;
import java.net.URISyntaxException;

public class PostClient {

    private static final int PORT = 8080;
    private static final String HOST = "localhost";

    public static void main(String[] args) throws Exception {

        var group = new NioEventLoopGroup();
        try {
            var bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new HttpClientInitializer());

            var channel = bootstrap.connect(HOST, PORT).sync().channel();
            sendRequest(channel);
            channel.closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    private static void sendRequest(Channel channel) throws URISyntaxException {
        var uri = new URI("http://" + HOST + ":" + PORT);
        var requestParams = "Sergey";
        var requestParamsBytes = Unpooled.copiedBuffer(requestParams, CharsetUtil.UTF_8);

        var request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, uri.getRawPath());
        request.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);
        request.headers().set(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED);

        request.headers().set(HttpHeaderNames.CONTENT_LENGTH, requestParamsBytes.readableBytes());
        request.content().writeBytes(requestParamsBytes);

        channel.writeAndFlush(request);
    }
}
