package com.upgrad.quora.service.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Generic error code
 */
public enum GenericErrorCode implements ErrorCode {

    /**
     * Error message: <b>An unexpected error occurred. Please contact System Administrator</b><br>
     * <b>Cause:</b> This error could have occurred due to undetermined runtime errors.<br>
     * <b>Action: None</b><br>
     */
    GEN_001("GEN-001", "An unexpected error occurred. Please contact System Administrator"),
    /**
     * Athr 001
     */
    ATHR_001("ATHR-001", "User has not signed in"),
    /**
     * Athr 002
     */
    ATHR_002("ATHR-002", "User is signed out.Sign in first to get user details"),
    /**
     * Athr 003
     */
    ATHR_003("ATHR-003", "Unauthorized Access, Entered user is not an admin"),
    /**
     * Usr 001
     */
    USR_001("USR-001", "User with entered uuid does not exist"),
    /**
     * Ques 001
     */
    QUES_001("QUES-001", "Entered question uuid does not exist"),
    /**
     * Ans 001
     */
    ANS_001("ANS-001", "Entered answer uuid does not exist");


    private static final Map<String, GenericErrorCode> LOOKUP = new HashMap<String, GenericErrorCode>();

    static {
        for (final GenericErrorCode enumeration : GenericErrorCode.values()) {
            LOOKUP.put(enumeration.getCode(), enumeration);
        }
    }

    private final String code;

    private final String defaultMessage;

    private GenericErrorCode(final String code, final String defaultMessage) {
        this.code = code;
        this.defaultMessage = defaultMessage;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDefaultMessage() {
        return defaultMessage;
    }

}
