package com.example.mydmsproject.model;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Random;

/**
 * A Sprite class implements the main game element Brick, store Crack objects.
 * @author Tianxiang Song - modified
 */
public class Brick extends Sprite {

    private final int m_score;
    private final Rectangle2D m_up;
    private final Rectangle2D m_down;
    private final Rectangle2D m_left;
    private final Rectangle2D m_right;

    private boolean m_isBonusBall = false;
    private boolean m_isBuff1 = false;
    private boolean m_isBuff2 = false;
    private int m_leftHitCount;
    private Crack m_crack;

    /**
     * Get breaking score of this brick.
     * @return the breaking score of this brick
     */
    public int getScore() {
        return m_score;
    }

    /**
     * Get a boolean value indicates if this brick has a BonusBall.
     * @return a boolean value indicates if this brick has a BonusBall
     * @see BonusBall
     */
    public boolean isBonusBall() {
        return m_isBonusBall;
    }

    /**
     * Get a boolean value indicates if this brick has a type 1 Buff.
     * @return a boolean value indicates if this brick has a type 1 Buff
     * @see Buff
     */
    public boolean isBuff1() {
        return m_isBuff1;
    }

    /**
     * Get a boolean value indicates if this brick has a type 2 Buff.
     * @return a boolean value indicates if this brick has a type 2 Buff
     * @see Buff
     */
    public boolean isBuff2() {
        return m_isBuff2;
    }

    /**
     * Default class constructor, set type, score and position of this brick,
     * assign Buff/BonusBall to it with specific probability and Crack with
     * specific type.
     * @param type the type of this brick
     * @param positionX the X position of this brick
     * @param positionY the Y position of this brick
     */
    public Brick(int type, int positionX, int positionY) {
        final int BRICK_SCORE_BOUND = 4;
        final int NUM_BRICKS_PER_BUFF = 3;

        m_score = BRICK_SCORE_BOUND - type;
        m_leftHitCount = m_score;
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
        if (type == 1) m_crack = new Crack(type, this);
        else if (type == 2) m_crack = new Crack(type, this);
    }

    /**
     * Check if a ball/bonusBall intersects with any bricks.
     * @param ball the ball/bonusBall to be checked
     * @param bricks the list of bricks
     * @see Ball
     * @see BonusBall
     */
    public void findImpact(Ball ball, ArrayList<Brick> bricks) {
        if (m_up.intersects(ball.getBoundary())) {
            if (ball.getVelocityY()>0 && ball.notLightning()) ball.reverseY();
            m_leftHitCount--;
            if (m_leftHitCount <= 0) ball.removeBrick(bricks, this, 1);
        }
        else if (m_down.intersects(ball.getBoundary())) {
            if (ball.getVelocityY()<0 && ball.notLightning()) ball.reverseY();
            m_leftHitCount--;
            if (m_leftHitCount <= 0) ball.removeBrick(bricks, this, 0);
        }
        else if (m_left.intersects(ball.getBoundary())) {
            if (ball.getVelocityX()>0 && ball.notLightning()) ball.reverseX();
            m_leftHitCount--;
            if (m_leftHitCount <= 0) ball.removeBrick(bricks, this, 1);
        }
        else if (m_right.intersects(ball.getBoundary())) {
            if (ball.getVelocityX()<0 && ball.notLightning()) ball.reverseX();
            m_leftHitCount--;
            if (m_leftHitCount <= 0) ball.removeBrick(bricks, this, 1);
        }
        if (m_score == 3 && m_leftHitCount == 1) m_crack.setType(2);
    }

    /**
     * Create an instance of BonusBall.
     * @param initialSpeed the initial speed of BonusBall
     * @param scenes the model class that stores all scenes and game elements
     * @return an instance of BonusBall
     * @see BonusBall
     */
    public BonusBall makeBonusBall(int initialSpeed, Scenes scenes) {
        BonusBall bonus = new BonusBall(scenes);
        initialize(bonus, initialSpeed);
        return bonus;
    }

    /**
     * Create an instance of Buff of specific type.
     * @param initialSpeed the initial speed of Buff
     * @param type the type of Buff
     * @return an instance of Buff of specific type
     * @see Buff
     */
    public Buff makeBuff(int initialSpeed, int type) {
        Buff buff = new Buff(type);
        initialize(buff, initialSpeed);
        return buff;
    }

    /**
     * Initialize a BonusBall/Buff, set its initial position and speed.
     * @param ball the BonusBall/Buff to be initialized
     * @param initialSpeed the initial speed of BonusBall/Buff
     */
    private void initialize(Ball ball, int initialSpeed) {
        int offsetX = (int) (getWidth()-ball.getWidth()) / 2;
        int offsetY = (int) (getHeight()-ball.getHeight()) / 2;
        ball.setPosition(getPositionX()+offsetX, getPositionY()+offsetY);
        ball.setVelocity(0, initialSpeed);
    }

    /**
     * Render itself and its associated Crack.
     * @param gc the GraphicsContext of Canvas in the game scene
     * @see Crack
     */
    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
        if (m_crack!=null && m_leftHitCount<m_score) m_crack.render(gc);
    }

}
