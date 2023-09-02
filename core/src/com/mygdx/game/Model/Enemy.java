package com.mygdx.game.Model;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.behaviors.FollowPath;
import com.badlogic.gdx.ai.steer.behaviors.PrioritySteering;
import com.badlogic.gdx.ai.steer.utils.paths.LinePath;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.SteampunkGame;
import com.mygdx.game.enums.BitFilters;
import com.mygdx.game.enums.TexturePaths;

public abstract class Enemy<T extends Enemy<T>> extends Character<T> implements Steerable<Vector2> {

    private PrioritySteering<Vector2> steeringBehavior;
    private final SteeringAcceleration<Vector2> steeringOutput;
    private final LinePath<Vector2> path;
    private boolean tagged;
    private float zeroLinearSpeedThreshold;
    private float maxLinearAcceleration;
    private float maxAngularAcceleration;
    private float maxAngularSpeed;
    private final Movable<?> target;


    /**
     * @param game         the game this Movable is in
     * @param map          the map this Movable is one
     * @param world        the world this Movables body is in
     * @param texturePaths the path to this characters texture
     * @param posX         the x position in tiles
     * @param posY         the y position in tiles
     * @param width        the width in tiles
     * @param height       the height in tiles
     * @param moveSpeed    the movement speed in tiles/second
     * @param health       the health of this
     * @param target       the thing this enemy will target
     */
    public Enemy(SteampunkGame game, Map map, World world, TexturePaths texturePaths, float posX, float posY, float width, float height, float moveSpeed, int health, float attackCooldown, Movable<? extends Movable<?>> target) {
        super(game, map, world, texturePaths, posX, posY, width, height, moveSpeed, health, attackCooldown);
        this.steeringOutput = new SteeringAcceleration<Vector2>(new Vector2());
        this.tagged = false;
        this.zeroLinearSpeedThreshold = .01f;
        this.maxLinearAcceleration = .1f;
        this.maxAngularAcceleration = 0f;
        this.maxAngularSpeed = 0f;
        this.target = target;

        this.path = new LinePath<Vector2>(map.getWaypoints(this, target));
        FollowPath<Vector2, LinePath.LinePathParam> followPath = new FollowPath<Vector2, LinePath.LinePathParam>(this, this.path, 1);
        this.steeringBehavior = new PrioritySteering<Vector2>(this);
        this.steeringBehavior.add(followPath);
//        CentralRayWithWhiskersConfiguration<Vector2> raycastConfig = new CentralRayWithWhiskersConfiguration<Vector2>(this, 1f, .5f, MathUtils.PI * .25f);
//        this.steeringBehavior.add(new RaycastObstacleAvoidance<Vector2>(this, raycastConfig, new RaycastCollision(world)));
//        this.steeringBehavior.add(new Seek<Vector2>(this, target));
    }

    public static abstract class Builder<U extends Enemy<U>, V extends Builder<U, V>> extends Character.Builder<U, V> {
        protected Movable<? extends Movable<?>> target;

        /**
         * must be set
         *
         * @param target the thing this enemy will target
         * @return this for chaining
         */
        public V set(Movable<? extends Movable<?>> target) {
            this.target = target;
            return this.self();
        }
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
        Body b = this.createBox(posX, posY, this.width, this.height, BodyDef.BodyType.DynamicBody, false);
        Filter filter = b.getFixtureList().get(0).getFilterData();
        filter.categoryBits = BitFilters.ENEMY;
        filter.maskBits = BitFilters.PLAYER | BitFilters.OBSTACLE | BitFilters.PLAYER_PROJECTILE;
        b.getFixtureList().get(0).setFilterData(filter);
        return b;
    }

    @Override
    public void update() {
        super.update();
        this.steeringBehavior.calculateSteering(this.steeringOutput);
        this.applySteering();
        if (this.getPosition().sub(this.target.getPosition()).len() <= 3) {
            this.shoot(new Vector3(this.target.getPosition(), 0));
        }
    }

    @Override
    public void dispose() {
        this.world.destroyBody(this.body);
        this.steeringBehavior = null;
    }

    private void applySteering() {

        this.body.applyLinearImpulse(this.steeringOutput.linear, this.getPosition(), false);
        this.body.setLinearVelocity(this.getLinearVelocity().limit(this.moveSpeed));
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

    public void setPath(Array<Vector2> waypoints) {
        if (waypoints.size > 2) {
            this.path.createPath(waypoints);
        }
    }

    public void drawPath(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.RED);
        for (LinePath.Segment<Vector2> segment : this.path.getSegments()) {
            shapeRenderer.line(segment.getBegin(), segment.getEnd());
        }
    }
}
