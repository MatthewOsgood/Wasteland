package com.mygdx.game.Model;

import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.SteampunkGame;
import com.mygdx.game.enums.TexturePaths;

public class TestEnemy extends Enemy {
    /**
     * @param game        the game this character is in
     * @param texturePaths the path to this characters texture
     * @param world       the world this characters body is in
     * @param map         the map this character is on
     * @param posX        the x position in tiles
     * @param posY        the y position in tiles
     * @param width       the width in tiles
     * @param height      the height in tiles
     * @param moveSpeed   the movement speed in tiles/second
     */
    public TestEnemy(SteampunkGame game, TexturePaths texturePaths, World world, Map map, float posX, float posY, float width, float height, float moveSpeed) {
        super(game, texturePaths, world, map, posX, posY, width, height, moveSpeed);
    }

    public TestEnemy(SteampunkGame game, TexturePaths texturePaths, World world, Map map, float posX, float posY, float width, float height) {
        super(game, texturePaths, world, map, posX, posY, width, height);
    }

    public TestEnemy(SteampunkGame game, TexturePaths texturePaths, World world, Map map, float posX, float posY, Player target) {
        super(game, texturePaths, world, map, posX, posY, target);
    }
}
