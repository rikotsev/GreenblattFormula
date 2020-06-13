package com.rikotsev.fin.grform.in.api;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Executes a <code>GET</code> request to an API
 */
class APICallGet implements APICall {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private APICallParams params;
    private byte[] response;
    final private CloseableHttpClient httpClient;

    public APICallGet(final CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }


    @Override
    public void setParams(APICallParams params) {
        this.params = params;
    }

    @Override
    public void execute() throws APIIncorrectParamsException, APIConnectionException {

        if(!params.getUrl().isPresent() ||
            !params.getAccessToken().isPresent()) {

            throw new APIIncorrectParamsException(params,"You have to have both an access token and url to execute a request");

        }

        final HttpGet httpGet = new HttpGet(params.getUrl().get());

        try(final CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            final InputStream contentStream = httpResponse.getEntity().getContent();) {

            response = contentStream.readAllBytes();
        }
        catch(final ClientProtocolException clientProtocolException) {
            final String message = "Encountered an exception while trying to match protocols";
            logger.error(message, clientProtocolException);

            throw new APIConnectionException(message, clientProtocolException, httpClient, httpGet);
        }
        catch(final IOException ioException) {

            final String message = "Encountered an exception while trying to establish a connection";
            logger.error(message, ioException);

            throw new APIConnectionException(message, ioException, httpClient, httpGet);
        }
        finally {
            //Who does a single thread API hit anymore? Always release so that the thread pool manager can assign the thread to another request
            httpGet.releaseConnection();
        }

    }

    @Override
    public byte[] getRawResponse() {
        return null;
    }

    @Override
    public String getResponse() {
        return new String(response, StandardCharsets.UTF_8);
    }

}
