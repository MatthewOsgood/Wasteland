package com.mygdx.game.Model.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Model.Map;
import com.mygdx.game.Model.Items.Weapon;
import com.mygdx.game.Wasteland;
import com.mygdx.game.enums.TexturePaths;

public abstract class Character<T extends Character<T>> extends Movable<T> {

    private Weapon weapon;


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
     */
    public Character(Wasteland game, Map map, World world, TexturePaths texturePaths, float posX, float posY, float width, float height, float moveSpeed, int health) {
        super(game, map, world, texturePaths, posX, posY, width, height, moveSpeed, health);
    }

    public static abstract class Builder<U extends Character<U>, V extends Builder<U, V>> extends Movable.Builder<U, V> {

        protected Weapon weapon;

        public V weapon(Weapon weapon) {
            this.weapon = weapon;
            return this.self();
        }
    }

    /**
     * only fires if the weapon is off cooldown
     *
     * @param touchPoint the point to be shot at
     */
    public void shoot(Vector3 touchPoint) {
        if (this.weapon.canShoot()) {
            Projectile<?> p = this.weapon.makeProjectile();
            p.setVelocity(new Vector2(touchPoint.x, touchPoint.y).sub(this.getPosition()).nor());
            p.update();
            this.map.addProjectile(p);
        }
    }

    @Override
    public void update() {
        this.weapon.update(Gdx.graphics.getDeltaTime());
    }
}
