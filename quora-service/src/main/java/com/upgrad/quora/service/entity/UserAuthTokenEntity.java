package com.upgrad.quora.service.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;

/**
 * User auth token entity
 */
@Entity
@Table(name = "user_auth")
@NamedQueries({
        @NamedQuery(
                name = "userAuthByAccessToken",
                query = "select u from UserAuthTokenEntity u where u.accessToken=:accessToken"),
        @NamedQuery(name = "userAuthTokenByAccessToken", query = "select ut from UserAuthTokenEntity ut where ut.accessToken =:accessToken")

})
public class UserAuthTokenEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "uuid")
    @NotNull
    @Size(max = 200)
    private String uuid;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Column(name = "access_token")
    @NotNull
    @Size(max = 500)
    private String accessToken;

    @Column(name = "expires_at")
    @NotNull
    private ZonedDateTime expiresAt;

    @Column(name = "login_at")
    @NotNull
    private ZonedDateTime loginAt;

    @Column(name = "logout_at")
    private ZonedDateTime logoutAt;

    /**
     * Gets user *
     *
     * @return the user
     */
    public UserEntity getUser() {
        return userEntity;
    }


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
     * Gets access token *
     *
     * @return the access token
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * Sets access token *
     *
     * @param accessToken access token
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * Gets expires at *
     *
     * @return the expires at
     */
    public ZonedDateTime getExpiresAt() {
        return expiresAt;
    }

    /**
     * Sets expires at *
     *
     * @param expiresAt expires at
     */
    public void setExpiresAt(ZonedDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    /**
     * Gets login at *
     *
     * @return the login at
     */
    public ZonedDateTime getLoginAt() {
        return loginAt;
    }

    /**
     * Sets login at *
     *
     * @param loginAt login at
     */
    public void setLoginAt(ZonedDateTime loginAt) {
        this.loginAt = loginAt;
    }

    /**
     * Gets logout at *
     *
     * @return the logout at
     */
    public ZonedDateTime getLogoutAt() {
        return logoutAt;
    }

    /**
     * Sets logout at *
     *
     * @param logoutAt logout at
     */
    public void setLogoutAt(ZonedDateTime logoutAt) {
        this.logoutAt = logoutAt;
    }

}
