package com.example.mydmsproject.model.actors;

import com.example.mydmsproject.controller.GameController;
import com.example.mydmsproject.model.scenes.EndScreen;
import com.example.mydmsproject.model.scenes.GameScreen;
import com.example.mydmsproject.model.scenes.SettingScreen;
import com.example.mydmsproject.view.GameRenderer;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

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
    private static Scene gameScene;
    private final GameScreen game;

    public Wall(int WIDTH, int HEIGHT, Stage stage, GameScreen game, GraphicsContext gc) {
        width = WIDTH;
        height = HEIGHT;
        this.game = game;
        ballSpeedBound = 2;
        playerSpeedBound = 2;
        upSpaceHeight = height / 10;

        ball = new Ball(ballCount);
        player = new Paddle(playerSpeedBound, width);
        initializeBallPlayer();

        bricks = makeBricks(1);

        gameScene = new Scene(game, width, height);
        stage.setScene(gameScene);

        GameController controller = new GameController(width, height, this, stage, ball, player, bricks);
        GameRenderer renderer = new GameRenderer(width, height, ball, player, bricks, gc);
    }

    public Scene getSettingScene() {
        return game.getSettingScene();
    }

    public Scene getEndScene() {
        return game.getEndScene();
    }

    public SettingScreen getSettings() {
        return game.getSettings();
    }

    public EndScreen getEnd() {
        return game.getEnd();
    }

    public void resetGame() {
        ball.reset();
        ArrayList<Brick> tmp = makeBricks(1);
        bricks.removeAll(bricks);
        bricks.addAll(tmp);
    }

    public void initializeBallPlayer() {
        ball.initialize(ballSpeedBound);
        ball.setPosition((width-ball.getWidth())/2.0, height-ball.getHeight()-player.getHeight()*2);
        player.setPosition((width-player.getWidth())/2.0, height-player.getHeight()*2);
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
