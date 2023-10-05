package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // Method to register a new user
    public Account registerUser(Account account) {
        // Save the user using the repository
        return accountRepository.save(account);
    }

    // Method to authenticate a user
    public Account authenticateUser(String username, String password) {
        // Implement your authentication logic using the repository
        // Example: Find by username and password
        return accountRepository.findByUsernameAndPassword(username, password);
    }
}

