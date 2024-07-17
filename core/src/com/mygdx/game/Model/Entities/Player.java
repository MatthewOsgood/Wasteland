package com.mygdx.game.Model.Entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Model.Items.Inventory;
import com.mygdx.game.Model.Items.PlayerInventory;
import com.mygdx.game.Model.Items.Weapons.StarterGun;
import com.mygdx.game.Model.Items.Weapons.Weapon;
import com.mygdx.game.Model.Map;
import com.mygdx.game.Wasteland;
import com.mygdx.game.enums.BitFilters;
import com.mygdx.game.enums.ConversationPaths;
import com.mygdx.game.enums.TexturePaths;

/**
 * represents a playable character
 */
public class Player extends Character<Player> {

    public boolean canInteract;
    private NPC interactTarget;

    private static final Inventory inventory = new PlayerInventory(16);

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
     */
    public Player(Wasteland game, Map map, World world, TexturePaths texturePaths, float posX, float posY, float width, float height, float moveSpeed, int health, Weapon weapon) {
        super(game, map, world, texturePaths, posX, posY, width, height, moveSpeed, health, weapon);
    }

    public static class Builder extends Character.Builder<Player, Builder> {

        /**
         * when using this method the texturePath must be already set
         * with {@link #set(TexturePaths)} or {@link #player() template}
         *
         * @param game  the game
         * @param map   the map
         * @param world the world
         * @return the final product
         */
        @Override
        public Player build(Wasteland game, Map map, World world) {
            return new Player(game, map, world, this.texturePath, this.posX, this.posY, this.width, this.height, this.moveSpeed, this.health, this.weapon);
        }

        /**
         * may use {@link Builder#build(Wasteland, Map, World)}
         *
         * @return this for chaining
         */
        public Builder player() {
            this.pos(15.5f, 15.5f)
                    .set(TexturePaths.PLAYER)
                    .health(100)
                    .weapon(new StarterGun(1f))
                    .size(1.75f, 1.75f);
            return this;
        }
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
        Body b = this.createBox(posX, posY, this.width * (.6f), this.height, BodyDef.BodyType.DynamicBody, false);
        Filter filter = b.getFixtureList().get(0).getFilterData();
        filter.categoryBits = BitFilters.PLAYER;
        filter.maskBits = BitFilters.ENEMY | BitFilters.OBSTACLE | BitFilters.ENEMY_PROJECTILE | BitFilters.INTERACT_SENSOR;
        b.getFixtureList().get(0).setFilterData(filter);
        return b;
    }

}
