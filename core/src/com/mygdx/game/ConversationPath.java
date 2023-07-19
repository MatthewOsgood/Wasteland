package com.mygdx.game;

public enum ConversationPath {

    TEST("TestConvo.txt");

    private final String path;

    ConversationPath(String path) {
        this.path = path;
    }

    public String Path() {
        return this.path;
    }
}
