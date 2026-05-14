package com.example.appenquetes1.controller;

import com.example.appenquetes1.dto.dashboard.*;
import com.example.appenquetes1.entity.User;
import com.example.appenquetes1.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/dashboard")

public class DashboardController {

    @Autowired private SurveySubmissionRepository submissionRepo;
    @Autowired private SurveyRepository surveyRepo;
    @Autowired private SessionSurveyRepository sessionSurveyRepo;
    @Autowired private UserRepository userRepo;
    @Autowired private SessionRepository sessionRepo;

    // Indicateurs globaux
    @GetMapping("/stats")
    public ResponseEntity<DashboardStatsDTO> getDashboardStats() {
        long totalEnqueteurs = userRepo.countByRole(User.Role.enqueteur);
        long totalSurveys = surveyRepo.count();
        long totalSessions = sessionRepo.count();
        long totalSubmissions = submissionRepo.count();

        return ResponseEntity.ok(new DashboardStatsDTO(totalEnqueteurs, totalSurveys, totalSessions, totalSubmissions));
    }

    // Soumissions par jour (7 derniers jours)
    @GetMapping("/submissions-per-day")
    public ResponseEntity<List<DailySubmissionDTO>> getSubmissionsPerDay(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        if (startDate == null) startDate = LocalDate.now().minusDays(6);
        if (endDate == null) endDate = LocalDate.now();
        List<Object[]> results = submissionRepo.countSubmissionsGroupByDate(startDate,endDate);
        List<DailySubmissionDTO> dailyStats = new ArrayList<>();
        for (Object[] row : results) {
            LocalDate date = (LocalDate) row[0];
            Long count = (Long) row[1];
            dailyStats.add(new DailySubmissionDTO(date.toString(), count));
        }
        // compléter les jours sans donnée
        Map<String, Long> map = dailyStats.stream().collect(Collectors.toMap(DailySubmissionDTO::date, DailySubmissionDTO::count));
        List<DailySubmissionDTO> complete = new ArrayList<>();
        for (LocalDate d = startDate; !d.isAfter(endDate); d = d.plusDays(1)) {
            String dateStr = d.toString();
            complete.add(new DailySubmissionDTO(dateStr, map.getOrDefault(dateStr, 0L)));
        }
        return ResponseEntity.ok(complete);
    }

    // Répartition par statut
    @GetMapping("/submissions-by-status")
    public ResponseEntity<List<StatusCountDTO>> getSubmissionsByStatus() {
        List<Object[]> results = submissionRepo.countSubmissionsGroupByStatus();
        List<StatusCountDTO> list = results.stream()
                .map(row -> new StatusCountDTO((String) row[0], (Long) row[1]))
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    // Nombre de surveys par session (chaque session contient plusieurs surveys via SessionSurvey)
    @GetMapping("/surveys-per-session")
    public ResponseEntity<List<SessionSurveysDTO>> getSurveysPerSession() {
        List<Object[]> results = sessionSurveyRepo.countSurveysPerSession();
        List<SessionSurveysDTO> list = results.stream()
                .map(row -> new SessionSurveysDTO((String) row[0], ((Number) row[1]).longValue()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    // Top surveys par nombre de soumissions (limité à 5)
    @GetMapping("/top-surveys")
    public ResponseEntity<List<TopSurveyDTO>> getTopSurveys() {
        List<Object[]> results = submissionRepo.findTopSurveysBySubmissions();
        List<TopSurveyDTO> list = results.stream()
                .limit(5)
                .map(row -> new TopSurveyDTO((String) row[0], ((Number) row[1]).longValue()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }
//small stats de comparaison
@GetMapping("/stats/historical")
public ResponseEntity<DashboardStatsDTO> getHistoricalStats(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
    LocalDateTime dateTime = date.atStartOfDay();
    long totalEnqueteurs = userRepo.countByCreatedAtBefore(dateTime);
    long totalSurveys = surveyRepo.countByDtAddBefore(dateTime);
    long totalSessions = sessionRepo.countByDtCreateBefore(dateTime);
    long totalSubmissions = submissionRepo.countBySubmissionDateBefore(dateTime);
    return ResponseEntity.ok(new DashboardStatsDTO(totalEnqueteurs, totalSurveys, totalSessions, totalSubmissions));
}
}