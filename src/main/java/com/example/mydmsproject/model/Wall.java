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
    private int ballSpeedBound;
    private int playerSpeedBound;
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
        new GameController(width, height, scenes, ball, player, bricks);
        new GameRenderer(width, height, ball, player, bricks, gc);
    }

    public void resetGame() {
        ball.reset();
        ArrayList<Brick> tmp = makeBricks(1);
        bricks.clear();
        bricks.addAll(tmp);
    }

    public void initializeBallPlayer() {
        ball.initialize(ballSpeedBound);
        ball.setPosition((width-ball.getWidth())/2.0, height-ball.getHeight()-player.getHeight()*2);
        player.setPosition((width-player.getWidth())/2.0, height-player.getHeight()*2);
    }

    public Paddle getPlayer() {
        return player;
    }

    public int getScore() {
        return ball.getScore();
    }

    private ArrayList makeBricks(int level) {
        ArrayList<Brick> bricks = new ArrayList<>();
        Brick brick = new Brick(1, 0, 0);
        int brickWidth = (int)brick.getWidth();
        int brickHeight = (int)brick.getHeight();
        int maxLineBricks = width / brickWidth + 1;

        switch (level) {
            case 1:
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < maxLineBricks; j++) {
                        Brick tmp = new Brick(i+1, j*brickWidth, upSpaceHeight+i*brickHeight);
                        bricks.add(tmp);
                    }
                }
                break;
        }
        return bricks;
    }

}
