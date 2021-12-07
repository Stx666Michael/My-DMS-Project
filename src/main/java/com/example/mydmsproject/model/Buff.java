package com.example.mydmsproject.model;

public class Buff extends Ball {

    private final int m_type;

    private boolean m_isValid = true;

    public int getType() {
        return m_type;
    }

    public boolean isValid() {
        return m_isValid;
    }

    public void setValid(boolean isValid) {
        m_isValid = isValid;
    }

    public Buff(int type) {
        m_type = type;
        setImage("file:src/main/resources/com/example/mydmsproject/" +
                "Buff" + type + ".png");
    }

    public void update(int height) {
        final int SPEED_AT_TOP = 10;
        final double ACCELERATION = 0.01;

        if (getPositionY()<=0 && getVelocityY()<0) reverseY();
        if (getVelocityY()>0)
            setVelocity(0, (getPositionY()+SPEED_AT_TOP)*ACCELERATION);
        else
            setVelocity(0, -(getPositionY()+SPEED_AT_TOP)*ACCELERATION);
        super.update();
        if (getPositionY() >= height) m_isValid = false;
    }

}
