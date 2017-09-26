package com.visiansystems.dao.repository;

import org.springframework.data.repository.CrudRepository;
import com.visiansystems.model.BankRateFeedReference;

import java.util.List;

public interface BankRateFeedReferenceRepository
        extends CrudRepository<BankRateFeedReference, Long> {

    List<BankRateFeedReference> findByCurrencyCode(String code);

    List<BankRateFeedReference> findByCurrencyCodeAndCentralBankId(String code, long bankId);
}
