package com.mygdx.game.Model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.SteampunkGame;
import com.mygdx.game.enums.BitFilters;
import com.mygdx.game.enums.TexturePath;

public class Bullet extends Projectile {

    public Bullet(SteampunkGame game, TexturePath texturePath, World world, Vector2 position, float width, float height, Character parent) {
        super(game, texturePath, world, position, width, height, parent);
        this.moveSpeed = 5;
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
        filter.categoryBits = BitFilters.FRIENDLY_PROJECTILE;
        filter.maskBits = BitFilters.ENEMY | BitFilters.OBSTACLE;
        body.getFixtureList().get(0).setFilterData(filter);
        return body;
    }
}
