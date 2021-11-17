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

public class SettingScreen extends BorderPane {

    private Scene lastScene;
    private final Stage stage;
    private final int width;
    private final int height;
    private final int buttonWidth;
    private final int buttonHeight;
    private final Font titleFont;
    private static final String TITLE = "SETTINGS";
    private static final String THEME = "Theme Colour";
    private static final String SPEED = "Ball Speed";
    private static final String MOVE = "Move Control";
    private static final String CONFIRM = "CONFIRM";

    public SettingScreen(Stage stage, int WIDTH, int HEIGHT) {
        this.stage = stage;
        this.width = WIDTH;
        this.height = HEIGHT;

        buttonWidth = 100;
        buttonHeight = 30;

        titleFont = Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20);

        setUp();
    }

    private void setUp() {
        StackPane menu = new StackPane();

        Text title = new Text();
        Button theme = new Button();
        Button speed = new Button();
        Button move = new Button();
        Button confirm = new Button();

        title.setText(TITLE);
        theme.setText(THEME);
        speed.setText(SPEED);
        move.setText(MOVE);
        confirm.setText(CONFIRM);

        title.setFont(titleFont);

        theme.setPrefSize(buttonWidth, buttonHeight);
        speed.setPrefSize(buttonWidth, buttonHeight);
        move.setPrefSize(buttonWidth, buttonHeight);
        confirm.setPrefSize(buttonWidth, buttonHeight);

        confirm.setOnAction(e -> stage.setScene(lastScene));

        menu.getChildren().addAll(title, theme, speed, move, confirm);
        menu.setMaxSize(width/2.0,height/2.0);

        StackPane.setAlignment(title, Pos.TOP_CENTER);
        StackPane.setAlignment(theme, Pos.CENTER_LEFT);
        StackPane.setAlignment(speed, Pos.CENTER_RIGHT);
        StackPane.setAlignment(move, Pos.BOTTOM_LEFT);
        StackPane.setAlignment(confirm, Pos.BOTTOM_RIGHT);

        this.setCenter(menu);
    }

    public void setLastScene(Scene lastScene) {
        this.lastScene = lastScene;
    }
}