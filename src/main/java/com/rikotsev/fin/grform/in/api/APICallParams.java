package com.rikotsev.fin.grform.in.api;

import java.util.Optional;

/**
 * Composition of multiple parameters that can be passed to execute successfully an API Call
 */
public final class APICallParams {

    private String url;
    private String accessToken;
    private String ticker;

    private APICallParams() {}

    /**
     * Get an instance of a builder for the class
     * @param url
     * @return
     */
    public static Builder builder(final String url) {
        return new Builder(url);
    }

    /**
     * The builder for the class. We rarely are going to add all the parameters.
     */
    public static class Builder {

        private String url;

        private String accessToken;

        /**
         * What is an API call without an url. We start from this
         * @param url
         */
        public Builder(final String url) {
            this.url = url;
        }

        public Builder accessToken(final String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        public APICallParams build() {
            final APICallParams params = new APICallParams();
            params.url = this.url;
            params.accessToken = this.accessToken;

            return params;
        }

    }

    public Optional<String> getUrl() {
        return Optional.ofNullable(url);
    }

    public Optional<String> getAccessToken() {
        return Optional.ofNullable(accessToken);
    }

    public Optional<String> getTicker() {
        return Optional.ofNullable(ticker);
    }

}
