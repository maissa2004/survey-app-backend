// com.example.appenquetes1.service.AnswersService.java
package com.example.appenquetes1.service;

import com.example.appenquetes1.dto.useranswer.UserAnswerRequestDTO;
import com.example.appenquetes1.dto.useranswer.UserAnswerResponseDTO;
import com.example.appenquetes1.entity.Answers;
import com.example.appenquetes1.mapper.AnswersMapper;
import com.example.appenquetes1.repository.AnswersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnswersService {

    @Autowired
    private AnswersRepository answersRepository;

    public UserAnswerResponseDTO save(UserAnswerRequestDTO request) {
        Answers answer = AnswersMapper.toEntity(request);
        Answers saved = answersRepository.save(answer);
        return AnswersMapper.toDTO(saved);
    }

    public List<UserAnswerResponseDTO> saveAll(List<UserAnswerRequestDTO> requests) {
        List<Answers> answers = requests.stream()
                .map(AnswersMapper::toEntity)
                .collect(Collectors.toList());
        List<Answers> saved = answersRepository.saveAll(answers);
        return saved.stream()
                .map(AnswersMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<UserAnswerResponseDTO> findByUser(Integer userId) {
        return answersRepository.findByIdUser(userId).stream()
                .map(AnswersMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<UserAnswerResponseDTO> findBySurvey(Integer surveyId) {
        return answersRepository.findByIdSurvey(surveyId).stream()
                .map(AnswersMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<UserAnswerResponseDTO> findByUserAndSurvey(Integer userId, Integer surveyId) {
        return answersRepository.findByIdUserAndIdSurvey(userId, surveyId).stream()
                .map(AnswersMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UserAnswerResponseDTO findById(Integer id) {
        return answersRepository.findById(id)
                .map(AnswersMapper::toDTO)
                .orElse(null);
    }

    public void delete(Integer id) {
        answersRepository.deleteById(id);
    }

    @Transactional
    public void deleteByUserAndSurvey(Integer userId, Integer surveyId) {
        answersRepository.deleteByUserIdAndSurveyId(userId, surveyId);
    }

    public long countUserAnswers(Integer userId, Integer surveyId) {
        return answersRepository.countByIdUserAndIdSurvey(userId, surveyId);
    }
}