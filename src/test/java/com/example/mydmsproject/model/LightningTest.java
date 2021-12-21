package com.example.mydmsproject.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * JUnit Test for Lightning Class.
 */
public class LightningTest {

    private final Lightning m_lightning = new Lightning();

    /**
     * Test method that returns max lightning break.
     */
    @Test
    public void testGetMaxLightningBreak() {
        assertEquals(6, m_lightning.getMaxLightningBreak());
    }

    /**
     * Test method that update position of the Lightning.
     */
    @Test
    public void testUpdate() {
        final int VALUE = 100;
        Lightning test = new Lightning();
        test.setPosition(VALUE, VALUE);
        m_lightning.update(test);
        assertEquals(VALUE, m_lightning.getPositionX());
        assertEquals(VALUE, m_lightning.getPositionY());
    }

}
