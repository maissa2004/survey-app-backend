package com.example.appenquetes1.service;

import com.example.appenquetes1.dto.Answer.AnswerCreateDTO;
import com.example.appenquetes1.entity.NmAnswers;
import com.example.appenquetes1.entity.Question;
import com.example.appenquetes1.entity.QuestionAnswers;
import com.example.appenquetes1.entity.Section;
import com.example.appenquetes1.repository.NmAnswersRepository;
import com.example.appenquetes1.repository.QuestionAnswersRepository;
import com.example.appenquetes1.repository.QuestionRepository;
import com.example.appenquetes1.repository.SectionRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionAnswersService {

    @Autowired
    private QuestionAnswersRepository repository;

    @Autowired
    private NmAnswersRepository nmAnswersRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private EntityManager entityManager;

    public QuestionAnswers save(QuestionAnswers qa) {

        if (qa.getNmAnswers() == null || qa.getNmAnswers().getId() == null) {
            throw new RuntimeException("Impossible de sauvegarder: nmAnswers est requis");
        }

        if (qa.getIsConditionnel() == null) {
            qa.setIsConditionnel(false);
        }
        return repository.save(qa);
    }

    public List<QuestionAnswers> findAll() {
        return repository.findAll();
    }

    public QuestionAnswers findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

    public List<QuestionAnswers> findByIsConditionnel(Boolean conditionnel) {
        return repository.findByIsConditionnel(conditionnel);
    }

    /*public List<NmAnswers> findNmAnswersByQuestionAnswersId(Integer id) {

        QuestionAnswers qa = findById(id);

        return qa.getNmAnswers();
    }*/

    @Transactional
    public void deleteByQuestionId(Integer questionId) {
        System.out.println("🔍 DELETE BY QUESTION ID: " + questionId);

        // Dissocier les références
        entityManager.createNativeQuery(
                "UPDATE question SET parent_answer_id = NULL WHERE parent_answer_id IN " +
                        "(SELECT id FROM question_answers WHERE id_question = ?)"
        ).setParameter(1, questionId).executeUpdate();

        entityManager.createNativeQuery(
                "UPDATE section SET parent_answer_id = NULL WHERE parent_answer_id IN " +
                        "(SELECT id FROM question_answers WHERE id_question = ?)"
        ).setParameter(1, questionId).executeUpdate();

        // Récupérer les IDs des NmAnswers (uniquement non-NULL)
        @SuppressWarnings("unchecked")
        List<Integer> nmAnswerIds = entityManager.createNativeQuery(
                "SELECT id_nm_answers FROM question_answers WHERE id_question = ? AND id_nm_answers IS NOT NULL"
        ).setParameter(1, questionId).getResultList();

        // Supprimer les QuestionAnswers
        entityManager.createNativeQuery("DELETE FROM question_answers WHERE id_question = ?")
                .setParameter(1, questionId)
                .executeUpdate();

        // Supprimer les NmAnswers
        for (Integer nmId : nmAnswerIds) {
            entityManager.createNativeQuery("DELETE FROM nm_answers WHERE id = ?")
                    .setParameter(1, nmId)
                    .executeUpdate();
        }

        System.out.println("✅ Suppression terminée pour la question " + questionId);
    }

    // Ajouter ces méthodes dans QuestionAnswersService.java

    @Transactional
    public QuestionAnswers addConditionalQuestion(Integer answerId, Integer questionId) {
        // 1. Récupérer la réponse (parent)
        QuestionAnswers answer = repository.findById(answerId)
                .orElseThrow(() -> new RuntimeException("Réponse non trouvée avec ID: " + answerId));

        // 2. Récupérer la question EXISTANTE
        Question conditionalQuestion = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question non trouvée avec ID: " + questionId));

        // 3. 🔥 IMPORTANT : Définir la relation bidirectionnelle (parent -> enfant)
        conditionalQuestion.setParentAnswer(answer);

        // 4. Sauvegarder la question avec la relation
        questionRepository.save(conditionalQuestion);

        // 5. Ajouter la question à la collection de la réponse
        answer.getCondiQuestions().add(conditionalQuestion);

        // 6. Sauvegarder la réponse
        QuestionAnswers saved = repository.save(answer);

        // 7. Log pour debug
        System.out.println("✅ Condition ajoutée!");
        System.out.println("   - Réponse ID: " + saved.getId());
        System.out.println("   - Question conditionnelle ID: " + questionId);
        System.out.println("   - Nombre total conditions: " + saved.getCondiQuestions().size());

        return saved;
    }

    @Transactional
    public QuestionAnswers addConditionalSection(Integer answerId, Integer sectionId) {
        System.out.println("🔍 addConditionalSection - answerId: " + answerId + ", sectionId: " + sectionId);

        // 1. Récupérer la réponse (parent)
        QuestionAnswers answer = repository.findById(answerId)
                .orElseThrow(() -> new RuntimeException("Réponse non trouvée avec ID: " + answerId));

        // 2. Récupérer la section EXISTANTE
        Section conditionalSection = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new RuntimeException("Section non trouvée avec ID: " + sectionId));

        System.out.println("Section trouvée: " + conditionalSection.getCode() + " - " + conditionalSection.getTitle());
        System.out.println("ParentAnswer avant: " + conditionalSection.getParentAnswer());

        // 3. Définir la relation bidirectionnelle
        conditionalSection.setParentAnswer(answer);

        // 4. Sauvegarder la section avec la relation
        Section savedSection = sectionRepository.save(conditionalSection);
        System.out.println("Section sauvegardée, parentAnswer: " + (savedSection.getParentAnswer() != null ? savedSection.getParentAnswer().getId() : "null"));

        // 5. Ajouter la section à la collection de la réponse
        answer.getCondiSections().add(savedSection);

        // 6. Sauvegarder la réponse
        QuestionAnswers saved = repository.save(answer);

        System.out.println("✅ Section conditionnelle ajoutée!");
        System.out.println("   - Réponse ID: " + saved.getId());
        System.out.println("   - Section conditionnelle ID: " + sectionId);
        System.out.println("   - Nombre total sections conditionnelles: " + saved.getCondiSections().size());

        return saved;
    }

    @Transactional
    public QuestionAnswers removeConditionalQuestion(Integer answerId, Integer questionId) {
        // 1. Récupérer la réponse
        QuestionAnswers answer = repository.findById(answerId)
                .orElseThrow(() -> new RuntimeException("Réponse non trouvée avec ID: " + answerId));

        // 2. Récupérer la question
        Question conditionalQuestion = questionRepository.findById(questionId).orElse(null);

        // 3. Supprimer la relation bidirectionnelle
        if (conditionalQuestion != null) {
            conditionalQuestion.setParentAnswer(null);
            questionRepository.save(conditionalQuestion);
        }

        // 4. Supprimer de la collection
        answer.getCondiQuestions().removeIf(q -> q.getId().equals(questionId));

        // 5. Sauvegarder
        return repository.save(answer);
    }

    @Transactional
    public QuestionAnswers removeConditionalSection(Integer answerId, Integer sectionId) {
        // 1. Récupérer la réponse
        QuestionAnswers answer = repository.findById(answerId)
                .orElseThrow(() -> new RuntimeException("Réponse non trouvée avec ID: " + answerId));

        // 2. Récupérer la section
        Section conditionalSection = sectionRepository.findById(sectionId).orElse(null);

        // 3. Supprimer la relation bidirectionnelle
        if (conditionalSection != null) {
            conditionalSection.setParentAnswer(null);
            sectionRepository.save(conditionalSection);
        }

        // 4. Supprimer de la collection
        answer.getCondiSections().removeIf(s -> s.getId().equals(sectionId));

        // 5. Sauvegarder
        return repository.save(answer);
    }

    @Transactional
    public void deleteFullAnswersByQuestionId(Integer questionId) {
        System.out.println("🗑️ deleteFullAnswersByQuestionId - questionId: " + questionId);

        // 🔥 1. D'abord, dissocier TOUTES les questions qui référencent ces réponses
        entityManager.createNativeQuery(
                "UPDATE question SET parent_answer_id = NULL " +
                        "WHERE parent_answer_id IN (SELECT id FROM question_answers WHERE id_question = ?)"
        ).setParameter(1, questionId).executeUpdate();

        // 2. Dissocier les sections
        entityManager.createNativeQuery(
                "UPDATE section SET parent_answer_id = NULL " +
                        "WHERE parent_answer_id IN (SELECT id FROM question_answers WHERE id_question = ?)"
        ).setParameter(1, questionId).executeUpdate();

        // 3. Récupérer les IDs des NmAnswers
        @SuppressWarnings("unchecked")
        List<Integer> nmAnswerIds = entityManager.createNativeQuery(
                "SELECT qa.id_nm_answers FROM question_answers qa WHERE qa.id_question = ? AND qa.id_nm_answers IS NOT NULL"
        ).setParameter(1, questionId).getResultList();

        System.out.println("NmAnswers IDs à supprimer: " + nmAnswerIds);

        // 4. 🔥 SUPPRIMER DIRECTEMENT AVEC NATIVE QUERY (évite Hibernate)
        int deleted = entityManager.createNativeQuery(
                "DELETE FROM question_answers WHERE id_question = ?"
        ).setParameter(1, questionId).executeUpdate();

        System.out.println("✅ " + deleted + " QuestionAnswers supprimées");

        // 5. Supprimer les NmAnswers
        for (Integer nmId : nmAnswerIds) {
            try {
                entityManager.createNativeQuery("DELETE FROM nm_answers WHERE id = ?")
                        .setParameter(1, nmId)
                        .executeUpdate();
                System.out.println("✅ NmAnswers " + nmId + " supprimée");
            } catch (Exception e) {
                System.err.println("⚠️ Impossible de supprimer NmAnswers " + nmId + ": " + e.getMessage());
            }
        }
    }

    public List<QuestionAnswers> findByQuestionId(Integer questionId) {
        return repository.findByQuestionId(questionId);
    }


    @Transactional
    public void replaceAnswers(Integer questionId, List<AnswerCreateDTO> newAnswers) {
        System.out.println("🔄 replaceAnswers - Question ID: " + questionId);
        System.out.println("📥 Réponses reçues: " + (newAnswers != null ? newAnswers.size() : 0));

        // 🔥 1. D'abord, dissocier les références parent_answer
        entityManager.createNativeQuery(
                "UPDATE question SET parent_answer_id = NULL WHERE parent_answer_id IN " +
                        "(SELECT id FROM question_answers WHERE id_question = ?)"
        ).setParameter(1, questionId).executeUpdate();

        entityManager.createNativeQuery(
                "UPDATE section SET parent_answer_id = NULL WHERE parent_answer_id IN " +
                        "(SELECT id FROM question_answers WHERE id_question = ?)"
        ).setParameter(1, questionId).executeUpdate();

        // 🔥 2. Récupérer les IDs des anciens NmAnswers (uniquement ceux qui ne sont pas NULL)
        @SuppressWarnings("unchecked")
        List<Integer> oldNmAnswerIds = entityManager.createNativeQuery(
                "SELECT id_nm_answers FROM question_answers WHERE id_question = ? AND id_nm_answers IS NOT NULL"
        ).setParameter(1, questionId).getResultList();
        System.out.println("Anciens NmAnswers IDs: " + oldNmAnswerIds);

        // 🔥 3. Supprimer TOUTES les QuestionAnswers (y compris celles avec nm_answers NULL)
        int deletedQA = entityManager.createNativeQuery(
                "DELETE FROM question_answers WHERE id_question = ?"
        ).setParameter(1, questionId).executeUpdate();
        System.out.println("✅ " + deletedQA + " QuestionAnswers supprimées");

        // 🔥 4. Supprimer les anciens NmAnswers
        for (Integer nmId : oldNmAnswerIds) {
            entityManager.createNativeQuery("DELETE FROM nm_answers WHERE id = ?")
                    .setParameter(1, nmId)
                    .executeUpdate();
            System.out.println("✅ NmAnswers " + nmId + " supprimé");
        }

        // 🔥 5. S'il n'y a pas de nouvelles réponses, on s'arrête
        if (newAnswers == null || newAnswers.isEmpty()) {
            System.out.println("ℹ️ Aucune nouvelle réponse à ajouter");
            return;
        }

        // 🔥 6. Récupérer la question
        Question question = entityManager.find(Question.class, questionId);
        if (question == null) {
            throw new RuntimeException("Question non trouvée avec ID: " + questionId);
        }

        LocalDate today = LocalDate.now();

        // 🔥 7. Créer les nouvelles réponses
        for (AnswerCreateDTO ans : newAnswers) {
            // Vérifier que le libellé n'est pas vide
            if (ans.getLibelle() == null || ans.getLibelle().trim().isEmpty()) {
                System.out.println("⚠️ Réponse ignorée: libellé vide");
                continue;
            }

            // Créer NmAnswers
            NmAnswers nmAnswer = new NmAnswers();
            nmAnswer.setCode(ans.getCode() != null && !ans.getCode().isEmpty() ? ans.getCode() : "ANS_" + System.currentTimeMillis());
            nmAnswer.setLibelle(ans.getLibelle());
            nmAnswer.setLibelleEn(ans.getLibelleEn() != null ? ans.getLibelleEn() : "");
            nmAnswer.setReference(ans.getReference() != null ? ans.getReference() : "");
            nmAnswer.setDtUpdate(today);
            NmAnswers savedNm = nmAnswersRepository.save(nmAnswer);

            // 🔥 VÉRIFICATION CRITIQUE : s'assurer que NmAnswers a bien été créé
            if (savedNm == null || savedNm.getId() == null) {
                System.err.println("❌ Erreur: NmAnswers non créé correctement pour la réponse: " + ans.getLibelle());
                throw new RuntimeException("Impossible de créer NmAnswers pour la réponse: " + ans.getLibelle());
            }

            System.out.println("✅ NmAnswers créée: " + savedNm.getId() + " - " + savedNm.getLibelle());

            // Créer QuestionAnswers
            QuestionAnswers qa = new QuestionAnswers();
            qa.setQuestion(question);
            qa.setNmAnswers(savedNm);  // Sera toujours non-null ici grâce à la vérification
            qa.setIsConditionnel(ans.getIsConditionnel() != null && ans.getIsConditionnel());
            qa.setDtUpdate(today);

            // Gérer les questions conditionnelles
            if (ans.getCondiQuestionIds() != null && !ans.getCondiQuestionIds().isEmpty()) {
                for (Integer condiId : ans.getCondiQuestionIds()) {
                    Question condiQuestion = questionRepository.findById(condiId).orElse(null);
                    if (condiQuestion != null) {
                        condiQuestion.setParentAnswer(qa);
                        questionRepository.save(condiQuestion);
                        qa.getCondiQuestions().add(condiQuestion);
                        System.out.println("  📌 Question conditionnelle ajoutée: " + condiQuestion.getCode());
                    }
                }
            }

            // Gérer les sections conditionnelles
            if (ans.getCondiSectionIds() != null && !ans.getCondiSectionIds().isEmpty()) {
                for (Integer condiId : ans.getCondiSectionIds()) {
                    Section condiSection = sectionRepository.findById(condiId).orElse(null);
                    if (condiSection != null) {
                        condiSection.setParentAnswer(qa);
                        sectionRepository.save(condiSection);
                        qa.getCondiSections().add(condiSection);
                        System.out.println("  📌 Section conditionnelle ajoutée: " + condiSection.getCode());
                    }
                }
            }

            repository.save(qa);
            System.out.println("✅ QuestionAnswers créée pour la réponse: " + ans.getLibelle());
        }

        System.out.println("✅ replaceAnswers terminé");
    }

    @Transactional
    public void cleanupQuestionAnswers(Integer questionId) {
        List<QuestionAnswers> answers = repository.findByQuestionId(questionId);

        for (QuestionAnswers answer : answers) {
            // Dissocier les questions conditionnelles
            for (Question q : answer.getCondiQuestions()) {
                q.setParentAnswer(null);
                questionRepository.save(q);
            }
            // Dissocier les sections conditionnelles
            for (Section s : answer.getCondiSections()) {
                s.setParentAnswer(null);
                sectionRepository.save(s);
            }
            // Vider les collections
            answer.getCondiQuestions().clear();
            answer.getCondiSections().clear();
            repository.save(answer);
        }
    }

    @Transactional
    public void cleanupOrphanedQuestionAnswers() {
        // Nettoyer toutes les QuestionAnswers qui ont nmAnswers = null
        int deleted = entityManager.createNativeQuery(
                "DELETE FROM question_answers WHERE id_nm_answers IS NULL"
        ).executeUpdate();
        System.out.println("🧹 Nettoyage: " + deleted + " QuestionAnswers orphelines supprimées");
    }

    private void createQuestionAnswer(Integer questionId, Integer nmAnswerId, String today, int index, int total, Runnable callback) {
        // 🔥 Vérifier que nmAnswerId n'est pas null
        if (nmAnswerId == null) {
            System.err.println("❌ Erreur: nmAnswerId est null pour l'index " + index);
            callback.run();
            return;
        }

        QuestionAnswers qa = new QuestionAnswers();
        Question question = questionRepository.findById(questionId).orElse(null);
        NmAnswers nmAnswer = nmAnswersRepository.findById(nmAnswerId).orElse(null);

        if (question == null) {
            System.err.println("❌ Question non trouvée: " + questionId);
            callback.run();
            return;
        }

        if (nmAnswer == null) {
            System.err.println("❌ NmAnswers non trouvée: " + nmAnswerId);
            callback.run();
            return;
        }

        qa.setQuestion(question);
        qa.setNmAnswers(nmAnswer);
        qa.setIsConditionnel(false);
        qa.setDtUpdate(LocalDate.parse(today));

        repository.save(qa);
        System.out.println("✅ QuestionAnswers créée (" + (index + 1) + "/" + total + ")");
        callback.run();
    }
}

