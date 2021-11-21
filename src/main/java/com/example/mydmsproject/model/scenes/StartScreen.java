package com.example.mydmsproject.model.scenes;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StartScreen extends BorderPane {

    private final Stage stage;
    private final int width;
    private final int height;
    private final int buttonWidth;
    private final int buttonHeight;
    private final Font titleFont;

    private final SettingScreen settings;
    private GameScreen game;
    private static Scene settingScene;

    private static final String TITLE = "BREAKOUT";
    private static final String START = "Start!";
    private static final String SETTINGS = "Settings";

    public StartScreen(Stage stage, int WIDTH, int HEIGHT) {
        this.stage = stage;
        this.width = WIDTH;
        this.height = HEIGHT;
        buttonWidth = 100;
        buttonHeight = 30;
        titleFont = Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30);

        settings = new SettingScreen(stage, WIDTH, HEIGHT);
        settingScene = new Scene(settings, WIDTH, HEIGHT);

        setUp();
    }

    private void setUp() {
        StackPane menu = new StackPane();

        Text title = new Text();
        Button start = new Button();
        Button settings = new Button();

        title.setText(TITLE);
        start.setText(START);
        settings.setText(SETTINGS);

        title.setFont(titleFont);

        start.setPrefSize(buttonWidth, buttonHeight);
        settings.setPrefSize(buttonWidth, buttonHeight);

        start.setOnAction(e -> {
            game = new GameScreen(stage, width, height);
        });
        settings.setOnAction(e -> {
            this.settings.setLastScene(stage.getScene());
            stage.setScene(settingScene);
        });

        menu.getChildren().addAll(title, start, settings);
        menu.setMaxSize(width,height/2.0);

        StackPane.setAlignment(title, Pos.TOP_CENTER);
        StackPane.setAlignment(start, Pos.CENTER);
        StackPane.setAlignment(settings, Pos.BOTTOM_CENTER);

        this.setCenter(menu);
    }

}
