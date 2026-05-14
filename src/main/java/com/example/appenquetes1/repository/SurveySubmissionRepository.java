package com.example.appenquetes1.repository;

import com.example.appenquetes1.dto.admin.AnswerDetailDTO;
import com.example.appenquetes1.entity.SurveySubmission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SurveySubmissionRepository extends JpaRepository<SurveySubmission, Integer> {
    //@EntityGraph(attributePaths = {"survey", "user", "answers"})
    @Query("SELECT s FROM SurveySubmission s WHERE " +
            "(:surveyId IS NULL OR s.surveyId = :surveyId) AND " +
            "(:userId IS NULL OR s.userId = :userId) AND " +
            "(:status IS NULL OR s.status = :status) AND " +
            "(:startDate IS NULL OR s.validatedAt BETWEEN :startDate AND :endDate)")
    Page<SurveySubmission> findAllWithFilters(@Param("surveyId") Integer surveyId,
                                              @Param("userId") Integer userId,
                                              @Param("status") String status,
                                              @Param("startDate") LocalDateTime startDate,
                                              @Param("endDate") LocalDateTime endDate,                                              Pageable pageable);
    @Query("SELECT s FROM SurveySubmission s WHERE " +
            "(:surveyId IS NULL OR s.surveyId = :surveyId) AND " +
            "(:userId IS NULL OR s.userId = :userId) AND " +
            "(:status IS NULL OR s.status = :status) AND " +
            "(:validatedAt IS NULL OR s.validatedAt = :validatedAt)"
            )
    List<SurveySubmission> findAllWithFiltersNoPage(@Param("surveyId") Integer surveyId,
                                                    @Param("userId") Integer userId,
                                                    @Param("status") String status,
                                                    @Param("validatedAt") LocalDate validationDate);

    long countBySurveyId(Integer surveyId);
    long countBySurveyIdAndStatus(Integer surveyId, String status);
    long countByUserIdAndSurveyId(Integer userId, Integer surveyId);
    List<SurveySubmission> findByUserIdAndSurveyId(Integer userId, Integer surveyId);
    //@EntityGraph(attributePaths = {"survey", "user", "answers"})
    Optional<SurveySubmission> findById(Integer id);

//Dashboard avec statistics
@Query(value = "SELECT DATE(submission_date) as date, COUNT(*) " +
        "FROM survey_submission " +
        "WHERE DATE(submission_date) BETWEEN :startDate AND :endDate " +
        "GROUP BY DATE(submission_date) " +
        "ORDER BY date", nativeQuery = true)
List<Object[]> countSubmissionsGroupByDate(@Param("startDate") LocalDate startDate,
                                           @Param("endDate") LocalDate endDate);

    @Query("SELECT s.status, COUNT(s) FROM SurveySubmission s GROUP BY s.status")
    List<Object[]> countSubmissionsGroupByStatus();

    @Query(value = "SELECT s.libelle, COUNT(sub.id) FROM survey_submission sub INNER JOIN survey s ON sub.id_survey = s.id GROUP BY s.libelle ORDER BY COUNT(sub.id) DESC", nativeQuery = true)
    List<Object[]> findTopSurveysBySubmissions();

    @Query("SELECT AnswerDetailDTO(" +
            "a.id, a.codeQuestion, q.titleFr, a.value, a.referenceCode, a.fileName, a.fileType) " +
            "FROM Answers a, Question q WHERE a.codeQuestion = q.code AND a.submissionId = :submissionId")
    List<AnswerDetailDTO> findAnswerDetailsBySubmissionId(@Param("submissionId") Integer submissionId);
    long countBySubmissionDateBefore(LocalDateTime date);



}
