package com.example.mydmsproject.model.actors;

public class Paddle extends Sprite {

    private int moveSpeed;

    public Paddle(int moveSpeed) {
        setImage("file:src/main/resources/com/example/mydmsproject/Paddle.png");
        this.moveSpeed = moveSpeed;
    }

    public void moveLeft() {
        setVelocity(-moveSpeed, 0);
        update();
    }

    public void moveRight() {
        setVelocity(moveSpeed, 0);
        update();
    }
}
