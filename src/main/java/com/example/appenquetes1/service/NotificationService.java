package com.example.appenquetes1.service;

import com.example.appenquetes1.entity.Notification;
import com.example.appenquetes1.entity.SurveySubmission;
import com.example.appenquetes1.repository.NotificationRepository;
import com.example.appenquetes1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DashboardWebSocketService webSocketService;

    @Transactional
    public Notification createNotification(SurveySubmission submission, String surveyLibelle, String enqueteurNom) {
        Notification notification = new Notification();
        notification.setTitle("Nouvelle réponse d'enquête");
        notification.setMessage(String.format(
                "Vous avez reçu une nouvelle réponse de l'enquête \"%s\" par l'enquêteur %s le %s",
                surveyLibelle,
                enqueteurNom,
                submission.getSubmissionDate().toString()
        ));
        notification.setType("SUBMISSION");
        notification.setReferenceId(submission.getId());
        notification.setSurveyLibelle(surveyLibelle);
        notification.setEnqueteurNom(enqueteurNom);
        notification.setSubmissionDate(submission.getSubmissionDate());
        notification.setCreatedAt(LocalDateTime.now());
        notification.setRead(false);

        Notification saved = notificationRepository.save(notification);

        // Envoyer via WebSocket aux admins
        webSocketService.emitNewNotification(saved);

        return saved;
    }

    public List<Notification> getUnreadNotifications() {
        return notificationRepository.findByIsReadFalseOrderByCreatedAtDesc();
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAllByOrderByCreatedAtDesc();
    }

    @Transactional
    public void markAsRead(Long notificationId) {
        notificationRepository.updateReadStatus(notificationId, true);
    }

    @Transactional
    public void markAllAsRead() {
        notificationRepository.updateAllReadStatus(true);
    }

    @Transactional
    public void deleteNotification(Long notificationId) {
        notificationRepository.deleteById(notificationId);
    }

    @Transactional
    public void deleteAllNotifications() {
        notificationRepository.deleteAll();
    }

    public long getUnreadCount() {
        return notificationRepository.countByIsReadFalse();
    }
}