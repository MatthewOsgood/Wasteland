package com.mygdx.game.Model.Items;

import com.mygdx.game.Model.Entities.Projectile;

public interface Weapon {

    /**
     * @return if the weapon can be fired
     */
    boolean canShoot();

    /**
     * note that the returned projectile has no velocity or position
     *
     * @return a new instance of the projectile this weapon shoots
     */
    Projectile<?> makeProjectile();

    /**
     * must be called every frame
     *
     * @param delta the amount of time that has passed between frames
     */
    void update(float delta);

    /**
     * resets time since last attack to 0;
     */
    void resetCooldown();
}
