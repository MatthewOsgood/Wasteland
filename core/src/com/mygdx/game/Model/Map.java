package com.mygdx.game.Model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class Map {
    private final Player player;
    private final Texture texture;
    private final Array<NPC> npcs;

    public Map(Texture texture, Player player) {
        this.player = player;
        this.texture = texture;
        this.npcs = new Array<NPC>();
    }

    /**
     * draws this map and everything on it
     *
     * @param batch the {@link SpriteBatch} this map will be drawn on
     */
    public void draw(SpriteBatch batch, BitmapFont font) {
        batch.draw(this.texture, 0, 0);
        this.player.draw(batch);
        for (NPC npc : this.npcs) {
            npc.draw(batch);
            if (this.player.canInteract(npc)) {
                npc.drawInteractPrompt(batch, font);
            }
        }
    }

    public void dispose() {
        this.texture.dispose();
    }

    public void addNPC(NPC npc) {
        this.npcs.add(npc);
    }
}
