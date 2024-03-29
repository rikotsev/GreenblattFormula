CREATE TABLE STOCK_EXCHANGE (
    STOCK_EXCHANGE_ID SERIAL,
    NAME VARCHAR(255),
    CODE VARCHAR(20),
    PRIMARY KEY (STOCK_EXCHANGE_ID)
    CONSTRAINT se_code_unique UNIQUE (code)
);

CREATE TABLE COMPANY (
    COMPANY_ID BIGSERIAL,
    TICKER VARCHAR(50),
    DESCRIPTION VARCHAR(255),
    STOCK_EXCHANGE_ID INT,
    PRIMARY KEY (COMPANY_ID),
    FOREIGN KEY (STOCK_EXCHANGE_ID) REFERENCES STOCK_EXCHANGE(STOCK_EXCHANGE_ID),
    CONSTRAINT company_ticker_unq UNIQUE (ticker, stock_exchange_id) --Tickers are unique for stock exchange :)
);


CREATE INDEX idx_company_ticker ON company(ticker);

INSERT INTO stock_exchange (name, code) VALUES ('New York Stock Exchange','NYSE');