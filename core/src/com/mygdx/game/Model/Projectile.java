package com.mygdx.game.Model;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.SteampunkGame;
import com.mygdx.game.enums.TexturePaths;

public abstract class Projectile<T extends Projectile<T>> extends Movable<T> {

    private final int damage;

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
     * @param damage       the amount of damage this projectile deals
     */
    public Projectile(SteampunkGame game, Map map, World world, TexturePaths texturePaths, float posX, float posY, float width, float height, float moveSpeed, int health, int damage) {
        super(game, map, world, texturePaths, posX, posY, width, height, moveSpeed, health);
        this.damage = damage;
    }

    public static abstract class Builder<U extends Projectile<U>, V extends Builder<U, V>> extends Movable.Builder<U, V> {

        protected int damage = 1;

        /**
         * default 1
         *
         * @param damage the amount of damage this projectile does
         * @return this for chaining
         */
        public V damage(int damage) {
            this.damage = damage;
            return this.self();
        }
    }

    @Override
    public void update() {
        this.angle = MathUtils.atan2(this.body.getLinearVelocity().y, this.body.getLinearVelocity().x);
        this.body.setTransform(this.getPosition(), this.angle);
    }

    public int getDamage() {
        return this.damage;
    }
}

