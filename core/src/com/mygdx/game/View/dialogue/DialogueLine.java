package com.mygdx.game.View.dialogue;

import com.mygdx.game.enums.Characters;

/**
 * record of a single line of dialogue and its speaker
 */
public class DialogueLine {
    private Characters speaker;
    private String dialogue;

    /**
     * for JSON
     */
    public DialogueLine() {
    }

    public DialogueLine(Characters speaker, String dialogue) {
        this.speaker = speaker;
        this.dialogue = dialogue;
    }

    public Characters getSpeaker() {
        return speaker;
    }

    public String getDialogue() {
        return dialogue;
    }
}
