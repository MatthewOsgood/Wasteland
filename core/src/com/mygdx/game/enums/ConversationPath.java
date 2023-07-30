package com.mygdx.game.enums;

public enum ConversationPath {

    TEST("Conversations/testConvo.txt");

    private final String path;

    ConversationPath(String path) {
        this.path = path;
    }

    public String Path() {
        return this.path;
    }
}
