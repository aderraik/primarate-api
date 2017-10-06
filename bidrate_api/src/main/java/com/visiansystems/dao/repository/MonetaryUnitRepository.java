package com.visiansystems.dao.repository;

import org.springframework.data.repository.CrudRepository;
import com.visiansystems.model.MonetaryUnit;

import java.util.List;

public interface MonetaryUnitRepository extends CrudRepository<MonetaryUnit, Long> {
    List<MonetaryUnit> findByCode(String code);
}
