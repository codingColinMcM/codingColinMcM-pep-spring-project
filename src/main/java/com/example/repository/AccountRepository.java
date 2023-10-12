package com.example.repository;

import org.springframework.stereotype.Repository; // Spring Framework's repository annotation
import org.springframework.data.jpa.repository.JpaRepository; // Spring Data JPA's repository interface
import java.util.Optional; // Java's Optional class for representing possibly null values

import com.example.entity.Account; // Import custom Account entity

/**
 * Repository interface for managing Account entities.
 *
 * This interface extends JpaRepository, which provides standard CRUD operations
 * for the Account entity. Additional custom query methods can be defined here.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    /**
     * Custom query method to find an account by username and password.
     *
     * @param username The username of the account.
     * @param password The password of the account.
     * @return An Optional containing the Account if found, or an empty Optional if not found.
     */
    Optional<Account> findByUsernameAndPassword(String username, String password);
}

