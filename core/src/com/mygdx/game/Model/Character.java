package com.mygdx.game.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Wasteland;
import com.mygdx.game.enums.TexturePaths;

public abstract class Character<T extends Character<T>> extends Movable<T> {

    /**
     * time in seconds between attacks
     */
    protected final float ATTACK_COOLDOWN;
    private float attackTime;

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
     * @param attackCooldown the time between attacks
     */
    public Character(Wasteland game, Map map, World world, TexturePaths texturePaths, float posX, float posY, float width, float height, float moveSpeed, int health, float attackCooldown) {
        super(game, map, world, texturePaths, posX, posY, width, height, moveSpeed, health);
        this.ATTACK_COOLDOWN = attackCooldown;
        this.attackTime = attackCooldown;
    }

    public static abstract class Builder<U extends Character<U>, V extends Builder<U, V>> extends Movable.Builder<U, V> {

        protected float attackCooldown = 1f;

        public V attackCooldown(float attackCooldown) {
            this.attackCooldown = attackCooldown;
            return this.self();
        }
    }

    /**
     * only fires if {@link #ATTACK_COOLDOWN} time has been met
     *
     * @param touchPoint the point to be shot at
     */
    public void shoot(Vector3 touchPoint) {
        if (this.attackTime >= this.ATTACK_COOLDOWN) {
            this.attackTime = 0f;
            Projectile<?> p = this.makeProjectile();
            p.setVelocity(new Vector2(touchPoint.x, touchPoint.y).sub(this.getPosition()).nor());
            p.update();
            this.map.addProjectile(p);
        }
    }

    protected abstract Projectile<?> makeProjectile();

    @Override
    public void update() {
        this.attackTime += Gdx.graphics.getDeltaTime();
    }
}
