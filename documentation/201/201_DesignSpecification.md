# 20.1 Design Specification

## Functionality: Update tickers

User enters the `/synchronizeTickers` url. All tickers for the NYSE are already downloaded 
as a file from [DataRepository](http://www.eoddata.com/stocklist/NYSE/A.htm). There is a cron job
that takes care to daily download the file (Maybe daily is an overkill?). The latest downloaded file now starts to load into the database.
When the load is complete the user receives an email that the tickers are loaded. After loading the file is archived and
moved to an archive directory for auditing/troubleshooting purposes. If the load was not successful the user receives an
email the load failed. 

__Previously loaded tickers should not be deleted if the load was unsuccessful__

## Functionality: Calculate Top 30 Companies

User enters the `/calculateFormula` url. Based on the tickers already existing in the database the calculations start.
1. Companies get filtered - companies with market capitalization under $100 million are not included
1. For each company calculate the __Earnings Yield__
    * > Earnings Yield = EBIT / Enterprise Value <br>
      ,where EBIT (Income Statement) and <br> Enterprise Value = 
       Number of Shares * Share Price + Total Liabilities (Balance Sheet) - Cash and Cash Equivalents (Balance Sheet)
1. For each company calculate the __Return on Capital__
    * > Return on Capital = EBIT / Net Fixed Assets + Net Working Capital <br>
      ,where EBIT (Income Statement) and <br>
       Net Fixed Assets = Long Term Assets - (Long Term Liabilities + Depreciation) (All from Balance Sheet) and <br>
       Net Working Capital = Accounts Receivable + Inventory - Accounts Payable (All from Balance Sheet)
1. Based on __Earnings Yield__ sort the companies in ascending order
1. Based on __Return on Capital__ sort the companies in ascending order
1. Sum the order value for each company and the result order again ascending 
1. Send an email with the top 30 companies from the final order
1. Save the results in the database

__All financial statements from which data is pulled are the most recent ones__


                                                                                                                                                                                                                                                                                                                                                                                         