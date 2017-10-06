package com.visiansystems.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Represents a country's central bank.
 * <p/>
 * Eg.:
 * +---------------+---------------------+-------------------------+
 * | centralBankId | defaultCurrencyCode | name                    |
 * +---------------+---------------------+-------------------------+
 * |             1 | BRL                 | Banco Central Do Brasil |
 * |             2 | EUR                 | European Central Bank's |
 * +---------------+---------------------+-------------------------+
 */
@Entity
@Table(name = "CentralBank")
public class CentralBank {
    public static final String outFormat = "%1$03d | %2$3.3s | %3$s";

    @Id
    @Column(name = "centralBankId")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(nullable = false, name = "defaultCurrencyCode")
    private String defaultCurrencyCode;

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefaultCurrencyCode() {
        return defaultCurrencyCode;
    }

    public void setDefaultCurrencyCode(String defaultCurrencyCode) {
        this.defaultCurrencyCode = defaultCurrencyCode;
    }

    @Override
    public String toString() {
        return String.format(outFormat, id, defaultCurrencyCode, name);
    }
}
