package me.duvu.tracking.internal.websocket.cmd;

public class AckCmd implements WSEvent {
    @Override
    public String getCommand() {
        return "ACK";
    }

    @Override
    public String getData() {
        return null;
    }
}
