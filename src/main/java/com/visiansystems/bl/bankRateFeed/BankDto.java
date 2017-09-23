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

    public void init();

    /**
     * Download and save locally all the latest data from the Remote Bank Service.
     */
    public void downloadRate() throws BankDtoException;

    public void downloadRate(String currencyCode) throws BankDtoException;

    public void downloadRate(String[] currenciesCode) throws BankDtoException;

    /**
     * Clear all rates saved within the object.
     */
    public void clear();

    /**
     * Returns the last available rate aggregated in the DTO. To get the latest value from the Bank,
     * it is required to call downloadRate() first. It will return null if downloadRate wasn't called at least
     * once.
     */
    public MonetaryData getLastRate(String currencyCode)
            throws BankDtoException;

    /**
     * Returns, if available, the rate given a currency code and a date.
     */
    public MonetaryData getRateOnDate(String currencyCode, LocalDate date)
            throws BankDtoException;

    /**
     * Returns values of one or more series on a date range. The output result is on the
     * MonetarySeriesData object format. The returned list might be in a different order than the
     * one passed on the input.
     */
    public MonetarySeriesData getRatesOnDateRange(
            String currencyCodes[], LocalDate startDate, LocalDate endDate) throws BankDtoException;

    public int getCentralBankId();

    /**
     * Getter and Setter of the central bank id.
     */
    public void setCentralBankId(int centralBankId);

    public MonetarySeriesData getDownloadedSeriesData();

    /**
     * Getter and setter of the downloaded series data.
     */
    public void setDownloadedSeriesData(MonetarySeriesData seriesData);

    /**
     *
     */
    public LocalDate getDtoReferenceDate(String currencyCode);

    void loadDtoReferenceDate();
}
