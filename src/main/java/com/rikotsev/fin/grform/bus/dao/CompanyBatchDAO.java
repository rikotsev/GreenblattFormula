package com.rikotsev.fin.grform.bus.dao;

import com.rikotsev.fin.grform.bus.bean.Company;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

class CompanyBatchDAO implements BeanBatchDAO<Company> {

    private static Logger logger = LoggerFactory.getLogger(CompanyBatchDAO.class);

    private Connection connection;

    public CompanyBatchDAO(final Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Company> select(Company bean) {
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    @Override
    public void update(Collection<Company> beans) {

        final String sqlQuery = "UPDATE company SET description = ? WHERE company_id = ?";

        try(final PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {


            connection.setAutoCommit(false);

            beans.stream().forEach(bean -> {
                try {
                    stmt.setString(1,bean.getDescription());
                    stmt.setLong(2, bean.getId());
                    stmt.execute();
                }
                catch(final SQLException e) {
                    logger.error("Failed to update company with id " + Long.toString(bean.getId()), e);
                }

            });

            connection.commit();
            connection.setAutoCommit(true);

        }
        catch(final SQLException e) {
            logger.error("Failed to update in batch!", e);
            throw new RuntimeException(e);
        }

    }

    @Override
    public void insert(Collection<Company> beans) {
        final String sqlQuery = "INSERT INTO company(ticker, description, stock_exchange_id) VALUES (?,?,?)";

        try(final PreparedStatement stmt = connection.prepareStatement(sqlQuery);) {

            connection.setAutoCommit(false);

            beans.stream().forEach(bean -> {
                try {
                    stmt.setString(1, bean.getTicker());
                    stmt.setString(2, bean.getDescription());
                    stmt.setInt(3, bean.getStockExchange().getId());
                    stmt.execute();
                }
                catch(SQLException e) {
                    logger.error("Failed to update company with ticker " + bean.getTicker(), e);
                }
            });

            connection.commit();
            connection.setAutoCommit(true);

        }
        catch(final SQLException e) {
            logger.error("Failed to insert in batch!", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
