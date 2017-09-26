package com.visiansystems.bl.bankRateFeed;

import com.visiansystems.exception.BankRateFeedException;
import com.visiansystems.model.CentralBank;
import com.visiansystems.model.MonetaryUnit;
import com.visiansystems.exception.BankDtoException;

import java.time.LocalDate;
import java.util.List;

/**
 * BankRateFeed is the main interface that controls exchange rate feeds.
 * <p/>
 * TODO: ecbRateFeed and bcbRateFeed looks very similar. Transform this class into an abstract one?
 */
public interface BankRateFeed {

    public void init();

    /**
     * Gets the bank data transfer object.
     */
    public BankDto getBankDto();

    /**
     * Gets the central bank of this rate feed.
     */
    public CentralBank getCentralBank();

    /**
     * Returns currencies for which exchange rates are available.
     */
    public List<MonetaryUnit> getAvailableCurrencies();

    /**
     * Check whether the exchange rate for a given currency is available.
     */
    public boolean isAvailable(String currencyCode);

    /**
     * Gets the last date which the rates were updated.
     */
    public LocalDate getReferenceDate(String code);

    /**
     * Convert methods. Converts a double precision floating point value from one currency
     * to another.
     * <p/>
     * Example: convert(29.95, "USD", "EUR") - converts $29.95 US Dollars to Euro.
     */
    public double convert(double amount, String fromCurrencyCode, String toCurrencyCode)
            throws BankRateFeedException, IllegalArgumentException;

    public double convert(double amount, String fromCurrencyCode, String toCurrencyCode,
                          LocalDate date)
            throws BankRateFeedException, IllegalArgumentException;

    /**
     *
     */
    public void update() throws BankDtoException;

    public void update(String[] currenciesCode) throws BankDtoException;

    /**
     *
     */
    public void persist() throws BankRateFeedException;
}
