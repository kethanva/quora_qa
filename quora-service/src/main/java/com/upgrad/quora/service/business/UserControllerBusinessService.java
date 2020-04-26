package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.UserAuthTokenDao;
import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthenticationFailedException;
import com.upgrad.quora.service.exception.SignOutRestrictedException;
import com.upgrad.quora.service.exception.SignUpRestrictedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * User controller business service
 */
@Service
public class UserControllerBusinessService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserAuthTokenDao userAuthTokenDao;

    @Autowired
    private PasswordCryptographyProvider cryptographyProvider;

    /**
     * Signup user entity
     *
     * @param userEntity user entity
     * @return the user entity
     * @throws SignUpRestrictedException sign up restricted exception
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public UserEntity signup(UserEntity userEntity) throws SignUpRestrictedException {

        if (userDao.getUserByUserName(userEntity.getUserName()) != null) {
            throw new SignUpRestrictedException("SGR-001", "Try any other Username, this Username has already been taken'.");
        }
        if (userDao.getUserByEmail(userEntity.getEmail()) != null) {
            throw new SignUpRestrictedException("SGR-002", "This user has already been registered, try with any other emailId");
        }

        userEntity.setUuid(UUID.randomUUID().toString());
        String[] encryptedText = cryptographyProvider.encrypt(userEntity.getPassword());
        userEntity.setSalt(encryptedText[0]);
        userEntity.setPassword(encryptedText[1]);
        return userDao.createUser(userEntity);
    }

    /**
     * Signin user auth token entity
     *
     * @param username username
     * @param password password
     * @return the user auth token entity
     * @throws AuthenticationFailedException authentication failed exception
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public UserAuthTokenEntity signin(final String username, final String password) throws AuthenticationFailedException {

        UserEntity userEntity = userDao.getUserByUserName(username);
        if (userEntity == null) {
            throw new AuthenticationFailedException("ATH-001", "This username does not exist");
        }
        final String encryptedPassword = PasswordCryptographyProvider.encrypt(password, userEntity.getSalt());
        if (!encryptedPassword.equals(userEntity.getPassword())) {
            throw new AuthenticationFailedException("ATH-002", "Password failed");
        }

        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(encryptedPassword);
        UserAuthTokenEntity userAuthTokenEntity = new UserAuthTokenEntity();
        userAuthTokenEntity.setUuid(UUID.randomUUID().toString());
        userAuthTokenEntity.setUserEntity(userEntity);
        final ZonedDateTime now = ZonedDateTime.now();
        final ZonedDateTime expiresAt = now.plusHours(8);
        userAuthTokenEntity.setAccessToken(
                jwtTokenProvider.generateToken(userEntity.getUuid(), now, expiresAt));
        userAuthTokenEntity.setLoginAt(now);
        userAuthTokenEntity.setExpiresAt(expiresAt);

        userAuthTokenDao.createAuthToken(userAuthTokenEntity);
        userDao.updateUserEntity(userEntity);

        return userAuthTokenEntity;
    }

    /**
     * Signout user entity
     *
     * @param accessToken access token
     * @return the user entity
     * @throws SignOutRestrictedException sign out restricted exception
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public UserEntity signout(final String accessToken) throws SignOutRestrictedException {
        UserAuthTokenEntity userAuthEntity = userAuthTokenDao.getUserAuthByToken(accessToken);
        if (userAuthEntity == null) {
            throw new SignOutRestrictedException("SGR-001", "User is not Signed in");
        }
        userAuthEntity.setLogoutAt(ZonedDateTime.now());
        userAuthTokenDao.updateUserAuth(userAuthEntity);
        return userAuthEntity.getUserEntity();
    }

}
