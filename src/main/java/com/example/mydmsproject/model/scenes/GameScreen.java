package com.example.mydmsproject.model.scenes;

import com.example.mydmsproject.model.actors.Wall;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GameScreen extends BorderPane {

    private Wall wall;
    private final Stage stage;
    private final SettingScreen settings;
    private final EndScreen end;
    private final Scene endScene;
    private final int width;
    private final int height;

    public GameScreen(Stage stage, SettingScreen settings, int WIDTH, int HEIGHT) {
        this.stage = stage;
        this.settings = settings;
        this.width = WIDTH;
        this.height = HEIGHT;

        end = new EndScreen(stage, this, WIDTH, HEIGHT);
        endScene = new Scene(end, WIDTH, HEIGHT);

        setUp();
    }

    private void setUp() {
        Pane game = new Pane();

        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        game.getChildren().add(canvas);
        this.setCenter(game);

        wall = new Wall(width, height, stage, this, gc);
    }

    public Scene getSettingScene() {
        return settings.getScene();
    }

    public Scene getEndScene() {
        return endScene;
    }

    public SettingScreen getSettings() {
        return settings;
    }

    public EndScreen getEnd() {
        return end;
    }

    public Wall getWall() {
        return wall;
    }
}
