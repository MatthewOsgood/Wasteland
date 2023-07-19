package com.mygdx.game.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.TexturePath;

public abstract class Character implements Entity {
    protected final Texture texture;
    private final Rectangle reachBox;
    protected float posX;
    protected float posY;
    public final float width;
    public final float height;
    private final float moveSpeed;


    public Character(TexturePath texturePath, float posX, float posY, float width, float height, float moveSpeed) {
        this.texture = new Texture(Gdx.files.internal(texturePath.getPath()));
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.moveSpeed = moveSpeed;
        this.reachBox = new Rectangle(posX, posY, width * 1.25f, height * 1.25f);
    }

    public Character(TexturePath texturePath, float posX, float posY, float width, float height) {
        this(texturePath, posX, posY, width, height, 100);
    }

    public Character(TexturePath texturePath, float posX, float posY) {
        this(texturePath, posX, posY, 8f, 4.5f);
    }

    public Character(TexturePath texturePath) {
        this(texturePath, 0, 0);
    }

    @Override
    public void translate(float x, float y) {
        this.posX += x * moveSpeed;
        this.posY += y * moveSpeed;
        this.reachBox.setCenter(this.getCenter());
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
        this.reachBox.setPosition(x, y);
    }

    @Override
    public boolean canInteract(Character that) {
        return this.reachBox.overlaps(that.reachBox);
    }
}
