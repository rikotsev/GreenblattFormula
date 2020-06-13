# 20.1 Technical Specification

## Functionality Update Tickers

Tasks: 
* [ ] Cron Job to download NYSE EoD CSV file with tickers.
* [X] Read the provided CSV file, determine the number of lines and separate into a set pool of equal number of lines
. `ThreadPool` to read lines, check if the ticker already exists, update it if needed or insert if a new one
    
    * Find the file and determine the number of lines. Build a `stack` from which each thread will `pop` the line numbers
    it has to work on. Set up a `ThreadExecutor` with up to 5 threads to work on these. Have the number of threads as a configurable option.
    Each thread will check what is currently in the database and determine if the description (name) of the company has changed, ticker already exists
    and is exactly the same or the ticker doesn't exist and has to be inserted. 
    
 * [ ] Send an email notification the update is completed
 
    * Build this is a interface to push. Initially it will be implemented by an EmailPush, later will add a front end app
    
## Functionality Acquire Data and Calculate
Tasks:
* [ ] Access https://financialmodelingprep.com/developer/docs/ API and acquire the needed data - multi-threaded API hits 
    * [ ] Access the real time company quote and end of market <br>
    https://financialmodelingprep.com/developer/docs/#Company-Quote -
        * [ ] Retrieve number of shares, share price 
    * [ ] Access the latest quarterly balance sheet <br> 
    https://financialmodelingprep.com/developer/docs/#Company-Financial-Statements
        * [ ] Retrieve Total Non Current Assets, Total Non Current Liabilities, 
        Total Liabilities, Cash & Cash Equivalents, Accounts Receivable, Accounts Payable, Inventory
    * [ ] Access the latest quarterly income statement <br> 
    https://financialmodelingprep.com/developer/docs/#Company-Financial-Statements
        * [ ] Retrieve EBITDA, Depreciation and Amortization
> Use Apache `HttpClient` with a `PoolManager` to start a configurable number of threads. Encapsulate the API requests
> in different implementation classes for `GET` and `POST`. Pass parameters as a separate object the decorates the API request.
> Maybe a `Decorator` pattern or `Builder` for an object with multiple parameters.
* [ ] `ExecutorService` to calculate all values - each thread calculates for a single company
    * [ ] Calculate the market capitalization = number of shares * share price.
    * [ ] Calculate Enterprise Value = Market Capitalization + Total Liabilities - Cash & Cash Equivalents
    * [ ] Calculate EBIT = EBITDA - Depreciation and Amortization
    * [ ] Calculate Earning Yield = EBIT / Enterprise Value
    * [ ] Calculate Net Fixed Assets = Total Non Current Assets - Total Non Current Liabilities - Depreciation and Amortizationa
    * [ ] Calculate Net Working Capital = Accounts Receivable - Accounts Payable - Inventory
> Maybe a `Command` pattern for an underlying object containing all the necessary data. The underlying object has a `Builder` to be populated with the parameters
* [ ] Store retrieved JSON values in corresponding tables as jsonb fields - multi-threaded

    