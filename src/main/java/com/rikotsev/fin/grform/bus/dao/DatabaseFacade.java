package com.rikotsev.fin.grform.bus.dao;

import com.rikotsev.fin.grform.bus.bean.Bean;
import com.rikotsev.fin.grform.bus.bean.Company;
import com.rikotsev.fin.grform.bus.bean.StockExchange;

import javax.xml.crypto.Data;
import java.sql.Connection;

public class DatabaseFacade {

    private static DatabaseFacade instance;
    private static Object lock = new Object();

    public static DatabaseFacade getInstance() {

        synchronized (lock) {
            if(instance == null) {
                instance = new DatabaseFacade();
            }
        }

        return instance;
    }

    private DatabaseFacade() {}

    public <T extends BeanDAO, K> T dao(final Class<K> beanClass, final Connection connection) {
        if(beanClass.equals(Company.class)) {
            return (T) new CompanyDAO(connection);
        }
        else if(beanClass.equals(StockExchange.class)) {
            return (T) new StockExchangeDAO(connection);
        }
        else {
            throw new UnsupportedOperationException("No implementation of dao for " + beanClass.toString());
        }
    }

}
