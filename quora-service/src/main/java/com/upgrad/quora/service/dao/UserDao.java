package com.upgrad.quora.service.dao;

import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.entity.UserEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 * User dao
 */
@Repository
public class UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Create user user entity
     *
     * @param userEntity user entity
     * @return the user entity
     */
    public UserEntity createUser(UserEntity userEntity) {
        entityManager.persist(userEntity);
        return userEntity;
    }

    /**
     * Create auth token user auth token entity
     *
     * @param userAuthTokenEntity user auth token entity
     * @return the user auth token entity
     */
    public UserAuthTokenEntity createAuthToken(final UserAuthTokenEntity userAuthTokenEntity) {
        entityManager.persist(userAuthTokenEntity);
        return userAuthTokenEntity;
    }

    /**
     * Update user entity *
     *
     * @param updatedUserEntity updated user entity
     */
    public void updateUserEntity(final UserEntity updatedUserEntity) {
        entityManager.merge(updatedUserEntity);
    }


    /**
     * Gets user by user name *
     *
     * @param userName user name
     * @return the user by user name
     */
    public UserEntity getUserByUserName(final String userName) {
        try {
            return entityManager
                    .createNamedQuery("userByUserName", UserEntity.class)
                    .setParameter("userName", userName)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }


    /**
     * Gets user auth token *
     *
     * @param authorizationToken authorization token
     * @return the user auth token
     */
    public UserAuthTokenEntity getUserAuthToken(String authorizationToken) {
        try {
            return entityManager.createNamedQuery("userAuthTokenByAccessToken", UserAuthTokenEntity.class).setParameter("accessToken", authorizationToken).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * Gets user by user id *
     *
     * @param userId user id
     * @return the user by user id
     */
    public UserEntity getUserByUserId(final String userId) {
        try {
            return entityManager.createNamedQuery("getUserByUserId", UserEntity.class).setParameter("userId", userId).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * Delete user by user id string
     *
     * @param userId user id
     * @return the string
     */
    public String deleteUserByUserId(String userId) {
        UserEntity user = getUserByUserId(userId);
        if (null != user) {
            entityManager.remove(user);
            return user.getUuid();
        }
        return null;
    }


    /**
     * Gets user by email *
     *
     * @param email email
     * @return the user by email
     */
    public UserEntity getUserByEmail(final String email) {
        try {
            return entityManager
                    .createNamedQuery("userByEmail", UserEntity.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * Gets user by id *
     *
     * @param userId user id
     * @return the user by id
     */
    public UserEntity getUserById(final String userId) {
        try {
            return entityManager
                    .createNamedQuery("userByUserId", UserEntity.class)
                    .setParameter("userId", userId)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

}
