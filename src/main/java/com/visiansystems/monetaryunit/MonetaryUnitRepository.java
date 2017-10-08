package com.visiansystems.monetaryunit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface MonetaryUnitRepository extends JpaRepository<MonetaryUnit, Long> {
    MonetaryUnit findByCode(String code);
}
