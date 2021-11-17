package com.example.mydmsproject.model.scenes;

import com.example.mydmsproject.model.actors.Wall;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GameScreen extends BorderPane {

    private final Stage stage;
    private final int width;
    private final int height;
    private Wall wall;

    public GameScreen(Stage stage, int WIDTH, int HEIGHT) {
        this.stage = stage;
        this.width = WIDTH;
        this.height = HEIGHT;

        setUp();
    }

    private void setUp() {
        Pane game = new Pane();

        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        wall = new Wall(width, height, stage, gc);

        game.getChildren().add(canvas);

        this.setCenter(game);
    }

    public void begin() {
        wall.begin();
    }
}
