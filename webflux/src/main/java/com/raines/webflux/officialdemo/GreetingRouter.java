package com.raines.webflux.officialdemo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * Create a Router
 */
@Configuration
public class GreetingRouter {

    /**
     * WebFlux服务器接收请求后，会将请求路由到带有RouterFunction的处理函数，
     * RouterFunction是一个接收ServerRequest并返回延迟的HandlerFunction（即Mono）的函数。
     * 当路由函数匹配时，返回一个处理函数；否则返回一个空的Mono流对象。
     *
     * RouterFunction相当于@RequestMapping注解本身，两者的主要区别在于，路由器功能不仅提供数据，还提供行为。
     *
     */
    //The router listens for traffic on the /hello path and returns the value provided by our reactive handler class.
    @Bean
    public RouterFunction<ServerResponse> route(GreetingHandler greetingHandler) {
        //RouterFunctions.route()方法则提供了一个便于创建路由规则的路由构建器
        return RouterFunctions
                .route(RequestPredicates.GET("/hello").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), greetingHandler::hello);
    }
}

















