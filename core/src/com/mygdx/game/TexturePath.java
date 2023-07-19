package com.mygdx.game;

public enum TexturePath {
    TEST_MAP("testMap.png"),
    TEST_CHARACTER("testCharacter.png"),
    TEST_NPC("testNPC.png");

    private final String path;
    TexturePath(String path){
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
