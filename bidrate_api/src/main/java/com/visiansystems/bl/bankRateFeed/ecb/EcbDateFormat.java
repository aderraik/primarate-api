package com.visiansystems.bl.bankRateFeed.ecb;

import com.visiansystems.util.MonetaryUtils;
import com.visiansystems.bl.bankRateFeed.BankDateFormat;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Date format: yyyy-MM-dd (No time)
 * Locale:
 */
public class EcbDateFormat implements BankDateFormat {

    @Override public long getBankId() {
        return MonetaryUtils.ECB_CENTRAL_BANK_ID;
    }

    @Override public boolean isValid(String date) {
        return false;
    }

    @Override public LocalDate parse(String date) {
        return null;
    }

    @Override public String format(LocalDate date) {
        return null;
    }

    @Override public LocalTime getUpdateTime() {
        return null;
    }
}
