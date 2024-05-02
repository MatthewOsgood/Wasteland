package com.mygdx.game.Model.Items.Weapons;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Model.Entities.Projectile;
import com.mygdx.game.Model.Map;
import com.mygdx.game.Wasteland;

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
    Projectile<?> makeProjectile(Vector2 position, Vector2 velocity, Wasteland game, Map map, World world);

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
