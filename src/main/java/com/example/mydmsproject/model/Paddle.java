package com.example.mydmsproject.model;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class Paddle extends Sprite {

    private double moveSpeed;
    private final int windowWidth;
    private int moveControl;
    private boolean isBuff1 = false;
    private boolean isBuff2 = false;
    private int buff1Timer = 0;
    private int buff2Timer = 0;
    private final int buffTimerLength = 1000;
    private Lightning lightning = new Lightning("paddle");

    public Paddle(double moveSpeed, int screenWidth) {
        setImage("file:src/main/resources/com/example/mydmsproject/Paddle.png");
        this.moveSpeed = moveSpeed;
        this.windowWidth = screenWidth;
        moveControl = 1;
    }

    public void moveLeft() {
        if (getPositionX()-moveSpeed >= 0) {
            setVelocity(-moveSpeed, 0);
            update();
        }
    }

    public void moveRight() {
        if (getPositionX()+getWidth()+moveSpeed <= windowWidth) {
            setVelocity(moveSpeed, 0);
            update();
        }
    }

    public int getMoveControl() {
        return moveControl;
    }

    public boolean isBuff2() {
        return isBuff2;
    }

    public void setMoveSpeed(double moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public void setMoveControl(int moveControl) {
        this.moveControl = moveControl;
    }

    public void setPositionX(double x) {
        if (x >= 0 && x+getWidth() <= windowWidth)
            setPosition(x, getPositionY());
    }

    public void findBuffImpacts(ArrayList<Buff> buffs) {
        Buff[] tmp = buffs.toArray(new Buff[0]);
        for (Buff buff : tmp) {
            if (buff.intersects(this)) {
                switch (buff.getType()) {
                    case 1 -> isBuff1 = true;
                    case 2 -> isBuff2 = true;
                }
                buff.setValid(false);
            }
            if (!buff.isValid())
                buffs.remove(buff);
        }
        updateBuff();
    }

    private void updateBuff() {
        if (isBuff1) {
            if (buff1Timer == 0) {
                setImage("file:src/main/resources/com/example/mydmsproject/Paddle-big.png");
                lightning.updateOffset(this);
            }
            buff1Timer++;
            if (buff1Timer == buffTimerLength) {
                setImage("file:src/main/resources/com/example/mydmsproject/Paddle.png");
                lightning.updateOffset(this);
                isBuff1 = false;
                buff1Timer = 0;
            }
        }
        if (isBuff2) {
            if (buff2Timer < buffTimerLength)
                lightning.update(this);
            buff2Timer++;
            if (buff2Timer == buffTimerLength) {
                isBuff2 = false;
                buff2Timer = 0;
            }
        }
    }

    public void removeBuff() {
        setImage("file:src/main/resources/com/example/mydmsproject/Paddle.png");
        lightning.updateOffset(this);
        isBuff1 = false;
        isBuff2 = false;
        buff1Timer = 0;
        buff2Timer = 0;
    }

    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
        if (isBuff2) lightning.render(gc);
    }

}
