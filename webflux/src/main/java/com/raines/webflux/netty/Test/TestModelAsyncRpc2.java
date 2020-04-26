package com.raines.webflux.netty.Test;

import com.raines.webflux.netty.client.RpcClient;
import io.reactivex.Flowable;
import io.reactivex.processors.ReplayProcessor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 基于CompletableFuture的能力，并发发起多次调用，然后对返回的多个CompletableFuture进行运算
 */
public class TestModelAsyncRpc2 {
    private static final RpcClient rpcClient = new RpcClient();



    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // 1.发起远程调用异步，马上返回
        CompletableFuture<String> future1 = rpcClient.rpcAsyncCall("who are you");
        // 2.发起远程调用异步，马上返回
        CompletableFuture<String> future2 = rpcClient.rpcAsyncCall("who are you");

        // 3.等两个请求都返回结果时候，使用结果做些事情
        //基于CompletableFuture的能力，意在让future1和future2都有结果后再基于两者的结果做一件事情（这里是拼接两者结果返回），并返回一个获取回调结果的新的future。
        CompletableFuture<String> future = future1.thenCombine(future2, (u, v) -> {
            return u + v;
        });

        // 4.等待最终结果
        future.whenComplete((v, t) -> {
            if (t != null) {
                t.printStackTrace();
            } else {
                System.out.println(v);
            }
        });
        System.out.println("---async rpc call over---");
        // rpcClient.close();
    }

}
