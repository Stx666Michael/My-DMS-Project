package com.example.mydmsproject.controller;

import com.example.mydmsproject.model.Scenes;
import javafx.scene.Node;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.IOException;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

/**
 * Junit Test for StartController Class.
 */
public class StartControllerTest extends ApplicationTest {

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
     * Test whether nodes in the start scene has proper text.
     */
    @Test
    public void testNodeText() {
        verifyThat("#m_start", hasText("START"));
        verifyThat("#m_settings", hasText("SETTINGS"));
        verifyThat("#m_help", hasText("HELP"));
    }

    /**
     * Test whether clicking setting button shows nodes in setting scene.
     */
    @Test
    public void testSettingButtonClick() {
        clickOn("#m_settings");
        verifyThat("#m_ballSpeed", Node::isVisible);
        verifyThat("#m_paddleSpeed", Node::isVisible);
        verifyThat("#m_theme", Node::isVisible);
        verifyThat("#m_control", Node::isVisible);
        verifyThat("#m_effect", Node::isVisible);
        verifyThat("#m_background", Node::isVisible);
    }

    /**
     * Test whether clicking help button shows help pane.
     */
    @Test
    public void testHelpButtonClick() {
        clickOn("#m_help");
        verifyThat("#m_helpPane", Node::isVisible);
    }

}
