package com.rikotsev.fin.grform.in.api;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;

/**
 * Wraps around all the possible causes that can prevent establishing a connection
 */
public class APIConnectionException extends Exception {

    final String clientClass;
    final String requestBaseConfig;

    /**
     *
     * @param message
     * @param cause
     */
    public APIConnectionException(final String message, final Throwable cause, final HttpClient httpClient, final HttpRequestBase requestBase) {
        super(message, cause);

        clientClass = httpClient.getClass().toString();
        requestBaseConfig = requestBase.getConfig().toString();

    }

    public String getClientClass() {
        return clientClass;
    }

    public String getRequestBaseConfig() {
        return requestBaseConfig;
    }



}
