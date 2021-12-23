package com.example.mydmsproject.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * JUnit Test for Lightning Class.
 * @author Tianxiang Song
 */
public class LightningTest {

    private final Lightning m_lightning = new Lightning();

    /**
     * Test method that returns max lightning break.
     */
    @Test
    public void testGetMaxLightningBreak() {
        try {
            assertEquals(6, m_lightning.getMaxLightningBreak());
        } catch (Exception e) {
            System.out.println("Something went wrong!");
        }
    }

    /**
     * Test method that update position of the Lightning.
     */
    @Test
    public void testUpdate() {
        try {
            final int VALUE = 100;
            Lightning test = new Lightning();
            test.setPosition(VALUE, VALUE);
            m_lightning.update(test);
            assertEquals(VALUE, m_lightning.getPositionX());
            assertEquals(VALUE, m_lightning.getPositionY());
        } catch (Exception e) {
            System.out.println("Something went wrong!");
        }
    }

}
