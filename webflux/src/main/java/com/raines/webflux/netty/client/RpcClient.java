package com.raines.webflux.netty.client;

import com.raines.webflux.netty.client.utlis.FutureMapUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.reactivex.Flowable;
import io.reactivex.processors.ReplayProcessor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 模拟服务消费方程序
 */
public class RpcClient {
    // 连接通道
    private volatile Channel channel;
    // 请求id生成器
    private static final AtomicLong INVOKE_ID = new AtomicLong(0);
    // 启动器
    private Bootstrap b;

    // 注册了业务的NettyClientHandler处理器到链接channel的管线里面，并且在与服务端完成TCP三次握手后把对应的channel对象保存了下来。
    public RpcClient() {
        // 1. 配置客户端.
        EventLoopGroup group = new NioEventLoopGroup();
        NettyClientHandler clientHandler = new NettyClientHandler();
        try {
            b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            // 1.1设置帧分隔符解码器
                            ByteBuf delimiter = Unpooled.copiedBuffer("|".getBytes());
                            p.addLast(new DelimiterBasedFrameDecoder(1000, delimiter));
                            // 1.2设置消息内容自动转换为String的解码器到管线
                            p.addLast(new StringDecoder());
                            // 1.3设置字符串消息自动进行编码的编码器到管线
                            p.addLast(new StringEncoder());
                            // 1.4添加业务Handler到管线
                            p.addLast(clientHandler);

                        }
                    });
            // 2.发起链接请求，并同步等待链接完成
            ChannelFuture f = b.connect("127.0.0.1", 12800).sync();
            if (f.isDone() && f.isSuccess()) {
                this.channel = f.channel();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendMsg(String msg) {
        channel.writeAndFlush(msg);
    }

    public void close() {

        if (null != b) {
            b.group().shutdownGracefully();
        }
        if (null != channel) {
            channel.close();
        }
    }

    // 根据消息内容和请求id，拼接消息帧
    private String generatorFrame(String msg, String reqId) {
        return msg + ":" + reqId + "|";
    }

    /**
     * 模拟异步远程调用
     */
    public CompletableFuture rpcAsyncCall(String msg) throws ExecutionException, InterruptedException {
        // 1. 创建future
        CompletableFuture<String> future = new CompletableFuture<>();
        // 2.创建消息id：使用原子变量生成一个请求id
        String reqId = INVOKE_ID.getAndIncrement() + "";

        // 3.组成协议帧：消息体后追加消息id和帧分隔符
        msg = generatorFrame(msg, reqId);

        // 4.nio异步发起网络请求，马上返回
        //调用sendMsg方法通过保存的channel对象把协议帧异步发送出去，该方法是非阻塞的，会马上返回，所以不会阻塞业务线程
        this.sendMsg(msg);

        // 5.保存future
        //把创建的future对象保存到FutureMapUtil中管理并发缓存，其中key为请求id，value为创建的future。
        FutureMapUtil.put(reqId, future);

        //没有同步等待future有结果值，而是直接将future返回给调用方，然后就直接返回了，该方法不会阻塞业务线程。
        return future;
    }

    /**
     * 模拟同步远程调用
     */
    public String rpcSyncCall(String msg) throws InterruptedException, ExecutionException {
        // 1. 创建future
        CompletableFuture<String> future = new CompletableFuture<>();
        // 2.创建消息id
        String reqId = INVOKE_ID.getAndIncrement() + "";

        // 3.消息体后追加消息id和帧分隔符
        msg = generatorFrame(msg, reqId);

        // 4.nio异步发起网络请求，马上返回
        this.sendMsg(msg);

        // 5.保存future
        FutureMapUtil.put(reqId, future);

        // 6.同步等待结果
        String result = future.get();
        return result;
    }

    // 异步转反应式:把异步调用改造为Reactive编程风格
    // 基于RxJava让异步调用返回结果为Flowable，其实我们只需要把返回的CompletableFuture转换为Flowable即可
    public Flowable<String> rpcAsyncCallFlowable(String msg) {
        // 1.1 使用defer操作，当订阅时候在执行rpc调用
        return Flowable.defer(() -> {
            // 1.2创建含有一个元素的流
            final ReplayProcessor<String> flowable = ReplayProcessor.createWithSize(1);
            // 1.3具体执行RPC调用
            CompletableFuture<String> future = rpcAsyncCall(msg);
            // 1.4设置回调函数等rpc结果返回后设置结果到流对象
            future.whenComplete((v, t) -> {
                if (t != null) {// 1.4.1结果异常则发射错误信息
                    flowable.onError(t);
                } else {// 1.4.2结果OK，则发射出rpc返回结果
                    flowable.onNext(v);
                    // 1.4.3结束流
                    flowable.onComplete();
                }
            });
            return flowable;
        });
    }

}












