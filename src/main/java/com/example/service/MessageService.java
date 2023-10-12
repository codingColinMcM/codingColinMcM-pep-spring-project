package com.example.service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity; // Spring's ResponseEntity for HTTP responses
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus; // Spring's HttpStatus for HTTP status codes

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    /**
     * Create a new message.
     *
     * @param message The message to create.
     * @return ResponseEntity with the created message if creation is successful, or a bad request response if validation fails.
     */
    public ResponseEntity<Object> createMessage(Message message) {
        // Check message length, presence of the user, and user validation
        Optional<Account> possibleUser = accountRepository.findById(message.getPosted_by());
        String messageText = message.getMessage_text();
        if (!messageText.isBlank() && !(messageText.length() > 255) && possibleUser.isPresent()) {
            return ResponseEntity.ok(messageRepository.save(message));
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Bad request response if validation fails
    }

    /**
     * Retrieve all messages.
     *
     * @return List of all messages.
     */
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    /**
     * Retrieve a message by its ID.
     *
     * @param messageId The ID of the message to retrieve.
     * @return The message if found, null otherwise.
     */
    public Message getMessageById(int messageId) {
        return messageRepository.findById(messageId).orElse(null);
    }

    /**
     * Delete a message by its ID.
     *
     * @param messageId The ID of the message to delete.
     * @return The number of rows deleted.
     */
    public int deleteMessageById(int messageId) {
        return messageRepository.deleteMessageTotal(messageId);
    }

    /**
     * Update a message by its ID.
     *
     * @param messageId       The ID of the message to update.
     * @param updatedMessage The updated message.
     * @return ResponseEntity with the number of rows modified if the update is successful, or a bad request response if validation fails.
     */
    public ResponseEntity<Object> updateMessage(int messageId, Message updatedMessage) {
        Optional<Message> possibleMessage = messageRepository.findById(messageId);
        String updatedMessageText = updatedMessage.getMessage_text();
        if (!updatedMessageText.isBlank() && !(updatedMessageText.length() > 255) && possibleMessage.isPresent()) {
            return ResponseEntity.ok(messageRepository.updateMessageTotal(updatedMessageText, messageId));
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Bad request response if validation fails
    }

    /**
     * Retrieve messages posted by a specific user.
     *
     * @param postedBy The ID of the user whose messages to retrieve.
     * @return List of messages posted by the user.
     */
    public List<Message> getMessagesByUser(int postedBy) {
        return messageRepository.findByPostedBy(postedBy);
    }
}
