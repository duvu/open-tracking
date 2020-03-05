package me.duvu.tracking.internal.websocket.cmd;

/**
 * @author beou on 11/4/18 08:09
 */

public interface WSEvent {
    String getCommand();
    String getData();
}
