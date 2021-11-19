package com.example.mydmsproject.model.actors;

import com.example.mydmsproject.controller.GameController;
import com.example.mydmsproject.view.GameRenderer;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Wall {

    private final int width;
    private final int height;
    private final Ball ball;
    private final Paddle player;
    private ArrayList<Brick> bricks;
    private final Stage stage;
    private int ballSpeedBound;
    private int playerSpeedBound;
    private int upSpaceHeight;

    public Wall(int WIDTH, int HEIGHT, Stage stage, GraphicsContext gc) {
        width = WIDTH;
        height = HEIGHT;
        this.stage = stage;
        ballSpeedBound = 2;
        playerSpeedBound = 2;
        upSpaceHeight = height / 10;

        ball = new Ball(ballSpeedBound);
        player = new Paddle(playerSpeedBound, width);

        ball.setPosition((width-ball.getWidth())/2.0, height-ball.getHeight()-player.getHeight()*2);
        player.setPosition((width-player.getWidth())/2.0, height-player.getHeight()*2);
        bricks = makeBricks(1);

        GameRenderer renderer = new GameRenderer(width, height, ball, player, bricks, gc);
    }

    public void begin() {
        GameController controller = new GameController(width, height, stage, ball, player, bricks);
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
