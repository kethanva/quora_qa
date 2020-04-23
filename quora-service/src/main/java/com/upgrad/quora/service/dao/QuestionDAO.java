package com.upgrad.quora.service.dao;

import com.upgrad.quora.service.entity.QuestionEntity;
import com.upgrad.quora.service.entity.UserEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Question dao
 */
@Repository
public class QuestionDAO {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Create *
     *
     * @param questionEntity question entity
     */
    public void create(QuestionEntity questionEntity) {
        entityManager.persist(questionEntity);
    }

    /**
     * Gets all questions *
     *
     * @return the all questions
     */
    public List<QuestionEntity> getAllQuestions() {
        return entityManager.createNamedQuery("getAllQuestions", QuestionEntity.class).getResultList();
    }

    /**
     * Gets question by id *
     *
     * @param questionId question id
     * @return the question by id
     */
    public QuestionEntity getQuestionById(String questionId) {
        try {
            return entityManager.createNamedQuery("getQuestionByUUID", QuestionEntity.class)
                    .setParameter("questionId", questionId).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * Delete question *
     *
     * @param questionEntity question entity
     */
    public void deleteQuestion(QuestionEntity questionEntity) {
        entityManager.remove(questionEntity);
    }

    /**
     * Gets all questions by user *
     *
     * @param userId user id
     * @return the all questions by user
     */
    public List<QuestionEntity> getAllQuestionsByUser(final UserEntity userId) {
        return entityManager
                .createNamedQuery("getQuestionByUser", QuestionEntity.class)
                .setParameter("user", userId)
                .getResultList();
    }

    /**
     * Edit question content *
     *
     * @param question question
     */
    public void editQuestionContent(QuestionEntity question) {
        entityManager.merge(question);
    }
}
