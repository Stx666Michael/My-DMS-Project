package com.example.mydmsproject.view;

import com.example.mydmsproject.controller.GameBoard;
import com.example.mydmsproject.model.Ball;
import com.example.mydmsproject.model.Brick;
import com.example.mydmsproject.model.Paddle;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.util.Timer;
import java.util.TimerTask;

public class GameRenderer extends JComponent {

    private final GameBoard gameBoard;

    private final String TITLE = "BREAKOUT";
    private final String BEGIN = "Begin (B)";
    private final String SETTINGS = "Settings (S)";
    private final String THEME = "Theme Colour";
    private final String SPEED = "Ball Speed";
    private final String MOVE = "Move Control";
    private final String CONFIRM = "Confirm (C)";
    private final String CONTINUE = "Continue (Esc)";
    private final String RESTART = "Restart (R)";
    private final String EXIT = "Exit (Q)";
    private final String PAUSE = "Pause Menu";

    private final Color MENU_COLOR = new Color(0,255,0);

    private final Color BG_COLOR = Color.WHITE;

    private final int TEXT_SIZE = 30;
    private final Font menuFont = new Font("Monospaced", Font.PLAIN, TEXT_SIZE);

    private int strLen = 0;

    public GameRenderer(GameBoard gameBoard) {
        this.gameBoard = gameBoard;

        this.setPreferredSize(new Dimension(gameBoard.getWidth(),gameBoard.getHeight()));

        Timer renderTimer = new Timer();
        renderTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                repaint();
            }
        }, 100, 10);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        clear(g2d);

        g2d.setColor(Color.BLUE);
        g2d.drawString(gameBoard.getMessage(),250,225);

        drawBall(gameBoard.getWall().getBall(),g2d);

        for(Brick b : gameBoard.getWall().getBricks())
            if(!b.isBroken())
                drawBrick(b,g2d);

        drawPlayer(gameBoard.getWall().getPlayer(),g2d);

        if(gameBoard.isShowStartScreen() || gameBoard.isShowSettingMenu() || gameBoard.isShowPauseMenu()) {
            obscureGameBoard(g2d);
        }

        if(gameBoard.isShowStartScreen()) {
            drawStartScreen(g2d);
        }

        if(gameBoard.isShowSettingMenu()) {
            drawSettingMenu(g2d);
        }

        if(gameBoard.isShowPauseMenu()) {
            drawPauseMenu(g2d);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    public void clear(Graphics2D g2d){
        Color tmp = g2d.getColor();
        g2d.setColor(BG_COLOR);
        g2d.fillRect(0,0,gameBoard.getWidth(),gameBoard.getHeight());
        g2d.setColor(tmp);
    }

    public void drawBrick(Brick brick,Graphics2D g2d){
        Color tmp = g2d.getColor();

        g2d.setColor(brick.getInnerColor());
        g2d.fill(brick.getBrick());

        g2d.setColor(brick.getBorderColor());
        g2d.draw(brick.getBrick());

        g2d.setColor(tmp);
    }

    public void drawBall(Ball ball, Graphics2D g2d){
        Color tmp = g2d.getColor();

        Shape s = ball.getBallFace();

        g2d.setColor(ball.getInnerColor());
        g2d.fill(s);

        g2d.setColor(ball.getBorderColor());
        g2d.draw(s);

        g2d.setColor(tmp);
    }

    public void drawPlayer(Paddle p, Graphics2D g2d){
        Color tmp = g2d.getColor();

        Shape s = p.getPaddleFace();
        g2d.setColor(Paddle.INNER_COLOR);
        g2d.fill(s);

        g2d.setColor(Paddle.BORDER_COLOR);
        g2d.draw(s);

        g2d.setColor(tmp);
    }

    public void obscureGameBoard(Graphics2D g2d){
        Composite tmp = g2d.getComposite();
        Color tmpColor = g2d.getColor();

        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.75f);
        g2d.setComposite(ac);

        g2d.setColor(Color.BLACK);
        g2d.fillRect(0,0, gameBoard.getWidth(), gameBoard.getHeight());

        g2d.setComposite(tmp);
        g2d.setColor(tmpColor);
    }

    public void drawStartScreen(Graphics2D g2d) {
        FontRenderContext frc = g2d.getFontRenderContext();

        g2d.setFont(menuFont);
        g2d.setColor(MENU_COLOR);

        strLen = menuFont.getStringBounds(TITLE,frc).getBounds().width;

        int x = (gameBoard.getWidth() - strLen) / 2;
        int y = gameBoard.getHeight() / 4;
        g2d.drawString(TITLE,x,y);

        strLen = menuFont.getStringBounds(SETTINGS,frc).getBounds().width;

        x = (gameBoard.getWidth() - strLen) / 2;
        y *= 2;
        g2d.drawString(SETTINGS,x,y);

        strLen = menuFont.getStringBounds(BEGIN,frc).getBounds().width;

        x = (gameBoard.getWidth() - strLen) / 2;
        y *= 1.5;
        g2d.drawString(BEGIN,x,y);
    }

    public void drawSettingMenu(Graphics2D g2d){
        g2d.setFont(menuFont);
        g2d.setColor(MENU_COLOR);

        int x = gameBoard.getWidth() / 8;
        int y = gameBoard.getHeight() / 5;
        g2d.drawString(THEME,x,y);

        y *= 2;
        g2d.drawString(SPEED,x,y);

        y *= 1.5;
        g2d.drawString(MOVE,x,y);

        y *= 4/3.0;
        g2d.drawString(CONFIRM,x,y);
    }

    public void drawPauseMenu(Graphics2D g2d){
        FontRenderContext frc = g2d.getFontRenderContext();

        g2d.setFont(menuFont);
        g2d.setColor(MENU_COLOR);

        strLen = menuFont.getStringBounds(PAUSE,frc).getBounds().width;

        int x = (gameBoard.getWidth() - strLen) / 2;
        int y = gameBoard.getHeight() / 6;
        g2d.drawString(PAUSE,x,y);

        x = gameBoard.getWidth() / 8;
        y *= 2;
        g2d.drawString(CONTINUE,x,y);

        y *= 1.5;
        g2d.drawString(SETTINGS,x,y);

        y *= 4/3.0;
        g2d.drawString(RESTART,x,y);

        y *= 1.25;
        g2d.drawString(EXIT,x,y);
    }

}
