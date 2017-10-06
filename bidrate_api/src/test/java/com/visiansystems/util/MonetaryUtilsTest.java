package com.visiansystems.util;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:main-context.xml")
public class MonetaryUtilsTest {

    @Autowired
    Logger logger;

    @Before
    public void setup() {
        //TODO: Database generator?
    }

    @Test
    public void testGetMonetaryIdFromCode() {
        Assert.assertTrue(MonetaryUtils.getMonetaryIdFromCode("USD") >= 0);
        Assert.assertTrue(MonetaryUtils.getMonetaryIdFromCode("BRL") >= 0);
        Assert.assertTrue(MonetaryUtils.getMonetaryIdFromCode("EUR") >= 0);
        Assert.assertTrue(MonetaryUtils.getMonetaryIdFromCode("CAD") >= 0);
        Assert.assertTrue(MonetaryUtils.getMonetaryIdFromCode("AUD") >= 0);
        Assert.assertTrue(MonetaryUtils.getMonetaryIdFromCode("DKK") >= 0);
        Assert.assertTrue(MonetaryUtils.getMonetaryIdFromCode("JPY") >= 0);

        Assert.assertTrue(MonetaryUtils.getMonetaryIdFromCode("KKK") ==
                          MonetaryUtils.INVALID_MONETARY_UNIT_CODE);
        Assert.assertTrue(MonetaryUtils.getMonetaryIdFromCode("SSS") ==
                          MonetaryUtils.INVALID_MONETARY_UNIT_CODE);
        Assert.assertTrue(MonetaryUtils.getMonetaryIdFromCode("") ==
                          MonetaryUtils.INVALID_MONETARY_UNIT_CODE);
    }

    @Test
    public void testIsRateFeedUpToDate() {

        //
        //        logger.info(cal.getTime());
        //        logger.info(MonetaryUtils.isRateFeedUpToDate(cal.getTime()));
        //
        //        //ECB - Work days 14:15 CET
        //        logger.info(cal.get(Calendar.DAY_OF_WEEK));

    }
}
