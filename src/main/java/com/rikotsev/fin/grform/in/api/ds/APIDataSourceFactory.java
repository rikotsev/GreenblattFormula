package com.rikotsev.fin.grform.in.api.ds;

import com.rikotsev.fin.grform.in.api.APIDataSource;
import org.springframework.stereotype.Service;

@Service
public class APIDataSourceFactory {

    public APIEndpoints getApiEndpoints(final APIDataSource source) {

        switch(source) {
            case IEXCloud:
                return new IEXCloud();
            default:
                throw new UnsupportedOperationException("No implementation available for " + source.toString());
        }

    }

    public GreenblattFormulaCompatibleEndpoints getGreenblattFormulaCompatibleEndpoints(final APIDataSource source) {
        switch(source) {
            case IEXCloud:
                return new IEXCloud();
            default:
                throw new UnsupportedOperationException("No implementation available for " + source.toString());
        }
    }

}
