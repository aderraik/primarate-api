package com.visiansystems.bl.bankRateFeed.bcb;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.visiansystems.bl.bankRateFeed.BankRateFeed;
import com.visiansystems.exception.BankRateFeedException;
import com.visiansystems.model.MonetaryUnit;

import java.util.List;

public class BcbRateFeedDemo {

    private static BankRateFeed bankRateFeed;

    public static void main(String[] args) {
        try {
            AbstractApplicationContext context =
                    new ClassPathXmlApplicationContext("classpath:main-context.xml");
            context.registerShutdownHook();

            bankRateFeed = (BankRateFeed)context.getBean("bcbRateFeed");

            printCentralBank();
            printCurrencies();
            printCheckAvailability();
            printUpdate();
            printConvert();
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void printCentralBank() {
        System.out.println("\n======================================");
        System.out.println("-> Central Bank:\n" + bankRateFeed.getCentralBank());

    }

    private static void printCurrencies() {
        System.out.println("\n======================================");
        System.out.println("-> BCB available currencies:");

        List<MonetaryUnit> currencies = bankRateFeed.getAvailableCurrencies();
        for (MonetaryUnit currency : currencies) {
            System.out.println(currency.toString());
        }
    }

    private static void printCheckAvailability() {
        System.out.println("\n======================================");
        System.out.println("-> Check currencies last downloadRate date:");
        isCurrencyAvailable("BRL");
        isCurrencyAvailable("USD");
        isCurrencyAvailable("EUR");
        isCurrencyAvailable("GBP");
        isCurrencyAvailable("ARS");
        isCurrencyAvailable("CHY");
        isCurrencyAvailable("SEK");
        isCurrencyAvailable("NOK");
        isCurrencyAvailable("AUD");
    }

    private static void printUpdate() throws BankRateFeedException {
        System.out.println("\n======================================");
        System.out.println("-> Updating all currencies...");
        //        bankRateFeed.downloadRate();
    }

    private static void printConvert() throws BankRateFeedException {
        System.out.println("\n======================================");
        System.out.println("-> TODO Convert test");

        convert("USD", "EUR");
        convert("USD", "AUD");
        convert("USD", "CAD");
        convert("USD", "NOK");
    }

    private static void convert(String fromCode, String toCode) throws BankRateFeedException {
        System.out.println("Converting 1 " + fromCode + " to " + toCode + ": " +
                           bankRateFeed.convert(1.0, fromCode, toCode));
    }

    private static void isCurrencyAvailable(String code) {
        System.out.println("\nIs " + code + " available?   " + bankRateFeed.isAvailable(code));
        //        System.out.println("Is up-to-date?      " + MonetaryUtils.isRateFeedUpToDate(
        //                bankRateFeed.getBankRateFeedReferenceDate(code), MonetaryUtils.BCB_CENTRAL_BANK_ID));
        System.out.println("Last downloadRate:        " + bankRateFeed.getReferenceDate(code));
    }
}
