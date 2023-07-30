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
 * represents a playable character
 */
public class Player extends Character {

    public boolean canInteract;
    private NPC interactTarget;

    public Player(SteampunkGame game, TexturePath texturePath, World world, float x, float y) {
        super(game, texturePath, world, x, y);
        this.canInteract = false;
    }

    @Override
    protected Projectile makeProjectile() {
        return new Bullet(this.game, TexturePath.BULLET, this.world, this.body.getPosition(), .5f, .5f, this);
    }

    /**
     * @param interactTarget the NPC this player can interact with
     */
    public void setInteract(NPC interactTarget) {
        this.interactTarget = interactTarget;
        this.canInteract = true;
    }

    public ConversationPath getConversationPath() {
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
