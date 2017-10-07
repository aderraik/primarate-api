package com.visiansystems.util;

import com.visiansystems.ecb.*;
import com.visiansystems.monetaryunit.MonetaryUnit;
import com.visiansystems.monetaryunit.MonetaryUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO: It needs to create a unit test.
 * TODO: Move Controller utils to here (?)
 * TODO: Transform bcbCurrencies in a map.
 * TODO: Get bank ids from properties file.
 */
public class MonetaryUtils {
    public static final long INVALID_MONETARY_UNIT_CODE = -1;
    public static final long INVALID_MONETARY_AMOUNT = -1;

    // Bank Ids
    public static final long INVALID_CENTRAL_BANK_ID = -1;
    public static final long UNDEFINED_CENTRAL_BANK_ID = 0;
    public static final long BCB_CENTRAL_BANK_ID = 1;
    public static final long ECB_CENTRAL_BANK_ID = 2;

    // Common Currencies Codes
    public static final String EUROPE_CURRENCY_CODE = "EUR";
    public static final String BRAZIL_CURRENCY_CODE = "BRL";
    public static final String USA_CURRENCY_CODE = "USD";
    public static final String BRITAIN_CURRENCY_CODE = "GBP";
    public static final String CANADA_CURRENCY_CODE = "CAD";
    public static final String AUSTRALIA_CURRENCY_CODE = "AUD";
    public static final String INVALID_CURRENCY_CODE = "XXX";

    // Static @Autowired variables
    private static MonetaryUnitRepository unitRepository;
    private static BankRateFeedReferenceRepository referenceRepository;

    // Static @Resource variables
    private static List<MonetaryUnit> bcbCurrencies;

    private static Map<String, Long> bcbIds;

    /**
     * Returns the database id of a monetary unit, given its currency code.
     * TODO: Verifies which tests uses this to mock out.
     */
    public static long getMonetaryIdFromCode(String currencyCode) {
        try {
//            return unitRepository.findByCode(currencyCode).get(0).getId();
            return 0;
        }
        catch (Exception e) {
            return INVALID_MONETARY_UNIT_CODE;
        }
    }

    /**
     * Returns the currency code given the his Id on the database.
     * TODO: Verifies which tests uses this to mock out.
     */
    public static String getMonetaryCodeFromId(long currencyId) {
        if (unitRepository != null && unitRepository.findOne(currencyId) != null) {
            return unitRepository.findOne(currencyId).getCode();
        }
        return INVALID_CURRENCY_CODE;
    }

    /**
     * Returns the monetary unit id of a given currency on the BCB service.
     */
    public static long getBcbMonetaryIdFromCode(String currencyCode) {
        return bcbIds.get(currencyCode);
    }

    /**
     * Returns the monetary unit code of a given currency on the BCB service.
     */
    public static long getBcbMonetaryCodeFromId(long bcbCurrencyId) {
        for (Map.Entry<String, Long> entry : bcbIds.entrySet()) {
            if (entry.getValue() == bcbCurrencyId) {
                return getMonetaryIdFromCode(entry.getKey());
            }
        }
        return INVALID_MONETARY_UNIT_CODE;
    }

    public static List<MonetaryUnit> getBcbCurrencies() {
        return bcbCurrencies;
    }

    @Autowired
    @Resource
    @Required
    public void setBcbCurrencies(List<MonetaryUnit> bcbCurrencies) {
        MonetaryUtils.bcbCurrencies = new ArrayList<>(bcbCurrencies.size());
        for (MonetaryUnit unit : bcbCurrencies) {
            MonetaryUtils.bcbCurrencies.add(unit);
        }
    }

    public static List<MonetaryUnit> getEcbCurrencies() {
        return null;
    }

