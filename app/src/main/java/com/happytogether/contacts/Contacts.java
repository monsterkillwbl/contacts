package com.happytogether.contacts;

/**
 * Created by Monsterkill on 2018/4/20.
 */

public class Contacts {
    private String name;
    private String number;

    public Contacts(String name, String number){
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
