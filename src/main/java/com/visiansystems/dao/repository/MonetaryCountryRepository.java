package com.visiansystems.dao.repository;

import org.springframework.data.repository.CrudRepository;
import com.visiansystems.model.MonetaryCountry;

import java.util.List;

public interface MonetaryCountryRepository extends CrudRepository<MonetaryCountry, Long> {
    List<MonetaryCountry> findByName(String name);

    List<MonetaryCountry> findByCode(String code);
}
