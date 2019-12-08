package me.duvu.tracking.internal.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @author beou on 10/4/18 12:54
 */
@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

    private final LocalMessageHandler localMessageHandler;

    public WebSocketConfiguration(LocalMessageHandler localMessageHandler) {
        this.localMessageHandler = localMessageHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(localMessageHandler, "/local");
    }
}
