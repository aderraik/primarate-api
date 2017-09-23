package com.visiansystems.dao.generator;

import org.springframework.beans.factory.annotation.Required;

public class DataBaseGeneratorToggles {
    private boolean centralBankTable;
    private boolean monetaryTables;
    private boolean bankFeedReferenceTable;

    public boolean isCentralBankTable() {
        return centralBankTable;
    }

    @Required
    public void setCentralBankTable(boolean centralBankTable) {
        this.centralBankTable = centralBankTable;
    }

    public boolean isMonetaryTables() {
        return monetaryTables;
    }

    @Required
    public void setMonetaryTables(boolean monetaryTables) {
        this.monetaryTables = monetaryTables;
    }

    public boolean isBankFeedReferenceTable() {
        return bankFeedReferenceTable;
    }

    @Required
    public void setBankFeedReferenceTable(boolean bankFeedReferenceTable) {
        this.bankFeedReferenceTable = bankFeedReferenceTable;
    }
}
