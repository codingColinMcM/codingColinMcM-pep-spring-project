package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/register") // Handle POST requests at /register
    public Account registerUser(@RequestBody Account account) throws JsonProcessingException {
        return accountService.registerUser(account);
    }

    @PostMapping("/login") // Handle POST requests at /login
    public Account loginUser(@RequestBody Account account) throws JsonProcessingException {
        return accountService.authenticateUser(account.getUsername(), account.getPassword());
    }

    @PostMapping("/messages") // Handle POST requests at /messages
    public Message createMessage(@RequestBody Message message) throws JsonProcessingException {
        return messageService.createMessage(message);
    }

    @GetMapping("/messages") // Handle GET requests at /messages
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }

    @GetMapping("/messages/{message_id}") // Handle GET requests at /messages/{message_id}
    public Message getMessageById(@PathVariable int message_id) {
        return messageService.getMessageById(message_id);
    }

    @DeleteMapping("/messages/{message_id}") // Handle DELETE requests at /messages/{message_id}
    public Message deleteMessageById(@PathVariable int message_id) {
        return messageService.deleteMessageById(message_id);
    }

    @PatchMapping("/messages/{message_id}") // Handle PUT requests at /messages/{message_id}
    public Message updateMessage(@PathVariable int message_id, @RequestBody Message message) {
        return messageService.updateMessage(message_id, message);
    }

    @GetMapping("/accounts/{account_id}/messages") // Handle GET requests at /accounts/{account_id}/messages
    public List<Message> getMessagesByUser(@PathVariable int account_id) {
        return messageService.getMessagesByUser(account_id);
    }
}
