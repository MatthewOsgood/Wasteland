package com.mygdx.game.View.dialogue;

import com.badlogic.gdx.utils.Array;

import java.util.Iterator;

/**
 * wrapper for an Array<DialogueLine> for JSON translation
 */
public class Conversation implements Iterable<DialogueLine> {
    private Array<DialogueLine> dialogueLines;

    /**
     * for JSON
     */
    public Conversation() {
    }

    public Conversation(Array<DialogueLine> dialogueLines) {
        this.dialogueLines = dialogueLines;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<DialogueLine> iterator() {
        return this.dialogueLines.iterator();
    }
}
