package com.rikotsev.fin.grform.bus.tickers;

import com.rikotsev.fin.grform.bus.bean.Company;
import com.rikotsev.fin.grform.bus.bean.StockExchange;
import com.rikotsev.fin.grform.bus.dao.BeanBatchDAO;
import com.rikotsev.fin.grform.bus.dao.BeanDAO;
import com.rikotsev.fin.grform.bus.dao.DatabaseFacade;
import com.rikotsev.fin.grform.bus.io.CSVFile;

import java.sql.Connection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Callable;

/**
 * Executes the work related to evaluating if a ticker should be inserted, updated or ignored.
 *
 * @Author Radoslav Kotsev
 */
class TickerWorker implements Callable<Void> {

    //TODO make a configuration option
    private static final int SECTION_SIZE = 100;

    /**
     * Each thread will have it's own connection. Majority of database drivers do not implement
     * thread safe database connections. No point in recycling them
     */
    private Connection conn;

    private int start;

    private CSVFile file;

    private StockExchange stockExchange;

    /**
     *  Creates a Ticker Worker
     * @param conn - DB connection
     * @param start - starting line to work from
     * @param file - the file to read from
     */
    public TickerWorker(final Connection conn, final int start, final CSVFile file, final StockExchange stockExchange) {
        this.conn = conn;
        this.start = start;
        this.file = file;
        this.stockExchange = stockExchange;
    }

    @Override
    public Void call() throws Exception {

        try(final BeanDAO<Company> companyDao = DatabaseFacade.getInstance().dao(Company.class, conn);
            final BeanBatchDAO<Company> batchCompanyDao = DatabaseFacade.getInstance().batchDao(Company.class, conn);) {

            final Set<String> lines = file.lines(start, SECTION_SIZE);

            final Set<Company> updates = new HashSet<>();
            final Set<Company> inserts = new HashSet<>();

            for(final String line : lines) {

                final Company company = new Company();
                final String[] values = line.split("\t");
                company.setTicker(values[0]);
                company.setDescription(values[1]);
                //TODO add Stock Exchange Information

                final Optional<Company> dbRecord = companyDao.select(company);

                //There is a record with that ticker, but a different description -> we need to update it
                if(dbRecord.isPresent() && !dbRecord.get().getDescription().equals(company.getDescription())) {
                    dbRecord.get().setDescription(company.getDescription());
                    updates.add(dbRecord.get());
                }
                else if(!dbRecord.isPresent()) {
                    company.setStockExchange(stockExchange);
                    inserts.add(company);
                }
                
            }

            // Let's do updates before inserts, because index regeneration will be faster with smaller amounts of records
            if(updates.size() > 0) {
                batchCompanyDao.update(updates);
            }

            if(inserts.size() > 0) {
                batchCompanyDao.insert(inserts);
            }

        }

        return null;
    }

}
