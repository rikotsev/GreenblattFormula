package com.rikotsev.fin.grform.in.api;

import org.apache.http.impl.client.CloseableHttpClient;

class APICallPost implements APICall {

    public APICallPost(final CloseableHttpClient httpClient) {

    }

    @Override
    public void setParams(APICallParams params) {

    }

    @Override
    public void execute() {
        throw new UnsupportedOperationException("POST is not yet implemented!");
    }

    @Override
    public byte[] getRawResponse() {
        return new byte[0];
    }

    @Override
    public String getResponse() {
        return null;
    }
}
