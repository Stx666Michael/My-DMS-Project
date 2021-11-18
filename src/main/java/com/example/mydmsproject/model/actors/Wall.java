package com.example.mydmsproject.model.actors;

import com.example.mydmsproject.controller.GameController;
import com.example.mydmsproject.view.GameRenderer;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class Wall {

    private final int width;
    private final int height;
    private final Ball ball;
    private final Paddle player;
    private final Stage stage;
    private int speedBound;

    public Wall(int WIDTH, int HEIGHT, Stage stage, GraphicsContext gc) {
        width = WIDTH;
        height = HEIGHT;
        this.stage = stage;
        speedBound = 5;

        ball = new Ball();
        player = new Paddle();

        ball.setPosition((width-ball.getWidth())/2.0, height-ball.getHeight()-player.getHeight());
        player.setPosition((width-player.getWidth())/2.0, height- player.getHeight());

        double speedX, speedY;
        speedX = (Math.random()-1)*speedBound;
        speedY = Math.sqrt(Math.pow(speedBound,2) - Math.pow(speedX,2));
        speedY = -speedY;

        ball.setVelocity(speedX, speedY);

        GameRenderer renderer = new GameRenderer(width, height, ball, player, gc);
    }

    public void begin() {
        GameController controller = new GameController(width, height, stage, ball, player);
    }

}
