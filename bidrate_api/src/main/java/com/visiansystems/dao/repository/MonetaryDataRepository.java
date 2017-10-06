package com.visiansystems.dao.repository;

import com.visiansystems.model.MonetaryData;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MonetaryDataRepository extends CrudRepository<MonetaryData, Long> {
    List<MonetaryData> findByCentralBankId(long bankId);
}
