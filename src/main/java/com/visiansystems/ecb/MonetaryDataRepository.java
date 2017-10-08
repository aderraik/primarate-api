package com.visiansystems.ecb;

import com.visiansystems.rates.Rate;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MonetaryDataRepository extends CrudRepository<Rate, Long> {
    List<Rate> findByCentralBankId(long bankId);
}
