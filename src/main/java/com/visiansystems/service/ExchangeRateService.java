package com.visiansystems.service;

import com.visiansystems.dao.repository.MonetaryCountryRepository;
import com.visiansystems.model.CentralBank;
import com.visiansystems.model.MonetaryUnit;
import com.visiansystems.util.MonetaryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.visiansystems.dao.repository.CentralBankRepository;
import com.visiansystems.model.MonetaryCountry;

import java.util.ArrayList;
import java.util.List;

/**
 * REST API samples: https://openexchangerates.org/documentation
 * <p/>
 * -- convert (bank, amount, currencyCode1, currencyCode2);
 * -- getAvailableBanks ();
 * -- getAvailableCurrencies (bank);
 * -- getLatestRates (bank, currencyList);
 * -- getTimeSeries (bank, startDate, endDate, currency);
 */
@Service
public class ExchangeRateService {

    @Autowired
    MonetaryCountryRepository monetaryCountryRepository;

    @Autowired
    CentralBankRepository centralBankRepository;

    public List<MonetaryCountry> getAllCountries() {
        return (List<MonetaryCountry>)monetaryCountryRepository.findAll();
    }

    public List<CentralBank> getAvailableBanks() {
        return (List<CentralBank>)centralBankRepository.findAll();
    }

    public List<MonetaryUnit> getAvailableCurrencies(long bankId) {
        if (bankId == MonetaryUtils.BCB_CENTRAL_BANK_ID) {
            return MonetaryUtils.getBcbCurrencies();
        }
        else if (bankId == MonetaryUtils.ECB_CENTRAL_BANK_ID) {
            return MonetaryUtils.getEcbCurrencies();
        }
        return new ArrayList<>();
    }
}
