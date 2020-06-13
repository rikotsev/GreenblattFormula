package com.rikotsev.fin.grform.in.api.ds;

public interface APIEndpoints {

    enum RequestType {
        GET,
        POST
    }

    String getBase();
    String getToken();

}
