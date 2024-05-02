package com.mygdx.game.Model.Items.Weapons;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Model.Entities.Projectile;
import com.mygdx.game.Model.Map;
import com.mygdx.game.Wasteland;

/**
 * use in place of null
 */
public class NullWeapon implements Weapon {
    /**
     * @return if the weapon can be fired
     */
    @Override
    public boolean canShoot() {
        return false;
    }

    /**
     * note that the returned projectile has no velocity or position
     *
     * @param game
     * @param map
     * @param world
     * @return a new instance of the projectile this weapon shoots
     */
    @Override
    public Projectile<?> makeProjectile(Vector2 position, Vector2 velocity, Wasteland game, Map map, World world) {
        throw new IllegalStateException("Trying to shoot from a null weapon");
    }

    /**
     * must be called every frame
     *
     * @param delta the amount of time that has passed between frames
     */
    @Override
    public void update(float delta) {

    }

    /**
     * resets time since last attack to 0;
     */
    @Override
    public void resetCooldown() {

    }
}
