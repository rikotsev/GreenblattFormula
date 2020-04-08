package com.rikotsev.fin.grform.bus.tickers;

import com.rikotsev.fin.grform.bus.bean.Company;
import com.rikotsev.fin.grform.bus.dao.BeanDAO;
import com.rikotsev.fin.grform.bus.dao.DatabaseFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    @Override
    public void manage() throws Exception {

        final Stack<Integer> startingPositionsPool = getStartingPositionsPool();
        final String filePath = RESOURCE_FILE_NAME;
        final ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        final List<Future<Void>> futures = new ArrayList<>();
        final CSVFile file = new CSVFile(filePath);

        while(!startingPositionsPool.empty()) {

            final BeanDAO<Company> dao = DatabaseFacade.getInstance().dao(Company.class, null);

            final Future<Void> future = (Future<Void>) executor.submit(() -> {

                int startingPosition = startingPositionsPool.pop();

                try {

                    final Set<String> lines = file.lines(startingPosition, SECTION_SIZE);

                    logger.info("Thread to work on {} lines, starting from {}", lines.size(), startingPosition);


                } catch (IOException e) {
                    logger.error("Thread failed to collect lines from file!");
                    logger.error(e.getMessage());
                }

                return ;
            });

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

}
