package com.example.appenquetes1.repository;

import com.example.appenquetes1.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByIsReadFalseOrderByCreatedAtDesc();

    List<Notification> findAllByOrderByCreatedAtDesc();

    long countByIsReadFalse();

    @Modifying
    @Transactional
    @Query("UPDATE Notification n SET n.isRead = :isRead WHERE n.id = :id")
    void updateReadStatus(@Param("id") Long id, @Param("isRead") boolean isRead);

    @Modifying
    @Transactional
    @Query("UPDATE Notification n SET n.isRead = :isRead")
    void updateAllReadStatus(@Param("isRead") boolean isRead);
}