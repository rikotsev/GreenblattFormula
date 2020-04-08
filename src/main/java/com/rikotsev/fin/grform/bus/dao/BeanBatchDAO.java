package com.rikotsev.fin.grform.bus.dao;

import com.rikotsev.fin.grform.bus.bean.Bean;

import java.util.Collection;
import java.util.List;

/**
 * A DAO for working with multiple records in batch <br>
 *     Database Connection commit based on settings
 *     //TODO add settings on how many records to commit
 *
 * @param <T>
 */
public interface BeanBatchDAO<T extends Bean> extends AutoCloseable {

    /**
     * The bean is treated as a filter
     * @param bean
     * @return
     */
    List<T> select(final T bean);

    /**
     * The records will be updated with individual update statements and afterwards committed
     * @param beans
     */
    void update(Collection<T> beans);

    /**
     * The records will be inserted with individual statements and afterwards committed
     * @param beans
     */
    void insert(Collection<T> beans);

}
