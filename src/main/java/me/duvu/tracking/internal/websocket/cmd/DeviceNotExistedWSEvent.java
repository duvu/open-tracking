package me.duvu.tracking.internal.websocket.cmd;

public class DeviceNotExistedWSEvent extends WSEvent {
    private String command;
    private String deviceId;

    public DeviceNotExistedWSEvent() {
        this.command = "DeviceNotExistedWSEvent";
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public String getCommand() {
        return command;
    }

    @Override
    public String getData() {
        return deviceId;
    }
}
