// com.example.appenquetes1.service.AnswersService.java
package com.example.appenquetes1.service;

import com.example.appenquetes1.dto.useranswer.UserAnswerRequestDTO;
import com.example.appenquetes1.dto.useranswer.UserAnswerResponseDTO;
import com.example.appenquetes1.entity.Answers;
import com.example.appenquetes1.entity.NmAnswers;
import com.example.appenquetes1.mapper.AnswersMapper;
import com.example.appenquetes1.repository.AnswersRepository;
import com.example.appenquetes1.repository.NmAnswersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnswersService {

    @Autowired
    private AnswersRepository answersRepository;

    @Autowired
    private NmAnswersRepository nmAnswersRepository;

    public UserAnswerResponseDTO save(UserAnswerRequestDTO request) {
        Answers answer = AnswersMapper.toEntity(request);
        Answers saved = answersRepository.save(answer);
        return AnswersMapper.toDTO(saved);
    }


    public List<UserAnswerResponseDTO> saveAll(List<UserAnswerRequestDTO> requests) {
        List<Answers> answers = requests.stream()
                .map(AnswersMapper::toEntity)
                .collect(Collectors.toList());
        List<Answers> savedList = answersRepository.saveAll(answers);
        return savedList.stream()
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

    @Transactional
    public void submitSurveyAnswers(List<UserAnswerRequestDTO> requests) {

        if (requests.isEmpty()) return;

        // Pour eviter le dedoublement des reponses(entre new et old answers)
        //Integer userId = requests.get(0).getIdUser();
        //Integer surveyId = requests.get(0).getIdSurvey();
        //answersRepository.deleteByUserIdAndSurveyId(userId, surveyId);

        List<Answers> answers = requests.stream()
                .map(dto -> {
                    Answers answer = AnswersMapper.toEntity(dto);
                    // Replace transient NmAnswers stubs with managed JPA proxies
                    // so Hibernate can resolve the FK in the answer_nm_answer join table
                    if (dto.getIdNmAnswer() != null && !dto.getIdNmAnswer().isEmpty()) {
                        List<NmAnswers> managed = dto.getIdNmAnswer().stream()
                                .map(id -> nmAnswersRepository.getReferenceById(id))
                                .collect(Collectors.toList());
                        answer.setNmAnswers(managed);
                    }
                    return answer;
                })
                .collect(Collectors.toList());

        answersRepository.saveAll(answers);
    }

    public long countUserAnswers(Integer userId, Integer surveyId) {
        return answersRepository.countByIdUserAndIdSurvey(userId, surveyId);
    }
}