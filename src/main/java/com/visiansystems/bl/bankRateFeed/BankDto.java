package com.visiansystems.bl.bankRateFeed;

import com.visiansystems.exception.BankDtoException;
import com.visiansystems.model.MonetaryData;
import com.visiansystems.model.MonetarySeriesData;

import java.time.LocalDate;

/**
 * This interface represents the Data transfer object (DTO) of Bank remote APIs. Due of the high cost
 * of each remote call related to the round-trip time between the client and the server, this DTO is
 * responsible to aggregates the data that would have been transferred by the several calls.
 */
public interface BankDto {

    void init();

    /**
     * Download and save locally all the latest data from the Remote Bank Service.
     */
    void downloadRate() throws BankDtoException;

    void downloadRate(String currencyCode) throws BankDtoException;

    void downloadRate(String[] currenciesCode) throws BankDtoException;

    /**
     * Clear all rates saved within the object.
     */
    void clear();

    /**
     * Returns the last available rate aggregated in the DTO. To get the latest value from the Bank,
     * it is required to call downloadRate() first. It will return null if downloadRate wasn't called at least
     * once.
     */
    MonetaryData getLastRate(String currencyCode) throws BankDtoException;

    /**
     * Returns, if available, the rate given a currency code and a date.
     */
    MonetaryData getRateOnDate(String currencyCode, LocalDate date) throws BankDtoException;

    /**
     * Returns values of one or more series on a date range. The output result is on the
     * MonetarySeriesData object format. The returned list might be in a different order than the
     * one passed on the input.
     */
    MonetarySeriesData getRatesOnDateRange(
            String currencyCodes[], LocalDate startDate, LocalDate endDate) throws BankDtoException;

    int getCentralBankId();

    /**
     * Getter and Setter of the central bank id.
     */
    void setCentralBankId(int centralBankId);

    MonetarySeriesData getDownloadedSeriesData();

    /**
     * Getter and setter of the downloaded series data.
     */
    void setDownloadedSeriesData(MonetarySeriesData seriesData);

    /**
     *
     */
    LocalDate getDtoReferenceDate(String currencyCode);

    void loadDtoReferenceDate();
}
