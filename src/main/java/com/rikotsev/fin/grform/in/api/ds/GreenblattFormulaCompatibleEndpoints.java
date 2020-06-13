package com.rikotsev.fin.grform.in.api.ds;

public interface GreenblattFormulaCompatibleEndpoints extends APIEndpoints {

    String incomeStatementUrl(final String ticker);
    APIEndpoints.RequestType incomeStatementRequestType();

    String balanceSheetUrl(final String ticker);
    APIEndpoints.RequestType balanceSheetRequestType();

    String quoteUrl(final String ticker);
    APIEndpoints.RequestType quoteRequestType();

}
