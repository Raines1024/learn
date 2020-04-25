package com.raines.webflux.controllerdemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 注解式使用 WebFlux
 */
@RestController
public class PersonHandle {

    //创建线程池
    private static final ThreadPoolExecutor bizPoolExecutor = new ThreadPoolExecutor(8, 8, 1, TimeUnit.MINUTES,
            new LinkedBlockingQueue<>(10),
            new NameThreadFactory("raines-pool"));

    /**
     * 返回一个反应式流对象Mono，每个Mono包含0个或者1个元素
     */
    @GetMapping("/getPerson")
    Mono<String> getPerson() {
        return Mono.just("raines");
    }

    /**
     * 返回一个Flux流对象，每个Flux包含0个或者多个元素
     */
    @GetMapping("/getPersonList")
    Flux<String> getPersonList() {
        return Flux.just("raines", "jane");
    }

    @GetMapping("/nioAsyncTest")
    Flux<String> nioTest() {
        return Flux.just("raines", "jane")
                // 切换到异步线程执行：让后续对元素的处理切换到Schedulers.elastic()，然后Netty的IO线程就可以及时被释放
//                .publishOn(Schedulers.elastic())
                // 切换到自定义线程池来执行异步任务
                .publishOn(Schedulers.fromExecutor(bizPoolExecutor))
                // 打印调用线程
                .map(e -> {
                    System.out.println(Thread.currentThread().getName());
                    return e;
                });
    }


}



















