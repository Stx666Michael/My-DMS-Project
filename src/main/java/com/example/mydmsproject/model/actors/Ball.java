package com.example.mydmsproject.model.actors;

public class Ball extends Sprite {

    public Ball() {
        setImage("file:src/main/resources/com/example/mydmsproject/Ball.png");
    }

    public void reverseX() {
        setVelocity(-getVelocityX(), getVelocityY());
    }

    public void reverseY() {
        setVelocity(getVelocityX(), -getVelocityY());
    }
}
