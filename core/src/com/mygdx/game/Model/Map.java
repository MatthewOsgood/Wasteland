package com.mygdx.game.Model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Map {
    private final Player player;
    private final Texture texture;

    public Map(Texture texture, Player player) {
        this.player = player;
        this.texture = texture;

        this.player.setMap(this);
    }

    /**
     * draws this map
     *
     * @param batch the {@link SpriteBatch} this map will be drawn on
     */
    public void draw(SpriteBatch batch) {
        batch.draw(this.texture, 0, 0);
    }

    public void dispose() {
        this.texture.dispose();
    }
}
