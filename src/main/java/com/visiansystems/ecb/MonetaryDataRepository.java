package com.visiansystems.ecb;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MonetaryDataRepository extends CrudRepository<MonetaryData, Long> {
    List<MonetaryData> findByCentralBankId(long bankId);
}
