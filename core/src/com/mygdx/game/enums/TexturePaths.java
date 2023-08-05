package com.mygdx.game.enums;

public enum TexturePaths {
    PLAYER("player.png"),
    TEST_NPC("testNPC.png"),
    LOGO("logo.png"),
    BULLET("bullet.png"),
    TEST_ENEMY("testEnemy.png");

    private final String path;
    TexturePaths(String path){
        this.path = path;
    }

    public String getPath() {
        return "Textures/" + path;
    }
}
