package com.example.mydmsproject.model;

/**
 * A Ball class, hide within a brick and appear in a probability
 * when the brick breaks.
 * Like the main Ball, BonusBall can bounce back, break bricks and be lost.
 * However, losing BonusBalls will not end the game, they only help to
 * win the game.
 */
public class BonusBall extends Ball {

    /**
     * Default class constructor, initialize a bonusBall with ballCount=0.
     * @param scenes the model class that stores all scenes and game elements
     */
    public BonusBall(Scenes scenes) {
        super(scenes);
        setImage("file:src/main/resources/com/example/mydmsproject/" +
                "Ball-bonus.png");
    }

    /**
     * Update its position with specified acceleration.
     */
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
