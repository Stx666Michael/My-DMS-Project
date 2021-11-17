package com.example.mydmsproject.main;

import com.example.mydmsproject.controller.old.GameFrame;

import java.awt.*;

public class StartGame {

    public static void main(String[] args){
        EventQueue.invokeLater(() -> new GameFrame().initialize());
    }
    // use [space] to start/pause the game
    // use [←] to move the player left
    // use [→] to move the player right
    // use [esc] to enter/exit pause menu
    // use [alt+shift+f1] at any time to display debug panel
}
