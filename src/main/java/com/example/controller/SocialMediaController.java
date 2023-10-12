package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity; // Spring's ResponseEntity for HTTP responses
import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
*/

/**
 * Controller for managing social media operations.
*/
@RestController
@RequestMapping("") // Define a base URL for all endpoints
public class SocialMediaController {

    private final AccountService accountService;
    private final MessageService messageService;

    @Autowired // Inject dependencies using Spring's DI
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    /**
     * Register a new user.
    *
    * @param account The user account to register.
    * @return ResponseEntity with the registered user if registration is successful, or a conflict response if the user already exists.
    */
    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody Account account) throws JsonProcessingException {
        return accountService.registerUser(account);
    }

    /**
     * Authenticate a user.
    *
    * @param account The user account to authenticate.
    * @return ResponseEntity with the authenticated user if authentication is successful, or an unauthorized response if authentication fails.
    */
    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody Account account) throws JsonProcessingException {
        return accountService.authenticateUser(account.getUsername(), account.getPassword());
    }

    /**
     * Create a new message.
    *
    * @param message The message to create.
    * @return ResponseEntity with the created message if creation is successful, or a bad request response if validation fails.
    */
    @PostMapping("/messages")
    public ResponseEntity<Object> createMessage(@RequestBody Message message) throws JsonProcessingException {
        return messageService.createMessage(message);
    }

    /**
     * Retrieve all messages.
    *
    * @return List of all messages.
    */
    @GetMapping("/messages")
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }

    /**
     * Retrieve a message by its ID.
    *
    * @param message_id The ID of the message to retrieve.
    * @return The message if found.
    */
    @GetMapping("/messages/{message_id}")
    public Message getMessageById(@PathVariable int message_id) {
        return messageService.getMessageById(message_id);
    }

    /**
     * Delete a message by its ID.
    *
    * @param message_id The ID of the message to delete.
    * @return The number of rows deleted.
    */
    @DeleteMapping("/messages/{message_id}")
    public int deleteMessageById(@PathVariable int message_id) {
        return messageService.deleteMessageById(message_id);
    }

    /**
     * Update a message by its ID.
    *
    * @param message_id The ID of the message to update.
    * @param message The updated message.
    * @return ResponseEntity with the number of rows modified if the update is successful, or a bad request response if validation fails.
    */
    @PatchMapping("/messages/{message_id}")
    public ResponseEntity<Object> updateMessage(@PathVariable int message_id, @RequestBody Message message) {
        return messageService.updateMessage(message_id, message);
    }

    /**
     * Retrieve messages posted by a specific user.
    *
    * @param account_id The ID of the user whose messages to retrieve.
    * @return List of messages posted by the user.
    */
    @GetMapping("/accounts/{account_id}/messages")
    public List<Message> getMessagesByUser(@PathVariable int account_id) {
        return messageService.getMessagesByUser(account_id);
    }
} 