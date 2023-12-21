package com.mygdx.game.View.overworld;

import com.badlogic.gdx.*;
import com.badlogic.gdx.ai.GdxAI;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Model.*;
import com.mygdx.game.Model.Entities.NPC;
import com.mygdx.game.Model.Entities.Player.Player;
import com.mygdx.game.Model.Entities.TestEnemy;
import com.mygdx.game.Wasteland;
import com.mygdx.game.View.dialogue.DialogueOverlay;
import com.mygdx.game.contactListeners.OverworldContactListener;
import com.mygdx.game.enums.ConversationPaths;
import com.mygdx.game.enums.TexturePaths;
import com.mygdx.game.enums.TiledMapPath;

import static com.mygdx.game.Wasteland.*;

public class OverworldScreen implements Screen {

    private final Wasteland game;
    private final SpriteBatch batch;
    private final OrthographicCamera camera;
    private final World world;
    private final Box2DDebugRenderer debugRenderer;
    private final OrthogonalTiledMapRenderer tiledMapRenderer;
    private final Map map;
    private final Player player;
    public final DialogueOverlay dialogueOverlay;
    /**
     * shared vector3 to prevent garbage collection
     */
    private final Vector3 tmp;

    public OverworldScreen(Wasteland game) {
        this.game = game;
        this.batch = game.batch;
        Box2D.init();
        this.world = new World(new Vector2(0, 0), true );
        this.world.setContactListener(new OverworldContactListener());
        this.debugRenderer = new Box2DDebugRenderer();

        this.map = new Map(this.game, this.world, TiledMapPath.TESTMAP);
        this.player = new Player.Builder().player().build(game, this.map, this.world, TexturePaths.PLAYER);
        this.map.setPlayer(this.player);

        this.tiledMapRenderer = new OrthogonalTiledMapRenderer(this.map.getTiledMap(), 1/PPT, this.batch);
        this.camera = game.camera;
        this.camera.setToOrtho(false, VIEW_WIDTH, VIEW_HEIGHT);

        this.dialogueOverlay = new DialogueOverlay(game);
        Gdx.input.setInputProcessor(new InputHandler(game, this, this.player, this.map));

        this.tmp = new Vector3();
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

        NPC testNPC = new NPC.Builder().pos(8f, 8f).set(ConversationPaths.TEST).set(TexturePaths.TEST_NPC).build(this.game, this.map, this.world);
        this.map.addNPC(testNPC);
        TestEnemy testEnemy = new TestEnemy.Builder().set(TexturePaths.TEST_ENEMY).health(100).set(this.player).pos(15.5f, 10f).build(this.game, this.map, this.world);
        this.map.addEnemy(testEnemy);
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
        this.tiledMapRenderer.setView(this.game.camera);
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
        Vector2 velocity = new Vector2(0, 0);
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            velocity.y += 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            velocity.y -= 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            velocity.x -= 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            velocity.x += 1;
        }
        velocity.nor();
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            velocity.scl(2f);
        }
        this.move(velocity);
    }

    /**
     * moves the player and the camera together
     * movement is based on delta time and MOVE_SPEED
     *
     * @param velocity the velocity to be set to this player
     */
    private void move(Vector2 velocity) {
        this.player.setVelocity(velocity);

        this.tmp.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        this.camera.unproject(this.tmp);
        Vector3 cameraPos = new Vector3(this.player.getPosition(), 0).lerp(this.tmp, .3f);
        this.camera.position.lerp(cameraPos, .2f);
    }

    /**
     * centers the camera in the center of the Player
     */
    private void centerCamera() {
        this.camera.position.lerp(new Vector3(this.player.getPosition(), 0), .2f);
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