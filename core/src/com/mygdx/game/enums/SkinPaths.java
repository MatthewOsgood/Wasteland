package com.mygdx.game.enums;

public enum SkinPaths {
    TEST("uiskin.json");

    private final String path;
    SkinPaths(String path) {
        this.path = path;
    }

    public String getPath() {
        return "Skins/" + path;
    }
}
