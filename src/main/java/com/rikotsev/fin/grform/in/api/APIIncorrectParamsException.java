package com.rikotsev.fin.grform.in.api;

/**
 *  To catch state when arguments that are insufficient, badly defined or not applicable to a call are passed
 */
public class APIIncorrectParamsException extends Exception {

    private APICallParams params;

    private String humanReadableError;

    public APIIncorrectParamsException(final APICallParams params, final String humanReadableError) {
        super();
        this.params = params;
        this.humanReadableError = humanReadableError;

    }

    public APICallParams getParams() {
        return params;
    }

    public String getHumanReadableError() {
        return humanReadableError;
    }

}
