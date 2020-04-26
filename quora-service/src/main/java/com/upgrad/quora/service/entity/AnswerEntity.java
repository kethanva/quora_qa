package com.upgrad.quora.service.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * Answer entity
 */
@Entity
@Table(name = "answer")
@NamedQueries({
        @NamedQuery(name = "answersByQuestionUUID", query = "select a from AnswerEntity a where a.questionEntity.uuid=:questionId"),
        @NamedQuery(name = "getAnswerByUUID", query = "select a from AnswerEntity a where a.uuid=:answerId")


})
public class AnswerEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "uuid")
    @Size(max = 200)
    @NotNull
    private String uuid;

    @Column(name = "ans")
    @NotNull
    @Size(max = 500)
    private String answer;

    @Column(name = "date")
    private ZonedDateTime date;

    @ManyToOne
    @NotNull
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne
    @NotNull
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "question_id")
    private QuestionEntity questionEntity;

    /**
     * Gets id *
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets id *
     *
     * @param id id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets uuid *
     *
     * @return the uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Sets uuid *
     *
     * @param uuid uuid
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * Gets answer *
     *
     * @return the answer
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * Sets answer *
     *
     * @param answer answer
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * Gets date *
     *
     * @return the date
     */
    public ZonedDateTime getDate() {
        return date;
    }

    /**
     * Sets date *
     *
     * @param date date
     */
    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    /**
     * Gets user entity *
     *
     * @return the user entity
     */
    public UserEntity getUserEntity() {
        return userEntity;
    }

    /**
     * Sets user entity *
     *
     * @param userEntity user entity
     */
    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    /**
     * Gets question entity *
     *
     * @return the question entity
     */
    public QuestionEntity getQuestionEntity() {
        return questionEntity;
    }

    /**
     * Sets question entity *
     *
     * @param questionEntity question entity
     */
    public void setQuestionEntity(QuestionEntity questionEntity) {
        this.questionEntity = questionEntity;
    }


}
