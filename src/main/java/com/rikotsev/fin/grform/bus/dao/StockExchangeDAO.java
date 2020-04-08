package com.rikotsev.fin.grform.bus.dao;

import com.rikotsev.fin.grform.bus.bean.StockExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

class StockExchangeDAO implements BeanDAO<StockExchange> {

    private static Logger logger = LoggerFactory.getLogger(CompanyDAO.class);

    private Connection connection;

    public StockExchangeDAO(final Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<StockExchange> select(StockExchange bean) {

        if (bean.getCode() == null) {
            throw new IllegalArgumentException("You can search for a single Stock Exchange by code!");
        }

        final String sqlQuery = "SELECT stock_exchange_id, name, code FROM stock_exchange WHERE code = ?";

        try (final PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {

            final ResultSet rs = stmt.executeQuery();

            if(rs.first()) {

                final StockExchange stockExchange = new StockExchange();
                stockExchange.setId(rs.getInt(1));
                stockExchange.setName(rs.getString(2));
                stockExchange.setCode(rs.getString(3));

                return Optional.of(stockExchange);

            }
            else {
                return Optional.empty();
            }

        }
        catch(SQLException e) {
            logger.error("Failed to select stock exchange with code = " + bean.getCode(), e);
            throw new RuntimeException(e);
        }

    }

    @Override
    public void update(StockExchange bean) {
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    @Override
    public void insert(StockExchange bean) {
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
