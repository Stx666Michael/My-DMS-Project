package com.example.mydmsproject.model;

public class Lightning extends Sprite {

    private int m_offsetX = 0;
    private int m_offsetY = 0;

    public int getMaxLightningBreak() {
        return 5;
    }

    public Lightning(String type) {
        setImage("file:src/main/resources/com/example/mydmsproject/" +
                "Lightning-" + type + ".png");
    }

    public void update(Sprite s) {
        if (m_offsetX == 0 && m_offsetY == 0) updateOffset(s);
        setPosition(s.getPositionX()+ m_offsetX, s.getPositionY()+ m_offsetY);
        super.update();
    }

    public void updateOffset(Sprite s) {
        m_offsetX = (int) (s.getWidth()-getWidth()) / 2;
        m_offsetY = (int) (s.getHeight()-getHeight()) / 2;
    }

}
