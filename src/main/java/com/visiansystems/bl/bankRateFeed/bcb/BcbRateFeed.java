package com.visiansystems.bl.bankRateFeed.bcb;

import com.visiansystems.bl.bankRateFeed.BankDto;
import com.visiansystems.dao.MonetarySeriesDataDao;
import com.visiansystems.dao.repository.BankRateFeedReferenceRepository;
import com.visiansystems.dao.repository.CentralBankRepository;
import com.visiansystems.exception.BankRateFeedException;
import com.visiansystems.model.CentralBank;
import com.visiansystems.model.MonetarySeriesData;
import com.visiansystems.model.MonetaryUnit;
import com.visiansystems.util.MonetaryUtils;
import com.visiansystems.util.logger.CallLogging;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.visiansystems.exception.BankDtoException;
import com.visiansystems.bl.bankRateFeed.BankRateFeed;
import com.visiansystems.dao.repository.MonetaryDataRepository;
import com.visiansystems.model.BankRateFeedReference;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;

/**
 * TODO: Organize exceptions.
 * TODO: Should it be a service??
 */
public class BcbRateFeed implements BankRateFeed {
    private CentralBank centralBank;

    @Autowired
    @Qualifier("bcbDto")
    private BankDto bankDto;

    @Autowired
    private Logger logger;

    @Autowired
    private CentralBankRepository bankRepository;

    @Autowired
    private MonetaryDataRepository dataRepository;

    @Autowired
    private BankRateFeedReferenceRepository referenceRepository;

    @Autowired
    private MonetarySeriesDataDao seriesDataDao;

    @Override
    @PostConstruct
    public void init() {
        centralBank = bankRepository.findOne((long)bankDto.getCentralBankId());
        bankDto.init();
        bankDto.setDownloadedSeriesData(
                seriesDataDao.findByCentralBankId(MonetaryUtils.BCB_CENTRAL_BANK_ID));
    }

    @Override
    public BankDto getBankDto() {
        return bankDto;
    }

    @Override
    public CentralBank getCentralBank() {
        return centralBank;
    }

    @Override
    public List<MonetaryUnit> getAvailableCurrencies() {
        return MonetaryUtils.getBcbCurrencies();
    }

    @Override
    public boolean isAvailable(String currencyCode) {
        if (centralBank != null && centralBank.getDefaultCurrencyCode().equals(currencyCode)) {
            return true;
        }
        List<MonetaryUnit> bcbCurrencies = MonetaryUtils.getBcbCurrencies();
        if (bcbCurrencies != null) {
            for (MonetaryUnit unit : bcbCurrencies) {
                if (unit.getCode().equals(currencyCode)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    @CallLogging(CallLogging.Level.INFO)
    public void persist() throws BankRateFeedException {
        try {
            for (MonetaryUnit unit : MonetaryUtils.getBcbCurrencies()) {
                if (!MonetaryUtils.isRateFeedUpToDate(centralBank.getId(), unit.getCode())) {
                    logger.info("Persisting currency " + unit.getCode() + " for from BCB ...");

                    BankRateFeedReference reference = referenceRepository.
                            findByCurrencyCodeAndCentralBankId(unit.getCode(), centralBank.getId())
                            .get(0);
                    LocalDate startDate = reference.getReferenceDate();
                    LocalDate endDate = bankDto.getDtoReferenceDate(unit.getCode());

                    // Save series data to the database for each currency
                    MonetarySeriesData currencySeriesData = bankDto.getRatesOnDateRange(
                            new String[] { unit.getCode() }, startDate, endDate);
                    seriesDataDao.save(currencySeriesData);

                    // Save reference date for each currency
                    reference.setReferenceDate(endDate);
                    referenceRepository.save(reference);
                }
            }
        }
        catch (Exception e) {
            throw new BankRateFeedException();
        }
    }

    @Override
    public LocalDate getReferenceDate(String currencyCode) {
        return MonetaryUtils.getBankRateFeedReferenceDate(centralBank.getId(), currencyCode);
    }

    @Override
    public double convert(double amount, String fromCurrencyCode, String toCurrencyCode)
            throws BankRateFeedException, IllegalArgumentException {

        try {
            if (!isAvailable(fromCurrencyCode)) {
                throw new IllegalArgumentException(
                        fromCurrencyCode + " currency is not available.");
            }
            if (!isAvailable(toCurrencyCode)) {
                throw new IllegalArgumentException(toCurrencyCode + " currency is not available.");
            }

            amount *= bankDto.getLastRate(fromCurrencyCode).getAmount();
            amount /= bankDto.getLastRate(toCurrencyCode).getAmount();
            return amount;
        }
        catch (BankDtoException e) {
        }
        return MonetaryUtils.INVALID_MONETARY_AMOUNT;
    }

    @Override
    public double convert(double amount, String fromCurrencyCode, String
            toCurrencyCode, LocalDate date)
            throws BankRateFeedException, IllegalArgumentException {

        try {
            if (!isAvailable(fromCurrencyCode)) {
                throw new IllegalArgumentException(
                        fromCurrencyCode + " currency is not available.");
            }
            if (!isAvailable(toCurrencyCode)) {
                throw new IllegalArgumentException(toCurrencyCode + " currency is not available.");
            }

            amount *= bankDto.getRateOnDate(fromCurrencyCode, date).getAmount();
            amount /= bankDto.getRateOnDate(toCurrencyCode, date).getAmount();
            return amount;
        }
        catch (BankDtoException e) {
        }
        return MonetaryUtils.INVALID_MONETARY_AMOUNT;
    }

    @Override
    @CallLogging(CallLogging.Level.INFO)
    public void update(String[] currenciesCode) throws BankDtoException {
        bankDto.downloadRate(currenciesCode);
    }

    @Override
    @CallLogging(CallLogging.Level.INFO)
    public void update() throws BankDtoException {
        bankDto.downloadRate();
    }
}
