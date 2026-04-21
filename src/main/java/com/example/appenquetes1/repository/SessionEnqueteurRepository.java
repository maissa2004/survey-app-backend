package com.example.appenquetes1.repository;

import com.example.appenquetes1.entity.SessionEnqueteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SessionEnqueteurRepository extends JpaRepository<SessionEnqueteur, Integer> {

    List<SessionEnqueteur> findByIdSessionSurvey(Integer idSessionSurvey);

    List<SessionEnqueteur> findByIdUser(Integer userId);

    @Query("SELECT se FROM SessionEnqueteur se WHERE se.idSessionSurvey IN :sessionSurveyIds")
    List<SessionEnqueteur> findByIdSessionSurveyIn(@Param("sessionSurveyIds") List<Integer> sessionSurveyIds);

    @Modifying
    @Transactional
    @Query("DELETE FROM SessionEnqueteur se WHERE se.idSessionSurvey = :idSessionSurvey AND se.idUser = :idUser")
    void deleteByIdSessionSurveyAndIdUser(@Param("idSessionSurvey") Integer idSessionSurvey, @Param("idUser") Integer idUser);

    @Modifying
    @Transactional
    @Query("DELETE FROM SessionEnqueteur se WHERE se.idSessionSurvey = :idSessionSurvey")
    void deleteByIdSessionSurvey(@Param("idSessionSurvey") Integer idSessionSurvey);

    boolean existsByIdSessionSurveyAndIdUser(Integer idSessionSurvey, Integer idUser);
}