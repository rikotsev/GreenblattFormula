package com.rikotsev.fin.grform.in.api;


import org.springframework.stereotype.Service;

@Service
final class APIFacadeImpl {

    private static APIFacadeImpl instance;

    public APIAccess access(final APIAccess.APIAccessConfig config) {
        return new APIAccessImpl(config);
    }

}
