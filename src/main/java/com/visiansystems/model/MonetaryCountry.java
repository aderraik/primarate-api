package com.visiansystems.model;

import javax.persistence.*;

/**
 * Represents a valid Country to the exchange system.
 * <p/>
 * Eg.:
 * +-------------------+------+---------------------------------------------+-----------------------------+
 * | monetaryCountryId | code | name                                        | monetaryUnit_monetaryUnitId |
 * +-------------------+------+---------------------------------------------+-----------------------------+
 * |                 1 | ALB  | Albânia                                     |                           1 |
 * |                 2 | ARE  | Emirados Árabes Unidos                      |                           2 |
 * |                 3 | ARG  | Argentina                                   |                           3 |
 * +-------------------+------+---------------------------------------------+-----------------------------+
 */
@Entity
@Table(name = "MonetaryCountry")
public class MonetaryCountry {
    public static final String outFormat = "%1$03d | %2$3.3s | %3$s ";

    @Id
    @GeneratedValue
    @Column(name = "monetaryCountryId")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(nullable = false, name = "code")
    private String code;

    @ManyToOne(cascade = CascadeType.ALL)
    private MonetaryUnit monetaryUnit;

    public MonetaryCountry() {
    }

    public MonetaryCountry(String name, String code, MonetaryUnit monetaryUnit) {
        this.name = name;
        this.code = code;
        this.monetaryUnit = monetaryUnit;
    }

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public MonetaryUnit getMonetaryUnit() {
        return monetaryUnit;
    }

    public void setMonetaryUnit(MonetaryUnit monetaryUnit) {
        this.monetaryUnit = monetaryUnit;
    }

    @Override
    public String toString() {
        return String.format(outFormat, id, code, name);
    }
}
