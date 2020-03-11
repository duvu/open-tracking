package me.duvu.tracking.internal.websocket.cmd;

public class AckCmd extends WSEvent {
    private String command;

    public AckCmd() {
        this.command = "ACK";
    }

    @Override
    public String getCommand() {
        return this.command;
    }

    @Override
    public Object getData() {
        return null;
    }
}
