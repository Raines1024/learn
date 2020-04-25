package com.raines.webflux.netty.client;

import com.raines.webflux.netty.client.utlis.FutureMapUtil;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.CompletableFuture;

/**
 * 服务端写回结果到客户端后。关于客户端是如何把接入写回对应的future的
 */
@Sharable
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 当NettyClientHandler的channelRead方法被调用时，其中msg已经是一个完整的本文的协议帧了（因为DelimiterBasedFrameDecoder与StringDecoder已经做过解析）。
     * <p>
     * 异步任务内代码1首先根据协议帧格式，从消息msg内获取到请求id，然后从FutureMapUtil管理的缓存内获取请求id对应的future对象，并移除；
     * 如果存在，代码2则从协议帧内获取服务端写回的数据，并调用future的complete方法把结果设置到future，这时候由于调用future的get()方法而被阻塞的线程就返回结果了。
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // 1.根据请求id，获取对应future
        CompletableFuture future = FutureMapUtil.remove(((String) msg).split(":")[1]);
        // 2.如果存在，则设置future结果
        if (future != null) {
            future.complete(((String) msg).split(":")[0]);
        }
    }

}