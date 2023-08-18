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
     */

    public TestEnemy(SteampunkGame game, TexturePaths texturePaths, World world, Map map, float posX, float posY, Movable target) {
        super(game, texturePaths, world, map, posX, posY, target);
    }


}
