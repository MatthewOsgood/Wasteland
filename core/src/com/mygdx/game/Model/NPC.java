package com.mygdx.game.Model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.AssetPath;

/**
 * represents a npc in the overworld
 */
public class NPC extends Character {
    private static final String INTERACT_PROMPT = "E";

    public NPC(AssetPath assetPath) {
        super(assetPath);
    }

    public NPC(AssetPath assetPath, float x, float y) {
        super(assetPath, x, y);
    }

    /**
     * draws text above this NPC prompting the player to interact with it
     *
     * @param batch the batch the prompt will be drawn on
     * @param font the font used to draw
     */
    public void drawInteractPrompt(SpriteBatch batch, BitmapFont font) {
        Vector2 center = this.getCenter();
        font.getData().setScale(.2f);
        font.setColor(Color.BLACK);
        font.draw(batch, INTERACT_PROMPT, center.x - 1f, center.y + this.height + 1);
    }
}
