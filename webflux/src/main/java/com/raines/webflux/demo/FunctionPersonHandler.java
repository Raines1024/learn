package com.raines.webflux.demo;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 不同的HandleFunction对不同请求做处理
 */
public class FunctionPersonHandler {

    private static final ThreadPoolExecutor bizPoolExecutor = new ThreadPoolExecutor(8,8,1, TimeUnit.MINUTES,new LinkedBlockingQueue<>(10));

    /**
     * “WebFlux的函数式编程模型中，使用HandlerFunction处理HTTP请求，Handler Function是一个接收ServerRequest并返回延迟写入结果的（delayed）ServerResponse（即Mono）的函数。HandlerFunction相当于在基于注解的编程模型中标注@Request Mapping注解的方法体。
     *
     *
     */
    Mono<ServerResponse> getPersonList(ServerRequest request){
        Flux<String> personList = Flux.just("raines","lxl","success")
                .publishOn(Schedulers.fromExecutor(bizPoolExecutor))
                .map(e -> {
                    System.out.println(Thread.currentThread().getName());
                    return e;
                });
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(personList,String.class);
    }

    /**
     * getPerson方法内创建了一个Mono对象作为查找结果，然后调用ServerResponse.ok()创建一个响应结果，并且设置响应的contentType为JSON，响应体为创建的person对象。与getPersonList方法类似，只不过getPersonList方法创建了Flux对象作为响应体内容。
     *
     */
    Mono<ServerResponse> getPerson(ServerRequest request){
        Mono<String> person = Mono.just("raines");
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(person,String.class);
    }

}






















