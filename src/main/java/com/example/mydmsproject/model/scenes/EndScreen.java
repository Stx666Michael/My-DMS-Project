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

public class EndScreen extends BorderPane {

    private Text score;
    private final GameScreen game;
    private final Stage stage;
    private final int width;
    private final int height;
    private final int buttonWidth;
    private final int buttonHeight;
    private final Font titleFont;

    private static final String TITLE = "Game Over";
    private static final String SCORE = "Your Score: ";
    private static final String REPLAY = "Try Again!";
    private static final String QUIT = "Quit Game";

    public EndScreen(Stage stage, GameScreen game, int WIDTH, int HEIGHT) {
        this.stage = stage;
        this.game = game;
        this.width = WIDTH;
        this.height = HEIGHT;

        buttonWidth = 100;
        buttonHeight = 30;
        titleFont = Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30);

        setUp();
    }

    private void setUp() {
        StackPane end = new StackPane();

        Text title = new Text();
        score = new Text();
        Button replay = new Button();
        Button quit = new Button();

        title.setText(TITLE);
        score.setText(SCORE + this.score);
        replay.setText(REPLAY);
        quit.setText(QUIT);

        title.setFont(titleFont);

        replay.setPrefSize(buttonWidth, buttonHeight);
        quit.setPrefSize(buttonWidth, buttonHeight);

        replay.setOnAction(e -> {
            stage.setScene(game.getScene());
            game.getWall().resetGame();
        });
        quit.setOnAction(e -> System.exit(0));

        end.getChildren().addAll(title, score, replay, quit);
        end.setMaxSize(width/2.0,height/2.0);

        StackPane.setAlignment(title, Pos.TOP_CENTER);
        StackPane.setAlignment(score, Pos.CENTER);
        StackPane.setAlignment(replay, Pos.BOTTOM_LEFT);
        StackPane.setAlignment(quit, Pos.BOTTOM_RIGHT);

        this.setCenter(end);
    }

    public void setScore(int score) {
        this.score.setText(SCORE + score);
    }

}
