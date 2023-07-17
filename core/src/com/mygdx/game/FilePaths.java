package com.mygdx.game;

public enum FilePaths {
    TEST_MAP("testMap.png"),
    TEST_CHARACTER("testCharacter.png");

    private final String path;
    FilePaths(String path){
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
