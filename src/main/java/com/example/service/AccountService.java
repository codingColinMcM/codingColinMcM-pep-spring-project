package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity; // Spring's ResponseEntity for HTTP responses
import org.springframework.stereotype.Service;

import java.util.Optional;
import org.springframework.http.HttpStatus; // Spring's HttpStatus for HTTP status codes

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * Register a new user.
     *
     * @param account The account to register.
     * @return ResponseEntity with the registered account if registration is successful, or a conflict response if the user already exists.
     */
    public ResponseEntity<Object> registerUser(Account account) {
        // Check if the user already exists by username and password
        Optional<Account> possibleUser = accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
        if (!possibleUser.isPresent()) {
            return ResponseEntity.ok(accountRepository.save(account));
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT); // Conflict response if the user already exists
    }

    /**
     * Authenticate a user by username and password.
     *
     * @param username The username to authenticate.
     * @param password The password to authenticate.
     * @return ResponseEntity with the authenticated user if successful, or an unauthorized response if authentication fails.
     */
    public ResponseEntity<Object> authenticateUser(String username, String password) {
        Optional<Account> possibleUser = accountRepository.findByUsernameAndPassword(username, password);
        if (possibleUser.isPresent()) {
            Account realUser = possibleUser.get();
            return ResponseEntity.ok(realUser);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // Unauthorized response if authentication fails
    }
}
