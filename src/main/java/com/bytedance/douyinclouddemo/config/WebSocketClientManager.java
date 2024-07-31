package com.bytedance.douyinclouddemo.config;


import com.bytedance.douyinclouddemo.websocket.MyWebSocketClient;
import org.springframework.stereotype.Component;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.net.URI;
@Component
public class WebSocketClientManager {

    private Session session;
    private final String uri = "ws://127.0.0.1:8888";

    public void start() {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        System.out.println("Connecting to " + uri);
        try {
            session = container.connectToServer(MyWebSocketClient.class, URI.create(uri));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            if (session != null && session.isOpen()) {
                session.close();
                System.out.println("WebSocket connection closed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isRunning() {
        return session != null && session.isOpen();
    }
}
