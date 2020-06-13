package com.rikotsev.fin.grform.in.api.ds;

class IEXCloud implements GreenblattFormulaCompatibleEndpoints {

    private String base = "https://cloud.iexapis.com/";
    private String token = "pk_5bb930f7c53a4fa9a856ffff314d7ea1 ";

    public String incomeStatementUrl(final String ticker) {
        return String.format("%s/%s/income", base, ticker);
    }

    public APIEndpoints.RequestType incomeStatementRequestType() {
        return APIEndpoints.RequestType.GET;
    }

    public String balanceSheetUrl(final String ticker) {
        return String.format("%s/%s/balance-sheet", base, ticker);
    }

    public APIEndpoints.RequestType balanceSheetRequestType() {
        return APIEndpoints.RequestType.GET;
    }

    public String quoteUrl(final String ticker) {
        return String.format("%s/%s/quote", base, ticker);
    }

    public APIEndpoints.RequestType quoteRequestType() {
        return APIEndpoints.RequestType.GET;
    }

    @Override
    public String getBase() {
        return base;
    }

    @Override
    public String getToken() {
        return token;
    }
}
