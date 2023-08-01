package com.mygdx.game.Model;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.SteampunkGame;
import com.mygdx.game.enums.BitFilters;
import com.mygdx.game.enums.ConversationPath;
import com.mygdx.game.enums.TexturePath;

/**
 * represents a npc in the overworld
 */
public class NPC extends Character {
    private final ConversationPath conversationPath;
    private final Body reachBox;

    public NPC(SteampunkGame game, TexturePath texturePath, World world, Map map, float x, float y, ConversationPath conversationPath) {
        super(game, texturePath, world, map, x, y);
        this.conversationPath = conversationPath;
        this.reachBox = this.createReachBox(x, y);
    }

    /**
     * creates the body for this Character
     *
     * @return the body to be set as this character's
     */
    @Override
    protected Body createBody(float posX, float posY) {
        Body b = this.createBox(posX, posY, width, height, BodyDef.BodyType.StaticBody, false);
        Filter filter = b.getFixtureList().get(0).getFilterData();
        filter.categoryBits = BitFilters.NPC;
        filter.maskBits = BitFilters.ENEMY | BitFilters.OBSTACLE | BitFilters.ENEMY_PROJECTILE;
        b.getFixtureList().get(0).setFilterData(filter);
        return b;
    }

    public ConversationPath getConversationPath() {
        return conversationPath;
    }

    /**
     * creates the reachBox for this Character
     *
     * @return the reach box for this character
     */
    Body createReachBox(float posX, float posY) {
        Body b = this.createBox(posX, posY, width * 1.5f, height * 1.5f, BodyDef.BodyType.KinematicBody, true);
        Filter filter = b.getFixtureList().get(0).getFilterData();
        filter.categoryBits = BitFilters.INTERACT_SENSOR;
        filter.maskBits = BitFilters.PLAYER;
        b.getFixtureList().get(0).setFilterData(filter);
        return b;
    }

    @Override
    protected Projectile makeProjectile() {
        return new Bullet(this.game, TexturePath.BULLET, this.world, this.map, this.getCenter(), .5f, .5f, this);
    }
}
