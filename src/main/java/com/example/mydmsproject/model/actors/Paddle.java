package com.example.mydmsproject.model.actors;

public class Paddle extends Sprite {

    private int moveSpeed;
    private int windowWidth;

    public Paddle(int moveSpeed, int screenWidth) {
        setImage("file:src/main/resources/com/example/mydmsproject/Paddle.png");
        this.moveSpeed = moveSpeed;
        this.windowWidth = screenWidth;
    }

    public void moveLeft() {
        if (getPositionX()-moveSpeed >= 0) {
            setVelocity(-moveSpeed, 0);
            update();
        }
    }

    public void moveRight() {
        if (getPositionX()+getWidth()+moveSpeed <= windowWidth) {
            setVelocity(moveSpeed, 0);
            update();
        }
    }

    public void setPositionX(double x) {
        if (x >= 0 && x+getWidth() <= windowWidth)
            setPosition(x, getPositionY());
    }

}
