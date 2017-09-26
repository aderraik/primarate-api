package com.visiansystems.dao;

import com.visiansystems.dao.repository.MonetaryCountryRepository;
import com.visiansystems.model.MonetaryCountry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Simple test to display all the contents from the MonetaryCountry table.
 * The table should be created and populated previously.
 * It uses the main hibernate config, which is hbm2ddl.auto = validate.
 */
public class MonetaryCountryDaoDemo {

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("main-context.xml");
        MonetaryCountryRepository repo = ctx.getBean(MonetaryCountryRepository.class);

        System.out.println("-----------------------------------");
        System.out.println("Monetary Country Dao Demo");
        System.out.println("-----------------------------------");

        System.out.println("\n--> findAll()\n");
        System.out.println(" Id | Cod | Name");
        System.out.println("-----------------------------------");
        for (MonetaryCountry country : repo.findAll()) {
            System.out.println(country);
        }

        System.out.println("\n--> findOne()\n");
        System.out.println(" Id | Cod | Name");
        System.out.println("-----------------------------------");
        MonetaryCountry country = repo.findOne(1L);
        System.out.println(country);
    }
}
