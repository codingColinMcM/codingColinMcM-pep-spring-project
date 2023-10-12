package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository; // Spring Data JPA's repository interface
import org.springframework.data.jpa.repository.Modifying; // Spring Data JPA's Modifying annotation
import org.springframework.data.repository.query.Param; // Spring Data's Param annotation
import org.springframework.data.jpa.repository.Query; // Spring Data JPA's Query annotation

import javax.transaction.Transactional; // Java's Transactional annotation
import java.util.List;

import com.example.entity.Message; // Import custom Message entity

/**
 * Repository interface for managing Message entities.
 *
 * This interface extends JpaRepository, which provides standard CRUD operations
 * for the Message entity. Additional custom query methods can be defined here.
 */
public interface MessageRepository extends JpaRepository<Message, Integer> {

    /**
     * Custom query method to find messages posted by a specific user.
     *
     * @param posted_by The ID of the user whose messages to retrieve.
     * @return A list of messages posted by the user.
     */
    @Query("SELECT m FROM Message m WHERE m.posted_by = :posted_by")
    List<Message> findByPostedBy(@Param("posted_by") int posted_by);

    /**
     * Custom query method to update a message's text.
     *
     * @param messageText The updated message text.
     * @param message_id The ID of the message to update.
     * @return The number of rows modified (usually 1 if the message is found).
     */
    @Modifying
    @Transactional
    @Query("UPDATE Message SET message_text = ?1 WHERE message_id = ?2")
    int updateMessageTotal(String messageText, int message_id);

    /**
     * Custom query method to delete a message by its ID.
     *
     * @param message_id The ID of the message to delete.
     * @return The number of rows deleted (usually 1 if the message is found).
     */
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Message WHERE message_id = ?1", nativeQuery = true)
    int deleteMessageTotal(int message_id);
}
