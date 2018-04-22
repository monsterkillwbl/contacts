package com.happytogether.framework.type;

import java.util.Objects;

public class Contacts extends FeatureObj {

    private String _number;
    private String _name;

    public Contacts(){
        _name = "";
        _number = "";
    }

    public void setNumber(String number){
        _number = number;
    }

    public String getNumber(){
        return _number;
    }

    public void setName(String name){
        _name = name;
    }

    public String getName(){
        return _name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }

        Contacts contacts = (Contacts) o;
        return Objects.equals(_number, contacts._number)
                && Objects.equals(_name, contacts._name);
    }

}
