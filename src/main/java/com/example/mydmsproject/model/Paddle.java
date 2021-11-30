package com.example.mydmsproject.model;

public class Paddle extends Sprite {

    private double moveSpeed;
    private final int windowWidth;
    private int moveControl;

    public Paddle(double moveSpeed, int screenWidth) {
        setImage("file:src/main/resources/com/example/mydmsproject/Paddle.png");
        this.moveSpeed = moveSpeed;
        this.windowWidth = screenWidth;
        moveControl = 1;
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

    public int getMoveControl() {
        return moveControl;
    }

    public void setMoveSpeed(double moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public void setMoveControl(int moveControl) {
        this.moveControl = moveControl;
    }

    public void setPositionX(double x) {
        if (x >= 0 && x+getWidth() <= windowWidth)
            setPosition(x, getPositionY());
    }

}
