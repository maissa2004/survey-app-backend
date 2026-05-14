package com.example.appenquetes1.service;

import com.example.appenquetes1.dto.admin.AnswerDetailDTO;
import com.example.appenquetes1.dto.admin.SubmissionAdminDTO;
import com.example.appenquetes1.dto.dashboard.DailySubmissionDTO;
import com.example.appenquetes1.entity.*;
import com.example.appenquetes1.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AdminAnswerService {
    @Autowired
    private SurveySubmissionRepository submissionRepo;
    @Autowired
    private QuestionRepository questionRepo;
    @Autowired
    private AnswersRepository answersRepo;
    @Autowired
    private SurveyRepository surveyRepo;
    @Autowired
    private UserRepository userRepo;
    //@Autowired private EmailService emailService;
    @Autowired
    private NmAnswersRepository nmAnswersRepo;
    @Autowired
    private SurveySubmissionRepository surveySubmissionRepository;
    @Autowired
    private SessionRepository sessionRepository;

    public Page<SubmissionAdminDTO> getSubmissions(Integer surveyId, Integer userId,
                                                   String status, LocalDate validationDate,
                                                   Pageable pageable) {
        LocalDateTime start = null, end = null;
        if (validationDate != null) {
            start = validationDate.atStartOfDay();//comme si 00:00:00
            end = validationDate.atTime(23, 59, 59);//comme si  23:59:59
        }
        Page<SurveySubmission> page = submissionRepo.findAllWithFilters(
                surveyId, userId, status, start, end, pageable);
        return page.map(this::toDTO);
    }

    public List<SubmissionAdminDTO> getAllSubmissionsNoPage(Integer surveyId, Integer userId,
                                                            String status, LocalDate validationDate) {
        LocalDateTime start = null, end = null;
        if (validationDate != null) {
            start = validationDate.atStartOfDay();
            end = validationDate.atTime(23, 59, 59);
        }
        Pageable allPageable = PageRequest.of(0, Integer.MAX_VALUE);
        Page<SurveySubmission> page = submissionRepo.findAllWithFilters(
                surveyId, userId, status, start, end, allPageable);
        return page.getContent().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public SubmissionAdminDTO getSubmissionDetail(Integer submissionId) {
        SurveySubmission sub = submissionRepo.findById(submissionId)
                .orElseThrow(() -> new RuntimeException("Soumission non trouvée"));
        return toDTO(sub);
    }


    @Transactional
    public void validateSubmission(Integer submissionId, Integer adminId, String comment) {
        SurveySubmission sub = submissionRepo.findById(submissionId).orElseThrow();
        sub.setStatus("ACCEPTE");
        sub.setValidationComment(comment);
        sub.setValidatedBy(adminId);
        sub.setValidatedAt(LocalDateTime.now());
        submissionRepo.save(sub);
        // Envoi email à l'enquêteur
        //emailService.sendValidationStatus(sub.getUserId(), sub.getSurveyId(), "acceptée", comment);
    }

    @Transactional
    public void rejectSubmission(Integer submissionId, Integer adminId, String comment) {
        SurveySubmission sub = submissionRepo.findById(submissionId).orElseThrow();
        sub.setStatus("REJETE");
        sub.setValidationComment(comment);
        sub.setValidatedBy(adminId);
        sub.setValidatedAt(LocalDateTime.now());
        submissionRepo.save(sub);
        //emailService.sendValidationStatus(sub.getUserId(), sub.getSurveyId(), "rejetée", comment);
    }

//    @Transactional
//    public void batchValidate(List<Integer> submissionIds, Integer adminId, String comment) {
//        for (Integer id : submissionIds) {
//            SurveySubmission sub = submissionRepo.findById(id)
//                    .orElseThrow(() -> new RuntimeException("Soumission introuvable : " + id));
//            sub.setStatus("ACCEPTE");
//            sub.setValidationComment(comment);
//            sub.setValidatedBy(adminId);
//            sub.setValidatedAt(LocalDateTime.now());
//            //submissionRepo.save(sub);
//            // Envoi email à l'enquêteur
//            //emailService.sendValidationStatus(sub.getUserId(), sub.getSurveyId(), "acceptée", comment);
//        }
//    }
//    @Transactional
//    public void batchReject(List<Integer> submissionIds, Integer adminId, String comment) {
//        for (Integer id : submissionIds) {
//            SurveySubmission sub = submissionRepo.findById(id)
//                    .orElseThrow(() -> new RuntimeException("Soumission introuvable : " + id));
//            sub.setStatus("REJETE");
//            sub.setValidationComment(comment);
//            sub.setValidatedBy(adminId);
//            sub.setValidatedAt(LocalDateTime.now());
//            //submissionRepo.save(sub);
//            //emailService.sendValidationStatus(sub.getUserId(), sub.getSurveyId(), "rejetée", comment);
//        }
//    }

    private SubmissionAdminDTO toDTO(SurveySubmission sub) {
        SubmissionAdminDTO dto = new SubmissionAdminDTO();
        dto.setId(sub.getId());

        dto.setSurveyId(sub.getSurveyId());

        if (sub.getSurveyId() != null) {
            surveyRepo.findById(sub.getSurveyId()).ifPresent(s -> dto.setSurveyLibelle(s.getLibelle()));
        } else {
            dto.setSurveyLibelle("N/A");
        }

        dto.setUserId(sub.getUserId());
        userRepo.findById(sub.getUserId()).ifPresent(u -> dto.setUsername(u.getUsername()));

        dto.setSubmissionDate(sub.getSubmissionDate());
        dto.setStatus(sub.getStatus());
        dto.setValidationComment(sub.getValidationComment());
        dto.setValidatedBy(sub.getValidatedBy());

        dto.setValidatedAt(sub.getValidatedAt());

        // Récupérer nom d'admin qui a validé / rejeté
        if (sub.getValidatedBy() != null) {
            userRepo.findById(sub.getValidatedBy()).ifPresent(admin ->
                    dto.setValidatedByUsername(admin.getUsername())
            );
        }
        List<Answers> answers = answersRepo.findBySubmissionId(sub.getId());
        List<AnswerDetailDTO> answerDetails = answers.stream().map(a -> {
            AnswerDetailDTO ad = new AnswerDetailDTO();
            ad.setId(a.getId());
            ad.setCodeQuestion(a.getCodeQuestion());

            String questionText = questionRepo.findByCode(a.getCodeQuestion())
                    .map(Question::getTitleFr)
                    .orElse(a.getCodeQuestion());
            ad.setQuestionText(questionText);

            ad.setValue(a.getValue());
            ad.setReferenceCode(a.getReferenceCode());
            ad.setFileName(a.getFileName());
            ad.setFileType(a.getFileType());

            if (a.getCodeQuestion() != null && a.getCodeQuestion().contains("SIGN")) {
                ad.setValue(a.getValue());
            }
            if (a.getNmAnswers() != null && !a.getNmAnswers().isEmpty()) {
                List<String> options = a.getNmAnswers().stream()
                        .map(NmAnswers::getLibelle)
                        .collect(Collectors.toList());
                ad.setSelectedOptions(options);
            }
            return ad;
        }).collect(Collectors.toList());
        dto.setAnswers(answerDetails);
        return dto;
    }

    public Map<String, Object> getSurveyStats(Integer surveyId) {
        Map<String, Object> stats = new HashMap<>();

        long total = submissionRepo.countBySurveyId(surveyId);
        long validated = submissionRepo.countBySurveyIdAndStatus(surveyId, "ACCEPTE");
        long rejected = submissionRepo.countBySurveyIdAndStatus(surveyId, "REJETE");
        long pending = submissionRepo.countBySurveyIdAndStatus(surveyId, "EN ATTENTE");

        stats.put("totalSubmissions", total);
        stats.put("ACCEPTE", validated);
        stats.put("REJETE", rejected);
        stats.put("EN ATTENTE", pending);

        // Add average score if you have an AnswerRepository
        // Double avgScore = answerRepository.calculateAverageScoreForSurvey(surveyId);
        // stats.put("averageScore", avgScore != null ? avgScore : 0.0);

        return stats;
    }

    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalEnqueteurs", userRepo.countByRole(User.Role.enqueteur));
        stats.put("totalSurveys", surveyRepo.count());
        stats.put("totalSessions", sessionRepository.count());
        stats.put("totalSubmissions", submissionRepo.count());
        return stats;
    }

    public List<DailySubmissionDTO> getDailySubmissions() {
        // Par défaut : 7 derniers jours (derniers 7 jours incluant aujourd'hui)
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(6); // 7 jours au total

        List<Object[]> results = submissionRepo.countSubmissionsGroupByDate(startDate, endDate);

        // Transformer les résultats en Map<date, count>
        Map<LocalDate, Long> map = results.stream()
                .collect(Collectors.toMap(
                        row -> (LocalDate) row[0],
                        row -> (Long) row[1]
                ));

        // Générer tous les jours entre startDate et endDate pour compléter les jours sans donnée
        List<DailySubmissionDTO> completeList = new ArrayList<>();
        for (LocalDate d = startDate; !d.isAfter(endDate); d = d.plusDays(1)) {
            long count = map.getOrDefault(d, 0L);
            completeList.add(new DailySubmissionDTO(d.toString(), count));
        }
        return completeList;
    }
}

