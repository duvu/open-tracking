package me.duvu.tracking.internal.websocket.cmd;

import me.duvu.tracking.utils.GsonFactory;
import org.springframework.web.socket.TextMessage;

/**
 * @author beou on 11/4/18 08:09
 */

public abstract class WSEvent {
    public abstract String getCommand();
    public abstract Object getData();
    public TextMessage textMessage() {
        return new TextMessage(String.format("{\"command\":\"%s\", \"data\":\"%s\"}", getCommand(), GsonFactory.create().toJson(getData())));
    }
}
