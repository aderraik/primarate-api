package com.visiansystems.bl.bankRateFeed.bcb;

import com.visiansystems.model.MonetaryUnit;
import com.visiansystems.util.MonetaryUtils;
import com.visiansystems.util.logger.CallLogging;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import com.visiansystems.bl.bankRateFeed.BankDto;
import com.visiansystems.exception.BankDtoException;
import com.visiansystems.model.MonetaryData;
import com.visiansystems.model.MonetarySeriesData;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents Brazil's Central Bank DTO. It's responsible to aggregates all the data
 * necessary by clients without the need to make several network requests, just one.
 */
public class BcbDto implements BankDto {
    private int centralBankId;
    private MonetarySeriesData seriesData;
    private BcbDateFormat dateFormat;
    private Map<String, LocalDate> bcbDtoReferenceDateMap;

    @Autowired
    private Logger logger;

    @Autowired
    private BcbRpcParser bcbRpcParser;

    @Override
    @PostConstruct
    public void init() {
        seriesData = new MonetarySeriesData(centralBankId);
        dateFormat = new BcbDateFormat();

        bcbDtoReferenceDateMap = new HashMap<>();
        loadDtoReferenceDate();
    }

    @Override
    @CallLogging(CallLogging.Level.INFO)
    public void downloadRate() throws BankDtoException {
        for (MonetaryUnit unit : MonetaryUtils.getBcbCurrencies()) {
            downloadRate(unit.getCode());
        }
    }

    @Override
    @CallLogging(CallLogging.Level.INFO)
    public void downloadRate(String[] currenciesCode) throws BankDtoException {
        for (String currencyCode : currenciesCode) {
            downloadRate(currencyCode);
        }
    }

    @Override
    @CallLogging(CallLogging.Level.INFO)
    public void downloadRate(String currencyCode) throws BankDtoException {

        if (bcbDtoReferenceDateMap == null || bcbDtoReferenceDateMap.isEmpty()) {
            throw new BankDtoException();
        }

        LocalDate startDate = bcbDtoReferenceDateMap.get(currencyCode);
        LocalDate endDate = MonetaryUtils.getLastValidMonetaryDate(centralBankId);

        if (!startDate.isEqual(endDate)) {
            logger.info("Downloading currency " + currencyCode + " data for from BCB ...");

            seriesData.addMonetarySeriesData(
                    getRatesOnDateRange(new String[] { currencyCode }, startDate, endDate));
            bcbDtoReferenceDateMap.remove(currencyCode);
            bcbDtoReferenceDateMap.put(currencyCode, endDate);
        }
        else {
            logger.info("Currency " + currencyCode + " already up-to-date.");
        }
    }

    @Override
    public void clear() {
        seriesData.clear();
    }

    @Override
    public MonetaryData getLastRate(String currencyCode) throws BankDtoException {
        LocalDate date = MonetaryUtils.getLastValidMonetaryDate(centralBankId);
        MonetaryData monetaryData = seriesData.getMonetaryDataOnDate(date, currencyCode);

        if (monetaryData == null) {
            monetaryData = bcbRpcParser.requestLastRate(currencyCode);
            monetaryData.setCentralBankId(centralBankId);
            seriesData.addMonetaryData(monetaryData);
        }
        return monetaryData;
    }

    @Override
    public MonetaryData getRateOnDate(String currencyCode, LocalDate date)
            throws BankDtoException {

        MonetaryData monetaryData = seriesData.getMonetaryDataOnDate(date, currencyCode);

        if (monetaryData == null) {
            String dateStr = dateFormat.format(date);
            MonetarySeriesData temp = bcbRpcParser.requestRatesOnDateRange(
                    new String[] { currencyCode }, dateStr, dateStr);

            monetaryData = temp.getMonetaryDataOnDate(date, currencyCode);
            seriesData.addMonetaryData(monetaryData);
        }
        return monetaryData;
    }

    @Override
    public MonetarySeriesData getRatesOnDateRange(String[] currenciesCode, LocalDate startDate,
                                                  LocalDate endDate) throws BankDtoException {

        MonetarySeriesData retSeriesData = seriesData.getMonetarySeriesDataOnDateRange(
                currenciesCode, startDate, endDate);

        if (retSeriesData == null || retSeriesData.getStartDate() != startDate ||
            retSeriesData.getEndDate() != endDate) {

            String startDateStr = dateFormat.format(startDate);
            String endDateStr = dateFormat.format(endDate);
            retSeriesData =
                    bcbRpcParser.requestRatesOnDateRange(currenciesCode, startDateStr, endDateStr);

            retSeriesData.setCentralBankId(centralBankId);

            for (MonetaryData data : retSeriesData.getAllMonetaryData()) {
                seriesData.addMonetaryData(data);
            }
        }

        return retSeriesData;
    }

    @Override
    public int getCentralBankId() {
        return centralBankId;
    }

    @Override
    @Required
    public void setCentralBankId(int centralBankId) {
        this.centralBankId = centralBankId;
    }

    @Override
    public MonetarySeriesData getDownloadedSeriesData() {
        return seriesData;
    }

    @Override
    public void setDownloadedSeriesData(MonetarySeriesData seriesData) {
        this.seriesData = seriesData;
    }

    @Override
    public LocalDate getDtoReferenceDate(String currencyCode) {
        return bcbDtoReferenceDateMap.get(currencyCode);
    }

    @Override
    public void loadDtoReferenceDate() {
        List<MonetaryUnit> bcbCurrencies = MonetaryUtils.getBcbCurrencies();

        if (bcbCurrencies != null) {
            for (MonetaryUnit unit : bcbCurrencies) {
                bcbDtoReferenceDateMap.put(unit.getCode(),
                                           MonetaryUtils.getBankRateFeedReferenceDate(centralBankId,
                                                                                      unit.getCode()));
            }
        }
    }
}
