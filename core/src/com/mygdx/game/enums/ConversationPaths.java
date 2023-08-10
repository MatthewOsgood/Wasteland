package com.mygdx.game.enums;

public enum ConversationPaths {

    TEST("testConvo.txt");

    private final String path;

    ConversationPaths(String path) {
        this.path = path;
    }

    public String Path() {
        return "Conversations/" + this.path;
    }
}
