package com.visiansystems.bl.bankRateFeed.bcb;

import com.google.common.math.DoubleMath;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.visiansystems.bl.bankRateFeed.BankRateFeed;
import com.visiansystems.dao.repository.BankRateFeedReferenceRepository;
import com.visiansystems.model.BankRateFeedReference;
import com.visiansystems.model.MonetaryData;
import com.visiansystems.model.MonetarySeriesData;
import com.visiansystems.model.MonetaryUnit;
import com.visiansystems.util.MonetaryUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.List;

/**
 * TODO: NEED TO MOCK OUT THE DATABASE!!! WRONG!!!!!!!!!!!!!
 * TODO: TRANSFORM THIS TEST ONTO A INTEGRATION TEST!!
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:main-context.xml")
public class BcbRateFeedTest {
    private static final String COMMA_DELIMITER = ",";
    private static final String USD_TO_EUR_FILE = "src/test/resources/bcb/usd_to_eur.csv";
    private static final double TOLERANCE = 0.0001;
    private static BcbDateFormat dateFormat;

    @Autowired
    @Qualifier("bcbRateFeed")
    private BankRateFeed bcbBankRateFeed;

    //    @Autowired
    //    private MonetaryDataDao monetaryDataDao;

    @Autowired
    private BankRateFeedReferenceRepository referenceRepository;

    @Autowired
    private Logger logger;

    @Before
    public void setUp() throws Exception {
        dateFormat = new BcbDateFormat();
    }

    @Test
    public void testDefaultCurrencyCode() {
        Assert.assertEquals("BRL", bcbBankRateFeed.getCentralBank().getDefaultCurrencyCode());
    }

    @Test
    public void testBcbCurrencies() {
        List<MonetaryUnit> currencies = bcbBankRateFeed.getAvailableCurrencies();
        Assert.assertNotNull(currencies);
        Assert.assertTrue(currencies.size() == 10);

        Assert.assertTrue(currencies.contains(new MonetaryUnit("USD")));
        Assert.assertTrue(currencies.contains(new MonetaryUnit("EUR")));
        Assert.assertTrue(currencies.contains(new MonetaryUnit("JPY")));
        Assert.assertTrue(currencies.contains(new MonetaryUnit("GBP")));
        Assert.assertTrue(currencies.contains(new MonetaryUnit("CHF")));
        Assert.assertTrue(currencies.contains(new MonetaryUnit("DKK")));
        Assert.assertTrue(currencies.contains(new MonetaryUnit("NOK")));
        Assert.assertTrue(currencies.contains(new MonetaryUnit("SEK")));
        Assert.assertTrue(currencies.contains(new MonetaryUnit("AUD")));
        Assert.assertTrue(currencies.contains(new MonetaryUnit("CAD")));

        Assert.assertTrue(bcbBankRateFeed.isAvailable("USD"));
        Assert.assertTrue(bcbBankRateFeed.isAvailable("EUR"));
        Assert.assertTrue(bcbBankRateFeed.isAvailable("JPY"));
        Assert.assertTrue(bcbBankRateFeed.isAvailable("GBP"));
        Assert.assertTrue(bcbBankRateFeed.isAvailable("CHF"));
        Assert.assertTrue(bcbBankRateFeed.isAvailable("DKK"));
        Assert.assertTrue(bcbBankRateFeed.isAvailable("NOK"));
        Assert.assertTrue(bcbBankRateFeed.isAvailable("SEK"));
        Assert.assertTrue(bcbBankRateFeed.isAvailable("AUD"));
        Assert.assertTrue(bcbBankRateFeed.isAvailable("CAD"));
    }

    @Test
    public void testCentralBankNotNull() {
        Assert.assertNotNull(bcbBankRateFeed.getCentralBank());
    }

    @Test
    public void testCentralBank() {
        Assert.assertEquals(bcbBankRateFeed.getCentralBank().getId(),
                            MonetaryUtils.BCB_CENTRAL_BANK_ID);
    }

    @Test
    public void testConvertUsdToEur() {
        Assert.assertTrue(verifyBenchmarkFile(USD_TO_EUR_FILE, "USD", "EUR"));
    }

    @Ignore
    @Test
    //TODO: Clear series+references
    //TODO: Fix it
    public void testPersistFromCleanDb() {
        try {
            clearReferenceData();
            clearMonetaryData();

            long bankId = MonetaryUtils.BCB_CENTRAL_BANK_ID;

            bcbBankRateFeed.update();
            bcbBankRateFeed.persist();

            MonetarySeriesData dtoSeriesData =
                    bcbBankRateFeed.getBankDto().getDownloadedSeriesData();
            List<MonetaryData> dtoSeriesDataList = dtoSeriesData.getAllMonetaryData();

            //            MonetarySeriesData dbSeriesData = monetaryDataDao.getAllData(bankId);
            MonetarySeriesData dbSeriesData = null;
            List<MonetaryData> dbSeriesDataList = dbSeriesData.getAllMonetaryData();

            // Compare it
            Assert.assertEquals(dtoSeriesData.getCentralBankId(),
                                dbSeriesData.getCentralBankId());
            Assert.assertEquals(dtoSeriesData.getTotalRates(), dbSeriesData.getTotalRates());
            Assert.assertEquals(dtoSeriesData.getStartDate(), dbSeriesData.getStartDate());
            Assert.assertEquals(dtoSeriesData.getEndDate(), dbSeriesData.getEndDate());
            Assert.assertEquals(dtoSeriesData.size(), dbSeriesData.size());

            for (MonetaryData data : dtoSeriesDataList) {
                logger.info(data);
                if (!dbSeriesDataList.contains(data)) {
                    Assert.fail();
                }
            }

            // Check reference date;
            for (MonetaryUnit unit : MonetaryUtils.getBcbCurrencies()) {
                Assert.assertEquals(
                        MonetaryUtils.getBankRateFeedReferenceDate(bankId, unit.getCode()),
                        MonetaryUtils.getLastValidMonetaryDate(bankId));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Ignore
    @Test
    public void testPersistFromDirtyDb() {
        try {
            clearReferenceData();
            clearMonetaryData();

            long bankId = MonetaryUtils.BCB_CENTRAL_BANK_ID;

            bcbBankRateFeed.update();
            bcbBankRateFeed.persist();

            bcbBankRateFeed.update();
            bcbBankRateFeed.persist();

            MonetarySeriesData dtoSeriesData =
                    bcbBankRateFeed.getBankDto().getDownloadedSeriesData();
            List<MonetaryData> dtoSeriesDataList = dtoSeriesData.getAllMonetaryData();

            //            MonetarySeriesData dbSeriesData = monetaryDataDao.getAllData(bankId);
            MonetarySeriesData dbSeriesData = null;
            List<MonetaryData> dbSeriesDataList = dbSeriesData.getAllMonetaryData();

            // Compare it
            Assert.assertEquals(dtoSeriesData.getCentralBankId(),
                                dbSeriesData.getCentralBankId());
            Assert.assertEquals(dtoSeriesData.getTotalRates(), dbSeriesData.getTotalRates());
            Assert.assertEquals(dtoSeriesData.getStartDate(), dbSeriesData.getStartDate());
            Assert.assertEquals(dtoSeriesData.getEndDate(), dbSeriesData.getEndDate());
            Assert.assertEquals(dtoSeriesData.size(), dbSeriesData.size());

            for (MonetaryData data : dtoSeriesDataList) {
                logger.info(data);
                if (!dbSeriesDataList.contains(data)) {
                    Assert.fail();
                }
            }

            // Check reference date;
            for (MonetaryUnit unit : MonetaryUtils.getBcbCurrencies()) {
                Assert.assertEquals(
                        MonetaryUtils.getBankRateFeedReferenceDate(bankId, unit.getCode()),
                        MonetaryUtils.getLastValidMonetaryDate(bankId));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * TODO: Drop changes made to the database by this test?
     * TODO: Move to TestUtils?
     */
    private void clearReferenceData() {
        referenceRepository.deleteAll();
        LocalDate startDay = LocalDate.now().minusMonths(3);

        for (MonetaryUnit unit : MonetaryUtils.getBcbCurrencies()) {
            BankRateFeedReference reference = new BankRateFeedReference(
                    MonetaryUtils.BCB_CENTRAL_BANK_ID,
                    unit.getCode(),
                    startDay);
            referenceRepository.save(reference);
        }
        bcbBankRateFeed.getBankDto().loadDtoReferenceDate();
    }

    /**
     * TODO: Drop changes made to the database by this test?
     * TODO: Move to TestUtils?
     */
    private void clearMonetaryData() {
        //        monetaryDataDao.removeMonetarySeriesData(MonetaryUtils.BCB_CENTRAL_BANK_ID);
        bcbBankRateFeed.getBankDto().clear();
    }

    private boolean verifyBenchmarkFile(String fileName, String fromCurrency, String toCurrency) {
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(fileName));
            fileReader.readLine();
            String line = "";

            while ((line = fileReader.readLine()) != null) {
                String[] tokens = line.split(COMMA_DELIMITER);

                if (tokens.length > 0) {
                    System.out.println(tokens[0] + " | " + tokens[1] + " | " + tokens[2]);

                    LocalDate date = dateFormat.parse(tokens[0]);
                    double rateExpected = Double.valueOf(tokens[1]);
                    double rateReturned =
                            bcbBankRateFeed.convert(1.0, fromCurrency, toCurrency, date);

                    if (DoubleMath.fuzzyCompare(rateExpected, rateReturned, TOLERANCE) != 0) {
                        return false;
                    }
                }
            }
            return true;
        }
        catch (Exception ignored) {
            return false;
        }
    }
}
