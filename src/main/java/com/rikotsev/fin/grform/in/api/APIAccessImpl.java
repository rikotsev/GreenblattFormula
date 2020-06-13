package com.rikotsev.fin.grform.in.api;

import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

class APIAccessImpl implements APIAccess {

    final CloseableHttpClient httpClient;

    public APIAccessImpl(final APIAccessConfig config) {

        final HttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();

        httpClient = HttpClientBuilder.create().setConnectionManager(connManager)
                .setMaxConnTotal(config.maxConn())
                .setMaxConnPerRoute(config.maxConnPerRoute())
                .build();
    }

    public APICall getCall() {
        return new APICallGet(httpClient);
    }

    public APICall postCall() {
        return new APICallPost(httpClient);
    }

    @Override
    public void close() throws Exception {
        //HttpClients close also closes the ConnectionManager
        httpClient.close();
    }
}
