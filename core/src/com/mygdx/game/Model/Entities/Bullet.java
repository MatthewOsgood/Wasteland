package com.mygdx.game.Model.Entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Model.Map;
import com.mygdx.game.Wasteland;
import com.mygdx.game.enums.BitFilters;
import com.mygdx.game.enums.TexturePaths;

public class Bullet extends Projectile<Bullet> {

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
    public Bullet(Wasteland game, Map map, World world, TexturePaths texturePaths, float posX, float posY, float width, float height, float moveSpeed, int health, int damage) {
        super(game, map, world, texturePaths, posX, posY, width, height, moveSpeed, health, damage);
    }

    public static class Builder extends Projectile.Builder<Bullet, Builder> {

        /**
         * when using this method the texturePath must be already set
         * with {@link #set(TexturePaths)} or a template
         *
         * @param game  the game
         * @param map   the map
         * @param world the world
         * @return the final product
         */
        @Override
        public Bullet build(Wasteland game, Map map, World world) {
            return new Bullet(game, map, world, this.texturePath, this.posX, this.posY, this.width, this.height, this.moveSpeed, this.health, this.damage);
        }

        /**
         * may use {@link Builder#build(Wasteland, Map, World)}
         *
         * @return this for chaining
         */
        public Builder playerBullet() {
            this.set(TexturePaths.BULLET)
                    .damage(25)
                    .moveSpeed(5)
                    .size(.5f, .5f);
            return this;
        }
    }

    /**
     * creates the body for this Character
     *
     * @param posX the x position
     * @param posY the t position
     * @return the body to be set as this character's
     */
    @Override
    protected Body createBody(float posX, float posY) {
        Body body = this.createBox(posX, posY, this.width, this.height, BodyDef.BodyType.DynamicBody, false);
        Filter filter = body.getFixtureList().get(0).getFilterData();
        filter.categoryBits = BitFilters.PLAYER_PROJECTILE;
        filter.maskBits = BitFilters.ENEMY | BitFilters.OBSTACLE | BitFilters.ENEMY_PROJECTILE;
        body.getFixtureList().get(0).setFilterData(filter);
        return body;
    }
}
