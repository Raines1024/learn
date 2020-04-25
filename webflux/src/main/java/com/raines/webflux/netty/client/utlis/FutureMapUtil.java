package com.raines.webflux.netty.client.utlis;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 管理并发缓存工具类
 */
public class FutureMapUtil {
    // <请求id，对应的future>
    private static final ConcurrentHashMap<String, CompletableFuture> futureMap = new ConcurrentHashMap<>();

    public static void put(String id, CompletableFuture future) {
        futureMap.put(id, future);
    }

    public static CompletableFuture remove(String id) {
        return futureMap.remove(id);
    }
}