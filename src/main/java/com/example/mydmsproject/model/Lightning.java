package com.example.mydmsproject.model;

public class Lightning extends Sprite {

    private int offsetX = 0;
    private int offsetY = 0;
    private final int maxLightningBreak = 5;

    public Lightning(String type) {
        setImage("file:src/main/resources/com/example/mydmsproject/Lightning-"+type+".png");
    }

    public int getMaxLightningBreak() {
        return maxLightningBreak;
    }

    public void update(Sprite s) {
        if (offsetX == 0 && offsetY == 0)
            updateOffset(s);
        setPosition(s.getPositionX()+offsetX, s.getPositionY()+offsetY);
        super.update();
    }

    public void updateOffset(Sprite s) {
        offsetX = (int) (s.getWidth()-getWidth()) / 2;
        offsetY = (int) (s.getHeight()-getHeight()) / 2;
    }

}
