package com.visiansystems.monetaryunit;

import javax.persistence.*;

/**
 * +-----+----------------------------+----------------+
 * |  id | name                       | monetaryUnitId |
 * +-----+----------------------------+----------------+
 * |   1 | Albânia                    |              1 |
 * |   2 | Emirados Árabes Unidos     |              2 |
 * |   3 | Argentina                  |              3 |
 * +-----+----------------------------+----------------+
 */
@Entity
@Table(name = "MonetaryCountry")
public class MonetaryCountry {
    public static final String outFormat = "%1$03d | %2$3.3s | %3$s ";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private long id;

    private String name;

    private long monetaryUnitId;

    public MonetaryCountry() {
    }

    public MonetaryCountry(String name, String code, long monetaryUnitId) {
        this.name = name;
        this.monetaryUnitId = monetaryUnitId;
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

    public long getMonetaryUnit() {
        return monetaryUnitId;
    }

    public void setMonetaryUnit(long unitId) {
        this.monetaryUnitId = unitId;
    }

    @Override
    public String toString() {
        return String.format(outFormat, id, monetaryUnitId, name);
    }
}