    /**
     * TODO: Verifies which tests uses this to mock out.
     */
    public static LocalDate getBankRateFeedReferenceDate(long centralBankId, String currencyCode) {
        List<BankRateFeedReference> list =
                referenceRepository.findByCurrencyCodeAndCentralBankId(currencyCode, centralBankId);

        if (list != null && list.size() > 0) {
            return list.get(0).getReferenceDate();
        }
        return LocalDate.now();
    }

    /**
     * Checks if a given currency code is valid based on the ISO 4217 standard.
     */
    public static boolean isCurrencyCodeValid(String currencyCode) {
        if (getMonetaryIdFromCode(currencyCode) == INVALID_MONETARY_UNIT_CODE) {
            return false;
        }
        return true;
    }

    /**
     * Verifies if a given date is valid published date.
     * TODO: Add holidays?
     */
    public static boolean isMonetaryDateValid(LocalDate date) {
        if (date == null ||
            date.getDayOfWeek() == DayOfWeek.SATURDAY ||
            date.getDayOfWeek() == DayOfWeek.SUNDAY) {
            return false;
        }
        return true;
    }

    /**
     * Returns the last valid day containing a exchange rate.
     * TODO: Place the hour/location on spring config.
     */
    public static LocalDate getLastValidMonetaryDate(long centralBankId) {
        LocalDateTime datetime = LocalDateTime.now();
        LocalDate date;

        if (centralBankId == BCB_CENTRAL_BANK_ID) {
            datetime = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));

            if (datetime.getHour() < 13) {
                datetime = datetime.minusDays(1);
            }
        }
        else if (centralBankId == ECB_CENTRAL_BANK_ID) {
            datetime = LocalDateTime.now(ZoneId.of("Europe/Paris"));

            if (datetime.getHour() < 15) {
                datetime = datetime.minusDays(1);
            }
        }
        date = datetime.toLocalDate();

        for (; !isMonetaryDateValid(date); date = date.minusDays(1)) {
        }

        return date;
    }

    /**
     * Verifies if a given date is the latest containing any Exchange rates data.
     * It assumes that the rates are never published on weekends.
     */
    public static boolean isRateFeedUpToDate(long centralBankId, String currencyCode) {
        LocalDate dbReferenceDay = getBankRateFeedReferenceDate(centralBankId, currencyCode);
        LocalDate lastValidDay = getLastValidMonetaryDate(centralBankId);

        return dbReferenceDay.isEqual(lastValidDay);
    }

    /**
     * Returns the number of weekdays between two dates.
     * TODO: Remove holidays
     */
    public static int getWorkingDaysBetweenTwoDates(LocalDate startDate, LocalDate endDate) {
        int workDays = 0;

        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
            if (isMonetaryDateValid(date)) {
                workDays++;
            }
        }
        return workDays;
    }

    /**
     * TODO: Verifies which tests uses this to mock out.
     */
    @PostConstruct
    public static void init() throws DaoException {

        if (bcbIds == null || bcbIds.size() == 0) {
            bcbIds = new HashMap<>();
            List<MonetaryUnit> temp = new ArrayList<>();

            for (MonetaryUnit unit : bcbCurrencies) {
                bcbIds.put(unit.getCode(), unit.getId());
            }

            for (MonetaryUnit unit : bcbCurrencies) {
//                List<MonetaryUnit> unitList = unitRepository.findByCode(unit.getCode());

//                if (unitList != null && unitList.size() > 0) {
//                    MonetaryUnit currency = unitList.get(0);
//
//                    if (currency != null) {
//                        temp.add(currency);
//                    }
//                }
            }

            if (temp.size() > 0) {
                bcbCurrencies = temp;
            }
        }
    }

    @Autowired
    @Required
    public void setMonetaryUnitDao(MonetaryUnitRepository unitRepository) {
        MonetaryUtils.unitRepository = unitRepository;
    }

    @Autowired
    @Required
    public void setBankFeedReferenceDao(BankRateFeedReferenceRepository referenceRepository) {
        MonetaryUtils.referenceRepository = referenceRepository;
    }
}
