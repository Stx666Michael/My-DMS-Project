package com.example.mydmsproject.model.actors;

public class Paddle extends Sprite {

    public Paddle() {
        this.setImage("file:src/main/resources/com/example/mydmsproject/Paddle.png");
    }

    public void moveLeft() {
        setVelocity(-2, 0);
        update();
    }

    public void moveRight() {
        setVelocity(2, 0);
        update();
    }
}
