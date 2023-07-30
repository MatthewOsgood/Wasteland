package com.mygdx.game.enums;

/**
 * represents all possible characters and their {@link TexturePath}
 */
public enum Characters {

    NULL(TexturePath.NULL, false),
    PLAYER(TexturePath.PLAYER, true),
    TEST_NPC(TexturePath.TEST_NPC, false);

    private final TexturePath texturePath;
    private final boolean isPlayer;
    Characters(TexturePath texturePath, boolean isPlayer) {
        this.texturePath = texturePath;
        this.isPlayer = isPlayer;
    }

    public TexturePath AssetPath() {
        return texturePath;
    }

    public boolean isPlayer() {
        return isPlayer;
    }
}
