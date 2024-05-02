package com.mygdx.game.Model.Items.Weapons;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Model.Entities.Bullet;
import com.mygdx.game.Model.Entities.Projectile;
import com.mygdx.game.Model.Map;
import com.mygdx.game.Wasteland;

public class StarterGun extends BasicGun {
    public StarterGun(float attackCooldown) {
        super(attackCooldown);
    }

    /**
     * note that the returned projectile has no velocity or position
     *
     * @return a new instance of the projectile this weapon shoots
     */
    @Override
    public Projectile<?> makeProjectile(Vector2 position, Vector2 velocity, Wasteland game, Map Map, World world) {
        this.resetCooldown();
        Projectile<?> p = new Bullet.Builder().playerBullet().pos(position.x, position.y).build(game, Map, world);
        p.setVelocity(velocity);
        return p;
    }
}
