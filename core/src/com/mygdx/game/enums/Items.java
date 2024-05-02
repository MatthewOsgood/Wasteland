package com.mygdx.game.enums;

/**
 * list of all items
 */
public enum Items {
    ;
    private final String name;
    private final TexturePaths texturePath;

    Items(String name, TexturePaths texturePath) {
        this.name = name;
        this.texturePath = texturePath;
    }

    public String Name() {
        return name;
    }


    public TexturePaths TexturePath() {
        return texturePath;
    }
}
