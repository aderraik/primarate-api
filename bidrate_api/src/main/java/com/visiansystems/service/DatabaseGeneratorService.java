package com.visiansystems.service;

import com.visiansystems.dao.generator.CentralBankTableGenerator;
import com.visiansystems.dao.generator.DataBaseGeneratorToggles;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.visiansystems.dao.generator.BankFeedReferenceTableGenerator;
import com.visiansystems.dao.generator.MonetaryTablesGenerator;

@Service
public class DatabaseGeneratorService {

    @Autowired
    private Logger logger;

    @Autowired
    private DataBaseGeneratorToggles dataBaseGeneratorToggles;

    @Autowired
    private CentralBankTableGenerator centralBankTableGenerator;

    @Autowired
    private MonetaryTablesGenerator monetaryTablesGenerator;

    @Autowired
    private BankFeedReferenceTableGenerator bankFeedReferenceTableGenerator;

    public void run() {
        logger.info("Restoring database...");

        logger.info("Toggle - CentralBank:            " +
                    dataBaseGeneratorToggles.isCentralBankTable());
        logger.info("Toggle - MonetaryTables:         " +
                    dataBaseGeneratorToggles.isMonetaryTables());
        logger.info("Toggle - BankFeedReferenceTable: " +
                    dataBaseGeneratorToggles.isBankFeedReferenceTable());

        logger.info("Generating tables...");

        if (dataBaseGeneratorToggles.isCentralBankTable()) {
            logger.info("Creating CentralBank table...");
            centralBankTableGenerator.createTable();
        }

        if (dataBaseGeneratorToggles.isMonetaryTables()) {
            logger.info("Creating Monetary tables...");
            monetaryTablesGenerator.createTable();
        }

        if (dataBaseGeneratorToggles.isBankFeedReferenceTable()) {
            logger.info("Creating BankRateFeedReference table...");
            bankFeedReferenceTableGenerator.createTable();
        }

        logger.info("Database restored.");
    }
}
