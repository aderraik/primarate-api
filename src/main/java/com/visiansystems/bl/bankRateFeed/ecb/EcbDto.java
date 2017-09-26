package com.visiansystems.bl.bankRateFeed.ecb;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import com.visiansystems.bl.bankRateFeed.BankDto;
import com.visiansystems.exception.BankDtoException;
import com.visiansystems.model.MonetaryData;
import com.visiansystems.model.MonetarySeriesData;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

/**
 * This class represents Europe's Central Bank DTO. It's responsible to aggregates all the data
 * necessary by clients without the need to make several network requests, just one.
 */
public class EcbDto implements BankDto {
    private int centralBankId;

    @Autowired
    private Logger logger;

    @Autowired
    private EcbRpcParser ecbRpcParser;

    @Override
    @PostConstruct
    public void init() {
        ecbRpcParser.setCentralBankId(centralBankId);
    }

    @Override
    public void downloadRate() {
        // This will downloadRate the full data on the parser. However it will only
        // downloadRate new data only if there's new available.
        ecbRpcParser.update();
    }

    @Override
    public void clear() {
        ecbRpcParser.clear();
    }

    @Override
    public MonetaryData getLastRate(String currencyCode) throws BankDtoException {
        return null;
        //return getRateOnDate(currencyCode, MonetaryUtils.getLastValidMonetaryDate(centralBankId));
    }

    @Override
    public MonetaryData getRateOnDate(String currencyCode, LocalDate date)
            throws BankDtoException {
        downloadRate();

        return ecbRpcParser.getMonetarySeriesData().
                getMonetaryDataOnDate(date, currencyCode);
    }

    @Override
    public MonetarySeriesData getRatesOnDateRange(
            String[] currencyCodes, LocalDate startDate, LocalDate endDate)
            throws BankDtoException {
        downloadRate();

        MonetarySeriesData inSeries = ecbRpcParser.getMonetarySeriesData();

        MonetarySeriesData retSeries = inSeries.getMonetarySeriesDataOnDateRange(currencyCodes,
                                                                                 startDate,
                                                                                 endDate);

        return retSeries;
    }

    @Override public int getCentralBankId() {
        return centralBankId;
    }

    @Override
    @Required
    public void setCentralBankId(int centralBankId) {
        this.centralBankId = centralBankId;
    }

    @Override
    public MonetarySeriesData getDownloadedSeriesData() {
        return null;
    }

    @Override public void setDownloadedSeriesData(MonetarySeriesData seriesData) {

    }

    @Override public LocalDate getDtoReferenceDate(String currencyCode) {
        return null;
    }

    @Override public void downloadRate(String currencyCode) throws BankDtoException {

    }

    @Override public void downloadRate(String[] currenciesCode) throws BankDtoException {

    }

    @Override public void loadDtoReferenceDate() {

    }
}
