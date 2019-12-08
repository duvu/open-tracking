package me.duvu.tracking.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.duvu.tracking.internal.websocket.Message;
import me.duvu.tracking.internal.websocket.MessageTypeAdapter;

/**
 * @author beou on 10/9/18 02:04
 */
public class GsonFactory {
    private static Gson instance = null;
    public static Gson create() {
        if (instance == null) {
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(Message.class, new MessageTypeAdapter());
            instance = builder.create();
        }
        return instance;
    }
}
