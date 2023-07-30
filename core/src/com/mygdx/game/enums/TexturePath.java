package com.mygdx.game.enums;

public enum TexturePath {
    NULL(null),
    PLAYER("testCharacter.png"),
    TEST_NPC("testNPC.png"),
    LOGO("logo.png"),
    BULLET("bullet.png");

    private final String path;
    TexturePath(String path){
        this.path = path;
    }

    public String getPath() {
        return "Textures/" + path;
    }
}
