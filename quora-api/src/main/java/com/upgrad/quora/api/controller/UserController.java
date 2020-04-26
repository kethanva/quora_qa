package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.SigninResponse;
import com.upgrad.quora.api.model.SignoutResponse;
import com.upgrad.quora.api.model.SignupUserRequest;
import com.upgrad.quora.api.model.SignupUserResponse;
import com.upgrad.quora.service.business.UserControllerBusinessService;
import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthenticationFailedException;
import com.upgrad.quora.service.exception.SignOutRestrictedException;
import com.upgrad.quora.service.exception.SignUpRestrictedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

/**
 * User controller
 */
@RestController
@RequestMapping("/")
public class UserController {


    @Autowired
    private UserControllerBusinessService userControllerBusinessService;

    /**
     * User signup response entity
     *
     * @param signupUserRequest signup user request
     * @return the response entity
     * @throws SignUpRestrictedException sign up restricted exception
     */
    @RequestMapping(
            method = RequestMethod.POST,
            path = "/user/signup",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SignupUserResponse> userSignup(SignupUserRequest signupUserRequest)
            throws SignUpRestrictedException {

        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(signupUserRequest.getFirstName());
        userEntity.setLastName(signupUserRequest.getLastName());
        userEntity.setUserName(signupUserRequest.getUserName());
        userEntity.setEmail(signupUserRequest.getEmailAddress());
        userEntity.setPassword(signupUserRequest.getPassword());
        userEntity.setCountry(signupUserRequest.getCountry());
        userEntity.setAboutMe(signupUserRequest.getAboutMe());
        userEntity.setDob(signupUserRequest.getDob());
        userEntity.setRole("nonadmin");
        userEntity.setContactNumber(signupUserRequest.getContactNumber());

        UserEntity createdUserEntity = userControllerBusinessService.signup(userEntity);
        SignupUserResponse userResponse =
                new SignupUserResponse()
                        .id(createdUserEntity.getUuid())
                        .status("USER SUCCESSFULLY REGISTERED");
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    /**
     * User sign in response entity
     *
     * @param authorization authorization
     * @return the response entity
     * @throws AuthenticationFailedException authentication failed exception
     */
    @RequestMapping(
            method = RequestMethod.POST,
            path = "/user/signin",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SigninResponse> userSignIn(
            @RequestHeader("authorization") final String authorization)
            throws AuthenticationFailedException {

        byte[] decode = Base64.getDecoder().decode(authorization.split("Basic ")[1]);
        String decodedText = new String(decode);
        String[] decodedArray = decodedText.split(":");
        UserAuthTokenEntity userAuthTokenEntity = userControllerBusinessService.signin(decodedArray[0], decodedArray[1]);

        HttpHeaders headers = new HttpHeaders();
        headers.add("access-token", userAuthTokenEntity.getAccessToken());

        SigninResponse signinResponse = new SigninResponse();
        signinResponse.setId(userAuthTokenEntity.getUserEntity().getUuid());
        signinResponse.setMessage("SIGNED IN SUCCESSFULLY");

        return new ResponseEntity<>(signinResponse, headers, HttpStatus.OK);
    }

    /**
     * User signout response entity
     *
     * @param accessToken access token
     * @return the response entity
     * @throws SignOutRestrictedException sign out restricted exception
     */
    @RequestMapping(
            method = RequestMethod.POST,
            path = "/user/signout",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SignoutResponse> userSignout(
            @RequestHeader("authorization") final String accessToken) throws SignOutRestrictedException {
        UserEntity userEntity = userControllerBusinessService.signout(accessToken);
        SignoutResponse signoutResponse =
                new SignoutResponse().id(userEntity.getUuid()).message("SIGNED OUT SUCCESSFULLY");
        return new ResponseEntity<>(signoutResponse, HttpStatus.OK);
    }

}

