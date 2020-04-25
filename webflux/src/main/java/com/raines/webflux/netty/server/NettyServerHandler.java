package com.raines.webflux.netty.server;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 业务类handler
 * 此处 @Sharable注解是让服务端所有接收的链接对应的channel复用同一个NettyServerHandler实例，
 * 这里可以使用@Sharable方式是因为NettyServer Handler内的处理是无状态的，不会存在线程安全问题。
 */
@Sharable
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    //5. 根据消息内容和请求id，拼接消息帧
    public String generatorFrame(String msg, String reqId) {
        return msg + ":" + reqId + "|";
    }

    /**
     * 当数据流程到NettyServerHandler时，会调用其channelRead方法进行处理，这里msg已经是一个完整的本文的协议帧了。
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        //6.处理请求
        try {
            System.out.println(msg);
            // 6.1.获取消息体，并且解析出请求id
            String str = (String) msg;
            String reqId = str.split(":")[1];

            // 6.2.拼接结果，请求id,协议帧分隔符(模拟服务端执行服务产生结果)
            String resp = generatorFrame("im raines ", reqId);

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 6.3.拼接好的协议帧写回客户端
            ctx.channel().writeAndFlush(Unpooled.copiedBuffer(resp.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}