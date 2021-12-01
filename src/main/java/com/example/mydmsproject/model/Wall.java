package com.example.mydmsproject.model;

import com.example.mydmsproject.controller.GameController;
import com.example.mydmsproject.view.GameRenderer;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class Wall {

    private final int width;
    private final int height;
    private final Ball ball;
    private final Paddle player;
    private ArrayList<Brick> bricks;
    private final int ballCount = 3;
    private double ballSpeedBound;
    private double playerSpeedBound;
    private int upSpaceHeight;
    private final GraphicsContext gc;

    public Wall(int WIDTH, int HEIGHT, GraphicsContext gc) {
        this.gc = gc;
        width = WIDTH;
        height = HEIGHT;
        ballSpeedBound = 2;
        playerSpeedBound = 2;
        upSpaceHeight = height / 10;

        ball = new Ball(ballCount);
        player = new Paddle(playerSpeedBound, width);
        initializeBallPlayer();

        bricks = makeBricks(1);
    }

    public void initializeGame(Scenes scenes) {
        new GameController(scenes);
        new GameRenderer(scenes, gc);
    }

    public void resetGame(int level) {
        ball.reset(level == 1);
        initializeBallPlayer();
        ArrayList<Brick> tmp = makeBricks(level);
        bricks.clear();
        bricks.addAll(tmp);
    }

    public void initializeBallPlayer() {
        ball.initialize(ballSpeedBound);
        player.removeBuff();
        ball.setPosition((width-ball.getWidth())/2.0, height-ball.getHeight()-player.getHeight()*2);
        player.setPosition((width-player.getWidth())/2.0, height-player.getHeight()*2);
    }

    public Ball getBall() {
        return ball;
    }

    public Paddle getPlayer() {
        return player;
    }

    public ArrayList<Brick> getBricks() {
        return bricks;
    }

    public int getScore() {
        return ball.getScore();
    }

    public void setBallSpeedBound(double ballSpeedBound) {
        this.ballSpeedBound = ballSpeedBound;
    }

    private ArrayList<Brick> makeBricks(int level) {
        ArrayList<Brick> bricks = new ArrayList<>();
        Brick brick = new Brick(1, 0, 0);
        int brickWidth = (int)brick.getWidth();
        int brickHeight = (int)brick.getHeight();
        int maxLineBricks = width / brickWidth;

        switch (level) {
            case 1 -> {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < maxLineBricks; j++) {
                        Brick tmp = new Brick(i+1, j*brickWidth, upSpaceHeight+i*brickHeight);
                        bricks.add(tmp);
                    }
                }
            }
            case 2 -> {
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < maxLineBricks; j++) {
                        Brick tmp = new Brick(i+1, j*brickWidth, upSpaceHeight+i*brickHeight);
                        bricks.add(tmp);
                    }
                }
            }

        }
        return bricks;
    }

}
