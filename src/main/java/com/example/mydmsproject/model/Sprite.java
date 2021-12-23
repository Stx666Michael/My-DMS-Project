package com.example.mydmsproject.model;

import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
import javafx.geometry.Rectangle2D;

/**
 * An abstract parent model class providing basic functionalities
 * such as image rendering and position setting for other game elements.
 * @author Tianxiang Song
 */
public abstract class Sprite {

    private Image m_image;
    private double m_positionX;
    private double m_positionY;
    private double m_velocityX;
    private double m_velocityY;
    private double m_width;
    private double m_height;

    /**
     * Get X position of the object.
     * @return X position of the project
     */
    public double getPositionX() {
        return m_positionX;
    }

    /**
     * Get Y position of the object.
     * @return Y position of the project
     */
    public double getPositionY() {
        return m_positionY;
    }

    /**
     * Get horizontal speed of the object.
     * @return horizontal speed of the object
     */
    public double getVelocityX() {
        return m_velocityX;
    }

    /**
     * Get vertical speed of the object.
     * @return vertical speed of the object
     */
    public double getVelocityY() {
        return m_velocityY;
    }

    /**
     * Get width of the object image.
     * @return width of the object image
     */
    public double getWidth() {
        return m_width;
    }

    /**
     * Get height of the object image.
     * @return height of the object image
     */
    public double getHeight() {
        return m_height;
    }

    /**
     * Get boundary of the object image.
     * @return boundary of the object image
     */
    public Rectangle2D getBoundary() {
        return new Rectangle2D(m_positionX, m_positionY, m_width, m_height);
    }

    /**
     * Set image of the object by Image instance.
     * @param i Image instance
     */
    public void setImage(Image i) {
        m_image = i;
        m_width = i.getWidth();
        m_height = i.getHeight();
    }

    /**
     * Set image of the object by image path.
     * @param filename image path
     */
    public void setImage(String filename) {
        Image i = new Image(filename);
        setImage(i);
    }

    /**
     * Set position of object relative to the stage.
     * @param x X position of object
     * @param y Y position of object
     */
    public void setPosition(double x, double y) {
        m_positionX = x;
        m_positionY = y;
    }

    /**
     * Set speed of object.
     * @param x horizontal speed of object
     * @param y vertical speed of object
     */
    public void setVelocity(double x, double y) {
        m_velocityX = x;
        m_velocityY = y;
    }

    /**
     * Add speed of object.
     * @param x horizontal speed increase of object
     * @param y vertical speed increase of object
     */
    public void addVelocity(double x, double y) {
        m_velocityX += x;
        m_velocityY += y;
    }

    /**
     * Default class constructor, set X, Y position and speed to zero.
     */
    public Sprite() {
        m_positionX = 0;
        m_positionY = 0;
        m_velocityX = 0;
        m_velocityY = 0;
    }

    /**
     * Render the object to its position.
     * @param gc the GraphicsContext of Canvas in the game scene
     */
    public void render(GraphicsContext gc) {
        gc.drawImage(m_image, m_positionX, m_positionY);
    }

    /**
     * Update object position according to its velocity.
     */
    public void update() {
        m_positionX += m_velocityX;
        m_positionY += m_velocityY;
    }

    /**
     * Check if two game elements intersects.
     * @param s another game elements to check if intersects with
     * @return true if they intersect, else false
     */
    public boolean intersects(Sprite s) {
        return s.getBoundary().intersects( this.getBoundary() );
    }

    /**
     * Convert object position and velocity to string.
     * @return string of object position and velocity
     */
    public String toString() {
        return " Position: [" + m_positionX + "," + m_positionY + "]"
                + " Velocity: [" + m_velocityX + "," + m_velocityY + "]";
    }

}
