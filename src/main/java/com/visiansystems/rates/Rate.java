package com.visiansystems.rates;

import com.visiansystems.ecb.LocalDatePersistenceConverter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

/**
 * +------+---------------+----------------+--------+------------+
 * |   id | centralBankId | monetaryUnitId | amount |       date |
 * +------+---------------+----------------+--------+------------+
 * |    1 |             1 |             12 |  2.469 | 2015-04-30 |
 * |    2 |             1 |             12 |  2.407 | 2015-04-28 |
 * |    3 |             1 |             12 |  2.456 | 2015-04-29 |
 * +------+---------------+----------------+--------+------------+
 */
@Entity
@Table(name = "Rate", uniqueConstraints = @UniqueConstraint(columnNames = { "centralBankId", "monetaryUnitId", "date" }))
public class Rate implements Comparable<Rate> {
    public static final String outFormat = "%1$03d | %2$03d | %3$td/%3$tm/%3$tY | %4$7.4f";

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(nullable = false, name = "centralBankId")
    private long centralBankId;

    @Column(nullable = false, name = "monetaryUnitId")
    private long monetaryUnitId;

    @Column(nullable = false, name = "amount")
    private double amount;

    @Column(nullable = false, name = "date")
//    @Convert(converter = LocalDatePersistenceConverter.class)
    private Date date;

    public Rate() {
    }

    public Rate(long centralBankId, long monetaryUnitId, Date date, double amount) {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
    public int compareTo(Rate o) {
        return Long.compare(monetaryUnitId, o.getMonetaryUnitId());
    }

    @Override
    public boolean equals(Object object) {
        boolean sameSame = false;

        if (object != null && object instanceof Rate) {
            sameSame = this.monetaryUnitId == ((Rate)object).monetaryUnitId;
        }
        return sameSame;
    }
}
