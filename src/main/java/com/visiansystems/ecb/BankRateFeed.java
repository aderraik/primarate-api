package com.visiansystems.ecb;

import com.visiansystems.monetaryunit.MonetaryUnit;

import java.time.LocalDate;
import java.util.List;

/**
 * BankRateFeed is the main interface that controls exchange rate feeds.
 * <p/>
 * TODO: ecbRateFeed and bcbRateFeed looks very similar. Transform this class into an abstract one?
 */
public interface BankRateFeed {

    void init();

    /**
     * Gets the bank data transfer object.
     */
    BankDto getBankDto();

    /**
     * Gets the central bank of this rate feed.
     */
    CentralBank getCentralBank();

    /**
     * Returns currencies for which exchange rates are available.
     */
    List<MonetaryUnit> getAvailableCurrencies();

    /**
     * Check whether the exchange rate for a given currency is available.
     */
    boolean isAvailable(String currencyCode);

    /**
     * Gets the last date which the rates were updated.
     */
    LocalDate getReferenceDate(String code);

    /**
     * Convert methods. Converts a double precision floating point value from one currency
     * to another.
     * <p/>
     * Example: convert(29.95, "USD", "EUR") - converts $29.95 US Dollars to Euro.
     */
    double convert(double amount, String fromCurrencyCode, String toCurrencyCode)
            throws BankRateFeedException, IllegalArgumentException;

    double convert(double amount, String fromCurrencyCode, String toCurrencyCode, LocalDate date)
            throws BankRateFeedException, IllegalArgumentException;

    /**
     *
     */
    void update() throws BankDtoException;

    void update(String[] currenciesCode) throws BankDtoException;

    /**
     *
     */
    void persist() throws BankRateFeedException;
}
