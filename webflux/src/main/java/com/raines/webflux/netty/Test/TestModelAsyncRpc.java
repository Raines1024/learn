package com.raines.webflux.netty.Test;

import com.raines.webflux.netty.client.RpcClient;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 测试如何使用RpcServer和RpcClient
 */
public class TestModelAsyncRpc {

    private static final RpcClient rpcClient = new RpcClient();

    /**
     * main函数内首先创建了一个rpcClient对象，然后代码1同步调用了其rpcSyncCall方法，由于是同步调用，所以在服务端执行返回结果前，当前调用线程会被阻塞，直到服务端把结果写回客户端，并且客户端把结果写回到对应的future对象后才会返回。
     * <p>
     * 代码2调用了异步方法rpcAsyncCall，其不会阻塞业务调用线程，而是马上返回一个CompletableFuture对象，然后我们在其上设置了一个回调函数，意在等future对象的结果被设置后进行回调，这个实现了真正意义上的异步。
     */
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        // 1.同步调用
        System.out.println(rpcClient.rpcSyncCall("who are you"));

        // 2.发起远程调用异步，并注册回调，马上返回
        CompletableFuture<String> future = rpcClient.rpcAsyncCall("who are you");
        future.whenComplete((v, t) -> {
            if (t != null) {
                t.printStackTrace();
            } else {
                System.out.println(v+"--=========");
            }

        });

        System.out.println("---async rpc call over");
    }

}
