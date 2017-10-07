package com.visiansystems.ecb;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MonetaryCountryRepository extends CrudRepository<MonetaryCountry, Long> {
    List<MonetaryCountry> findByName(String name);

    List<MonetaryCountry> findByCode(String code);
}
