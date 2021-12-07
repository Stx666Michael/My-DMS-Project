package com.example.mydmsproject.model;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

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

    public int getMoveControl() {
        return m_moveControl;
    }

    public double getLastPositionX() {
        return m_lastPositionX;
    }

    public boolean isBuff2() {
        return m_isBuff2;
    }

    public void setLastPositionX(double lastPositionX) {
        m_lastPositionX = lastPositionX;
    }

    public void setMoveSpeed(double moveSpeed) {
        m_moveSpeed = moveSpeed;
    }

    public void setMoveControl(int moveControl) {
        m_moveControl = moveControl;
    }

    public void setPositionX(double x) {
        if (x >= 0 && x+getWidth() <= m_windowWidth)
            setPosition(x, getPositionY());
    }

    public Paddle(double moveSpeed, int screenWidth) {
        setImage(m_path + "mydmsproject/Paddle.png");
        //final int KEYBOARD = 1;
        final int MOUSE = 2;
        m_moveSpeed = moveSpeed;
        m_windowWidth = screenWidth;
        m_moveControl = MOUSE;
    }

    public void moveLeft() {
        if (getPositionX()- m_moveSpeed >= 0) {
            setVelocity(-m_moveSpeed, 0);
            update();
        }
    }

    public void moveRight() {
        if (getPositionX()+getWidth()+ m_moveSpeed <= m_windowWidth) {
            setVelocity(m_moveSpeed, 0);
            update();
        }
    }

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

    public void removeBuff() {
        setImage(m_path + "mydmsproject/Paddle.png");
        m_lightning.updateOffset(this);
        m_isBuff1 = false;
        m_isBuff2 = false;
        m_buff1Timer = 0;
        m_buff2Timer = 0;
    }

    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
        if (m_isBuff2) m_lightning.render(gc);
    }

}
