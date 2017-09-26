package com.visiansystems.dao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.visiansystems.dao.repository.MonetaryDataRepository;
import com.visiansystems.model.MonetaryData;

import java.time.LocalDate;
import java.util.List;

public class MonetaryDataDaoDemo {
    private static final String PRINT_HEADER = " Id | Cod | Syb | Name";
    private static final String PRINT_SEPARATOR = "-------------------------";

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("main-context.xml");
        MonetaryDataRepository repo = ctx.getBean(MonetaryDataRepository.class);

        System.out.println("-----------------------------------");
        System.out.println("Monetary Data Dao Demo");
        System.out.println("-----------------------------------");

        repo.deleteAll();
        repo.save(new MonetaryData(1, 1, LocalDate.now(), 1));
        repo.save(new MonetaryData(2, 2, LocalDate.now(), 2));
        repo.save(new MonetaryData(3, 3, LocalDate.now(), 3));
        repo.save(new MonetaryData(4, 4, LocalDate.now(), 4));

        System.out.println("\n--> count() :");
        System.out.println(repo.count());

        System.out.println("\n--> findAll() :");
        printReferences((List<MonetaryData>)repo.findAll());

        System.out.println("\n--> findOne( id=2 ) :");
        printReference(repo.findOne(2L));

        System.out.println("\n--> findByCurrencyCode( bankId=1 ) :");
        printReferences(repo.findByCentralBankId(1L));
    }

    private static void printReference(MonetaryData unit) {
        System.out.println(PRINT_HEADER);
        System.out.println(PRINT_SEPARATOR);
        System.out.println(unit);
    }

    private static void printReferences(List<MonetaryData> dataList) {
        System.out.println(PRINT_HEADER);
        System.out.println(PRINT_SEPARATOR);
        for (MonetaryData data : dataList) {
            System.out.println(data);
        }
    }
}
