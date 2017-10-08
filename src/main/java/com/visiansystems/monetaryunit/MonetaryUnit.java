package com.visiansystems.monetaryunit;

import javax.persistence.*;

/**
 * +----------------+------+----------------------------------------+
 * |             id | code | name                                   |
 * +----------------+------+----------------------------------------+
 * |              1 | ALL  | Lek Albanês                            |
 * |              2 | AED  | Dirém dos Emirados Árabes Unidos       |
 * |              3 | ARS  | Peso argentino                         |
 * +----------------+------+----------------------------------------+
 */
@Entity
@Table(name = "MonetaryUnit")
public class MonetaryUnit {
    public static final String outFormat = "%1$03d | %2$3.3s | %3$3.3s | %4$s ";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private long id;

    private String name;

    private String code;

    public MonetaryUnit() {
    }

    public MonetaryUnit(String code) {
        this.code = code;
    }

    public MonetaryUnit(int id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
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

    @Override
    public String toString() {
        return String.format(outFormat, id, code, name);
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
