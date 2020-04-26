package com.upgrad.quora.service.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;

/**
 * Question entity
 */
@Entity
@Table(name = "question")
@NamedQueries({
        @NamedQuery(name = "getAllQuestions", query = "select q from QuestionEntity q"),
        @NamedQuery(name = "getQuestionByUUID", query = "select q from QuestionEntity q where q.uuid=:questionId"),
        @NamedQuery( name = "getQuestionByUser",
                query = "select q from QuestionEntity q where q.userEntity=:user")

})
public class QuestionEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "uuid")
    @Size(max = 200)
    private String uuid;

    @Column(name = "content")
    @Size(max = 500)
    private String content;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Column(name = "date")
    @NotNull
    private ZonedDateTime date;

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
     * Gets content *
     *
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets content *
     *
     * @param content content
     */
    public void setContent(String content) {
        this.content = content;
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

}