package com.visiansystems.dao;

import com.visiansystems.model.CentralBank;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.visiansystems.dao.repository.CentralBankRepository;

/**
 * Simple demo to list all items from the CentralBank table.
 * The table should be created and populated previously.
 * It uses the main hibernate config, which is hbm2ddl.auto = validate.
 */
public class CentralBankDaoDemo {

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("main-context.xml");
        CentralBankRepository repo = ctx.getBean(CentralBankRepository.class);

        System.out.println("-----------------------------------");
        System.out.println("Central Bank Dao Demo");
        System.out.println("-----------------------------------");

        System.out.println("\n--> findAll()\n");
        System.out.println(" Id | Cod | Name");
        System.out.println("-----------------------------------");
        for (CentralBank bank : repo.findAll()) {
            System.out.println(bank);
        }

        System.out.println("\n--> findOne()\n");
        System.out.println(" Id | Cod | Name");
        System.out.println("-----------------------------------");
        CentralBank bank = repo.findOne(1L);
        System.out.println(bank);
    }
}
