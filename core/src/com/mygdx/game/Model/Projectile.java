package com.mygdx.game.Model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.SteampunkGame;
import com.mygdx.game.enums.TexturePath;

public abstract class Projectile extends Movable {

    protected final Character parent;

    public Projectile(SteampunkGame game, TexturePath texturePath, World world, Vector2 position, float width, float height, Character parent) {
        super(game, texturePath, world, position.x, position.y, width, height);
        this.parent = parent;
        this.angle = 0f;
    }

    @Override
    public void update() {
        this.angle = this.body.getLinearVelocity().angleRad(this.getCenter());
        this.body.setTransform(this.getCenter(), this.angle);
    }

}

