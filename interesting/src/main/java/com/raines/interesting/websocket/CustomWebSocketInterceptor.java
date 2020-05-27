package com.raines.interesting.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * WebSocket握手时的拦截器
 * 这个类的作用就是在连接成功前和成功后增加一些额外的功能
 * <p>
 * 我们希望能够把websocketSession和httpsession对应起来，这样就能根据当前不同的session，定向对websocketSession进行数据返回;在查询资料之后，发现spring中有一个拦截器接口，HandshakeInterceptor，可以实现这个接口，来拦截握手过程，向其中添加属性
 */
public class CustomWebSocketInterceptor implements HandshakeInterceptor {
    private Logger logger = LoggerFactory.getLogger(CustomWebSocketInterceptor.class);

    /**
     * 关联HeepSession和WebSocketSession，
     * beforeHandShake方法中的Map参数 就是对应websocketSession里的属性
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler handler, Map<String, Object> map) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            logger.info("*****beforeHandshake******");
            HttpServletRequest httpServletRequest = ((ServletServerHttpRequest) request).getServletRequest();
            HttpSession session = httpServletRequest.getSession(true);

            logger.info("mchNo：{}", httpServletRequest.getParameter("mchNo"));
            if (session != null) {

                map.put("sessionId", session.getId());
                map.put("mchNo", httpServletRequest.getParameter("mchNo"));
            }
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
        logger.info("******afterHandshake******");
    }
}