package com.rikotsev.fin.grform.controllers;

import com.rikotsev.fin.grform.bus.tickers.TickerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.Normalizer;

@RestController
public class FormulaModuleController {

    private Logger logger = LoggerFactory.getLogger(FormulaModuleController.class);

    private TickerManager tickerManager;

    @Autowired
    public void setTickerManager(TickerManager tickerManager) {
        this.tickerManager = tickerManager;
    }

    @RequestMapping("/")
    public String index() {
        return "Hello World!";
    }

    @RequestMapping("/synchronizeTickers")
    public String synchronizeTickers() {

        try {
            tickerManager.manage();
            return "Starting Ticker Synchronization";
        }
        catch(final Exception e) {
            logger.error("Failed to synchronized tickers!");
            logger.error(e.getMessage());
            logger.error("Exception:", e);

            return "Failed to start Ticker Synchronization";
        }

    }

}
