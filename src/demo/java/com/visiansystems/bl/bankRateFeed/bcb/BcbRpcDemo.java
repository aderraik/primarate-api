package com.visiansystems.bl.bankRateFeed.bcb;

import com.visiansystems.util.MonetaryUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BcbRpcDemo {

    public static void main(String[] args) {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("main-context.xml");
        Logger logger = (Logger)ctx.getBean("logger");

        final java.io.PrintStream out = System.out;
        int n = args.length;

        out.format("<< BCB Remote Procedure Call Demo >>\n");

        try {
            if (n == 0) {
                out.format("\nERROR: Missing arguments. Possible input parameters:   \n\n");
                out.print("  [1]: requestLastRateXml()                               \n");
                out.print("       | In  |  currency_code                             \n");
                out.print("       | Out |  in XML format                             \n\n");

                out.print("  [2]: requestRateOnDate()                                \n");
                out.print("       | In  |  currency_code,  date                      \n");
                out.print("       | Out |  in STR format                             \n\n");

                out.print("  [3]: requestRatesOnDateRange()                          \n");
                out.print("       | In  | currency_code_list,  start_date,  end_Date \n");
                out.print("       | Out | in XML format                              \n\n");

                out.print("  [INFO] Date format: dd/MM/yyyy                          \n");
            }
            else {
                BcbRpc bcbRpc = new BcbRpc();

                if (n == 1) {
                    logger.info("Input: " + args[0]);
                    String xml = bcbRpc.requestLastRateXml(
                            MonetaryUtils.getBcbMonetaryIdFromCode(args[0]));
                    logger.info("Received a response: \n\n" + xml);
                }
                else if (n == 2) {
                    logger.info("Input: " + args[0] + ", " + args[1]);
                    String value = bcbRpc.requestRateOnDate(
                            MonetaryUtils.getBcbMonetaryIdFromCode(args[0]),
                            args[1]);
                    logger.info("Received a response: \n\n" + value);
                }
                else if (n == 3) {
                    logger.info("Input: " + args[0] + ", " + args[1] + ", " + args[2]);
                    long codes[] = new long[n - 2];
                    for (int i = 0; i < n - 2; ++i) {
                        codes[i] = MonetaryUtils.getBcbMonetaryIdFromCode(args[i]);
                    }
                    String startDate = args[n - 2];
                    String endDate = args[n - 1];
                    String xml = bcbRpc.requestRatesOnDateRange(codes, startDate, endDate);

                    logger.info("Received a response: \n\n" + xml);
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
