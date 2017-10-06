package com.visiansystems.simpleobject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The SimpleRepository interface is a Spring Data JPA data repository for 'SimpleObject' entities.
 * The SimpleRepository provides all the data access behaviors exposed by <code>JpaRepository</code>
 * and additional custom behaviors may be defined in this interface.
 */
@Repository
public interface SimpleRepository extends JpaRepository<SimpleObject, Long> {
}
