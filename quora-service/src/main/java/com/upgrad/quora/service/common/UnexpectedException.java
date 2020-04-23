package com.upgrad.quora.service.common;


import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.MessageFormat;

/**
 * Unexpected exception
 */
public class UnexpectedException extends RuntimeException {

    private static final long serialVersionUID = 2737472949025937415L;

    private final ErrorCode errorCode;

    private Throwable cause;

    private final Object[] parameters;

    /**
     * Unexpected exception
     *
     * @param errorCode  error code
     * @param parameters parameters
     */
    public UnexpectedException(final ErrorCode errorCode, final Object... parameters) {
        super();
        this.errorCode = errorCode;
        this.parameters = parameters;
    }

    /**
     * Unexpected exception
     *
     * @param errorCode  error code
     * @param cause      cause
     * @param parameters parameters
     */
    public UnexpectedException(final ErrorCode errorCode, final Throwable cause, final Object... parameters) {
        super();
        this.errorCode = errorCode;
        this.cause = cause;
        this.parameters = parameters;
    }

    /**
     * Gets error code *
     *
     * @return the error code
     */
    public ErrorCode getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return MessageFormat.format(errorCode.getDefaultMessage(), this.parameters);
    }

    @Override
    public Throwable getCause() {
        return cause;
    }

    @Override
    public final void printStackTrace(final PrintStream stream) {
        super.printStackTrace(stream);
    }

    @Override
    public final void printStackTrace(final PrintWriter writer) {
        super.printStackTrace(writer);
    }

}