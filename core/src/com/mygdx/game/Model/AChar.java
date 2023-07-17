package com.mygdx.game.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.AssetPath;

public abstract class AChar implements Character {
    private final Texture texture;
    private Map map;
    private float posX;
    private float posY;
    public final float width;
    public final float height;
    private final float moveSpeed;


    public AChar(AssetPath assetPath, float posX, float posY, float width, float height, float moveSpeed) {
        this.texture = new Texture(Gdx.files.internal(assetPath.getPath()));
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.moveSpeed = moveSpeed;
    }

    public AChar(AssetPath assetPath, float posX, float posY, float width, float height) {
        this(assetPath, posX, posY, width, height, 100);
    }

    public AChar(AssetPath assetPath, float posX, float posY) {
        this(assetPath, posX, posY, 8f, 4.5f, 100);
    }

    public AChar(AssetPath assetPath) {
        this(assetPath, 0, 0);
    }

    @Override
    public void setMap(Map map) {
        this.map = map;
    }
    @Override
    public void translate(float x, float y) {
        this.posX += x * moveSpeed;
        this.posY += y * moveSpeed;
    }
    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(this.texture, this.posX, this.posY, this.width, this.height);
    }
    @Override
    public void dispose() {
        this.texture.dispose();
    }
    @Override
    public Vector2 getCenter() {
        return new Vector2(this.posX + this.width / 2, this.posY + this.height  / 2);
    }

    @Override
    public void setPosition(float x, float y) {
        this.posX = x;
        this.posY = y;
    }
}
