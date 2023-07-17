package com.mygdx.game.screens.mainMenu.eventListener;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.SteampunkGame;
import com.mygdx.game.screens.overworld.OverworldScreen;


public class NewGameClickListener extends ClickListener {

    private final SteampunkGame game;
    private final OrthographicCamera camera;

    public NewGameClickListener(SteampunkGame game, OrthographicCamera camera) {
        this.game = game;
        this.camera = camera;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        this.game.setScreen(new OverworldScreen(this.game));
    }
}
