package com.example.mydmsproject.model;

import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
import javafx.geometry.Rectangle2D;

public abstract class Sprite {

    private Image m_image;
    private double m_positionX;
    private double m_positionY;
    private double m_velocityX;
    private double m_velocityY;
    private double m_width;
    private double m_height;

    public double getPositionX() {
        return m_positionX;
    }

    public double getPositionY() {
        return m_positionY;
    }

    public double getVelocityX() {
        return m_velocityX;
    }

    public double getVelocityY() {
        return m_velocityY;
    }

    public double getWidth() {
        return m_width;
    }

    public double getHeight() {
        return m_height;
    }

    public Rectangle2D getBoundary() {
        return new Rectangle2D(m_positionX, m_positionY, m_width, m_height);
    }

    public void setImage(Image i) {
        m_image = i;
        m_width = i.getWidth();
        m_height = i.getHeight();
    }

    public void setImage(String filename) {
        Image i = new Image(filename);
        setImage(i);
    }

    public void setPosition(double x, double y) {
        m_positionX = x;
        m_positionY = y;
    }

    public void setVelocity(double x, double y) {
        m_velocityX = x;
        m_velocityY = y;
    }

    public void addVelocity(double x, double y) {
        m_velocityX += x;
        m_velocityY += y;
    }

    public Sprite() {
        m_positionX = 0;
        m_positionY = 0;
        m_velocityX = 0;
        m_velocityY = 0;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(m_image, m_positionX, m_positionY);
    }

    public void update() {
        m_positionX += m_velocityX;
        m_positionY += m_velocityY;
    }

    public boolean intersects(Sprite s) {
        return s.getBoundary().intersects( this.getBoundary() );
    }

    public String toString() {
        return " Position: [" + m_positionX + "," + m_positionY + "]"
                + " Velocity: [" + m_velocityX + "," + m_velocityY + "]";
    }

}
