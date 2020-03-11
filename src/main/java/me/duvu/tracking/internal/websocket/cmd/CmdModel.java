package me.duvu.tracking.internal.websocket.cmd;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class CmdModel extends WSEvent {

    private static final long serialVersionUID = -158399572283055282L;

    private String command;
    private String data;

    private String remoteAddress;
    private Integer remotePort;
    private String deviceId;
}
