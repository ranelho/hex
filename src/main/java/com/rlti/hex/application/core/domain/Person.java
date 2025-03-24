package com.rlti.hex.application.core.domain;

public class Person {
    Long id;
    String name;

    public Person(){}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
