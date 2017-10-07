package com.visiansystems.ecb;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represents a date format to a central bank data.
 */
public interface BankDateFormat {

    long getBankId();

    boolean isValid(String date);

    LocalDate parse(String date);

    String format(LocalDate date);

    LocalTime getUpdateTime();
}
