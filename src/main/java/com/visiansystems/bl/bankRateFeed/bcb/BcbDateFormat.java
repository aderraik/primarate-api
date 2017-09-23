package com.visiansystems.bl.bankRateFeed.bcb;

import com.visiansystems.util.MonetaryUtils;
import com.visiansystems.bl.bankRateFeed.BankDateFormat;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Brazilian Central Bank date format class.
 * Date Format: dd/MM/yyyy
 * Locale: pt-BR
 */
public class BcbDateFormat implements BankDateFormat {

    public static final char DATE_FIELDS_SEPARATOR = '/';

    @Override public long getBankId() {
        return MonetaryUtils.BCB_CENTRAL_BANK_ID;
    }

    @Override public boolean isValid(String date) {
        // TODO
        return true;
    }

    @Override public LocalDate parse(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            return LocalDate.parse(date, formatter);
        }
        catch (DateTimeException e) {
            throw e;
        }
    }

    @Override public String format(LocalDate date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            return date.format(formatter);
        }
        catch (DateTimeException e) {
            throw e;
        }
    }

    @Override public LocalTime getUpdateTime() {
        // The rates are updated at 13:00 GMT-3
        // TODO
        return null;
    }

    /**
     * Transforms an incomplete date to a complete one.
     * Examples of incomplete dates:
     * - missing day field;
     * - missing date field separator;
     */
    public synchronized String complete(String data) {
        int len = data.length();
        if (len > 7) {
            return data;
        }
        else {
            char[] buf = new char[len + 2];
            System.arraycopy(data.toCharArray(), 0, buf, 2, len);
            buf[0] = '1';
            buf[1] = DATE_FIELDS_SEPARATOR;
            return new String(buf);
        }
    }
}
