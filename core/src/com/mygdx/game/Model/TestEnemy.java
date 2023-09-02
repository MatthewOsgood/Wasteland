package com.mygdx.game.Model;

import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.SteampunkGame;
import com.mygdx.game.enums.TexturePaths;

public class TestEnemy extends Enemy<TestEnemy> {


    /**
     * @param game           the game this Movable is in
     * @param map            the map this Movable is one
     * @param world          the world this Movables body is in
     * @param texturePaths   the path to this characters texture
     * @param posX           the x position in tiles
     * @param posY           the y position in tiles
     * @param width          the width in tiles
     * @param height         the height in tiles
     * @param moveSpeed      the movement speed in tiles/second
     * @param health         the health of this
     * @param attackCooldown the time between attacks
     *
     * @param target         the thing this enemy will target
     */
    public TestEnemy(SteampunkGame game, Map map, World world, TexturePaths texturePaths, float posX, float posY, float width, float height, float moveSpeed, int health, float attackCooldown, Movable<? extends Movable<?>> target) {
        super(game, map, world, texturePaths, posX, posY, width, height, moveSpeed, health, attackCooldown, target);
    }

    @Override
    protected Projectile<?> makeProjectile() {
        return new Bullet.Builder().playerBullet(this.getPosition()).build(this.game, this.map, this.world);
    }

    public static class Builder extends Enemy.Builder<TestEnemy, Builder> {

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
        public TestEnemy build(SteampunkGame game, Map map, World world) {
            return new TestEnemy(game, map, world, this.texturePath, this.posX, this.posY, this.width, this.height, this.moveSpeed, this.health, this.attackCooldown, this.target);
        }
    }
}
