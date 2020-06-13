package com.rikotsev.fin.grform.in.api;

/**
 * A call to an API
 */
public interface APICall {

    /**
     * The parameters of the API Call
     * @param params
     */
    void setParams(final APICallParams params);

    /**
     * Executes the call
     */
    void execute() throws APIIncorrectParamsException, APIConnectionException;

    /**
     * Retrieves the response
     * @return
     */
    byte[] getRawResponse();

    String getResponse();

}

