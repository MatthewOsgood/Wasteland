package com.mygdx.game.Model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.SteampunkGame;
import com.mygdx.game.enums.TexturePath;

public abstract class Character extends Movable {

    /**
     * @param game the game this character is in
     * @param texturePath the path to this characters texture
     * @param world the world this characters body is in
     * @param posX the x position in tiles
     * @param posY the y position in tiles
     * @param width the width in tiles
     * @param height the height in tiles
     * @param moveSpeed the movement speed in tiles/second
     */
    public Character(SteampunkGame game, TexturePath texturePath, World world, float posX, float posY, float width, float height, float moveSpeed) {
        super(game, texturePath, world, posX, posY, width, height, moveSpeed);
    }

    public Character(SteampunkGame game, TexturePath texturePath, World world, float posX, float posY, float width, float height) {
        super(game, texturePath, world, posX, posY, width, height, 3);
    }

    public Character(SteampunkGame game, TexturePath texturePath, World world, float posX, float posY) {
        super(game, texturePath, world, posX, posY, 1f, 1f);
    }

    /**
     * @param touchPoint the point to be shot at
     */
    public void shoot(Vector3 touchPoint) {
        Projectile p = this.makeProjectile();
        p.setVelocity(new Vector2(touchPoint.x, touchPoint.y).sub(this.getCenter()).nor());
        this.map.addProjectile(p);
    }

    protected abstract Projectile makeProjectile();

    @Override
    public void update() {
    }
}
