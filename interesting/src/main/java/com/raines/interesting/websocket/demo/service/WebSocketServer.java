package com.raines.interesting.websocket.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.server.ServerEndpoint;

@Slf4j
@ServerEndpoint("/websocket/{sid}")
@Component
public class WebSocketServer {



}
