package com.example.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import com.example.entity.Account;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import com.example.entity.Message;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public ResponseEntity<Object> createMessage(Message message) {
        Optional<Account> possibleUser = accountRepository.findById(message.getPosted_by());
        String messageText = message.getMessage_text();
        if (!messageText.isBlank() && !(messageText.length() > 255) && possibleUser.isPresent()){
            return ResponseEntity.ok(messageRepository.save(message));
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(int messageId) {
        return messageRepository.findById(messageId).orElse(null);
    }

    public int deleteMessageById(int messageId) {
        int numDeleted = messageRepository.deleteMessageTotal(messageId);
        return numDeleted;
    }
    
    public ResponseEntity<Object> updateMessage(int messageId, Message updatedMessage) {
        Optional<Message> possibleMessage = messageRepository.findById(messageId);
        String updatedMessageText = updatedMessage.getMessage_text();
        if (!updatedMessageText.isBlank() && !(updatedMessageText.length() > 255) && possibleMessage.isPresent()){
            return ResponseEntity.ok(messageRepository.updateMessageTotal(updatedMessageText , messageId));
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public List<Message> getMessagesByUser(int postedBy) {
        return messageRepository.findByPostedBy(postedBy);
    }
}