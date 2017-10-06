package com.visiansystems.bl.bankRateFeed.bcb;

import com.visiansystems.bl.bankRateFeed.BankDto;
import com.visiansystems.model.MonetaryData;
import com.visiansystems.model.MonetarySeriesData;
import com.visiansystems.model.MonetaryUnit;
import com.visiansystems.util.MonetaryUtils;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

/**
 * DTOs are simple objects that should not contain any business logic that would require testing.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:main-context.xml")
public class BcbDtoTest {
    @Autowired
    @Qualifier("bcbDto")
    private BankDto bcbDto; //TODO: How to create an alias to 'underTest'?

    @Autowired
    private Logger logger;

    @Test
    public void testGetCentralBankId() {
        Assert.assertTrue(bcbDto.getCentralBankId() == MonetaryUtils.BCB_CENTRAL_BANK_ID);
    }

    @Test
    public void testGetLastUsdRate() {
        try {
            MonetaryData data = bcbDto.getLastRate(MonetaryUtils.USA_CURRENCY_CODE);

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
    public void testGetLastEurRate() {
        try {
            MonetaryData data = bcbDto.getLastRate(MonetaryUtils.EUROPE_CURRENCY_CODE);

            Assert.assertTrue(data.getCentralBankId() == MonetaryUtils.BCB_CENTRAL_BANK_ID);
            Assert.assertTrue(
                    data.getMonetaryUnitId() ==
                    MonetaryUtils.getMonetaryIdFromCode(MonetaryUtils.EUROPE_CURRENCY_CODE));
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
    public void testGetUsdRateOnDate() {
        try {
            LocalDate date = LocalDate.of(2015, Month.JUNE, 10);
            MonetaryData data = bcbDto.getRateOnDate(MonetaryUtils.USA_CURRENCY_CODE, date);

            Assert.assertTrue(data.getCentralBankId() == MonetaryUtils.BCB_CENTRAL_BANK_ID);
            Assert.assertTrue(
                    data.getMonetaryUnitId() == MonetaryUtils.getMonetaryIdFromCode(
                            MonetaryUtils.USA_CURRENCY_CODE));
            Assert.assertTrue(data.getAmount() > 0);
            Assert.assertTrue(MonetaryUtils.isMonetaryDateValid(data.getDate()));
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void testGetEurRateOnDate() {
        try {
            LocalDate date = LocalDate.of(2015, Month.JUNE, 10);
            MonetaryData data = bcbDto.getRateOnDate(MonetaryUtils.EUROPE_CURRENCY_CODE, date);

            Assert.assertTrue(data.getCentralBankId() == MonetaryUtils.BCB_CENTRAL_BANK_ID);
            Assert.assertTrue(
                    data.getMonetaryUnitId() == MonetaryUtils.getMonetaryIdFromCode(
                            MonetaryUtils.EUROPE_CURRENCY_CODE));
            Assert.assertTrue(data.getAmount() > 0);
            Assert.assertTrue(MonetaryUtils.isMonetaryDateValid(data.getDate()));
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void testGetRateOnDateRange() {
        try {
            LocalDate startDate = LocalDate.of(2015, Month.JANUARY, 5);
            LocalDate endDate = LocalDate.of(2015, Month.JUNE, 9);

            MonetarySeriesData data = bcbDto.getRatesOnDateRange(
                    new String[] { MonetaryUtils.USA_CURRENCY_CODE }, startDate, endDate);

            Assert.assertTrue(data.getStartDate().equals(startDate));
            Assert.assertTrue(data.getEndDate().equals(endDate));

            for (MonetaryData unit : data
                    .getAllMonetaryDataFromCurrency(MonetaryUtils.USA_CURRENCY_CODE)) {
                Assert.assertTrue(unit.getCentralBankId() == MonetaryUtils.BCB_CENTRAL_BANK_ID);
                Assert.assertTrue(
                        unit.getMonetaryUnitId() == MonetaryUtils.getMonetaryIdFromCode(
                                MonetaryUtils.USA_CURRENCY_CODE));
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
    public void testGetRatesOnDateRange() {
        try {
            LocalDate startDate = LocalDate.of(2015, Month.JANUARY, 5);
            LocalDate endDate = LocalDate.of(2015, Month.JUNE, 9);

            MonetarySeriesData data = bcbDto
                    .getRatesOnDateRange(new String[] { MonetaryUtils.USA_CURRENCY_CODE,
                                                        MonetaryUtils.EUROPE_CURRENCY_CODE },
                                         startDate, endDate);

            Assert.assertTrue(startDate.equals(data.getStartDate()));
            Assert.assertTrue(endDate.equals(data.getEndDate()));

            for (MonetaryData unit : data
                    .getAllMonetaryDataFromCurrency(MonetaryUtils.USA_CURRENCY_CODE)) {
                Assert.assertTrue(unit.getCentralBankId() == MonetaryUtils.BCB_CENTRAL_BANK_ID);
                Assert.assertTrue(unit.getMonetaryUnitId() == MonetaryUtils.getMonetaryIdFromCode(
                        MonetaryUtils.USA_CURRENCY_CODE));
                Assert.assertTrue(unit.getAmount() > 0);
                Assert.assertTrue(MonetaryUtils.isMonetaryDateValid(unit.getDate()));
            }

            for (MonetaryData unit : data
                    .getAllMonetaryDataFromCurrency(MonetaryUtils.EUROPE_CURRENCY_CODE)) {
                Assert.assertTrue(unit.getCentralBankId() == MonetaryUtils.BCB_CENTRAL_BANK_ID);
                Assert.assertTrue(unit.getMonetaryUnitId() == MonetaryUtils.getMonetaryIdFromCode(
                        MonetaryUtils.EUROPE_CURRENCY_CODE));
                Assert.assertTrue(unit.getAmount() > 0);
                Assert.assertTrue(MonetaryUtils.isMonetaryDateValid(unit.getDate()));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGivenInvalidCurrencyCodeWhenGettingLastValue() throws Exception {
        bcbDto.getLastRate(MonetaryUtils.INVALID_CURRENCY_CODE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGivenInvalidCurrencyCodeWhenGettingLastValuesOnDateRange() throws Exception {
        bcbDto.getRatesOnDateRange(new String[] { MonetaryUtils.INVALID_CURRENCY_CODE },
                                   LocalDate.now(), LocalDate.now());
    }

    @Test
    //TODO: Clear series+references
    //TODO: MOCK OUT THE DATABASE!!!!!!! WRONG!!!!!
    public void testUpdate() {
        try {
            LocalDate startDate = MonetaryUtils.getBankRateFeedReferenceDate(
                    MonetaryUtils.BCB_CENTRAL_BANK_ID,
                    MonetaryUtils.USA_CURRENCY_CODE);
            LocalDate endDate =
                    MonetaryUtils.getLastValidMonetaryDate(MonetaryUtils.BCB_CENTRAL_BANK_ID);

            // Runs downloadRate and get the data downloaded from it
            bcbDto.downloadRate();
            MonetarySeriesData downloadedSeriesData = bcbDto.getDownloadedSeriesData();
            List<MonetaryData> downloadedDataList = downloadedSeriesData.getAllMonetaryData();

            // Gets the same data via getRates
            List<MonetaryUnit> currencyList = MonetaryUtils.getBcbCurrencies();
            String[] currencies = new String[currencyList.size()];
            int i = 0;
            for (MonetaryUnit unit : currencyList) {
                currencies[i] = unit.getCode();
                i++;
            }
            MonetarySeriesData seriesData =
                    bcbDto.getRatesOnDateRange(currencies, startDate, endDate);
            List<MonetaryData> seriesDataList = seriesData.getAllMonetaryData();

            // Compare it
            Assert.assertEquals(downloadedSeriesData.getCentralBankId(),
                                seriesData.getCentralBankId());

            for (MonetaryData data : seriesDataList) {
                logger.info(data);
                if (!downloadedDataList.contains(data)) {
                    Assert.fail();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void testReferenceDateAfterUpdate() {
        try {
            long bankId = MonetaryUtils.BCB_CENTRAL_BANK_ID;

            for (MonetaryUnit unit : MonetaryUtils.getBcbCurrencies()) {
                Assert.assertEquals(
                        MonetaryUtils.getBankRateFeedReferenceDate(bankId, unit.getCode()),
                        bcbDto.getDtoReferenceDate(unit.getCode()));
            }

            bcbDto.downloadRate();

            for (MonetaryUnit unit : MonetaryUtils.getBcbCurrencies()) {
                Assert.assertEquals(MonetaryUtils.getLastValidMonetaryDate(bankId),
                                    bcbDto.getDtoReferenceDate(unit.getCode()));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    @Ignore
    //TODO: Use Mockito?
    public void testUpdateShouldBeCalledJustOnce() {
        try {
            bcbDto.downloadRate();
            bcbDto.downloadRate();
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
}
