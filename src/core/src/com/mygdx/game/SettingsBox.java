package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;

public class SettingsBox extends VerticalGroup {

    // mainly for access to batch
    final private SAC game;

    //create a new settings box that have frame and some buttons



    public SettingsBox(SAC game) {
        this.game = game;
    }
}
