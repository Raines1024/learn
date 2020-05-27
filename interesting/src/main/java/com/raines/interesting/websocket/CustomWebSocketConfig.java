package com.raines.interesting.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import javax.annotation.Resource;

/**
 * https://www.cnblogs.com/onlymate/p/9521327.html
 * <p>
 * websocket的配置类
 * 这个类是配置类向Spring中注入handler
 * <p>
 * 补充说明：
 * setAllowedOrigins("*")一定要加上，不然只有访问localhost，其他的不予许访问
 * setAllowedOrigins(String[] domains),允许指定的域名或IP(含端口号)建立长连接，如果只允许自家域名访问，这里轻松设置。如果不限时使用"*"号，如果指定了域名，则必须要以http或https开头
 * 经查阅官方文档springwebsocket 4.1.5版本前默认支持跨域访问，之后的版本默认不支持跨域，需要设置
 * 使用withSockJS()的原因：
 * 　　一些浏览器中缺少对WebSocket的支持,因此，回退选项是必要的，而Spring框架提供了基于SockJS协议的透明的回退选项。
 * SockJS的一大好处在于提供了浏览器兼容性。优先使用原生WebSocket，如果在不支持websocket的浏览器中，会自动降为轮询的方式。
 * 除此之外，spring也对socketJS提供了支持。
 * 如果代码中添加了withSockJS()如下，服务器也会自动降级为轮询。
 * registry.addEndpoint("/coordination").withSockJS();
 * SockJS的目标是让应用程序使用WebSocket API，但在运行时需要在必要时返回到非WebSocket替代，即无需更改应用程序代码。
 * SockJS是为在浏览器中使用而设计的。它使用各种各样的技术支持广泛的浏览器版本。对于SockJS传输类型和浏览器的完整列表，可以看到SockJS客户端页面。
 * 传输分为3类:WebSocket、HTTP流和HTTP长轮询（按优秀选择的顺序分为3类）
 */
@Configuration
@EnableWebSocket
public class CustomWebSocketConfig implements WebSocketConfigurer {

    @Resource
    private CustomWebSocketHandler customWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(customWebSocketHandler, "/webSocketBySpring/customWebSocketHandler").addInterceptors(new CustomWebSocketInterceptor()).setAllowedOrigins("*");
        registry.addHandler(customWebSocketHandler, "/sockjs/webSocketBySpring/customWebSocketHandler").addInterceptors(new CustomWebSocketInterceptor()).setAllowedOrigins("*").withSockJS();
        registry.addHandler(customWebSocketHandler,"/ws").setAllowedOrigins("*");
    }

//    @Bean
//    public WebSocketHandler customWebSocketHandler() {
//        return new CustomWebSocketHandler();
//    }
}
