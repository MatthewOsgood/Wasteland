package com.mygdx.game.View.mainMenu.eventListener;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.SteampunkGame;
import com.mygdx.game.View.overworld.OverworldScreen;


public class NewGameClickListener extends ClickListener {

    private final SteampunkGame game;

    public NewGameClickListener(SteampunkGame game) {
        this.game = game;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        this.game.setScreen(new OverworldScreen(this.game));
    }
}
