package com.mygdx.game.View.overworld;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.TexturePath;
import com.mygdx.game.Model.Map;
import com.mygdx.game.Model.NPC;
import com.mygdx.game.Model.Player;
import com.mygdx.game.SteampunkGame;

public class OverworldScreen implements Screen {

    private final SpriteBatch batch;
    private final BitmapFont font;
    private final OrthographicCamera camera;
    private float delta;
    private final World world;
    private final Box2DDebugRenderer debugRenderer;
    private final Map map;
    private final Player player;

    public OverworldScreen(SteampunkGame game) {
        this.batch = game.batch;
        this.font = game.font;
        Box2D.init();
        this.world = new World(new Vector2(0, 0), true);
        this.debugRenderer = new Box2DDebugRenderer();

        this.player = new Player(TexturePath.TEST_CHARACTER);
        this.map = new Map(new Texture(Gdx.files.internal(TexturePath.TEST_MAP.getPath())), this.player);

        this.map.addNPC(new NPC(TexturePath.TEST_NPC, 50f, 50f));

        this.camera = new OrthographicCamera(SteampunkGame.GAME_WIDTH, SteampunkGame.GAME_HEIGHT);
        this.camera.setToOrtho(false, SteampunkGame.VIEW_WIDTH, SteampunkGame.VIEW_HEIGHT);
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
        this.map.draw(this.batch, this.font);
        this.batch.end();
    }

    public void update(float delta) {
        this.delta = delta;
        this.handleInput();
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
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            this.map.handleInteract();
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
        x *= this.delta;
        y *= this.delta;
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
        this.camera.viewportWidth = SteampunkGame.VIEW_WIDTH * height/width;
        this.camera.viewportHeight = SteampunkGame.VIEW_HEIGHT * height/width;
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
        this.batch.dispose();
        this.font.dispose();
        this.map.dispose();
        this.player.dispose();
        this.world.dispose();
        this.debugRenderer.dispose();
    }
}
