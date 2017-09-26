package com.visiansystems.dao;

import com.visiansystems.dao.repository.BankRateFeedReferenceRepository;
import com.visiansystems.model.BankRateFeedReference;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class BankFeedReferenceDaoDemo {
    private static final String PRINT_HEADER =
            " Id | CentralBankId | CurrencyCode | ReferenceDate ";
    private static final String PRINT_SEPARATOR =
            "---------------------------------------------------";

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("main-context.xml");
        BankRateFeedReferenceRepository repo = ctx.getBean(BankRateFeedReferenceRepository.class);

        System.out.println("-----------------------------------");
        System.out.println("Bank Feed Reference Dao Demo");
        System.out.println("-----------------------------------");

        System.out.println("\n--> count() :");
        System.out.println(repo.count());

        System.out.println("\n--> findAll() :");
        printReferences((List<BankRateFeedReference>)repo.findAll());

        System.out.println("\n--> findOne( id=4 ) :");
        printReference(repo.findOne(4L));

        System.out.println("\n--> findByCurrencyCode( code=USD ) :");
        printReferences(repo.findByCurrencyCode("USD"));

        System.out.println("\n--> findByCurrencyCodeAndCentralBankId( code=USD, bank=1 ) :");
        printReferences(repo.findByCurrencyCodeAndCentralBankId("USD", 1L));
    }

    private static void printReference(BankRateFeedReference reference) {
        System.out.println(PRINT_HEADER);
        System.out.println(PRINT_SEPARATOR);
        System.out.println(reference);
    }

    private static void printReferences(List<BankRateFeedReference> references) {
        System.out.println(PRINT_HEADER);
        System.out.println(PRINT_SEPARATOR);
        for (BankRateFeedReference ref : references) {
            System.out.println(ref);
        }
    }
}
