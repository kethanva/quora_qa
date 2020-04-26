package com.upgrad.quora.service.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * UserNotFoundException is thrown when user is not found in the database.
 */
public class UserNotFoundException extends Exception {
    private final String code;
    private final String errorMessage;

    /**
     * User not found exception
     *
     * @param code         code
     * @param errorMessage error message
     */
    public UserNotFoundException(final String code, final String errorMessage) {
        this.code = code;
        this.errorMessage = errorMessage;
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }

    @Override
    public void printStackTrace(PrintStream s) {
        super.printStackTrace(s);
    }

    @Override
    public void printStackTrace(PrintWriter s) {
        super.printStackTrace(s);
    }

    /**
     * Gets code *
     *
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Gets error message *
     *
     * @return the error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }

}
