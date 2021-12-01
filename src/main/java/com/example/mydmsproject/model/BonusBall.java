package com.example.mydmsproject.model;

public class BonusBall extends Ball {

    public BonusBall() {
        setImage("file:src/main/resources/com/example/mydmsproject/Ball-bonus.png");
    }

    @Override
    public void update() {
        if (getVelocityY()>0)
            setVelocity(0, (getPositionY()+10)/150);
        else
            setVelocity(0, -(getPositionY()+10)/150);
        super.update();
    }

}
