package com.mygdx.game.enums;

/**
 * represents all possible characters and their {@link TexturePaths}
 */
public enum Characters {

    PLAYER(TexturePaths.PLAYER, true),
    TEST_NPC(TexturePaths.TEST_NPC, false);

    private final TexturePaths texturePaths;
    private final boolean isPlayer;
    Characters(TexturePaths texturePaths, boolean isPlayer) {
        this.texturePaths = texturePaths;
        this.isPlayer = isPlayer;
    }

    public TexturePaths AssetPath() {
        return texturePaths;
    }

    public boolean isPlayer() {
        return isPlayer;
    }
}
