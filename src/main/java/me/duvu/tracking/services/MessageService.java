package me.duvu.tracking.services;

import me.duvu.tracking.domain.Message;
import me.duvu.tracking.web.rest.model.request.MessageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author beou on 11/2/18 19:29
 */
@Service
public class MessageService {
    public Page<Message> getAll(String search, Pageable pageable) {
        return null;
    }
    public List<Message> getAll(String search) {
        return null;
    }
    public Message getById(Long id) {
        return null;
    }
    public Message create(MessageRequest request) {
        return null;
    }
    public void update(Long id, MessageRequest request) {}
    public void delete(Long id) {}
}
