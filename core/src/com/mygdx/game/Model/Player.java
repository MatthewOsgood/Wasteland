package com.mygdx.game.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.FilePaths;

public class Player {
    private final Texture texture;
    private Map map;
    private float posX;
    private float posY;
    public static final float WIDTH = 8f;
    public static final float HEIGHT = 4.5f;
    private static final float MOVE_SPEED = 100;

    public Player(FilePaths spriteAssetPath) {
        this.texture = new Texture(Gdx.files.internal(spriteAssetPath.getPath()));
        this.posX = 0f;
        this.posY = 0f;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    /**
     * translates this players sprite by the given amount based on MOVE_SPEED
     *
     * @param x x axis units
     * @param y y axis units
     */
    public void translate(float x, float y) {
        this.posX += x * MOVE_SPEED;
        this.posY += y * MOVE_SPEED;
    }


    /**
     * draws this player
     *
     * @param batch the {@link SpriteBatch} that this player will be drawn on
     */
    public void draw(SpriteBatch batch) {
        batch.draw(this.texture, this.posX, this.posY, WIDTH, HEIGHT);
    }

    public void dispose() {
        this.texture.dispose();
    }

    /**
     * @return a vector representing the center of this player
     */
    public Vector2 getCenter() {
        return new Vector2(this.posX + WIDTH / 2, this.posY + HEIGHT / 2);
    }
}
