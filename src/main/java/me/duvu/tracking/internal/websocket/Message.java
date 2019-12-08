package me.duvu.tracking.internal.websocket;

import lombok.Data;

/**
 * @author beou on 10/24/18 00:24
 */

@Data
public class Message {
    private String command;
    private Object data;
}
