package com.mygdx.game.Model;

import com.badlogic.gdx.ai.GdxAI;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.behaviors.PrioritySteering;
import com.badlogic.gdx.ai.steer.behaviors.Seek;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.SteampunkGame;
import com.mygdx.game.enums.TexturePaths;

public abstract class Enemy extends Character implements Steerable<Vector2> {

    private PrioritySteering<Vector2> steeringBehavior;
    private final SteeringAcceleration<Vector2> steeringOutput;
    private boolean tagged;
    private float zeroLinearSpeedThreshold;
    private float maxLinearAcceleration;
    private float maxAngularAcceleration;
    private float maxAngularSpeed;

    /**
     * @param game        the game this character is in
     * @param texturePaths the path to this characters texture
     * @param world       the world this characters body is in
     * @param map         the map this character is on
     * @param posX        the x position in tiles
     * @param posY        the y position in tiles
     * @param width       the width in tiles
     * @param height      the height in tiles
     * @param moveSpeed   the movement speed in tiles/second
     */
    public Enemy(SteampunkGame game, TexturePaths texturePaths, World world, Map map, float posX, float posY, float width, float height, float moveSpeed) {
        super(game, texturePaths, world, map, posX, posY, width, height, moveSpeed);
        steeringOutput = new SteeringAcceleration<Vector2>(new Vector2());
        this.tagged = false;
        this.zeroLinearSpeedThreshold = .001f;
        this.maxLinearAcceleration = this.moveSpeed;
        this.maxAngularAcceleration = 10f;
        this.maxAngularSpeed = 20f;
    }

    public Enemy(SteampunkGame game, TexturePaths texturePaths, World world, Map map, float posX, float posY, float width, float height) {
        super(game, texturePaths, world, map, posX, posY, width, height);
        steeringOutput = new SteeringAcceleration<Vector2>(new Vector2());
        this.tagged = false;
        this.zeroLinearSpeedThreshold = .001f;
        this.maxLinearAcceleration = this.moveSpeed;
        this.maxAngularAcceleration = 10f;
        this.maxAngularSpeed = 20f;
    }

    public Enemy(SteampunkGame game, TexturePaths texturePaths, World world, Map map, float posX, float posY, Player target) {
        super(game, texturePaths, world, map, posX, posY);
        steeringOutput = new SteeringAcceleration<Vector2>(new Vector2());
        this.tagged = false;
        this.zeroLinearSpeedThreshold = .01f;
        this.maxLinearAcceleration = 10000f;
        this.maxAngularAcceleration = 10000f;
        this.maxAngularSpeed = 20f;
        this.steeringBehavior = new PrioritySteering<Vector2>(this);
        this.steeringBehavior.add(new Seek<Vector2>(this, target));
    }

    /**
     * creates the body for this Character
     *
     * @param posX x
     * @param posY y
     * @return the body to be set as this character's
     */
    @Override
    protected Body createBody(float posX, float posY) {
        return this.createBox(posX, posY, this.width, this.height, BodyDef.BodyType.DynamicBody, false);
    }

    @Override
    protected Projectile makeProjectile() {
        return new Bullet(this.game, TexturePaths.BULLET, this.world, this.map, this.getPosition(), .5f, .5f, this);
    }

    @Override
    public void update() {
        if (steeringBehavior != null) {
            steeringBehavior.calculateSteering(this.steeringOutput);
            this.applySteering(GdxAI.getTimepiece().getDeltaTime());
        }
    }

    private void applySteering(float deltaTime) {
        if (!this.steeringOutput.linear.isZero()) {
            this.body.applyForceToCenter(this.steeringOutput.linear.scl(deltaTime), true);
        }
        if (this.steeringOutput.angular != 0) {
            this.body.applyTorque(this.steeringOutput.angular * deltaTime, true);
        }
        this.body.setLinearVelocity(this.body.getLinearVelocity().limit(this.moveSpeed));
        this.body.setAngularVelocity(Math.min(this.body.getAngularVelocity(), this.maxAngularSpeed));
    }
    /**
     * Returns the vector indicating the linear velocity of this Steerable.
     */
    @Override
    public Vector2 getLinearVelocity() {
        return this.body.getLinearVelocity();
    }

    /**
     * Returns the float value indicating the angular velocity in radians of this Steerable.
     */
    @Override
    public float getAngularVelocity() {
        return this.body.getAngularVelocity();
    }

    /**
     * Returns the bounding radius of this Steerable.
     */
    @Override
    public float getBoundingRadius() {
        return (float) Math.sqrt(Math.pow(this.width, 2) + Math.pow(this.height, 2));
    }

    /**
     * Returns {@code true} if this Steerable is tagged; {@code false} otherwise.
     */
    @Override
    public boolean isTagged() {
        return this.tagged;
    }

    /**
     * Tag/untag this Steerable. This is a generic flag utilized in a variety of ways.
     *
     * @param tagged the boolean value to set
     */
    @Override
    public void setTagged(boolean tagged) {
        this.tagged = tagged;
    }

    /**
     * Returns the threshold below which the linear speed can be considered zero. It must be a small positive value near to zero.
     * Usually it is used to avoid updating the orientation when the velocity vector has a negligible length.
     */
    @Override
    public float getZeroLinearSpeedThreshold() {
        return this.zeroLinearSpeedThreshold;
    }

    /**
     * Sets the threshold below which the linear speed can be considered zero. It must be a small positive value near to zero.
     * Usually it is used to avoid updating the orientation when the velocity vector has a negligible length.
     *
     * @param value zeroLinearSpeedThreshold
     */
    @Override
    public void setZeroLinearSpeedThreshold(float value) {
        this.zeroLinearSpeedThreshold = value;
    }

    /**
     * Returns the maximum linear speed.
     */
    @Override
    public float getMaxLinearSpeed() {
        return this.moveSpeed;
    }

    /**
     * Sets the maximum linear speed.
     *
     * @param maxLinearSpeed maxLinearSpeed
     */
    @Override
    public void setMaxLinearSpeed(float maxLinearSpeed) {
        this.moveSpeed = maxLinearSpeed;
    }

    /**
     * Returns the maximum linear acceleration.
     */
    @Override
    public float getMaxLinearAcceleration() {
        return this.maxLinearAcceleration;
    }

    /**
     * Sets the maximum linear acceleration.
     *
     * @param maxLinearAcceleration maxLinearAcceleration
     */
    @Override
    public void setMaxLinearAcceleration(float maxLinearAcceleration) {
        this.maxLinearAcceleration = maxLinearAcceleration;
    }

    /**
     * Returns the maximum angular speed.
     */
    @Override
    public float getMaxAngularSpeed() {
        return this.maxAngularSpeed;
    }

    /**
     * Sets the maximum angular speed.
     *
     * @param maxAngularSpeed maxAngularSpeed
     */
    @Override
    public void setMaxAngularSpeed(float maxAngularSpeed) {
        this.maxAngularSpeed = maxAngularSpeed;
    }

    /**
     * Returns the maximum angular acceleration.
     */
    @Override
    public float getMaxAngularAcceleration() {
        return this.maxAngularAcceleration;
    }

    /**
     * Sets the maximum angular acceleration.
     *
     * @param maxAngularAcceleration maxAngularAcceleration
     */
    @Override
    public void setMaxAngularAcceleration(float maxAngularAcceleration) {
        this.maxAngularAcceleration = maxAngularAcceleration;
    }

}
