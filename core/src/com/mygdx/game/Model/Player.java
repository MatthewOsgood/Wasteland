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
 * represents a playable character
 */
public class Player extends Character {

    public boolean canInteract;
    private NPC interactTarget;

    public Player(SteampunkGame game, TexturePaths texturePaths, World world, Map map, float x, float y) {
        super(game, texturePaths, world, map, x, y);
        this.canInteract = false;
    }

    @Override
    protected Projectile makeProjectile() {
        return new Bullet(this.game, TexturePaths.BULLET, this.world, this.map, this.body.getPosition(), .5f, .5f, this);
    }

    /**
     * @param interactTarget the NPC this player can interact with
     */
    public void setInteract(NPC interactTarget) {
        this.interactTarget = interactTarget;
        this.canInteract = true;
    }

    public ConversationPaths getConversationPath() {
        return this.interactTarget.getConversationPath();
    }

    /**
     * creates the body for this Character
     *
     * @return the body to be set as this character's
     */
    @Override
    protected Body createBody(float posX, float posY) {
        Body b = this.createBox(posX, posY, width, height, BodyDef.BodyType.DynamicBody, false);
        Filter filter = b.getFixtureList().get(0).getFilterData();
        filter.categoryBits = BitFilters.PLAYER;
        filter.maskBits = BitFilters.ENEMY | BitFilters.OBSTACLE | BitFilters.ENEMY_PROJECTILE | BitFilters.INTERACT_SENSOR;
        b.getFixtureList().get(0).setFilterData(filter);
        return b;
    }



}
