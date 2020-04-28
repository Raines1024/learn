package com.raines.webflux.httpServer;

import reactor.core.publisher.Mono;
import reactor.netty.DisposableServer;
import reactor.netty.http.server.HttpServer;

/**
 * 创建一个HttpServer
 */
public class ReactorNetty {

    public static void main(String[] args) {
        DisposableServer server = HttpServer.create()//创建一个待配置的HttpServer
                .host("localhost")//配置Http服务的主机
                .port(8080)//配置Http服务的监听端口号
                //配置HTTP服务路由，为访问路径/hello提供GET请求并返回“Hello World!”；
                // 为访问路径/echo提供POST请求，并将收到的请求正文作为响应返回；
                // 为访问路径/path/{param}提供GET请求并返回path参数的值；
                // 将websocket提供给/ws并将接收的传入数据作为传出数据返回。
                .route(routes -> routes//设置路由规则
                        .get("/hello", (httpServerRequest, httpServerResponse) -> httpServerResponse.sendString(Mono.just("hello world!")))
                        .post("/echo", (request, response) -> response.send(request.receive().retain()))
                        .get("/path/{param}", (request, response) -> response.sendString(Mono.just(request.param("param"))))
                        .ws("/ws", (wsInbound, wsOutbound) -> wsOutbound.send(wsInbound.receive().retain())))
                .bindNow();
        //以阻塞的方式等待服务器关闭。
        //阻塞方式启动服务器，同步等待服务停止
        server.onDispose().block();
    }

}
