package com.visiansystems.bl.bankRateFeed;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represents a date format to a central bank data.
 */
public interface BankDateFormat {

    public long getBankId();

    public boolean isValid(String date);

    public LocalDate parse(String date);

    public String format(LocalDate date);

    public LocalTime getUpdateTime();
}
