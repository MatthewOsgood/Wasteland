package com.mygdx.game.View.overworld;

import com.badlogic.gdx.*;
import com.badlogic.gdx.ai.GdxAI;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Model.*;
import com.mygdx.game.SteampunkGame;
import com.mygdx.game.View.dialogue.DialogueOverlay;
import com.mygdx.game.contactListeners.OverworldContactListener;
import com.mygdx.game.enums.ConversationPath;
import com.mygdx.game.enums.TexturePaths;
import com.mygdx.game.enums.TiledMapPath;

import static com.mygdx.game.SteampunkGame.*;

public class OverworldScreen implements Screen {

    private final SteampunkGame game;
    private final SpriteBatch batch;
    private final BitmapFont font;
    private final OrthographicCamera camera;
    private final World world;
    private final Box2DDebugRenderer debugRenderer;
    private final OrthogonalTiledMapRenderer tiledMapRenderer;
    private final Map map;
    private final Player player;
    public final DialogueOverlay dialogueOverlay;

    public OverworldScreen(SteampunkGame game) {
        this.game = game;
        this.batch = game.batch;
        this.font = game.font;
        Box2D.init();
        this.world = new World(new Vector2(0, 0), true );
        this.world.setContactListener(new OverworldContactListener());
        this.debugRenderer = new Box2DDebugRenderer();

        this.map = new Map(this.game, this.world, TiledMapPath.TESTMAP);
        this.player = new Player(game, TexturePaths.PLAYER, world, this.map, 15.5f, 15.5f);
        this.map.setPlayer(this.player);

        this.tiledMapRenderer = new OrthogonalTiledMapRenderer(this.map.getTiledMap(), 1/PPT, this.batch);
        this.camera = game.camera;
        this.camera.setToOrtho(false, VIEW_WIDTH, VIEW_HEIGHT);

        this.dialogueOverlay = new DialogueOverlay(game);
        Gdx.input.setInputProcessor(new InputHandler(game, this, this.player, this.map));
    }

    /**
     * Called when this screen becomes the current screen for a {@link Game}.
     */
    @Override
    public void show() {
        this.camera.update();
        this.centerCamera();
        MapBodyBuilder.buildShapes(this.map.getTiledMap(), PPT, this.world);
        this.tiledMapRenderer.setView(this.camera);

        this.map.addNPC(new NPC(this.game, TexturePaths.TEST_NPC, this.world, this.map, 11.5f, 11.5f, ConversationPath.TEST));
        this.map.addEnemy(new TestEnemy(this.game, TexturePaths.TEST_ENEMY, this.world, this.map, 15f, 15f, this.player));
    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        GdxAI.getTimepiece().update(delta);
        this.update();
        this.camera.update();
        this.tiledMapRenderer.setView(game.camera);
        this.batch.setProjectionMatrix(this.camera.combined);
        this.tiledMapRenderer.render();
        this.batch.begin();
        this.map.draw(this.batch);
        this.batch.end();
        this.debugRenderer.render(this.world, this.camera.combined);
        this.dialogueOverlay.draw();
    }

    public void update() {
        this.world.step(1 / this.game.REFRESH_RATE, 6, 2);
        this.map.update();
        this.handleMovement();
        if (!this.player.canInteract) this.dialogueOverlay.setVisibility(false);
    }

    public void handleInteract() {
        if (!this.dialogueOverlay.isVisible()) {
            this.dialogueOverlay.setConversation(this.player.getConversationPath());
            this.dialogueOverlay.setVisibility(true);
        }
        if (this.dialogueOverlay.update()) {
            this.dialogueOverlay.setVisibility(false);
        }
    }


    /**
     * handles player movement
     * not handled in {@link InputHandler} bc ths makes smoother movement
     */
    private void handleMovement() {
        float vx = 0;
        float vy = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            vy += 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            vy -= 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            vx -= 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            vx += 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            vx *= 2;
            vy *= 2;
        }
        this.move(vx, vy);
    }

    /**
     * moves the player and the camera together
     * movement is based on delta time and MOVE_SPEED
     *
     * @param x x-axis movement
     * @param y y-axis movement
     */
    private void move(float x, float y) {
        this.player.setVelocity(x, y);
        this.centerCamera();
    }

    /**
     * centers the camera in the center of the Player
     */
    private void centerCamera() {
        this.camera.position.lerp(new Vector3(this.player.getPosition(), 0), .3f);
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
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {
        this.map.dispose();
        this.player.dispose();
        this.world.dispose();
        this.debugRenderer.dispose();
        this.tiledMapRenderer.dispose();
        this.dialogueOverlay.dispose();
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
}