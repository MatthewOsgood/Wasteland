package com.mygdx.game.Model;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.behaviors.FollowPath;
import com.badlogic.gdx.ai.steer.behaviors.PrioritySteering;
import com.badlogic.gdx.ai.steer.utils.paths.LinePath;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.SteampunkGame;
import com.mygdx.game.enums.TexturePaths;

public abstract class Enemy extends Character implements Steerable<Vector2> {

    private final PrioritySteering<Vector2> steeringBehavior;
    private final SteeringAcceleration<Vector2> steeringOutput;
    private final LinePath<Vector2> path;
    private boolean tagged;
    private float zeroLinearSpeedThreshold;
    private float maxLinearAcceleration;
    private float maxAngularAcceleration;
    private float maxAngularSpeed;
    /**
     * temp velocity
     */
    private final Vector2 velocity;
    private final FollowPath<Vector2, LinePath.LinePathParam> followPath;

    /**
     * @param game        the game this character is in
     * @param texturePaths the path to this characters texture
     * @param world       the world this characters body is in
     * @param map         the map this character is on
     * @param posX        the x position in tiles
     * @param posY        the y position in tiles
     */

    public Enemy(SteampunkGame game, TexturePaths texturePaths, World world, Map map, float posX, float posY, Movable target) {
        super(game, texturePaths, world, map, posX, posY);
        this.moveSpeed = 5f;
        this.steeringOutput = new SteeringAcceleration<Vector2>(new Vector2());
        this.tagged = false;
        this.zeroLinearSpeedThreshold = .01f;
        this.maxLinearAcceleration = 10000f;
        this.maxAngularAcceleration = 10000f;
        this.maxAngularSpeed = 20f;
        this.velocity = new Vector2();
        this.path = new LinePath<Vector2>(map.getWaypoints(this, target));
        this.steeringBehavior = new PrioritySteering<Vector2>(this);
        this.followPath = new FollowPath<Vector2, LinePath.LinePathParam>(this, this.path, 1);
        this.steeringBehavior.add(this.followPath);
//        CentralRayWithWhiskersConfiguration<Vector2> raycastConfig = new CentralRayWithWhiskersConfiguration<Vector2>(this, 1f, .5f, MathUtils.PI * .25f);
//        this.steeringBehavior.add(new RaycastObstacleAvoidance<Vector2>(this, raycastConfig, new RaycastCollision(world)));
//        this.steeringBehavior.add(new Seek<Vector2>(this, target));
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
        if (this.steeringBehavior != null) {
            this.steeringBehavior.calculateSteering(this.steeringOutput);
            this.applySteering();
        }
    }

    private void applySteering() {
//        this.velocity.set(0, 0);
        if (this.steeringOutput.linear.x > 0) {
            this.velocity.x = 1;
        } else if (this.steeringOutput.linear.x < 0) {
            this.velocity.x = -1;
        }
        if (this.steeringOutput.linear.y > 0) {
            this.velocity.y = 1;
        } else if (this.steeringOutput.linear.y < 0) {
            this.velocity.y = -1;
        }
        this.setVelocity(this.velocity.nor());
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
//            this.followPath.setPathOffset(this.path.getLength());
        }
    }

    public void drawPath(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.RED);
        for (LinePath.Segment<Vector2> segment : this.path.getSegments()) {
            shapeRenderer.line(segment.getBegin(), segment.getEnd());
        }
    }
}
