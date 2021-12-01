package com.example.mydmsproject.model;

public class Buff extends Ball {

    private final int type;
    private boolean isValid = true;

    public Buff(int type) {
        this.type = type;
        setImage("file:src/main/resources/com/example/mydmsproject/Buff"+type+".png");
    }

    public void update(int height) {
        if (getPositionY()<=0 && getVelocityY()<0)
            reverseY();
        if (getVelocityY()>0)
            setVelocity(0, (getPositionY()+10)/100);
        else
            setVelocity(0, -(getPositionY()+10)/100);
        super.update();
        if (getPositionY()>=height)
            isValid = false;
    }

    public int getType() {
        return type;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}
