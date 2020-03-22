# 20.1 Technical Specification

## Functionality Update Tickers

Tasks: 
* [ ] Cron Job to download NYSE EoD CSV file with tickers.
* [ ] Read the provided CSV file, determine the number of lines and separate into a set pool of equal number of lines
. `ThreadPool` to read lines, check if the ticker already exists, update it if needed or insert if a new one
    
    * Find the file and determine the number of lines. Build a `stack` from which each thread will `pop` the line numbers
    it has to work on. Set up a `ThreadExecutor` with up to 5 threads to work on these. Have the number of threads as a configurable option.
    Each thread will check what is currently in the database and determine if the description (name) of the company has changed, ticker already exists
    and is exactly the same or the ticker doesn't exist and has to be inserted. 
    
 * [ ] Send an email notification the update is completed
 
    * Build this is a interface to push. Initially it will be implemented by an EmailPush, later will add a front end app