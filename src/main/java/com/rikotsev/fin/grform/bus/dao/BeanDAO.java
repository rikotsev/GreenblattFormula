package com.rikotsev.fin.grform.bus.dao;

import com.rikotsev.fin.grform.bus.bean.Bean;

import java.util.Collection;
import java.util.Optional;

/**
 * Simple DAO implementation. <br>
 * Basic methods to insert, update and select a single record
 *
 * @param <T>
 */
public interface BeanDAO<T extends Bean> extends AutoCloseable {

    /**
     * The bean is used as a filter
     * @param bean
     * @return
     */
    Optional<T> select(T bean);


    /**
     * The values will be assigned to the bean record <br>
     *     <b>An identifier is mandatory. Please refer to the PK of the table</b>
     * @param bean
     */
    void update(T bean);

    /**
     * Inserting a new record with the values from the bean
     * @param bean
     */
    void insert(T bean);

}
