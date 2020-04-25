package com.raines.webflux.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * 服务方提供程序
 * 协议帧格式        消息体:请求id|
 */
public final class RpcServer {
    public static void main(String[] args) throws Exception {
        // 0.创建NettyServerd的boss和worker线程池
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);// boss
        EventLoopGroup workerGroup = new NioEventLoopGroup();// worker
        // 1.创建业务处理hander
        NettyServerHandler servrHandler = new NettyServerHandler();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 100)
                    .handler(new LoggingHandler(LogLevel.INFO)).childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline p = ch.pipeline();
                    // 1.1设置帧分隔符解码器
                    //添加DelimiterBasedFrameDecoder解码器到链接channel的管道以便使用“|”分隔符来确定一个协议帧的边界（避免半包粘包问题）
                    ByteBuf delimiter = Unpooled.copiedBuffer("|".getBytes());
                    p.addLast(new DelimiterBasedFrameDecoder(1000, delimiter));
                    // 1.2设置消息内容自动转换为String的解码器到管线
                    //添加字符串解码器，它在服务端链接channel接收到客户端发来的消息后会自动把消息内容转换为字符串
                    p.addLast(new StringDecoder());
                    // 1.3设置字符串消息自动进行编码的编码器到管线
                    // 设置字符串编码器，它会在服务端链接channel向客户端写入数据时，对数据进行编码
                    p.addLast(new StringEncoder());
                    // 1.4添加业务hander到管线
                    p.addLast(servrHandler);
                }
            });

            // 2.启动服务，并且在12800端口监听
            ChannelFuture f = b.bind(12800).sync();

            // 3. 等待服务监听套接字关闭
            f.channel().closeFuture().sync();
        } finally {
            // 4.优雅关闭两级线程池，以便释放线程
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
