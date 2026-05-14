//package com.example.appenquetes1.service;
//
//import com.example.appenquetes1.entity.Survey;
//import com.example.appenquetes1.entity.User;
//import com.example.appenquetes1.repository.SurveyRepository;
//import com.example.appenquetes1.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//
//@Service
//public class EmailService {
//
//    @Autowired private JavaMailSender mailSender;
//    @Autowired private UserRepository userRepo;
//    @Autowired private SurveyRepository surveyRepo;
//
//    public void sendValidationStatus(Integer userId, Integer surveyId, String status, String comment) {
//        User user = userRepo.findById(userId).orElse(null);
//        Survey survey = surveyRepo.findById(surveyId).orElse(null);
//        if (user == null || user.getEmail() == null || survey == null) return;
//
//        SimpleMailMessage msg = new SimpleMailMessage();
//        msg.setTo(user.getEmail());
//        msg.setSubject("Votre soumission pour l'enquête \"" + survey.getLibelle() + "\" a été " + status);
//        msg.setText("Bonjour " + user.getUsername() + ",\n\n" +
//                "Votre soumission pour l'enquête \"" + survey.getLibelle() + "\" a été " + status + ".\n" +
//                "Commentaire : " + (comment != null ? comment : "Aucun") + "\n\n" +
//                "Cordialement,\nL'équipe d'administration");
//        mailSender.send(msg);
//    }
//}
