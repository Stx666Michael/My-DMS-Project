package com.example.mydmsproject.model;

/**
 * A Ball class, hide within a brick and appear in a probability
 * when the brick breaks, fall with acceleration.
 * There are two kinds of Buff, hide within different Bricks. They cannot
 * bounce back, and disappear when falling out of the stage. When the
 * Paddle touches a Buff, it becomes larger or carries "Lightning" for
 * some time, these two effects can be superimposed.
 * @author Tianxiang Song
 */
public class Buff extends Ball {

    private final int m_type;

    private boolean m_isValid = true;

    /**
     * Get the type of itself.
     * @return the type of itself
     */
    public int getType() {
        return m_type;
    }

    /**
     * Get a boolean value indicates if this buff is used or falling out.
     * @return a boolean value indicates if this buff is used or falling out
     */
    public boolean isValid() {
        return m_isValid;
    }

    /**
     * Set the boolean value indicates if this buff is used or falling out.
     * @param isValid the boolean value to set
     */
    public void setValid(boolean isValid) {
        m_isValid = isValid;
    }

    /**
     * Default class constructor, initialize a buff of specified type
     * with ballCount=0.
     * @param type the type of this buff
     */
    public Buff(int type) {
        m_type = type;
        setImage("file:src/main/resources/com/example/mydmsproject/" +
                "Buff" + type + ".png");
    }

    /**
     * Update its position with specified acceleration.
     * @param height the height of game scene
     */
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
