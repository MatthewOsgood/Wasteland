package com.mygdx.game.View.mainMenu;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.SteampunkGame;
import com.mygdx.game.View.mainMenu.eventListener.NewGameClickListener;

public class MainMenuScreen implements Screen {

    private final SteampunkGame game;
    private final Stage stage;

    public MainMenuScreen(SteampunkGame game) {
        this.game = game;
        ScreenViewport viewport = new ScreenViewport(game.camera);
        this.stage = new Stage(viewport, game.batch);
        this.stage.addActor(this.makeTable());
        Gdx.input.setInputProcessor(this.stage);
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
        newGame.addListener(new NewGameClickListener(this.game, this.stage));
        return newGame;
    }

    public Table makeTable() {
        Table table = new Table();
        table.add(this.makeNewGame());
        table.setFillParent(true);
        return table;
    }

    /**
     * Called when this screen becomes the current screen for a {@link com.mygdx.game.SteampunkGame}.
     */
    @Override
    public void show() {

    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        this.stage.act(Gdx.graphics.getDeltaTime());
        this.stage.draw();
    }

    /**
     * @param width the new width
     * @param height th new height
     * @see ApplicationListener#resize(int, int)
     */
    @Override
    public void resize(int width, int height) {
        this.stage.getViewport().update(width, height, true);
    }

    /**
     * @see ApplicationListener#pause()
     */
    @Override
    public void pause() {

    }

    /**
     * @see ApplicationListener#resume()
     */
    @Override
    public void resume() {

    }

    /**
     * Called when this screen is no longer the current screen for a {@link Game}.
     */
    @Override
    public void hide() {

    }

    /**
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {
        this.stage.dispose();
    }
}
