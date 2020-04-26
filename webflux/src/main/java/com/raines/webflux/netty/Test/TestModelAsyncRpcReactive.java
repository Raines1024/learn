package com.raines.webflux.netty.Test;

import com.raines.webflux.netty.client.RpcClient;
import io.reactivex.Flowable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 测试RPCClient rpcAsyncCallFlowable
 * <p>
 * 发起rpc调用后马上返回了一个Flowable流对象，但这时真正的rpc调用还没有发出去，等代码3订阅了流对象时才真正发起rpc调用。”
 */
public class TestModelAsyncRpcReactive {

    // 1.创建rpc客户端
    private static final RpcClient rpcClient = new RpcClient();

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // 2.发起远程调用异步，并注册回调，马上返回
        Flowable<String> result = rpcClient.rpcAsyncCallFlowable("who are you");
        //3.订阅流对象
        result.subscribe(/* onNext */r -> {
            System.out.println(Thread.currentThread().getName() + ":" + r);
        }, /* onError */error -> {
            System.out.println(Thread.currentThread().getName() + "error:" + error.getLocalizedMessage());
        });
        System.out.println("----async rpc call over");
    }
}