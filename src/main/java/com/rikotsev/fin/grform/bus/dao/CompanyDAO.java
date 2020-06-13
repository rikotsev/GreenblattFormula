package com.rikotsev.fin.grform.bus.dao;

import com.rikotsev.fin.grform.bus.bean.Company;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * This class should always be instantiated. <br>
 *     Methods here are related to selecting, inserting, updating a single record
 */
class CompanyDAO implements BeanDAO<Company>, AutoCloseable {

    private static Logger logger = LoggerFactory.getLogger(CompanyDAO.class);

    private Connection connection;

    /**
     *
     * @param connection - fresh connection from the data source. It will be closed correctly
     */
    public CompanyDAO(final Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Company> select(Company bean) {

        if(bean.getId() != 0) {
            return select(bean.getId());
        }
        else if(bean.getTicker() != null && bean.getStockExchange()!= null && bean.getStockExchange().getId() != 0) {
            return select(bean.getTicker(), bean.getStockExchange().getId());
        }
        else {
            throw new IllegalArgumentException("You have to pass either id or ticker to select a single company!");
        }
    }

    @Override
    public void update(Company bean) {
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    @Override
    public void insert(Company bean) {
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }

    private Optional<Company> select(final long companyId) {
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    private Optional<Company> select(final String ticker, final int stockExchangeId) {
        final String sqlQuery = "SELECT company_id, ticker, description, stock_exchange_id FROM company WHERE ticker = ? AND stock_exchange_id = ?";

        try(final PreparedStatement stmt = connection.prepareStatement(sqlQuery);) {

            stmt.setString(1, ticker);
            stmt.setInt(2, stockExchangeId);

            final ResultSet rs = stmt.executeQuery();
            if (rs.next()) {

                final Company company = new Company();
                company.setId(rs.getLong(1));
                company.setTicker(rs.getString(2));
                company.setDescription(rs.getString(3));
                company.setStockExchangeId(rs.getInt(4)); //Let's load them lazy. We can have an adapter filling out these ids if needed

                rs.close();

                return Optional.of(company);
            }
            else {
                return Optional.empty();
            }

        }
        catch(final SQLException e){
            logger.error("Failed to find company with ticker = " + ticker, e);
            throw new RuntimeException(e);
        }

    }
}
