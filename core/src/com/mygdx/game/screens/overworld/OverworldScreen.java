package com.mygdx.game.screens.overworld;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.FilePaths;
import com.mygdx.game.Model.Map;
import com.mygdx.game.Model.Player;
import com.mygdx.game.SteampunkGame;

public class OverworldScreen implements Screen {

    private static final float GAME_WIDTH = 1200;
    private static final float GAME_HEIGHT = 715;
    private static final float VIEW_WIDTH = 160;
    private static final float VIEW_HEIGHT = 90;
    private final SteampunkGame game;
    private final SpriteBatch batch;
    private final OrthographicCamera camera;
    private final Map map;
    private final Player player;

    public OverworldScreen(SteampunkGame game) {
        this.game = game;
        this.batch = game.batch;

        this.player = new Player(FilePaths.TEST_CHARACTER);
        this.map = new Map(new Texture(Gdx.files.internal(FilePaths.TEST_MAP.getPath())), this.player);

        this.camera = new OrthographicCamera(GAME_WIDTH, GAME_HEIGHT);
        this.camera.setToOrtho(false, VIEW_WIDTH, VIEW_HEIGHT);
        this.centerCamera();
        this.camera.update();
    }

    /**
     * Called when this screen becomes the current screen for a {@link Game}.
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
        this.handleInput();
        this.camera.update();
        this.batch.setProjectionMatrix(this.camera.combined);
        this.batch.begin();
        this.map.draw(this.batch);
        this.player.draw(this.batch);
        this.batch.end();
    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            this.handleW();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            this.handleS();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            this.handleA();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            this.handleD();
        }

    }

    /**
     * handles when the D key is pressed
     */
    private void handleD() {
        this.move(1, 0);
    }

    /**
     * handles when the A key is pressed
     */
    private void handleA() {
        this.move(-1, 0);
    }

    /**
     * handles when the S key is pressed
     */
    private void handleS() {
        this.move(0, -1);
    }

    /**
     * handles when the W key is pressed
     */
    private void handleW() {
        this.move(0, 1);
    }

    /**
     * moves the player and the camera together
     * movement is based on delta time and MOVE_SPEED
     *
     * @param x x-axis movement
     * @param y y-axis movement
     */
    private void move(float x, float y) {
        x *= Gdx.graphics.getDeltaTime();
        y *= Gdx.graphics.getDeltaTime();
        this.player.translate(x, y);
        this.centerCamera();
    }

    /**
     * centers the camera in the center of the Player
     */
    private void centerCamera() {
        this.camera.position.set(this.player.getCenter(), 0);
    }

    /**
     * @param width the new width of the window
     * @param height the new height of the window
     * @see ApplicationListener#resize(int, int)
     */
    @Override
    public void resize(int width, int height) {
        this.camera.viewportWidth = VIEW_WIDTH * height/width;
        this.camera.viewportHeight = VIEW_HEIGHT * height/width;
        this.camera.update();
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
        this.map.dispose();
        this.player.dispose();
    }
}
