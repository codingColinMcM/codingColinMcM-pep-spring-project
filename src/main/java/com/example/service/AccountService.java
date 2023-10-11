package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Optional;
import org.springframework.http.HttpStatus;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // Method to register a new user
    public ResponseEntity<Object> registerUser(Account account) {
        // Save the user using the repository
        Optional<Account> possibleUser = accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
        if (!possibleUser.isPresent()){
            return ResponseEntity.ok(accountRepository.save(account));
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    // Method to authenticate a user
    public ResponseEntity<Object> authenticateUser(String username, String password) {
        Optional<Account> possibleUser = accountRepository.findByUsernameAndPassword(username, password);
        if (possibleUser.isPresent()){
            Account realUser = possibleUser.get();
            return ResponseEntity.ok(realUser);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}

