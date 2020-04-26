package com.upgrad.quora.service.business;

import com.upgrad.quora.service.common.GenericErrorCode;
import com.upgrad.quora.service.dao.QuestionDAO;
import com.upgrad.quora.service.dao.UserAuthTokenDao;
import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.QuestionEntity;
import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.InvalidQuestionException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Question business service
 */
@Service
public class QuestionBusinessService {

    @Autowired
    private QuestionDAO questionDAO;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserAuthTokenDao userAuthTokenDao;

    /**
     * Create question entity
     *
     * @param questionEntity     question entity
     * @param authorizationToken authorization token
     * @return the question entity
     * @throws AuthorizationFailedException authorization failed exception
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public QuestionEntity create(QuestionEntity questionEntity, String authorizationToken) throws AuthorizationFailedException {
        UserAuthTokenEntity userAuthTokenEntity = validateAuthorizationToken(authorizationToken);
        questionEntity.setDate(ZonedDateTime.now());
        questionEntity.setUuid(UUID.randomUUID().toString());
        questionEntity.setUserEntity(userAuthTokenEntity.getUserEntity());
        questionDAO.create(questionEntity);
        return questionEntity;
    }

    /**
     * Gets all questions
     *
     * @param authorizationToken authorization token
     * @return the all questions
     * @throws AuthorizationFailedException authorization failed exception
     */
    public List<QuestionEntity> getAllQuestions(String authorizationToken) throws AuthorizationFailedException {
        UserAuthTokenEntity userAuthTokenEntity = validateAuthorizationToken(authorizationToken);
        return questionDAO.getAllQuestions();

    }

    /**
     * Edit question content
     *
     * @param questionEntity     question entity
     * @param authorizationToken authorization token
     * @return the question entity
     * @throws AuthorizationFailedException authorization failed exception
     * @throws InvalidQuestionException     invalid question exception
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public QuestionEntity editQuestionContent(QuestionEntity questionEntity, String authorizationToken) throws AuthorizationFailedException, InvalidQuestionException {
        UserAuthTokenEntity userAuthTokenEntity = validateAuthorizationToken(authorizationToken);

        QuestionEntity questionById = questionDAO.getQuestionById(questionEntity.getUuid());

        if (null == questionById)
            throw new InvalidQuestionException(GenericErrorCode.QUES_001.getCode(), GenericErrorCode.QUES_001.getDefaultMessage());
        if (!questionById.getUserEntity().getUuid().equals(userAuthTokenEntity.getUser().getUuid()))
            throw new AuthorizationFailedException(GenericErrorCode.ATHR_003.getCode(), "Only the question owner can edit the question");
        questionById.setContent(questionEntity.getContent());
        questionDAO.editQuestionContent(questionById);
        return questionById;
    }


    /**
     * Delete question entity
     *
     * @param accessToken access token
     * @param questionId  question id
     * @return the question entity
     * @throws AuthorizationFailedException authorization failed exception
     * @throws InvalidQuestionException     invalid question exception
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public QuestionEntity deleteQuestion(final String accessToken, final String questionId)
            throws AuthorizationFailedException, InvalidQuestionException {
        UserAuthTokenEntity userAuthEntity = userAuthTokenDao.getUserAuthByToken(accessToken);
        if (userAuthEntity == null) {
            throw new AuthorizationFailedException("ATHR-001", "User has not signed in");
        } else if (userAuthEntity.getLogoutAt() != null) {
            throw new AuthorizationFailedException(
                    "ATHR-002", "User is signed out.Sign in first to delete the question");
        }
        QuestionEntity questionEntity = questionDAO.getQuestionById(questionId);
        if (questionEntity == null) {
            throw new InvalidQuestionException("QUES-001", "Entered question uuid does not exist");
        }
        if (!questionEntity.getUserEntity().getUuid().equals(userAuthEntity.getUserEntity().getUuid())
                && !userAuthEntity.getUserEntity().getRole().equals("admin")) {
            throw new AuthorizationFailedException(
                    "ATHR-003", "Only the question owner or admin can delete the question");
        }

        questionDAO.deleteQuestion(questionEntity);
        return questionEntity;
    }

    /**
     * Gets all questions by user
     *
     * @param userId      user id
     * @param accessToken access token
     * @return the all questions by user
     * @throws AuthorizationFailedException authorization failed exception
     * @throws UserNotFoundException        user not found exception
     */
    public List<QuestionEntity> getAllQuestionsByUser(final String userId, final String accessToken)
            throws AuthorizationFailedException, UserNotFoundException {
        UserEntity user = userDao.getUserByUserId(userId);
        if (user == null) {
            throw new UserNotFoundException("USR-001", "User with entered uuid whose question details are to be seen does not exist");
        }
        validateAuthorizationToken(accessToken);
        return questionDAO.getAllQuestionsByUser(user);
    }

    /**
     * Validate Authorization Token
     *
     * @param authorizationToken authorization token
     * @return the answer entity
     * @throws AuthorizationFailedException authorization failed exception
     */
    private UserAuthTokenEntity validateAuthorizationToken(String authorizationToken) throws AuthorizationFailedException {
        UserAuthTokenEntity userAuthTokenEntity = userDao.getUserAuthToken(authorizationToken);
        if (userAuthTokenEntity == null)
            throw new AuthorizationFailedException(GenericErrorCode.ATHR_001.getCode(), GenericErrorCode.ATHR_001.getDefaultMessage());
        if (null != userAuthTokenEntity.getLogoutAt() && userAuthTokenEntity.getLogoutAt().compareTo(ZonedDateTime.now()) < 0)
            throw new AuthorizationFailedException(GenericErrorCode.ATHR_002.getCode(), "User is signed out.Sign in first to delete an answer");
        return userAuthTokenEntity;
    }

}

