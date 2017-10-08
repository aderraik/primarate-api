package com.visiansystems.util;

import com.visiansystems.ecb.*;
import com.visiansystems.monetaryunit.MonetaryUnit;
import com.visiansystems.monetaryunit.MonetaryUnitRepository;
import com.visiansystems.monetaryunit.MonetaryUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

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
@Service
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


    @Autowired
    private MonetaryUnitService monetaryUnitService;


    public long getCurrencyId(String currencyCode) {
        return monetaryUnitService.findByCode(currencyCode).getId();
    }




    /*
    public static String getMonetaryCodeFromId(long currencyId) {
        if (unitRepository != null && unitRepository.findOne(currencyId) != null) {
            return unitRepository.findOne(currencyId).getCode();
        }
        return INVALID_CURRENCY_CODE;
    }

    public static long getBcbMonetaryIdFromCode(String currencyCode) {
        return bcbIds.get(currencyCode);
    }

    public static long getBcbMonetaryCodeFromId(long bcbCurrencyId) {
        for (Map.Entry<String, Long> entry : bcbIds.entrySet()) {
            if (entry.getValue() == bcbCurrencyId) {
                return getCurrencyId(entry.getKey());
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

    public static LocalDate getBankRateFeedReferenceDate(long centralBankId, String currencyCode) {
        List<BankRateFeedReference> list =
                referenceRepository.findByCurrencyCodeAndCentralBankId(currencyCode, centralBankId);

        if (list != null && list.size() > 0) {
            return list.get(0).getReferenceDate();
        }
        return LocalDate.now();
    }

    public static boolean isCurrencyCodeValid(String currencyCode) {
        if (getCurrencyId(currencyCode) == INVALID_MONETARY_UNIT_CODE) {
            return false;
        }
        return true;
    }

    public static boolean isMonetaryDateValid(LocalDate date) {
        if (date == null ||
            date.getDayOfWeek() == DayOfWeek.SATURDAY ||
            date.getDayOfWeek() == DayOfWeek.SUNDAY) {
            return false;
        }
        return true;
    }

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

    public static boolean isRateFeedUpToDate(long centralBankId, String currencyCode) {
        LocalDate dbReferenceDay = getBankRateFeedReferenceDate(centralBankId, currencyCode);
        LocalDate lastValidDay = getLastValidMonetaryDate(centralBankId);

        return dbReferenceDay.isEqual(lastValidDay);
    }

    public static int getWorkingDaysBetweenTwoDates(LocalDate startDate, LocalDate endDate) {
        int workDays = 0;

        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
            if (isMonetaryDateValid(date)) {
                workDays++;
            }
        }
        return workDays;
    }

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
    */
}
