package com.visiansystems.monetaryunit;

import java.util.Collection;

public interface MonetaryUnitService {
    Collection<MonetaryUnit> findAll();

    MonetaryUnit findOne(Long id);
}
