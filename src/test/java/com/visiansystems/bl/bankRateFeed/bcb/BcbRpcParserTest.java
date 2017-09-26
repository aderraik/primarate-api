package com.visiansystems.bl.bankRateFeed.bcb;

import com.visiansystems.util.MonetaryUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.visiansystems.model.MonetaryData;
import com.visiansystems.model.MonetarySeriesData;

import java.time.LocalDate;
import java.time.Month;

/**
 * This is a simple tentative of an integration test. It requires that the BCB web service is up and
 * running without any issues. For that reason this test is not a proper unit test. It could be
 * removed in the future.
 * <p/>
 * TODO: Create exception tests.
 * TODO: Maybe accepts when the webservice is down an exception.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:main-context.xml")
@DirtiesContext
public class BcbRpcParserTest {

    @Autowired
    private Logger logger;

    @Autowired
    private BcbRpcParser bcbRpcParser;

    @Test
    public void testRequestLastUsdRate() {
        try {
            MonetaryData data = bcbRpcParser.requestLastRate(MonetaryUtils.USA_CURRENCY_CODE);

            Assert.assertTrue(data.getCentralBankId() == MonetaryUtils.BCB_CENTRAL_BANK_ID);
            Assert.assertTrue(
                    data.getMonetaryUnitId() ==
                    MonetaryUtils.getMonetaryIdFromCode(MonetaryUtils.USA_CURRENCY_CODE));
            Assert.assertTrue(data.getAmount() > 0);
            Assert.assertTrue(MonetaryUtils.getLastValidMonetaryDate(
                    MonetaryUtils.BCB_CENTRAL_BANK_ID).isEqual(data.getDate()));
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void testRequestLastEurRate() {
        try {
            MonetaryData data = bcbRpcParser.requestLastRate(MonetaryUtils.EUROPE_CURRENCY_CODE);

            Assert.assertTrue(data.getCentralBankId() == MonetaryUtils.BCB_CENTRAL_BANK_ID);
            Assert.assertTrue(
                    data.getMonetaryUnitId() == MonetaryUtils.getMonetaryIdFromCode(
                            MonetaryUtils.EUROPE_CURRENCY_CODE));
            Assert.assertTrue(data.getAmount() > 0);
            Assert.assertTrue(MonetaryUtils.getLastValidMonetaryDate(
                    MonetaryUtils.BCB_CENTRAL_BANK_ID).isEqual(data.getDate()));
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void testRequestUsdRatesOnDateRange() {
        try {
            MonetarySeriesData data = bcbRpcParser.requestRatesOnDateRange(
                    new String[] { MonetaryUtils.USA_CURRENCY_CODE },
                    "02/06/2015",
                    "03/06/2015");

            LocalDate day1 = LocalDate.of(2015, Month.JUNE, 2);
            LocalDate day2 = LocalDate.of(2015, Month.JUNE, 3);
            Assert.assertTrue(data.getStartDate().equals(day1));
            Assert.assertTrue(data.getEndDate().equals(day2));

            for (MonetaryData unit : data.getAllMonetaryData()) {
                Assert.assertTrue(
                        unit.getCentralBankId() == MonetaryUtils.BCB_CENTRAL_BANK_ID);
                Assert.assertTrue(unit.getMonetaryUnitId() ==
                                  MonetaryUtils
                                          .getMonetaryIdFromCode(MonetaryUtils.USA_CURRENCY_CODE));
                Assert.assertTrue(unit.getAmount() > 0);
                Assert.assertTrue(MonetaryUtils.isMonetaryDateValid(unit.getDate()));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void testRequestEurRatesOnDateRange() {
        try {
            MonetarySeriesData data = bcbRpcParser.requestRatesOnDateRange(
                    new String[] { MonetaryUtils.EUROPE_CURRENCY_CODE },
                    "01/04/2000",
                    "30/07/2000");

            LocalDate day1 = LocalDate.of(2000, Month.APRIL, 1);
            LocalDate day2 = LocalDate.of(2000, Month.JULY, 30);
            Assert.assertTrue(data.getStartDate().equals(day1));
            Assert.assertTrue(data.getEndDate().equals(day2));

            for (MonetaryData unit : data.getAllMonetaryData()) {
                Assert.assertTrue(
                        unit.getCentralBankId() == MonetaryUtils.BCB_CENTRAL_BANK_ID);
                Assert.assertTrue(
                        unit.getMonetaryUnitId() ==
                        MonetaryUtils.getMonetaryIdFromCode(MonetaryUtils.EUROPE_CURRENCY_CODE));
                Assert.assertTrue(unit.getAmount() > 0);
                Assert.assertTrue(MonetaryUtils.isMonetaryDateValid(unit.getDate()));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void testTotalRates() {
        try {
            MonetarySeriesData data = bcbRpcParser.requestRatesOnDateRange(
                    new String[] { MonetaryUtils.EUROPE_CURRENCY_CODE,
                                   MonetaryUtils.USA_CURRENCY_CODE,
                                   MonetaryUtils.BRITAIN_CURRENCY_CODE },
                    "10/05/2015",
                    "31/05/2015");

            int totalCurrencies = 3;
            int numRates = 0;
            for (MonetaryData unit : data.getAllMonetaryData()) {
                numRates++;
            }

            LocalDate day1 = LocalDate.of(2015, Month.MAY, 10);
            LocalDate day2 = LocalDate.of(2015, Month.MAY, 31);
            Assert.assertEquals(numRates,
                                totalCurrencies *
                                MonetaryUtils.getWorkingDaysBetweenTwoDates(day1, day2));
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
}
