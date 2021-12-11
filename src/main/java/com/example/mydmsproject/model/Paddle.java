package com.example.mydmsproject.model;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

/**
 * A Sprite class implements the main game element Paddle, controlled by
 * keyboard or mouse.
 */
public class Paddle extends Sprite {

    private final int m_windowWidth;
    private final Lightning m_lightning = new Lightning("paddle");
    private final String m_path = "file:src/main/resources/com/example/";

    private int m_buff1Timer = 0;
    private int m_buff2Timer = 0;
    private int m_moveControl;
    private double m_moveSpeed;
    private double m_lastPositionX;
    private boolean m_isBuff1 = false;
    private boolean m_isBuff2 = false;

    /**
     * Get how user controls this paddle.
     * @return 1 if controlled by keyboard, 2 if controlled by mouse
     * @see com.example.mydmsproject.controller.GameController
     */
    public int getMoveControl() {
        return m_moveControl;
    }

    /**
     * Get the last X position of itself, used to compute speed when
     * controlled by mouse.
     * @return last X position of itself
     */
    public double getLastPositionX() {
        return m_lastPositionX;
    }

    /**
     * Get a boolean value indicates if the paddle has Buff2.
     * @return a boolean value indicates if the paddle has Buff2
     * @see Buff
     */
    public boolean isBuff2() {
        return m_isBuff2;
    }

    /**
     * Set the last X position of itself, used to compute speed when
     * controlled by mouse.
     * @param lastPositionX last X position of itself
     */
    public void setLastPositionX(double lastPositionX) {
        m_lastPositionX = lastPositionX;
    }

    /**
     * Set the move speed in keyboard control.
     * @param moveSpeed the move speed in keyboard control
     */
    public void setMoveSpeed(double moveSpeed) {
        m_moveSpeed = moveSpeed;
    }

    /**
     * Set how user controls this paddle.
     * @param moveControl 1 if controlled by keyboard, 2 if controlled by mouse
     * @see com.example.mydmsproject.controller.SettingController
     */
    public void setMoveControl(int moveControl) {
        m_moveControl = moveControl;
    }

    /**
     * Set X position of itself, used when controlled by mouse.
     * @param x the X position to be set
     */
    public void setPositionX(double x) {
        if (x >= 0 && x+getWidth() <= m_windowWidth)
            setPosition(x, getPositionY());
    }

    /**
     * Default class constructor, set initial move control and move speed.
     * @param moveSpeed initial move speed in keyboard control
     * @param windowWidth the width of game scene
     */
    public Paddle(double moveSpeed, int windowWidth) {
        setImage(m_path + "mydmsproject/Paddle.png");
        final int KEYBOARD = 1;
        //final int MOUSE = 2;
        m_moveSpeed = moveSpeed;
        m_windowWidth = windowWidth;
        m_moveControl = KEYBOARD;
    }

    /**
     * Move itself left according to moveSpeed.
     */
    public void moveLeft() {
        if (getPositionX()- m_moveSpeed >= 0) {
            setVelocity(-m_moveSpeed, 0);
            update();
        }
    }

    /**
     * Move itself right according to moveSpeed.
     */
    public void moveRight() {
        if (getPositionX()+getWidth()+ m_moveSpeed <= m_windowWidth) {
            setVelocity(m_moveSpeed, 0);
            update();
        }
    }

    /**
     * Check if this paddle intersects with any Buffs and update its
     * buff properties.
     * @param buffs the list of Buffs
     * @see Buff
     */
    public void findBuffImpacts(ArrayList<Buff> buffs) {
        Buff[] tmp = buffs.toArray(new Buff[0]);
        for (Buff buff : tmp) {
            if (buff.intersects(this)) {
                switch (buff.getType()) {
                    case 1 -> m_isBuff1 = true;
                    case 2 -> m_isBuff2 = true;
                }
                buff.setValid(false);
            }
            if (!buff.isValid()) buffs.remove(buff);
        }
        updateBuff();
    }

    /**
     * Act as a timer, if any buff exists in itself, clear it after a period.
     */
    private void updateBuff() {
        final int BUFF_TIMER_LENGTH = 1000;
        if (m_isBuff1) {
            if (m_buff1Timer == 0) {
                setImage(m_path + "mydmsproject/Paddle-big.png");
                m_lightning.updateOffset(this);
            }
            m_buff1Timer++;
            if (m_buff1Timer == BUFF_TIMER_LENGTH) {
                setImage(m_path + "mydmsproject/Paddle.png");
                m_lightning.updateOffset(this);
                m_isBuff1 = false;
                m_buff1Timer = 0;
            }
        }
        if (m_isBuff2) {
            if (m_buff2Timer < BUFF_TIMER_LENGTH) m_lightning.update(this);
            m_buff2Timer++;
            if (m_buff2Timer == BUFF_TIMER_LENGTH) {
                m_isBuff2 = false;
                m_buff2Timer = 0;
            }
        }
    }

    /**
     * Remove all the buff properties.
     * @see Buff
     * @see Lightning
     */
    public void removeBuff() {
        setImage(m_path + "mydmsproject/Paddle.png");
        m_lightning.updateOffset(this);
        m_isBuff1 = false;
        m_isBuff2 = false;
        m_buff1Timer = 0;
        m_buff2Timer = 0;
    }

    /**
     * Render itself and its associated Lightning.
     * @param gc the GraphicsContext of Canvas in the game scene
     * @see Lightning
     */
    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
        if (m_isBuff2) m_lightning.render(gc);
    }

}
