package com.example.mydmsproject.model;

public class Buff extends BonusBall {

    private final int type;

    public Buff(int type) {
        this.type = type;
        setImage("file:src/main/resources/com/example/mydmsproject/Buff"+type+".png");
    }
}
