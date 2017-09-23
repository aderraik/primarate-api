package com.visiansystems.bl.bankRateFeed.ecb;

import com.visiansystems.bl.bankRateFeed.BankDto;
import com.visiansystems.model.MonetaryData;
import com.visiansystems.util.MonetaryUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:main-context.xml")
@DirtiesContext
public class EcbDtoTest {
    private static final String USD_CURRENCY_CODE = "USD";

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    @Qualifier("ecbDto")
    private BankDto ecbDto;

    @Autowired
    private Logger logger;

    @Test
    public void testCentralBank() {
        Assert.assertTrue(ecbDto.getCentralBankId() == MonetaryUtils.ECB_CENTRAL_BANK_ID);
    }

    @Test
    @Ignore
    public void testGetLastValueNotNull() {
        try {
            MonetaryData data = ecbDto.getLastRate(USD_CURRENCY_CODE);
            Assert.assertTrue(data.getAmount() > 0);
            Assert.assertTrue(data.getMonetaryUnitId() > 0);
            Assert.assertTrue(data.getCentralBankId() == ecbDto.getCentralBankId());
            Assert.assertTrue(MonetaryUtils.isMonetaryDateValid(data.getDate()));

            logger.info(data);
        }
        catch (Exception e) {
            logger.error(e);
            Assert.fail();
        }
    }

    @Test
    @Ignore
    public void testOnlyParseOnce() {

        try {
            //            ecbDto.requestLastRate(USD_CURRENCY_CODE);
            //            ecbDto.requestLastRate(USD_CURRENCY_CODE);

            //TODO: Setup Mockito
            //Mockito.verify(ecbClient, times(1)).send();

        }
        catch (Exception e) {
            Assert.fail();
        }
    }
}
