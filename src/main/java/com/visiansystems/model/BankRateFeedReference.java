package com.visiansystems.model;

import com.visiansystems.dao.LocalDatePersistenceConverter;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * This class represents the 'BankRateFeedReference' table. It's main job is to hold the last date which
 * each currency was updates for a given central bank.
 * <p/>
 * Eg.:
 * +----+---------------+------------------+---------------+
 * | id | centralBankId | monetaryUnitCode | referenceDate |
 * +----+---------------+------------------+---------------+
 * |  1 |             1 | USD              | 2015-01-01    |
 * |  2 |             1 | EUR              | 2015-01-01    |
 * |  3 |             1 | JPY              | 2015-01-01    |
 * +----+---------------+------------------+---------------+
 */
@Entity
@Table(name = "BankRateFeedReference")
public class BankRateFeedReference {

    public static final String outFormat =
            "%1$03d | %2$03d | %3$s | %4$td/%4$tm/%4$tY";

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(nullable = false, name = "centralBankId")
    private long centralBankId;

    @Column(nullable = false, name = "monetaryUnitCode")
    private String currencyCode;

    @Column(name = "referenceDate")
    @Convert(converter = LocalDatePersistenceConverter.class)
    private LocalDate referenceDate;

    public BankRateFeedReference() {
    }

    public BankRateFeedReference(long centralBankId, String currencyCode, LocalDate referenceDate) {
        this.centralBankId = centralBankId;
        this.currencyCode = currencyCode;
        this.referenceDate = referenceDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCentralBankId() {
        return centralBankId;
    }

    public void setCentralBankId(long centralBankId) {
        this.centralBankId = centralBankId;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public LocalDate getReferenceDate() {
        return referenceDate;
    }

    public void setReferenceDate(LocalDate referenceDate) {
        this.referenceDate = referenceDate;
    }

    @Override
    public String toString() {
        return String.format(outFormat, id, centralBankId, currencyCode, referenceDate);
    }
}
