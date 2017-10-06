package com.visiansystems.simpleobject;

import java.util.Collection;

/**
 * The SimpleObjectService interface defines all public business behaviors for operations on the
 * 'SimpleObject' entity model.
 * <p/>
 * This interface should be injected into SimpleObjectService clients, not the implementation bean.
 */
public interface SimpleService {

    /**
     * Find all 'SimpleObject' entities.
     *
     * @return A Collection of 'SimpleObject'.
     */
    Collection<SimpleObject> findAll();

    /**
     * Find a single 'SimpleObject' entity by primary key identifier.
     *
     * @param id A BigInteger primary key identifier.
     * @return A Greeting or <code>null</code> if none found.
     */
    SimpleObject findOne(Long id);

    /**
     * Persists a Greeting entity in the data store.
     *
     * @param greeting A Greeting object to be persisted.
     * @return A persisted Greeting object or <code>null</code> if a problem
     * occurred.
     */
    SimpleObject create(SimpleObject greeting);

    /**
     * Updates a previously persisted Greeting entity in the data store.
     *
     * @param greeting A Greeting object to be updated.
     * @return An updated Greeting object or <code>null</code> if a problem
     * occurred.
     */
    SimpleObject update(SimpleObject greeting);

    /**
     * Removes a previously persisted Greeting entity from the data store.
     *
     * @param id A BigInteger primary key identifier.
     */
    void delete(Long id);
}
