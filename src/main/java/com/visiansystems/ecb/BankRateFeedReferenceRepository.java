package com.visiansystems.ecb;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BankRateFeedReferenceRepository
        extends CrudRepository<BankRateFeedReference, Long> {

    List<BankRateFeedReference> findByCurrencyCode(String code);

    List<BankRateFeedReference> findByCurrencyCodeAndCentralBankId(String code, long bankId);
}
