package com.mygdx.game.enums;

public enum TiledMapPath {
    TESTMAP("TiledMaps/test.tmx");

    private final String path;
    TiledMapPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
