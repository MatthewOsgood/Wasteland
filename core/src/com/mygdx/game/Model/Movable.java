package com.mygdx.game.Model;

import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Model.ai.SteeringUtils;
import com.mygdx.game.SteampunkGame;
import com.mygdx.game.enums.TexturePaths;

public abstract class Movable implements Entity, Location<Vector2> {

    protected final SteampunkGame game;
    protected final TextureRegion texture;
    protected final Body body;
    protected final World world;
    protected final Map map;
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
     * the angle of this Movable
     */
    protected float angle;
    private final Vector2 tmpVelocity;


    /**
     * @param game        the game this Movable is in
     * @param texturePaths the path to this characters texture
     * @param world       the world this Movables body is in
     * @param map         the map this Movable is one
     * @param posY        the y position in tiles
     * @param posX        the x position in tiles
     * @param width       the width in tiles
     * @param height      the height in tiles
     * @param moveSpeed   the movement speed in tiles/second
     */
    public Movable(SteampunkGame game, TexturePaths texturePaths, World world, Map map, float posY, float posX, float width, float height, float moveSpeed) {
        this.game = game;
        this.map = map;
        this.texture = new TextureRegion(game.assetManager.get(texturePaths.getPath(), Texture.class));
        this.world = world;
        this.width = width;
        this.height = height;
        this.moveSpeed = moveSpeed;
        this.body = this.createBody(posX, posY);
        this.tmpVelocity = new Vector2();
    }

    public Movable(SteampunkGame game, TexturePaths texturePaths, World world, Map map, float posY, float width, float height, float posX) {
        this(game, texturePaths, world, map, posY, posX, width, height, 3);
    }

    public Movable(SteampunkGame game, TexturePaths texturePaths, World world, float posX, float posY, Map map) {
        this(game, texturePaths, world, map, posY, .75f, .75f, posX);
    }

    @Override
    public void setVelocity(float x, float y) {
        this.tmpVelocity.set(x, y);
        this.setVelocity(this.tmpVelocity);
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
                this.width / 2, this.height / 2,
                this.width, this.height,
                1f, 1f,
                this.angle * MathUtils.radiansToDegrees);
    }
    @Override
    public void dispose() {
        this.world.destroyBody(this.body);
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

    /**
     * Returns the vector indicating the position of this location.
     */
    @Override
    public Vector2 getPosition() {
        return this.body.getPosition();
    }

    /**
     * Returns the float value indicating the orientation of this location. The orientation is the angle in radians representing
     * the direction that this location is facing.
     */
    @Override
    public float getOrientation() {
        return this.body.getAngle();
    }

    /**
     * Sets the orientation of this location, i.e. the angle in radians representing the direction that this location is facing.
     *
     * @param orientation the orientation in radians
     */
    @Override
    public void setOrientation(float orientation) {
        this.body.setTransform(this.getPosition(), orientation);
    }

    /**
     * Returns the angle in radians pointing along the specified vector.
     *
     * @param vector the vector
     */
    @Override
    public float vectorToAngle(Vector2 vector) {
        return SteeringUtils.vectorToAngle(vector);
    }

    /**
     * Returns the unit vector in the direction of the specified angle expressed in radians.
     *
     * @param outVector the output vector.
     * @param angle     the angle in radians.
     * @return the output vector for chaining.
     */
    @Override
    public Vector2 angleToVector(Vector2 outVector, float angle) {
        return SteeringUtils.angleToVector(outVector, angle);
    }

    /**
     * Creates a new location.
     * <p>
     * This method is used internally to instantiate locations of the correct type parameter {@code T}. This technique keeps the API
     * simple and makes the API easier to use with the GWT backend because avoids the use of reflection.
     *
     * @return the newly created location.
     */
    @Override
    public Location<Vector2> newLocation() {
        return new MapLocation();
    }

}
