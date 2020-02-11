package me.duvu.tracking.internal.websocket;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import me.duvu.tracking.entities.UnknownDevice;
import me.duvu.tracking.internal.model.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;

/**
 * @author beou on 10/24/18 00:49
 */
public class MessageTypeAdapter implements JsonDeserializer<Message> {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public Message deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        String command = jsonElement.getAsJsonObject().get("command").getAsString();
        JsonElement je = jsonElement.getAsJsonObject().get("data");
        Message msg = new Message();
        msg.setCommand(command);
        switch (command) {
            case "EVENTDATA":
                Position p = context.deserialize(je, Position.class);
                msg.setData(p);
                break;
            case "UNKNOWNDEVICE":
                UnknownDevice ud = context.deserialize(je, UnknownDevice.class);
                msg.setData(ud);
                break;
        }

        return msg;
    }
}
