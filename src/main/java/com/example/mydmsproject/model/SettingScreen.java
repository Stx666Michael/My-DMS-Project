package com.example.mydmsproject.model;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SettingScreen extends BorderPane {

    private Stage stage;
    private final int width;
    private final int height;
    private final int buttonWidth;
    private final int buttonHeight;
    private final Font titleFont;
    private static final String TITLE = "HAHA";
    private static final String START = "Start!";
    private static final String SETTINGS = "Settings";

    public SettingScreen(Stage stage, int WIDTH, int HEIGHT) {
        this.stage = stage;
        this.width = WIDTH;
        this.height = HEIGHT;
        buttonWidth = 100;
        buttonHeight = 30;
        titleFont = Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30);
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

        menu.getChildren().addAll(title, start, settings);
        menu.setMaxSize(width/2.0,height/2.0);

        StackPane.setAlignment(title, Pos.TOP_LEFT);
        StackPane.setAlignment(start, Pos.CENTER_LEFT);
        StackPane.setAlignment(settings, Pos.BOTTOM_LEFT);

        this.setCenter(menu);
    }

}