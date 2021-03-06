package com.visiansystems.model;

import javax.persistence.*;

/**
 * Monetary Unit class represents a Country's currency.
 * <p/>
 * Eg.:
 * +----------------+------+----------------------------------------+--------+
 * | monetaryUnitId | code | name                                   | symbol |
 * +----------------+------+----------------------------------------+--------+
 * |              1 | ALL  | Lek Albanês                            | ALL    |
 * |              2 | AED  | Dirém dos Emirados Árabes Unidos       | AED    |
 * |              3 | ARS  | Peso argentino                         | ARS    |
 * +----------------+------+----------------------------------------+--------+
 */
@Entity
@Table(name = "MonetaryUnit")
public class MonetaryUnit {
    public static final String outFormat = "%1$03d | %2$3.3s | %3$3.3s | %4$s ";

    @Id
    @GeneratedValue
    @Column(name = "monetaryUnitId")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(unique = true, nullable = false, name = "code")
    private String code;

    @Column(nullable = false, name = "symbol")
    private String symbol;

    public MonetaryUnit() {
    }

    public MonetaryUnit(String code) {
        this.code = code;
    }

    public MonetaryUnit(int id, String name, String code, String symbol) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.symbol = symbol;
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

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return String.format(outFormat, id, code, symbol, name);
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof MonetaryUnit) {
            MonetaryUnit toCompare = (MonetaryUnit)o;
            return this.code.equals(toCompare.code);
        }
        return false;
    }
}
