package com.mygdx.game.Model.Entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Model.Items.Weapons.Weapon;
import com.mygdx.game.Model.Map;
import com.mygdx.game.Wasteland;
import com.mygdx.game.enums.BitFilters;
import com.mygdx.game.enums.ConversationPaths;
import com.mygdx.game.enums.TexturePaths;

/**
 * represents a npc in the overworld
 */
public class NPC extends Character<NPC> {
    private final ConversationPaths conversationPath;
    private final Body reachBox;

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
     */
    public NPC(Wasteland game, Map map, World world, TexturePaths texturePaths, float posX, float posY, float width, float height, float moveSpeed, int health, Weapon weapon, ConversationPaths conversationPath) {
        super(game, map, world, texturePaths, posX, posY, width, height, moveSpeed, health, weapon);
        this.conversationPath = conversationPath;
        this.reachBox = this.createReachBox(posX, posY);
    }

    public static class Builder extends Character.Builder<NPC, Builder> {

        private ConversationPaths conversationPath;

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
        public NPC build(Wasteland game, Map map, World world) {
            return new NPC(game, map, world, this.texturePath, this.posX, this.posY, this.width, this.height, this.moveSpeed, this.health, this.weapon, this.conversationPath);
        }

        public Builder set(ConversationPaths conversationPath) {
            this.conversationPath = conversationPath;
            return this;
        }
    }

    /**
     * creates the body for this Character
     *
     * @return the body to be set as this character's
     */
    @Override
    protected Body createBody(float posX, float posY) {
        Body b = this.createBox(posX, posY, this.width, this.height, BodyDef.BodyType.StaticBody, false);
        Filter filter = b.getFixtureList().get(0).getFilterData();
        filter.categoryBits = BitFilters.NPC;
        filter.maskBits = BitFilters.ENEMY | BitFilters.OBSTACLE | BitFilters.ENEMY_PROJECTILE;
        b.getFixtureList().get(0).setFilterData(filter);
        return b;
    }

    public ConversationPaths getConversationPath() {
        return this.conversationPath;
    }

    /**
     * creates the reachBox for this Character
     *
     * @return the reach box for this character
     */
    Body createReachBox(float posX, float posY) {
        Body b = this.createBox(posX, posY, this.width + .5f, this.height + .5f, BodyDef.BodyType.KinematicBody, true);
        Filter filter = b.getFixtureList().get(0).getFilterData();
        filter.categoryBits = BitFilters.INTERACT_SENSOR;
        filter.maskBits = BitFilters.PLAYER;
        b.getFixtureList().get(0).setFilterData(filter);
        return b;
    }

}
