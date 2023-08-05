package com.mygdx.game.Model;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.SteampunkGame;
import com.mygdx.game.enums.TexturePaths;

public abstract class Projectile extends Movable {

    protected final Character parent;

    public Projectile(SteampunkGame game, TexturePaths texturePaths, World world, Map map, Vector2 position, float width, float height, Character parent) {
        super(game, texturePaths, world, map, position.y, width, height, position.x);
        this.parent = parent;
    }

    @Override
    public void update() {
        this.angle = MathUtils.atan2(this.body.getLinearVelocity().y, this.body.getLinearVelocity().x);
        this.body.setTransform(this.getPosition(), this.angle);
    }

    public void setToDestroy() {
        this.map.setToDestroy(this);
    }

}

