package com.mygdx.game.View.loadingScreen;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.SteampunkGame;
import com.mygdx.game.View.mainMenu.MainMenuScreen;
import com.mygdx.game.enums.SkinPaths;
import com.mygdx.game.enums.TexturePaths;
import com.mygdx.game.enums.TiledMapPath;

public class LoadingScreen implements Screen {

    private final SteampunkGame game;
    private final Stage stage;
    private final Skin skin;
    private float progress;
    private ProgressBar progressBar;

    public LoadingScreen(final SteampunkGame game) {
        this.game = game;
        ScreenViewport viewport = new ScreenViewport(game.camera);
        this.stage = new Stage(viewport, game.batch);
        game.assetManager.load(TexturePaths.LOGO.getPath(), Texture.class);
        game.assetManager.load(SkinPaths.TEST.getPath(), Skin.class);
        game.assetManager.finishLoading();
        this.skin = game.assetManager.get(SkinPaths.TEST.getPath());
        this.setStage();
    }

    private void setStage() {
        Image logo = new Image(this.game.assetManager.<Texture>get(TexturePaths.LOGO.getPath()));
        this.progressBar = new ProgressBar(0f, 1f, .01f, false, this.skin);
        Table table = new Table();
        table.setFillParent(true);
        table.add(logo).padBottom(this.stage.getViewport().getScreenHeight() / 10f);
        table.row();
        table.add(this.progressBar);
        this.stage.addActor(table);
    }

    /**
     * Called when this screen becomes the current screen for a {@link Game}.
     */
    @Override
    public void show() {
        this.loadAssets();
        this.progress = 0f;
    }

    /**
     * queues assets to be loaded
     */
    private void loadAssets() {
        for (TexturePaths texturePaths : TexturePaths.values()) {
            this.game.assetManager.load(texturePaths.getPath(), Texture.class);
        }
        for (SkinPaths skinPath : SkinPaths.values()) {
            this.game.assetManager.load(skinPath.getPath(), Skin.class);
        }
        for (TiledMapPath tiledMapPath : TiledMapPath.values()) {
            this.game.assetManager.load(tiledMapPath.getPath(), TiledMap.class);
        }
    }

    private void update(float delta) {
        this.progress = Math.min(this.game.assetManager.getProgress(), this.progress + delta);
        if (this.game.assetManager.update() && this.progress >= 1f) {
            this.game.setScreen(new MainMenuScreen(this.game));
        }
    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.update(delta);
        this.progressBar.setValue(this.progress);
        this.stage.act();
        this.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        this.stage.getViewport().update(width, height);
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

    }
}
