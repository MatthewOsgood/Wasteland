package com.mygdx.game.View.Dialogue;

import com.mygdx.game.Characters;

/**
 * record of a single line of dialogue and its speaker
 */
public class DialogueLine {
    private final Characters speaker;
    private final String dialogue;

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
