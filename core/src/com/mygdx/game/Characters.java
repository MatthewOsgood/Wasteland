package com.mygdx.game;

/**
 * represents all possible characters and their {@link TexturePath}
 */
public enum Characters {
    TEST_CHARACTER(TexturePath.TEST_CHARACTER, true),
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
