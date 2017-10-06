package com.visiansystems.dao.generator;

import com.visiansystems.model.CentralBank;
import com.visiansystems.model.MonetaryUnit;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.visiansystems.model.BankRateFeedReference;
import com.visiansystems.util.DateConvertUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Responsible for generating the BankRateFeedReference table.
 */
public class BankFeedReferenceTableGenerator implements ITableGenerator {

    @Autowired
    private SessionFactory sessionFactory;

    @Resource
    private Map<CentralBank, List<MonetaryUnit>> centralBankMap;

    @Resource
    private Map<CentralBank, Date> updateDateMap;

    @Override
    public void createTable() {
        Session session = sessionFactory.openSession();

        for (Map.Entry<CentralBank, List<MonetaryUnit>> entry : centralBankMap.entrySet()) {

            CentralBank bank = entry.getKey();
            List<MonetaryUnit> currencies = entry.getValue();

            Date date = updateDateMap.get(bank);
            LocalDate referenceDate = DateConvertUtils.asLocalDate(date);

            for (MonetaryUnit unit : currencies) {
                BankRateFeedReference reference = new BankRateFeedReference(
                        bank.getId(), unit.getCode(), referenceDate);

                session.beginTransaction();
                session.save(reference);
                session.getTransaction().commit();
            }
        }

        session.close();
    }
}
