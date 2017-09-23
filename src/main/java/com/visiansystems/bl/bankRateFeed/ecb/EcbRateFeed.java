package com.visiansystems.bl.bankRateFeed.ecb;

import com.visiansystems.bl.bankRateFeed.BankDto;
import com.visiansystems.exception.BankRateFeedException;
import com.visiansystems.model.CentralBank;
import com.visiansystems.model.MonetaryUnit;
import com.visiansystems.util.logger.CallLogging;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.visiansystems.exception.BankDtoException;
import com.visiansystems.bl.bankRateFeed.BankRateFeed;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

/**
 * TODO: Organize exceptions.
 */
public class EcbRateFeed implements BankRateFeed {
    private CentralBank centralBank;

    @Autowired
    @Qualifier("ecbDto")
    private BankDto bankDto;

    //    @Autowired
    //    private CentralBankDao centralBankDao;

    //    @Autowired
    //    private MonetaryDataDao monetaryDataDao;

    //    @Autowired
    //    private MonetaryUnitDao monetaryUnitDao;

    //    @Autowired
    //    private BankFeedReferenceDao bankFeedReferenceDao;

    @Autowired
    private Logger logger;

    @Resource
    private List<MonetaryUnit> ecbCurrencies;

    //    @PostConstruct
    //    public void init() {
    //        centralBank = centralBankDao.getBankById(bankDto.getCentralBankId());
    //    }

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
        return ecbCurrencies;
    }

    @Override public boolean isAvailable(String currencyCode) {
        if (centralBank != null && centralBank.getDefaultCurrencyCode().equals(currencyCode)) {
            return true;
        }
        if (ecbCurrencies != null) {
            for (MonetaryUnit unit : ecbCurrencies) {
                if (unit.getCode().equals(currencyCode)) {
                    return true;
                }
            }
        }
        return false;
    }

    //    @Override public void downloadRate() throws BankRateFeedException {
    //        for (MonetaryUnit unit : ecbCurrencies) {
    //            downloadRate(unit.getCode());
    //        }
    //    }
    //
    //    @Override public void downloadRate(String[] codes) throws BankRateFeedException {
    //        for (String code : codes) {
    //            downloadRate(code);
    //        }
    //    }

    @CallLogging(CallLogging.Level.INFO)
    public void update(String code) throws BankRateFeedException {
        LocalDate referenceDate = getReferenceDate(code);

        logger.info("Updating " + code + " ...");

        //        try {
        //            if (!MonetaryUtils.isRateFeedUpToDate(referenceDate, centralBank.getId())) {

        // Get the series data from the DTO
        //                MonetarySeriesData seriesData = bankDto.getRatesOnDateRange(
        //                        new String[] { code }, referenceDate, Calendar.getInstance().getTime());

        //                if (seriesData == null) {
        //                    throw new BankRateFeedException();
        //                }
        //                Save local data do the db
        //                monetaryDataDao.addMonetarySeriesData(seriesData);
        //
        //                Update referenceLocalDate for the requested currency
        //                BankRateFeedReference reference =
        //                        bankFeedReferenceDao.getReferenceByCode(centralBank.getId(), code);
        //                reference.setReferenceDate(Calendar.getInstance());
        //                bankFeedReferenceDao.updateReference(reference);
        //            }
        //        }
        //        catch (BankDtoException e) {
        //        }
    }

    @Override public LocalDate getReferenceDate(String code) {
        //        BankRateFeedReference reference =
        //                bankFeedReferenceDao.getReferenceByCode(centralBank.getId(), code);

        //        if (reference != null) {
        //            return reference.getBankRateFeedReferenceDate().getTime();
        //        }
        return null;
    }

    @Override public double convert(double amount, String fromCurrencyCode, String toCurrencyCode)
            throws BankRateFeedException, IllegalArgumentException {
        // ...

        return 0;
    }

    @Override
    public double convert(double amount, String fromCurrencyCode, String toCurrencyCode,
                          LocalDate date)
            throws BankRateFeedException, IllegalArgumentException {
        // ...

        return 0;
    }

    @Override public void persist() throws BankRateFeedException {

    }

    @Override public void update() throws BankDtoException {
    }

    @Override public void update(String[] currenciesCode) throws BankDtoException {

    }

    @Override
    public void init() {
    }
}
