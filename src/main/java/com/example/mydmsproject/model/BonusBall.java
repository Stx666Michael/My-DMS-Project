package com.example.mydmsproject.model;

public class BonusBall extends Ball {

    public BonusBall() {
        setImage("file:src/main/resources/com/example/mydmsproject/Ball-bonus.png");
    }

    public void updateSpeed() {
        if (getVelocityY()>0)
            setVelocity(0, (getPositionY()+10)/150);
        else
            setVelocity(0, -(getPositionY()+10)/150);
    }

}
