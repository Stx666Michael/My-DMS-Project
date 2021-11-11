package code.view;

import code.controller.GameBoard;
import code.model.Ball;
import code.model.Brick;
import code.model.Paddle;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.util.Timer;
import java.util.TimerTask;

public class GameRenderer extends JComponent {

    private final GameBoard gameBoard;

    private final String START = "Start (S)";
    private final String CONTINUE = "Continue (Esc)";
    private final String RESTART = "Restart (R)";
    private final String EXIT = "Exit (Q)";
    private final String PAUSE = "Pause Menu";

    private final Color MENU_COLOR = new Color(0,255,0);

    private final Color BG_COLOR = Color.WHITE;

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

        if(gameBoard.isShowPauseMenu()) {
            drawMenu(g2d);
        }

        if(gameBoard.isShowStartScreen()) {
            drawStartScreen(g2d);
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

    public void drawMenu(Graphics2D g2d){
        obscureGameBoard(g2d);
        drawPauseMenu(g2d);
    }

    public void obscureGameBoard(Graphics2D g2d){

        Composite tmp = g2d.getComposite();
        Color tmpColor = g2d.getColor();

        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.55f);
        g2d.setComposite(ac);

        g2d.setColor(Color.BLACK);
        g2d.fillRect(0,0, gameBoard.getWidth(), gameBoard.getHeight());

        g2d.setComposite(tmp);
        g2d.setColor(tmpColor);
    }

    public void drawStartScreen(Graphics2D g2d) {
        obscureGameBoard(g2d);

        g2d.setFont(gameBoard.getMenuFont());
        g2d.setColor(MENU_COLOR);

        if(gameBoard.getStrLen() == 0){
            FontRenderContext frc = g2d.getFontRenderContext();
            gameBoard.setStrLen(gameBoard.getMenuFont().getStringBounds(START,frc).getBounds().width);
        }

        int x = (gameBoard.getWidth() - gameBoard.getStrLen()) / 2;
        int y = gameBoard.getHeight() / 2;

        g2d.drawString(START,x,y);
    }

    public void drawPauseMenu(Graphics2D g2d){
        Font tmpFont = g2d.getFont();
        Color tmpColor = g2d.getColor();

        g2d.setFont(gameBoard.getMenuFont());
        g2d.setColor(MENU_COLOR);

        if(gameBoard.getStrLen() == 0){
            FontRenderContext frc = g2d.getFontRenderContext();
            gameBoard.setStrLen(gameBoard.getMenuFont().getStringBounds(PAUSE,frc).getBounds().width);
        }

        int x = (gameBoard.getWidth() - gameBoard.getStrLen()) / 2;
        int y = gameBoard.getHeight() / 10;

        g2d.drawString(PAUSE,x,y);

        x = gameBoard.getWidth() / 8;
        y = gameBoard.getHeight() / 4;

        if(gameBoard.getContinueButtonRect() == null){
            FontRenderContext frc = g2d.getFontRenderContext();
            gameBoard.setContinueButtonRect(gameBoard.getMenuFont().getStringBounds(CONTINUE,frc).getBounds());
            gameBoard.getContinueButtonRect().setLocation(x,y-gameBoard.getContinueButtonRect().height);
        }

        g2d.drawString(CONTINUE,x,y);

        y *= 2;

        if(gameBoard.getRestartButtonRect() == null){
            gameBoard.setRestartButtonRect((Rectangle) gameBoard.getContinueButtonRect().clone());
            gameBoard.getRestartButtonRect().setLocation(x,y-gameBoard.getRestartButtonRect().height);
        }

        g2d.drawString(RESTART,x,y);

        y *= 3.0/2;

        if(gameBoard.getExitButtonRect() == null){
            gameBoard.setExitButtonRect((Rectangle) gameBoard.getContinueButtonRect().clone());
            gameBoard.getExitButtonRect().setLocation(x,y-gameBoard.getExitButtonRect().height);
        }

        g2d.drawString(EXIT,x,y);

        g2d.setFont(tmpFont);
        g2d.setColor(tmpColor);
    }

}
