package com.example.mydmsproject.controller.old;

import com.example.mydmsproject.model.old.Wall;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameBoard extends JComponent implements KeyListener,MouseListener,MouseMotionListener {

    private final int DEF_WIDTH = 600;
    private final int DEF_HEIGHT = 450;

    private final Timer gameTimer;

    private final Wall wall;

    private String message;

    private boolean showStartScreen;
    private boolean showSettingMenu;
    private boolean showPauseMenu;

    private final DebugConsole debugConsole;

    public GameBoard(JFrame owner){
        super();

        showStartScreen = true;
        showSettingMenu = false;
        showPauseMenu = false;

        this.initialize();
        message = "Press SPACE to start";
        wall = new Wall(new Rectangle(0,0,DEF_WIDTH,DEF_HEIGHT),30,3,6/2.0,new Point(300,430));

        debugConsole = new DebugConsole(owner,wall,this);
        //initialize the first level
        wall.nextLevel();

        gameTimer = new Timer(5,e -> step());
    }

    public void initialize(){
        this.setPreferredSize(new Dimension(DEF_WIDTH,DEF_HEIGHT));
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(this);
    }

    public void step() {
        wall.move();
        wall.findImpacts();
        message = String.format("Bricks: %d Balls %d",wall.getBrickCount(),wall.getBallCount());
        if(wall.isBallLost()){
            if(wall.ballEnd()){
                wall.wallReset();
                message = "Game over";
            }
            wall.ballReset();
            gameTimer.stop();
        }
        else if(wall.isDone()){
            if(wall.hasLevel()){
                message = "Go to Next Level";
                gameTimer.stop();
                wall.ballReset();
                wall.wallReset();
                wall.nextLevel();
            }
            else{
                message = "ALL WALLS DESTROYED";
                gameTimer.stop();
            }
        }
    }

    public int getHeight() {
        return DEF_HEIGHT;
    }

    public int getWidth() {
        return DEF_WIDTH;
    }

    public String getMessage() {
        return message;
    }

    public Wall getWall() {
        return wall;
    }

    public boolean isShowStartScreen() {
        return showStartScreen;
    }

    public boolean isShowSettingMenu() {
        return showSettingMenu;
    }

    public boolean isShowPauseMenu() {
        return showPauseMenu;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code=e.getKeyCode();
        if(code==KeyEvent.VK_LEFT){
            wall.getPlayer().moveLeft();
        }
        if(code==KeyEvent.VK_RIGHT){
            wall.getPlayer().movRight();
        }
        if(code==KeyEvent.VK_SPACE){
            if(!showPauseMenu && !showStartScreen && !showSettingMenu)
                if(gameTimer.isRunning())
                    gameTimer.stop();
                else
                    gameTimer.start();
        }
        if(code==KeyEvent.VK_ESCAPE){
            if(!showStartScreen && !showSettingMenu){
                showPauseMenu = !showPauseMenu;
                gameTimer.stop();
            }
        }
        if(code==KeyEvent.VK_F1){
            if(e.isAltDown() && e.isShiftDown())
                debugConsole.setVisible(true);
        }
        if(showStartScreen) {
            if(code==KeyEvent.VK_B)
                showStartScreen = false;
        }
        if(showStartScreen || showPauseMenu) {
            if(code==KeyEvent.VK_S){
                showStartScreen = false;
                showPauseMenu = false;
                showSettingMenu = true;
            }
        }
        if(code==KeyEvent.VK_C)
            showSettingMenu = false;
        if(showPauseMenu) {
            if(code==KeyEvent.VK_R){
                message = "Restarting Game...";
                wall.ballReset();
                wall.wallReset();
                showPauseMenu = false;
            }
            if(code==KeyEvent.VK_Q){
                System.exit(0);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        wall.getPlayer().stop();
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }

    public void onLostFocus(){
        gameTimer.stop();
        message = "Focus Lost";
    }

}
