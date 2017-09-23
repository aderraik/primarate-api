package com.visiansystems.bl.bankRateFeed.ecb;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.visiansystems.bl.bankRateFeed.BankDto;
import com.visiansystems.bl.bankRateFeed.BankRateFeed;
import com.visiansystems.model.CentralBank;
import com.visiansystems.model.MonetaryUnit;
import com.visiansystems.util.MonetaryUtils;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:main-context.xml")
@DirtiesContext
public class EcbRateFeedTest {

    @Autowired
    private Logger logger;

    @Autowired
    private BankRateFeed ecbBankRateFeed;

    @Test
    @Ignore
    public void testGetBankDto() {
        BankDto bankDto = ecbBankRateFeed.getBankDto();
        Assert.assertNotNull(bankDto);
        Assert.assertEquals(bankDto.getCentralBankId(), MonetaryUtils.ECB_CENTRAL_BANK_ID);
    }

    @Test
    @Ignore
    public void testGetCentralBank() {
        CentralBank centralBank = ecbBankRateFeed.getCentralBank();
        Assert.assertNotNull(centralBank);
        Assert.assertEquals(centralBank.getId(), MonetaryUtils.ECB_CENTRAL_BANK_ID);
        Assert.assertEquals(centralBank.getDefaultCurrencyCode(),
                            MonetaryUtils.EUROPE_CURRENCY_CODE);
    }

    @Test
    @Ignore
    public void testGetAvailableCurrencies() {
        List<MonetaryUnit> currencies = ecbBankRateFeed.getAvailableCurrencies();
        Assert.assertNotNull(currencies);
        Assert.assertTrue(currencies.size() == 31);

        Assert.assertTrue(currencies.contains(new MonetaryUnit("USD")));
        Assert.assertTrue(currencies.contains(new MonetaryUnit("JPY")));
        Assert.assertTrue(currencies.contains(new MonetaryUnit("BGN")));
        Assert.assertTrue(currencies.contains(new MonetaryUnit("CZK")));
        Assert.assertTrue(currencies.contains(new MonetaryUnit("DKK")));
        Assert.assertTrue(currencies.contains(new MonetaryUnit("GBP")));
        Assert.assertTrue(currencies.contains(new MonetaryUnit("HUF")));
        Assert.assertTrue(currencies.contains(new MonetaryUnit("PLN")));
        Assert.assertTrue(currencies.contains(new MonetaryUnit("RON")));
        Assert.assertTrue(currencies.contains(new MonetaryUnit("SEK")));
        Assert.assertTrue(currencies.contains(new MonetaryUnit("CHF")));
        Assert.assertTrue(currencies.contains(new MonetaryUnit("NOK")));
        Assert.assertTrue(currencies.contains(new MonetaryUnit("HRK")));
        Assert.assertTrue(currencies.contains(new MonetaryUnit("RUB")));
        Assert.assertTrue(currencies.contains(new MonetaryUnit("TRY")));
        Assert.assertTrue(currencies.contains(new MonetaryUnit("AUD")));
        Assert.assertTrue(currencies.contains(new MonetaryUnit("BRL")));
        Assert.assertTrue(currencies.contains(new MonetaryUnit("CAD")));
        Assert.assertTrue(currencies.contains(new MonetaryUnit("CNY")));
        Assert.assertTrue(currencies.contains(new MonetaryUnit("HKD")));
        Assert.assertTrue(currencies.contains(new MonetaryUnit("IDR")));
        Assert.assertTrue(currencies.contains(new MonetaryUnit("ILS")));
        Assert.assertTrue(currencies.contains(new MonetaryUnit("INR")));
        Assert.assertTrue(currencies.contains(new MonetaryUnit("KRW")));
        Assert.assertTrue(currencies.contains(new MonetaryUnit("MXN")));
        Assert.assertTrue(currencies.contains(new MonetaryUnit("MYR")));
        Assert.assertTrue(currencies.contains(new MonetaryUnit("NZD")));
        Assert.assertTrue(currencies.contains(new MonetaryUnit("PHP")));
        Assert.assertTrue(currencies.contains(new MonetaryUnit("SGD")));
        Assert.assertTrue(currencies.contains(new MonetaryUnit("THB")));
        Assert.assertTrue(currencies.contains(new MonetaryUnit("ZAR")));
    }

    @Test
    @Ignore
    public void testIsAvailable() {
        Assert.assertTrue(ecbBankRateFeed.isAvailable("BRL"));
        Assert.assertTrue(ecbBankRateFeed.isAvailable("USD"));
        Assert.assertTrue(ecbBankRateFeed.isAvailable("GBP"));
        Assert.assertTrue(ecbBankRateFeed.isAvailable("CAD"));

        Assert.assertFalse(ecbBankRateFeed.isAvailable("XXX"));
        Assert.assertFalse(ecbBankRateFeed.isAvailable("ZVF"));
        Assert.assertFalse(ecbBankRateFeed.isAvailable(""));
        Assert.assertFalse(ecbBankRateFeed.isAvailable("SSS"));
    }

    @Test
    @Ignore
    public void testGetReferenceDateOfDefaultCurrency() {
        String currencyCode = ecbBankRateFeed.getCentralBank().getDefaultCurrencyCode();
        Assert.assertNull(ecbBankRateFeed.getReferenceDate(currencyCode));
    }

    //    @Test
    //    public void testGetReferenceDate() {
    //        String currencyCode;
    //        Date referenceDate;
    //
    //        try {
    //            currencyCode = MonetaryUtils.USA_CURRENCY_CODE;
    //            referenceDate = ecbBankRateFeed.getBankRateFeedReferenceDate(currencyCode);
    //            logger.info("Currency: " + currencyCode + " | " + referenceDate);
    //
    //            Assert.assertNotNull(referenceDate);
    //
    //            ecbBankRateFeed.downloadRate();
    //
    //            //Assert today...
    //
    //        }
    //        catch (Exception e) {
    //            logger.error(e);
    //            e.printStackTrace();
    //
    //            Assert.fail();
    //        }
    //    }

    @Test
    @Ignore
    public void testDefaultCurrencyCode() {
        Assert.assertEquals(MonetaryUtils.EUROPE_CURRENCY_CODE,
                            ecbBankRateFeed.getCentralBank().getDefaultCurrencyCode());
    }
}
