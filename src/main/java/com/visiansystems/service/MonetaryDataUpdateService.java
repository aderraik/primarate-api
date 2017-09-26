package com.visiansystems.service;

import com.visiansystems.exception.BankRateFeedException;
import com.visiansystems.exception.DaoException;
import com.visiansystems.util.MonetaryUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.visiansystems.exception.BankDtoException;
import com.visiansystems.bl.bankRateFeed.BankRateFeed;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MonetaryDataUpdateService {

    @Resource
    List<BankRateFeed> bankRateFeedList;

    @Autowired
    private Logger logger;

    public void run() throws DaoException, BankRateFeedException, BankDtoException {
        logger.info("Updating monetary data...");

        logger.info("Initializing classes...");
        MonetaryUtils.init();
        for (BankRateFeed feed : bankRateFeedList) {
            feed.init();
        }

        logger.info("Downloading monetary rates...");
        for (BankRateFeed feed : bankRateFeedList) {
            feed.update();
        }

        logger.info("Persisting monetary rates...");
        for (BankRateFeed feed : bankRateFeedList) {
            feed.persist();
        }

        logger.info("Monetary data updated!");
    }
}
