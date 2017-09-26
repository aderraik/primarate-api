package com.visiansystems.model;

import com.visiansystems.dao.LocalDatePersistenceConverter;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Represents an entry on the MonetaryData table.
 * <p/>
 * Eg.:
 * +------+--------+---------------+------------+----------------+
 * | id   | amount | centralBankId | date       | monetaryUnitId |
 * +------+--------+---------------+------------+----------------+
 * |    8 | 2.4699 |             1 | 2015-04-30 |             12 |
 * |   18 |  2.407 |             1 | 2015-04-28 |             12 |
 * |   28 |  2.456 |             1 | 2015-04-29 |             12 |
 * +------+--------+---------------+------------+----------------+
 */
@Entity
@Table(name = "MonetaryData",
        uniqueConstraints = @UniqueConstraint(columnNames = { "centralBankId",
                                                              "monetaryUnitId",
                                                              "date" }))

public class MonetaryData implements Comparable<MonetaryData> {

    public static final String outFormat = "%1$03d | %2$03d | %3$td/%3$tm/%3$tY | %4$7.4f";

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(nullable = false, name = "centralBankId")
    private long centralBankId;

    @Column(nullable = false, name = "monetaryUnitId")
    private long monetaryUnitId;

    @Column(nullable = false, name = "date")
    @Convert(converter = LocalDatePersistenceConverter.class)
    private LocalDate date;

    @Column(nullable = false, name = "amount")
    private double amount;

    public MonetaryData() {
    }

    public MonetaryData(long centralBankId, long monetaryUnitId, LocalDate date, double amount) {
        this.centralBankId = centralBankId;
        this.monetaryUnitId = monetaryUnitId;
        this.date = date;
        this.amount = amount;
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

    public long getMonetaryUnitId() {
        return monetaryUnitId;
    }

    public void setMonetaryUnitId(long monetaryUnitId) {
        this.monetaryUnitId = monetaryUnitId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isValid() {
        if ((amount < 0) || (centralBankId < 0) || (id < 0) || (monetaryUnitId < 0) ||
            (date == null)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format(outFormat, centralBankId, monetaryUnitId, date, amount);
    }

    @Override
    public int compareTo(MonetaryData o) {
        return Long.compare(monetaryUnitId, o.getMonetaryUnitId());
    }

    @Override
    public boolean equals(Object object) {
        boolean sameSame = false;

        if (object != null && object instanceof MonetaryData) {
            sameSame = this.monetaryUnitId == ((MonetaryData)object).monetaryUnitId;
        }
        return sameSame;
    }
}
