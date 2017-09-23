package com.visiansystems.dao;

import com.visiansystems.dao.repository.MonetaryUnitRepository;
import com.visiansystems.model.MonetaryUnit;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Simple test to display all the contents from the MonetaryUnit table.
 * The table should be created and populated previously.
 * It uses the main hibernate config, which is hbm2ddl.auto = validate.
 */
public class MonetaryUnitDaoDemo {
    private static final String PRINT_HEADER = " Id | Cod | Syb | Name";
    private static final String PRINT_SEPARATOR = "-------------------------";

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("main-context.xml");
        MonetaryUnitRepository repo = ctx.getBean(MonetaryUnitRepository.class);

        System.out.println("-----------------------------------");
        System.out.println("Monetary Unit Dao Demo");
        System.out.println("-----------------------------------");

        System.out.println("\n--> count() :");
        System.out.println(repo.count());

        System.out.println("\n--> findAll() :");
        printReferences((List<MonetaryUnit>)repo.findAll());

        System.out.println("\n--> findOne( id=23 ) :");
        printReference(repo.findOne(23L));

        System.out.println("\n--> findByCurrencyCode( code=USD ) :");
        printReferences(repo.findByCode("USD"));
    }

    private static void printReference(MonetaryUnit unit) {
        System.out.println(PRINT_HEADER);
        System.out.println(PRINT_SEPARATOR);
        System.out.println(unit);
    }

    private static void printReferences(List<MonetaryUnit> unities) {
        System.out.println(PRINT_HEADER);
        System.out.println(PRINT_SEPARATOR);
        for (MonetaryUnit unit : unities) {
            System.out.println(unit);
        }
    }
}
