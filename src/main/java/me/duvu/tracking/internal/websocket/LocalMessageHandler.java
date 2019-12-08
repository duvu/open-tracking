package me.duvu.tracking.internal.websocket;

import me.duvu.tracking.utils.Log;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * @author beou on 10/4/18 13:30
 */

@Component
public class LocalMessageHandler extends TextWebSocketHandler {

    private final LocalWSService localWSService;

    public LocalMessageHandler(LocalWSService localWSService) {
        this.localWSService = localWSService;
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        // protocol here.
        localWSService.handle(session, message);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //the messages will be broadcasted to all users.
        Log.info("Established a websocket connection");
        localWSService.getSessions().add(session);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        Log.info("Closing WebSocket");
        localWSService.getSessions().remove(session);
        session.close(CloseStatus.SERVICE_RESTARTED);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        localWSService.getSessions().remove(session);
        session.close(status);
    }
}
