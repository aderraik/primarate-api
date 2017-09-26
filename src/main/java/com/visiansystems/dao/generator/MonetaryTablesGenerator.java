package com.visiansystems.dao.generator;

import com.visiansystems.model.CentralBank;
import com.visiansystems.model.MonetaryUnit;
import com.visiansystems.util.LocaleUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.visiansystems.model.MonetaryCountry;

import javax.annotation.Resource;
import java.util.*;

/**
 * Responsible for generating the MonetaryCountry and MonetaryUnit tables.
 */
public class MonetaryTablesGenerator implements ITableGenerator {

    private static HashMap<String, MonetaryUnit> monetaryUnitMap = new HashMap<>();
    @Autowired
    private SessionFactory sessionFactory;
    @Resource
    private Map<CentralBank, List<MonetaryUnit>> centralBankMap;
    @Resource
    private Map<CentralBank, Date> updateDateMap;

    public void createTable() {

        Session session = sessionFactory.openSession();

        for (Locale locale : LocaleUtils.getValidLocales()) {

            MonetaryCountry monetaryCountry = createMonetaryCountry(locale);

            if (monetaryCountry != null) {
                session.beginTransaction();
                session.save(monetaryCountry);
                session.getTransaction().commit();
            }
        }

        session.close();
    }

    private MonetaryCountry createMonetaryCountry(Locale locale) {

        MonetaryCountry monetaryCountry = new MonetaryCountry();

        try {
            Currency currency = Currency.getInstance(locale);

            monetaryCountry.setName(locale.getDisplayCountry());
            monetaryCountry.setCode(locale.getISO3Country());
            monetaryCountry.setMonetaryUnit(createMonetaryUnit(currency));
        }
        catch (NullPointerException | IllegalArgumentException | MissingResourceException ignored) {
            monetaryCountry = null;
        }

        return monetaryCountry;
    }

    private MonetaryUnit createMonetaryUnit(Currency currency) {

        MonetaryUnit monetaryUnit = null;
        String currencyCode = currency.getCurrencyCode();

        if (monetaryUnitMap.containsKey(currencyCode)) {
            monetaryUnit = monetaryUnitMap.get(currencyCode);
        }
        else {
            monetaryUnit = new MonetaryUnit();
            monetaryUnit.setName(currency.getDisplayName());
            monetaryUnit.setCode(currency.getCurrencyCode());
            monetaryUnit.setSymbol(currency.getSymbol());

            monetaryUnitMap.put(currencyCode, monetaryUnit);
        }

        return monetaryUnit;
    }
}
