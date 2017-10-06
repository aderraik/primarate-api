package com.visiansystems.model;

import javax.persistence.*;

/**
 * Ilustrates a simple hibernate entity.
 */
@Entity
@Table(name = "SIMPLE_OBJECT")
public class SimpleObject {

    @Id
    @GeneratedValue
    @Column(name = "simpleobject_id")
    private Long objectId;

    @Column(name = "text")
    private String text;

    public SimpleObject() {
    }

    public SimpleObject(long id, String text) {
        this.objectId = id;
        this.text = text;
    }

    public Long getId() {
        return objectId;
    }

    public void setId(Long id) {
        this.objectId = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
