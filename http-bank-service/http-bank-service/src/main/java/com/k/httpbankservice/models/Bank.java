package com.k.httpbankservice.models;

public class Bank {

    private long id;

    private String name;

    public Bank() {
    }

    public Bank(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
