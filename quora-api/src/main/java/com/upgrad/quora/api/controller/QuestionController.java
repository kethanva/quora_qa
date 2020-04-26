package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.*;
import com.upgrad.quora.service.business.QuestionBusinessService;
import com.upgrad.quora.service.entity.QuestionEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.InvalidQuestionException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Question controller with /question mapping
 */
@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionBusinessService questionBusinessService;

    /**
     * Create question
     *
     * @param questionRequest question request
     * @param authorization   authorization
     * @return the response entity
     * @throws AuthorizationFailedException authorization failed exception
     */
    @RequestMapping(method = RequestMethod.POST, path = "/create", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<QuestionResponse> create(final QuestionRequest questionRequest, @RequestHeader("authorization") final String authorization) throws AuthorizationFailedException {
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setContent(questionRequest.getContent());
        QuestionEntity createdQuestionEntity = questionBusinessService.create(questionEntity, authorization);
        QuestionResponse questionResponse = new QuestionResponse().id(createdQuestionEntity.getUuid()).status("QUESTION CREATED");
        return new ResponseEntity<>(questionResponse, HttpStatus.CREATED);
    }

    /**
     * Gets all questions
     *
     * @param authorization authorization
     * @return the all questions
     * @throws AuthorizationFailedException authorization failed exception
     */
    @GetMapping(path = "/all")
    public ResponseEntity<List<QuestionDetailsResponse>> getAllQuestions(@RequestHeader("authorization") final String authorization) throws AuthorizationFailedException {
        List<QuestionEntity> questionList = questionBusinessService.getAllQuestions(authorization);
        List<QuestionDetailsResponse> questionResponseList = questionList.parallelStream().map(ques -> {
            QuestionDetailsResponse quesRes = new QuestionDetailsResponse();
            quesRes.setId(ques.getUuid());
            quesRes.setContent(ques.getContent());
            return quesRes;
        }).collect(Collectors.toList());

        return new ResponseEntity<>(questionResponseList, HttpStatus.OK);
    }

    /**
     * Edit question content
     *
     * @param questionId          question id
     * @param questionEditRequest question edit request
     * @param authorization       authorization
     * @return the response entity
     * @throws AuthorizationFailedException authorization failed exception
     * @throws InvalidQuestionException     invalid question exception
     */
    @PutMapping(path = "edit/{questionId}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<QuestionEditResponse> editQuestionContent(@PathVariable("questionId") String questionId, final QuestionEditRequest questionEditRequest, @RequestHeader("authorization") final String authorization) throws AuthorizationFailedException, InvalidQuestionException {
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setUuid(questionId);
        questionEntity.setContent(questionEditRequest.getContent());
        QuestionEntity questionContent = questionBusinessService.editQuestionContent(questionEntity, authorization);

        return new ResponseEntity<>(new QuestionEditResponse().id(questionContent.getUuid()).status("QUESTION EDITED"), HttpStatus.OK);
    }


    /**
     * Delete question
     *
     * @param accessToken access token
     * @param questionId  question id
     * @return the response entity
     * @throws AuthorizationFailedException authorization failed exception
     * @throws InvalidQuestionException     invalid question exception
     */
    @RequestMapping(method = RequestMethod.DELETE, path = "/delete/{questionId}")
    public ResponseEntity<QuestionDeleteResponse> deleteQuestion(
            @RequestHeader("authorization") final String accessToken,
            @PathVariable("questionId") final String questionId)
            throws AuthorizationFailedException, InvalidQuestionException {

        QuestionEntity questionEntity = questionBusinessService.deleteQuestion(accessToken, questionId);
        QuestionDeleteResponse questionDeleteResponse = new QuestionDeleteResponse();
        questionDeleteResponse.setId(questionEntity.getUuid());
        questionDeleteResponse.setStatus("QUESTION DELETED");
        return new ResponseEntity<>(questionDeleteResponse, HttpStatus.OK);
    }

    /**
     * Gets question by user id
     *
     * @param accessToken access token
     * @param userId      user id
     * @return the question by user id
     * @throws AuthorizationFailedException authorization failed exception
     * @throws UserNotFoundException        user not found exception
     */
    @RequestMapping(
            method = RequestMethod.GET,
            path = "/all/{userId}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<QuestionDetailsResponse>> getQuestionByUserId(
            @RequestHeader("authorization") final String accessToken,
            @PathVariable("userId") String userId)
            throws AuthorizationFailedException, UserNotFoundException {

        List<QuestionEntity> questions = questionBusinessService.getAllQuestionsByUser(userId, accessToken);
        List<QuestionDetailsResponse> questionDetailResponses = new ArrayList<>();
        for (QuestionEntity questionEntity : questions) {
            QuestionDetailsResponse questionDetailResponse = new QuestionDetailsResponse();
            questionDetailResponse.setId(questionEntity.getUuid());
            questionDetailResponse.setContent(questionEntity.getContent());
            questionDetailResponses.add(questionDetailResponse);
        }
        return new ResponseEntity<List<QuestionDetailsResponse>>(
                questionDetailResponses, HttpStatus.OK);
    }


}
