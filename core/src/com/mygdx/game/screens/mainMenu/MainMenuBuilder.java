package com.mygdx.game.screens.mainMenu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.SteampunkGame;
import com.mygdx.game.screens.mainMenu.eventListener.NewGameClickListener;

/**
 * creates the components on the main menu
 */
public class MainMenuBuilder {
    private final SteampunkGame game;
    private final OrthographicCamera camera;
    private final Table table;

    public MainMenuBuilder(SteampunkGame game, OrthographicCamera camera) {
        this.game = game;
        this.camera = camera;
        this.table = new Table();
    }

    /**
     * initializes the New Game button
     * @return the New Game button
     */
    private TextButton makeNewGame() {
        TextButton.TextButtonStyle newGameStyle = new TextButton.TextButtonStyle();
        newGameStyle.fontColor = Color.WHITE;
        newGameStyle.font = new BitmapFont();
        TextButton newGame = new TextButton("New Game", newGameStyle);
        newGame.addListener(new NewGameClickListener(this.game, this.camera));
        return newGame;
    }

    public Table build() {
        this.table.add(this.makeNewGame());
        this.table.setFillParent(true);
        return this.table;
    }
}
