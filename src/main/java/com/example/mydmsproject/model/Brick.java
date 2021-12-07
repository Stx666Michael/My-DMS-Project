package com.example.mydmsproject.model;

import javafx.geometry.Rectangle2D;

import java.util.ArrayList;
import java.util.Random;

public class Brick extends Sprite {

    private final int m_score;
    private final Rectangle2D m_up;
    private final Rectangle2D m_down;
    private final Rectangle2D m_left;
    private final Rectangle2D m_right;

    private boolean m_isBonusBall = false;
    private boolean m_isBuff1 = false;
    private boolean m_isBuff2 = false;

    public int getScore() {
        return m_score;
    }

    public boolean isBonusBall() {
        return m_isBonusBall;
    }

    public boolean isBuff1() {
        return m_isBuff1;
    }

    public boolean isBuff2() {
        return m_isBuff2;
    }

    public Brick(int type, int positionX, int positionY) {
        final int BRICK_SCORE_BOUND = 4;
        final int NUM_BRICKS_PER_BUFF = 3;

        m_score = BRICK_SCORE_BOUND - type;
        setImage("file:src/main/resources/com/example/mydmsproject/" +
                "Brick" + type + ".png");
        setPosition(positionX, positionY);
        m_up = new Rectangle2D
                (getPositionX(), getPositionY(), getWidth(), 1);
        m_down = new Rectangle2D
                (getPositionX(), getPositionY()+getHeight(), getWidth(), 1);
        m_left = new Rectangle2D
                (getPositionX(), getPositionY(), 1, getHeight());
        m_right = new Rectangle2D
                (getPositionX()+getWidth(), getPositionY(), 1, getHeight());

        if (new Random().nextInt(NUM_BRICKS_PER_BUFF) == 0)
            switch (type) {
                case 1 -> m_isBuff1 = true;
                case 2 -> m_isBonusBall = true;
                case 3 -> m_isBuff2 = true;
            }
    }

    public void findImpact(Ball ball, ArrayList<Brick> bricks) {
        if (m_up.intersects(ball.getBoundary())) {
            if (ball.getVelocityY()>0 && ball.notLightning()) ball.reverseY();
            ball.removeBrick(bricks, this, 1);
        }
        else if (m_down.intersects(ball.getBoundary())) {
            if (ball.getVelocityY()<0 && ball.notLightning()) ball.reverseY();
            ball.removeBrick(bricks, this, 0);
        }
        else if (m_left.intersects(ball.getBoundary())) {
            if (ball.getVelocityX()>0 && ball.notLightning()) ball.reverseX();
            ball.removeBrick(bricks, this, 1);
        }
        else if (m_right.intersects(ball.getBoundary())) {
            if (ball.getVelocityX()<0 && ball.notLightning()) ball.reverseX();
            ball.removeBrick(bricks, this, 1);
        }
    }

    public BonusBall makeBonusBall(int initialSpeed) {
        BonusBall bonus = new BonusBall();
        makeIt(bonus, initialSpeed);
        return bonus;
    }

    public Buff makeBuff(int initialSpeed, int type) {
        Buff buff = new Buff(type);
        makeIt(buff, initialSpeed);
        return buff;
    }

    private void makeIt(Ball ball, int initialSpeed) {
        int offsetX = (int) (getWidth()-ball.getWidth()) / 2;
        int offsetY = (int) (getHeight()-ball.getHeight()) / 2;
        ball.setPosition(getPositionX()+offsetX, getPositionY()+offsetY);
        ball.setVelocity(0, initialSpeed);
    }

}
