package com.mygdx.game.View.mainMenu.eventListener;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Wasteland;
import com.mygdx.game.View.overworld.OverworldScreen;


public class NewGameClickListener extends ClickListener {

    private final Wasteland game;
    private final Stage stage;

    public NewGameClickListener(Wasteland game, Stage stage) {
        this.game = game;
        this.stage = stage;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        this.game.setScreen(new OverworldScreen(this.game));
        this.stage.dispose();
    }
}
