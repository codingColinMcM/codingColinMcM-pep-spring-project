package com.example.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.example.repository.MessageRepository;
import com.example.entity.Message;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message createMessage(Message message) {
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(Long messageId) {
        return messageRepository.findById(messageId).orElse(null);
    }

    public Message deleteMessageById(Long messageId) {
        Message message = getMessageById(messageId);
        if (message != null) {
            messageRepository.delete(message);
        }
        return message;
    }

    public Message updateMessage(Long messageId, Message updatedMessage) {
        Message existingMessage = getMessageById(messageId);
        if (existingMessage != null) {
            existingMessage.setMessage_text(updatedMessage.getMessage_text());
            existingMessage.setTime_posted_epoch(updatedMessage.getTime_posted_epoch());
            messageRepository.save(existingMessage);
        }
        return existingMessage;
    }

    public List<Message> getMessagesByUser(Long postedBy) {
        return messageRepository.findByPostedBy(postedBy);
    }
}
