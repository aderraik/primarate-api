package com.visiansystems.bl.bankRateFeed.bcb;

import com.visiansystems.bl.bankRateFeed.BankDto;
import com.visiansystems.model.MonetaryData;
import com.visiansystems.model.MonetarySeriesData;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDate;

public class BcbDtoDemo {

    public static void main(String[] args) {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("main-context.xml");
        Logger logger = (Logger)ctx.getBean("logger");
        BcbDateFormat dateFormat = new BcbDateFormat();

        final java.io.PrintStream out = System.out;
        int n = args.length;

        out.format("<< BCB Data Transfer Object Demo >>\n");

        try {
            if (n == 0) {
                out.format("\nERROR: Missing arguments. Possible input parameters:\n\n");
                out.print("  [1]: getLastRate()                                   \n");
                out.print("       | In  | currency_code                           \n");
                out.print("       | Out | in Rate format                  \n\n");

                out.print("  [2]: getRateOnDate()                                 \n");
                out.print("       | In  | currencyCode,  date (dd/mm/YYYY)        \n");
                out.print("       | Out | in Rate format                  \n\n");

                out.print("  [3]: getRatesOnDateRange                            \n");
                out.print("       | In  | currencyCode,  initial_date,  final_date\n");
                out.print("       | Out | in MonetarySeriesData format            \n\n");
            }
            else {
                BankDto dto = (BankDto)ctx.getBean("bcbDto");

                if (n == 1) {
                    logger.info("Input: " + args[0]);
                    MonetaryData data = dto.getLastRate(args[0]);
                    logger.info("Received a response: \n\n" + data.toString());
                }
                else if (n == 2) {
                    logger.info("Input: " + args[0] + ", " + args[1]);
                    LocalDate date = dateFormat.parse(args[1]);
                    MonetaryData data = dto.getRateOnDate(args[0], date);
                    logger.info("Received a response: \n\n" + data.toString());
                }
                else if (n == 3) {
                    logger.info("Input: " + args[0] + ", " + args[1]);
                    LocalDate startDate = dateFormat.parse(args[1]);
                    LocalDate endDate = dateFormat.parse(args[2]);
                    MonetarySeriesData seriesData = dto.getRatesOnDateRange(
                            new String[] { args[0] },
                            startDate,
                            endDate);
                    logger.info("Received a response: \n\n" + seriesData.toString());
                }
                else {
                    logger.error("Invalid input!");
                }
            }
        }
        catch (Exception e) {
            logger.error("Returned an exception: " + e);
            e.printStackTrace();
        }
    }
}
