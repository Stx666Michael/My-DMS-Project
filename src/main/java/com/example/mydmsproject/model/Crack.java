package com.example.mydmsproject.model;

/**
 * A Sprite class, appears in red and green bricks when the brick is hit
 * by a ball.
 */
public class Crack extends Sprite {

    /**
     * Default class constructor, initialize a Crack with specified type and
     * its Brick owner.
     * @param type the type of this crack
     * @param brick the crack's owner
     */
    public Crack(int type, Brick brick) {
        setPosition(brick.getPositionX(), brick.getPositionY());
        setImage("file:src/main/resources/com/example/mydmsproject/" +
                "Crack" + type + ".png");
    }

    /**
     * Set the type of this crack.
     * @param type the type of crack to change to
     */
    public void setType(int type) {
        setImage("file:src/main/resources/com/example/mydmsproject/" +
                "Crack" + type + ".png");
    }
}
