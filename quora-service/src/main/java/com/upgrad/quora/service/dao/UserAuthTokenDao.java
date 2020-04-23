package com.upgrad.quora.service.dao;

import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 * User auth token dao
 */
@Repository
public class UserAuthTokenDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Gets user auth by token *
     *
     * @param accessToken access token
     * @return the user auth by token
     */
    public UserAuthTokenEntity getUserAuthByToken(final String accessToken) {
        try {
            return entityManager
                    .createNamedQuery("userAuthByAccessToken", UserAuthTokenEntity.class)
                    .setParameter("accessToken", accessToken)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * Create auth token user auth token entity
     *
     * @param userAuthEntity user auth entity
     * @return the user auth token entity
     */
    public UserAuthTokenEntity createAuthToken(final UserAuthTokenEntity userAuthEntity) {
        entityManager.persist(userAuthEntity);
        return userAuthEntity;
    }

    /**
     * Update user auth *
     *
     * @param updatedUserAuthEntity updated user auth entity
     */
    public void updateUserAuth(final UserAuthTokenEntity updatedUserAuthEntity) {
        entityManager.merge(updatedUserAuthEntity);
    }
}


