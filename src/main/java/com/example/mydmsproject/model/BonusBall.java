package com.example.mydmsproject.model;

public class BonusBall extends Ball {

    public BonusBall() {
        setImage("file:src/main/resources/com/example/mydmsproject/" +
                "Ball-bonus.png");
    }

    @Override
    public void update() {
        final int SPEED_AT_TOP = 10;
        final double ACCELERATION = 0.008;

        if (getVelocityY()>0)
            setVelocity(getVelocityX(), (getPositionY()+SPEED_AT_TOP)
                    *ACCELERATION);
        else
            setVelocity(getVelocityX(), -(getPositionY()+SPEED_AT_TOP)
                    *ACCELERATION);
        super.update();
    }

}
