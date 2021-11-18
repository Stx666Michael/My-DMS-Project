package com.example.mydmsproject.model.actors;

public class Paddle extends Sprite {

    private int moveSpeed = 5;

    public Paddle() {
        setImage("file:src/main/resources/com/example/mydmsproject/Paddle.png");
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
