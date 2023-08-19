package com.mygdx.game.Model.ai;

import com.badlogic.gdx.ai.utils.Collision;
import com.badlogic.gdx.ai.utils.Ray;
import com.badlogic.gdx.ai.utils.RaycastCollisionDetector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.World;

public class RaycastCollision implements RaycastCollisionDetector<Vector2>, RayCastCallback {
    private final World world;
    private final Collision<Vector2> collision;
    private boolean collided;


    public RaycastCollision(World world) {
        this.world = world;
        this.collided = false;
        this.collision = new Collision<Vector2>(new Vector2(), new Vector2());
    }

    /**
     * Casts the given ray to test if it collides with any objects in the game world.
     *
     * @param ray the ray to cast.
     * @return {@code true} in case of collision; {@code false} otherwise.
     */
    @Override
    public boolean collides(Ray<Vector2> ray) {
        return this.findCollision(this.collision, ray);
    }

    /**
     * Find the closest collision between the given input ray and the objects in the game world. In case of collision,
     * {@code outputCollision} will contain the collision point and the normal vector of the obstacle at the point of collision.
     *
     * @param outputCollision the output collision.
     * @param inputRay        the ray to cast.
     * @return {@code true} in case of collision; {@code false} otherwise.
     */
    @Override
    public boolean findCollision(Collision<Vector2> outputCollision, Ray<Vector2> inputRay) {
        this.collided = false;
        if (!inputRay.start.epsilonEquals(inputRay.end, MathUtils.FLOAT_ROUNDING_ERROR)) {
            this.world.rayCast(this, inputRay.start, inputRay.end);
        }
        return this.collided;
    }

    /**
     * Called for each fixture found in the query. You control how the ray cast proceeds by returning a float: return -1: ignore
     * this fixture and continue return 0: terminate the ray cast return fraction: clip the ray to this point return 1: don't clip
     * the ray and continue.
     * <p>
     * The {@link Vector2} instances passed to the callback will be reused for future calls so make a copy of them!
     *
     * @param fixture  the fixture hit by the ray
     * @param point    the point of initial intersection
     * @param normal   the normal vector at the point of intersection
     * @param fraction the fraction of distance the ray has traveled
     * @return -1 to filter, 0 to terminate, fraction to clip the ray for closest hit, 1 to continue
     **/
    @Override
    public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
        this.collided = true;
        this.collision.set(point, normal);
        return 0;
    }
}
