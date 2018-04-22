package com.happytogether.contacts.util;

/**
 * Created by Monsterkill on 2018/4/20.
 */

public class History {
    private String name;
    private String number;
    private String type;
    private String date;
    private String duration;
    private String location;

    public History(String name, String number, String type, String date, String duration, String location) {
        this.name = name;
        this.number = number;
        this.type = type;
        this.date = date;
        this.duration = duration;
        this.location = location;
    }

    public String getName(){
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public String getDuration() {
        return duration;
    }

    public String getLocation() {
        return location;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setType(String type) {
        this.type = type;
    }
}
