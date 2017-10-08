package com.visiansystems.bank;

import javax.persistence.*;

/**
 * +----+--------------+-------------------------+
 * | id | currencyId   | name                    |
 * +----+--------------+-------------------------+
 * |  1 | BRL          | Banco Central Do Brasil |
 * |  2 | EUR          | European Central Bank's |
 * +----+--------------+-------------------------+
 */
@Entity
@Table(name = "CentralBank")
public class CentralBank {
    public static final String outFormat = "%1$03d | %2$3.3s | %3$s";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private long id;

    private String name;

    private long currencyId;

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

    public long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(long currencyId) {
        this.currencyId = currencyId;
    }

    @Override
    public String toString() {
        return String.format(outFormat, id, currencyId, name);
    }
}
