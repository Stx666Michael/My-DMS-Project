package com.example.mydmsproject.model.actors;

public class Ball extends Sprite {

    public Ball(int speedBound) {
        setImage("file:src/main/resources/com/example/mydmsproject/Ball.png");
        double speedX, speedY;
        speedX = (Math.random()-1)*speedBound;
        speedY = Math.sqrt(Math.pow(speedBound,2) - Math.pow(speedX,2));
        speedY = -speedY;
        setVelocity(speedX, speedY);
    }

    public void reverseX() {
        setVelocity(-getVelocityX(), getVelocityY());
    }

    public void reverseY() {
        setVelocity(getVelocityX(), -getVelocityY());
    }
}
