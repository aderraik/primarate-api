package com.visiansystems.dao.generator;

import com.visiansystems.model.CentralBank;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.List;

/**
 * Responsible for generating the CentralBank table.
 */
public class CentralBankTableGenerator implements ITableGenerator {

    @Autowired
    private SessionFactory sessionFactory;

    @Resource
    private List<CentralBank> centralBankList;

    public void createTable() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        for (CentralBank bank : centralBankList) {
            session.save(bank);
        }

        session.getTransaction().commit();
        session.close();
    }
}
