package com.raines.webflux.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class FunctionModelConfig {

    @Bean
    public FunctionPersonHandler handler(){
        return new FunctionPersonHandler();
    }

    /**
     * WebFlux服务器接收请求后，会将请求路由到带有RouterFunction的处理函数，RouterFunction是一个接收ServerRequest并返回延迟的HandlerFunction（即Mono）的函数。当路由函数匹配时，返回一个处理函数；否则返回一个空的Mono流对象。RouterFunction相当于@RequestMapping注解本身，两者的主要区别在于，路由器功能不仅提供数据，还提供行为。
     *
     * routerFunction方法创建RouterFunction的核心逻辑，其中代码1创建一个Router Function的builder对象；
     * 代码2注册GET方式请求的路由，意思是当用户访问/getPersonF路径的请求时，若accept头中匹配JSON类型数据，则使用FunctionPersonHandler类中的getPerson方法进行处理；
     * 代码3注册GET方式请求的路由，意思是当用户访问/getPersonListF路径的请求时，若accept头中匹配JSON类型数据，则使用Function-PersonHandler类中的getPersonList方法进行处理。
     */
    @Bean
    public RouterFunction<ServerResponse> routerFunction(final FunctionPersonHandler handler){
        //RouterFunctions.route()方法提供了一个便于创建路由规则的路由构建器
        RouterFunction<ServerResponse> route = RouterFunctions.route()//1
                .GET("/getPersonF", RequestPredicates.accept(MediaType.APPLICATION_JSON),handler::getPerson)//2
                .GET("/getPersonListF",RequestPredicates.accept(MediaType.APPLICATION_JSON),handler::getPersonList)//3
                .build();
        return route;
    }

}














