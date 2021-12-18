package com.example.mydmsproject.model;

/**
 * A Sprite class, can be "carried" by both the main Ball and the Paddle.
 * When the main Ball bounced from the "Lightning" Paddle, the Ball
 * carries "Lightning", which makes it able to penetrate a few Bricks.
 */
public class Lightning extends Sprite {

    private int m_offsetX = 0;
    private int m_offsetY = 0;

    /**
     * Get the maximum brick hits that a "Lightning" Ball can do at once.
     * @return the maximum brick hits that a "Lightning" Ball can do at once
     */
    public int getMaxLightningBreak() {
        return 6;
    }

    /**
     * Default class constructor, initialize a lightning of specified type.
     * @param type the type of lightning, indicates which object the lightning
     *             belongs to
     */
    public Lightning(String type) {
        setImage("file:src/main/resources/com/example/mydmsproject/" +
                "Lightning-" + type + ".png");
    }

    /**
     * Set position of lightning the same as its owner.
     * @param s the owner of the lightning
     */
    public void update(Sprite s) {
        if (m_offsetX == 0 && m_offsetY == 0) updateOffset(s);
        setPosition(s.getPositionX()+ m_offsetX, s.getPositionY()+ m_offsetY);
        super.update();
    }

    /**
     * Update offset to make lightning appears at the center of its owner.
     * @param s the owner of the lightning
     */
    public void updateOffset(Sprite s) {
        m_offsetX = (int) (s.getWidth()-getWidth()) / 2;
        m_offsetY = (int) (s.getHeight()-getHeight()) / 2;
    }

}
