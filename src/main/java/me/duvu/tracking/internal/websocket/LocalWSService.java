package me.duvu.tracking.internal.websocket;

import com.google.gson.Gson;
import me.duvu.tracking.entities.Device;
import me.duvu.tracking.entities.UnknownDevice;
import me.duvu.tracking.internal.PositionService;
import me.duvu.tracking.internal.model.Position;
import me.duvu.tracking.internal.websocket.cmd.DeviceNotExistedWSEvent;
import me.duvu.tracking.repository.DeviceRepository;
import me.duvu.tracking.services.UnknownDeviceService;
import me.duvu.tracking.utils.GsonFactory;
import me.duvu.tracking.utils.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author beou on 10/4/18 15:12
 */
@Service
public class LocalWSService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    private final PositionService positionService;
    private final UnknownDeviceService unknownDeviceService;
    private final DeviceRepository deviceRepository;
    private final Gson gson = GsonFactory.create();

    public LocalWSService(PositionService positionService, UnknownDeviceService unknownDeviceService,
                          DeviceRepository deviceRepository) {
        this.positionService = positionService;
        this.unknownDeviceService = unknownDeviceService;
        this.deviceRepository = deviceRepository;
    }

    public List<WebSocketSession> getSessions() {
        return sessions;
    }

    public void send(String data) throws IOException {
        for (WebSocketSession session: sessions) {
            session.sendMessage(new TextMessage(data));
        }
    }

    public void send(Object data) throws IOException {
        for (WebSocketSession session: sessions) {
            session.sendMessage(new TextMessage(gson.toJson(data)));
        }
    }


    public void handle(WebSocketSession session, TextMessage message) {
        try {
            Message msg = gson.fromJson(message.getPayload(), Message.class);
            Object data = msg.getData();

            if (data instanceof Position) {
                Position position = (Position) data;
                String deviceId = position.getDeviceId();
                Device device = deviceRepository.findByDeviceId(deviceId);
                if (device != null) {
                    positionService.add(position);
                } else {
                    DeviceNotExistedWSEvent event = new DeviceNotExistedWSEvent();
                    event.setDeviceId(deviceId);
                    session.sendMessage(event.textMessage());
                }
            }

            if (data instanceof UnknownDevice) {
                unknownDeviceService.create((UnknownDevice)data);
            }
        } catch (Exception ex) {
            Log.error("Got error!", ex);
        }
    }

    @PreDestroy
    public void disconnect() throws IOException {
        for (WebSocketSession session: sessions) {
            session.sendMessage(new TextMessage("SHUTTING_DOWN"));
        }
    }
}
