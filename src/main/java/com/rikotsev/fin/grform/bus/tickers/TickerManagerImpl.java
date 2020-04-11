package com.rikotsev.fin.grform.bus.tickers;

import com.rikotsev.fin.grform.bus.bean.StockExchange;
import com.rikotsev.fin.grform.bus.dao.BeanDAO;
import com.rikotsev.fin.grform.bus.dao.DatabaseFacade;
import com.rikotsev.fin.grform.bus.io.CSVFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.*;

/**
 * Provides business logic around loading
 * company tickers into the application's database
 *
 * @Author rikotsev
 */
@Service
final class TickerManagerImpl implements TickerManager {

    //TODO make a configuration option
    private static final int SECTION_SIZE = 100;

    //TODO make a configuration option
    private static final int THREAD_POOL_SIZE = 5;

    //TODO make a configuration option
    private static final String RESOURCE_FILE_NAME = "external/dev/NYSE.txt";

    private Logger logger = LoggerFactory.getLogger(TickerManagerImpl.class);

    @Autowired
    DataSource dataSource;

    @Override
    public void manage() throws Exception {

        final Stack<Integer> startingPositionsPool = getStartingPositionsPool();
        final String filePath = RESOURCE_FILE_NAME;
        final ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        final List<Future<Void>> futures = new ArrayList<>();
        final StockExchange defaultStockExchange = getDefaultStockExchange();


        while(!startingPositionsPool.empty()) {

            int startingPosition = startingPositionsPool.pop();

            final TickerWorker worker = new TickerWorker(
                    dataSource.getConnection(),
                    startingPosition,
                    new CSVFile(filePath),
                    defaultStockExchange
            );

            final Future<Void> future = executor.submit(worker);

            futures.add(future);
        }

        new Thread() {
            public void run() {

                try {

                    while (futures.stream().filter(fut -> !fut.isDone()).findAny().isPresent()) {
                        sleep(1000);
                    }

                    logger.info("Threads finished work!");
                }
                catch(final InterruptedException e) {
                    logger.error("Could not notify on finished!");
                    logger.error("Exception: ", e);
                }

            }
        }.start();

    }

    /**
     *
     * @return the pool of starting lines
     */
    private Stack<Integer> getStartingPositionsPool() throws URISyntaxException, IOException {

        //final Path filePath = Paths.get(getClass().getClassLoader().getResource("external/dev/NYSE.txt").toURI());
        //logger.info(filePath.toString());
        final CSVFile file = new CSVFile(RESOURCE_FILE_NAME);

        return file.startingLinesPool(SECTION_SIZE);

    }

    /**
     * Implementation as of 11.04.2020 - will be removed later
     * @return the default stock exchange
     */
    private StockExchange getDefaultStockExchange() throws Exception {

        try(final BeanDAO<StockExchange> dao = DatabaseFacade.getInstance().dao(StockExchange.class, dataSource.getConnection());) {

            final StockExchange stockExchangeFilter = new StockExchange();
            stockExchangeFilter.setCode("NYSE");

            final Optional<StockExchange> result = dao.select(stockExchangeFilter);

            if(result.isPresent()) {
                return result.get();
            }
            else {
                throw new UnsupportedOperationException("There is no default instance of stock exchange!");
            }
        }
        catch(final SQLException e) {
            logger.error("Failed to get default Stock Exchange ", e);

            throw new RuntimeException(e);
        }
    }

}
