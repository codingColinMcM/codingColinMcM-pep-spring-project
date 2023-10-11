package com.example.repository;

import com.example.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.data.jpa.repository.Query;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    @Query("SELECT m FROM Message m WHERE m.posted_by = :posted_by")
    List<Message> findByPostedBy(@Param("posted_by") int posted_by);

    @Modifying
    @Transactional
    // No idea what the second parameter would be 
    @Query("UPDATE Message SET message_text=?1 WHERE message_id=?2")
    int updateMessageTotal(String messageText, int message_id);

    @Modifying
    @Transactional
    // Only returns 0
    @Query(value="DELETE FROM Message WHERE message_id=?1", nativeQuery = true)
    int deleteMessageTotal(int message_id);

}
