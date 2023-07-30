package com.mygdx.game.Model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.SteampunkGame;
import com.mygdx.game.enums.TexturePath;

public abstract class Movable implements Entity {

    protected final SteampunkGame game;
    protected final TextureRegion texture;
    protected final Body body;
    protected final World world;
    protected Map map;
    /**
     * width in tiles
     */
    public float width;
    /**
     * height in tiles
     */
    public float height;
    /**
     * movement speed in tiles/second
     */
    protected float moveSpeed;
    /**
     * temp instance to avoid making a new one each time
     */
    protected float angle;


    /**
     * @param game the game this character is in
     * @param texturePath the path to this characters texture
     * @param world the world this characters body is in
     * @param posX the x position in tiles
     * @param posY the y position in tiles
     * @param width the width in tiles
     * @param height the height in tiles
     * @param moveSpeed the movement speed in tiles/second
     */
    public Movable(SteampunkGame game, TexturePath texturePath, World world, float posX, float posY, float width, float height, float moveSpeed) {
        this.game = game;
        this.texture = new TextureRegion(game.assetManager.get(texturePath.getPath(), Texture.class));
        this.world = world;
        this.width = width;
        this.height = height;
        this.moveSpeed = moveSpeed;
        this.body = this.createBody(posX, posY);
    }

    public Movable(SteampunkGame game, TexturePath texturePath, World world, float posX, float posY, float width, float height) {
        this(game, texturePath, world, posX, posY, width, height, 3);
    }

    public Movable(SteampunkGame game, TexturePath texturePath, World world, float posX, float posY) {
        this(game, texturePath, world, posX, posY, 1f, 1f);
    }

    public void setMap(Map map) {
        this.map = map;
    }

    @Override
    public void setVelocity(float x, float y) {
        this.setVelocity(new Vector2(x, y));
    }

    @Override
    public void setVelocity(Vector2 v) {
        this.body.setLinearVelocity(v.scl(this.moveSpeed));
    }

    /**
     * @return the velocity relative to the move speed
     */
    @Override
    public Vector2 getVelocity() {
        return this.body.getLinearVelocity().scl(1/this.moveSpeed);
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(this.texture,
                this.body.getPosition().x - this.width / 2,
                this.body.getPosition().y - this.height / 2,
                this.getCenter().x, this.getCenter().y,
                this.width, this.height,
                1f, 1f,
                this.angle);
    }
    @Override
    public void dispose() {
        this.texture.getTexture().dispose();
    }
    @Override
    public Vector2 getCenter() {
        return this.body.getPosition();
    }

    @Override
    public void setPosition(float x, float y) {
        this.body.getPosition().set(x, y);
    }

    /**
     * convenience method to make simple boxes
     *
     * @param x x position of bottom left
     * @param y y position of bottom left
     * @param width width
     * @param height height
     * @param bodyType BodyType of this body
     * @param isSensor if it is a sensor
     * @return a body
     */
    public Body createBox(float x, float y, float width, float height, BodyDef.BodyType bodyType, boolean isSensor) {
        BodyDef bDef = new BodyDef();
        bDef.type = bodyType;
        bDef.position.set(new Vector2(x , y ));
        bDef.fixedRotation = true;
        bDef.linearDamping = 0f;
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);
        Body body = this.world.createBody(bDef);
        FixtureDef fDef = new FixtureDef();
        fDef.shape = shape;
        fDef.density = 1f;
        fDef.friction = 0f;
        fDef.restitution = .5f;
        fDef.isSensor = isSensor;
        body.createFixture(fDef).setUserData(this);
        shape.dispose();
        return body;
    }

    /**
     * creates the body for this Character
     *
     * @return the body to be set as this character's
     */
    protected abstract Body createBody(float posX, float posY);

    public abstract void update();
}
