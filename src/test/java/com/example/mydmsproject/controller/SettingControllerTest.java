package com.example.mydmsproject.controller;

import com.example.mydmsproject.model.Scenes;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.IOException;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

/**
 * Junit Test for SettingController Class.
 */
public class SettingControllerTest extends ApplicationTest {

    /**
     * Initialize stage and set start scene.
     * @param stage the primary stage for this application,
     *              onto which the application scene can be set
     * @throws IOException signals that an I/O exception has occurred
     */
    @Override
    public void start(Stage stage) throws IOException {
        final int WIDTH = 600;
        final int HEIGHT = 450;

        new Scenes(WIDTH, HEIGHT, stage);
        stage.show();
    }

    /**
     * Test whether nodes in the setting scene has proper text.
     */
    @Test
    public void testNodeText() {
        try {
            clickOn("#m_settings");
            verifyThat("#m_restart", hasText("Restart"));
            verifyThat("#m_effect", hasText("Effect"));
            verifyThat("#m_background", hasText("Background"));
        } catch (Exception e) {
            System.out.println("Something went wrong!");
        }
    }

}
