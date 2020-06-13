package com.rikotsev.fin.grform.controllers;

import com.rikotsev.fin.grform.bus.bean.StockExchange;
import com.rikotsev.fin.grform.bus.comm.Protocol;
import com.rikotsev.fin.grform.bus.tickers.TickerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class FormulaModuleController {

    private Logger logger = LoggerFactory.getLogger(FormulaModuleController.class);

    private TickerManager tickerManager;

    private Protocol protocol;

    @Autowired
    public void setTickerManager(TickerManager tickerManager) {
        this.tickerManager = tickerManager;
    }

    @Autowired
    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    @GetMapping("/")
    public String index() {
        return "Hello World!";
    }

    @GetMapping("/synchronizeTickers")
    public ResponseEntity<Protocol.ActionStatus> synchronizeTickers() {

        try {
            tickerManager.manage();

            final Protocol.ActionStatus res = protocol.actionStatusBuilder(Protocol.ActionStatusType.STARTED)
                    .message("Started Ticker Synchronization!")
                    .statusCode(0)
                    .build();

            return new ResponseEntity<Protocol.ActionStatus>(res, HttpStatus.MULTI_STATUS.OK);

        }
        catch(final Exception e) {
            logger.error("Failed to synchronized tickers!");
            logger.error(e.getMessage());
            logger.error("Exception:", e);

            final Protocol.ActionStatus res = protocol.actionStatusBuilder(Protocol.ActionStatusType.ERROR)
                    .message("Failed to start ticker synchronization!")
                    .statusCode(-1)
                    .build();

            return new ResponseEntity<Protocol.ActionStatus>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping(value = "/stockExchange/create", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Protocol.ActionStatus> createStockExchange(@RequestBody StockExchange stockExchange) {
        try {

            //TODO create a new stock exchange

            final Protocol.ActionStatus res = protocol.actionStatusBuilder(Protocol.ActionStatusType.COMPLETED)
                    .message("Successfully created a stock exchange")
                    .statusCode(0)
                    .build();
            //TODO add entityId

            return new ResponseEntity<Protocol.ActionStatus>(res, HttpStatus.OK);

        }
        catch(final Exception e) {
            logger.error("Failed to create a new stock exchange!");
            logger.error(e.getMessage());
            logger.error("Exception:", e);

            final Protocol.ActionStatus res = protocol.actionStatusBuilder(Protocol.ActionStatusType.ERROR)
                    .message("Failed to create a new stock exchange!")
                    .statusCode(-1)
                    .build();

            return new ResponseEntity<Protocol.ActionStatus>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
