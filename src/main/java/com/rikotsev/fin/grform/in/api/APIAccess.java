package com.rikotsev.fin.grform.in.api;

/**
 * Provides access to an API via HTTP requests. <br>
 *     Allows thread
 */
public interface APIAccess extends AutoCloseable {

    APICall getCall();
    APICall postCall();

    interface APIAccessConfig {

        /**
         * Number of maximum connections
         * @return
         */
        int maxConn();

        /**
         * Number of maximum connections for route
         * @return
         */
        int maxConnPerRoute();

    }

}
