package com.mygdx.game.Model;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.SteampunkGame;
import com.mygdx.game.enums.BitFilters;
import com.mygdx.game.enums.ConversationPaths;
import com.mygdx.game.enums.TexturePaths;

/**
 * represents a npc in the overworld
 */
public class NPC extends Character {
    private final ConversationPaths conversationPaths;
    private final Body reachBox;

    public NPC(SteampunkGame game, TexturePaths texturePaths, World world, Map map, float x, float y, ConversationPaths conversationPaths) {
        super(game, texturePaths, world, map, x, y);
        this.conversationPaths = conversationPaths;
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

    public ConversationPaths getConversationPath() {
        return conversationPaths;
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
        return new Bullet(this.game, TexturePaths.BULLET, this.world, this.map, this.getPosition(), .5f, .5f, this);
    }
}
